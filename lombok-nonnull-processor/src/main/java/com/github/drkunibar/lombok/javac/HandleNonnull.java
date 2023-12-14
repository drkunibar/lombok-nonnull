/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.drkunibar.lombok.javac;

import static lombok.core.AST.Kind.ARGUMENT;
import static lombok.core.AST.Kind.METHOD;

import java.util.Collection;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.kohsuke.MetaInfServices;
import com.github.drkunibar.lombok.DisableNonNull;
import com.github.drkunibar.lombok.javac.codeprocessor.CodeProcessor;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCAnnotation;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCMethodDecl;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.util.List;
import lombok.NonNull;
import lombok.core.AST;
import lombok.core.AnnotationValues;
import lombok.core.HandlerPriority;
import lombok.core.configuration.ConfigurationKey;
import lombok.javac.JavacAnnotationHandler;
import lombok.javac.JavacNode;
import lombok.javac.handlers.HandleNonNull;
import lombok.javac.handlers.JavacHandlerUtil;

@MetaInfServices
@HandlerPriority(value = 512) // identical with Lomboks 'HandleNonNull'
public class HandleNonnull extends JavacAnnotationHandler<Nonnull> {

    public static final String IGNORE_UNSUPPORTED_TYPES_PROPERTY = "lombok.javax.nonnull.ignoreUnsupportedTypes";
    public static final ConfigurationKey<Boolean> IGNORE_UNSUPPORTED_TYPES = new ConfigurationKeyImpl(
            IGNORE_UNSUPPORTED_TYPES_PROPERTY,
            "Do not generate warnings while @Nonnull is used with unsupported types (default = false).");

    public void handle(AnnotationValues<Nonnull> annotation, JCAnnotation ast, JavacNode annotationNode,
            String hubblebubble) {

    }

    @Override
    public void handle(AnnotationValues<Nonnull> annotation, JCAnnotation ast, JavacNode annotationNode) {
        if (!needCodeGeneration(annotationNode)) {
            return;
        }
        switch (annotationNode.up()
                .getKind()) {
        case ARGUMENT:
            HandleNonNull handleNonNull = new HandleNonNull();
            AnnotationValues<NonNull> annotationValues = AnnotationValues.of(NonNull.class, annotationNode);
            handleNonNull.handle(annotationValues, ast, annotationNode);
            break;
        case METHOD:
            JCMethodDecl method = (JCMethodDecl) annotationNode.up()
                    .get();
            generateReturnNullCheck(annotationNode, method);
            break;

        }
    }

    private void warnIfNotIgnored(@Nonnull JavacNode node, @Nonnull String message) {
        if (!isUnsupportedTypeWarning(node)) {
            node.addWarning(message);
        }
    }

    private boolean isUnsupportedTypeWarning(JavacNode node) {
        Boolean ignoreUnsupportedTypes = node.getAst()
                .readConfiguration(IGNORE_UNSUPPORTED_TYPES);
        return ignoreUnsupportedTypes == null ? false : ignoreUnsupportedTypes;
    }

    private boolean needCodeGeneration(JavacNode annotationNode) {
        JavacNode parentNode = annotationNode.directUp();
        AST.Kind astKind = parentNode.getKind();
        if (astKind != ARGUMENT && astKind != METHOD) {
            warnIfNotIgnored(annotationNode, "@Nonnull only works on methods and arguments");
            return false;
        }
        if (JavacHandlerUtil.hasAnnotation(DisableNonNull.class, parentNode)) {
            return false;
        }
        JavacNode cls = JavacHandlerUtil.upToTypeNode(parentNode);
        if (!JavacHandlerUtil.isClassAndDoesNotHaveFlags(cls, Flags.INTERFACE | Flags.ENUM | Flags.ANNOTATION)) {
            warnIfNotIgnored(annotationNode, "@Nonnull ignored in interfaces, annotations or enums");
            return false;
        }
        JCMethodDecl method = upToMethodNode(annotationNode);
        if ((method.mods.flags & Flags.ABSTRACT) != 0L) {
            warnIfNotIgnored(annotationNode, "@Nonnull ignored on abstract methods");
            return false;
        }
        return true;
    }

    private JCMethodDecl upToMethodNode(@Nonnull JavacNode node) {
        Objects.requireNonNull(node, "node must no be null");
        while ((node != null) && !(node.get() instanceof JCMethodDecl)) {
            node = node.up();
        }
        return (node != null) ? (JCMethodDecl) node.get() : null;
    }

    private void generateReturnNullCheck(JavacNode annotationNode, final JCMethodDecl methodNode) {
        if (methodNode.name.toString()
                .equals("<init>")) {
            warnIfNotIgnored(annotationNode, "constructors cannot annoted with @Nonnull - they are always 'nonnull'");
            return;
        }
        if (methodNode.body == null) {
            annotationNode.addWarning("@Nonnull annotated method '" + methodNode.getName() + "' has no body");
            return;
        }
        if (Objects.equals(methodNode.getReturnType()
                .toString(), "void")) {
            warnIfNotIgnored(annotationNode,
                    "@Nonnull annotated method '" + methodNode.getName() + "' has no return value");
            return;
        }
        if (methodNode.restype instanceof JCTree.JCPrimitiveTypeTree) {
            warnIfNotIgnored(annotationNode, "@NonNull is meaningless on a primitive");
            return;
        }

        final JCBlock body = methodNode.body;
        List<JCStatement> statements = body.getStatements();
        CodeProcessor processor = new CodeProcessor();
        Collection<JCStatement> newBody = processor.parse(statements, annotationNode, methodNode.restype);
        List<JCStatement> from = List.from(newBody.toArray(new JCStatement[0]));
        methodNode.body.stats = from;
    }

    private static class ConfigurationKeyImpl extends ConfigurationKey<Boolean> {

        public ConfigurationKeyImpl(String keyName, String description) {
            super(keyName, description);
        }
    }
}
