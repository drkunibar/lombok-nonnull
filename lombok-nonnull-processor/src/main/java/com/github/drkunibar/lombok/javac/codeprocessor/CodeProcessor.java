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
package com.github.drkunibar.lombok.javac.codeprocessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.StreamSupport;
import javax.annotation.Nonnull;
import com.sun.tools.javac.code.Flags;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCReturn;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.tree.JCTree.JCVariableDecl;
import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.Name;
import lombok.javac.JavacNode;
import lombok.javac.JavacTreeMaker;
import lombok.javac.apt.LombokProcessor;
import lombok.javac.handlers.JavacHandlerUtil;

public class CodeProcessor {

    protected static final Map<Class<? extends JCTree>, Processor<? extends JCTree>> PROCESSORS = new HashMap<>();

    static {
        // Load the 'prozessors' with the correct ClassLoader
        ServiceLoader<Processor> processors = ServiceLoader.load(Processor.class,
                LombokProcessor.class.getClassLoader());
        StreamSupport.stream(processors.spliterator(), false)
                .forEach(CodeProcessor::registerProcessor);
    }

    private static final BlockProcessor BLOCK_PROCESSOR = new BlockProcessor();
    private static final AtomicInteger RESULT_COUNTER = new AtomicInteger();

    @Nonnull
    public JCStatement parseToSingleStatement(@Nonnull JCStatement statement, @Nonnull JavacNode annotationNode,
            @Nonnull JCExpression returnType) {
        Objects.requireNonNull(statement, "'statement' must not be null");
        Objects.requireNonNull(annotationNode, "'annotationNode' must not be null");
        Objects.requireNonNull(returnType, "'returnType' must not be null");
        Collection<JCStatement> parseStatement = parseStatement(statement, annotationNode, returnType);
        return collect(annotationNode.getTreeMaker(), parseStatement);
    }

    @Nonnull
    public JCBlock parseToBlock(@Nonnull JCStatement statement, @Nonnull JavacNode annotationNode,
            @Nonnull JCExpression returnType) {
        Objects.requireNonNull(statement, "'statement' must not be null");
        Objects.requireNonNull(annotationNode, "'annotationNode' must not be null");
        Objects.requireNonNull(returnType, "'returnType' must not be null");
        Collection<JCStatement> parseStatement = parseStatement(statement, annotationNode, returnType);
        JCStatement result = collect(annotationNode.getTreeMaker(), parseStatement);
        if (result instanceof JCBlock) {
            return (JCBlock) result;
        } else {
            JavacTreeMaker maker = annotationNode.getTreeMaker();
            List<JCStatement> stmts = List.from(new JCStatement[] { result });
            return maker.Block(0, stmts);
        }
    }

    @Nonnull
    public Collection<JCStatement> parseStatement(@Nonnull JCStatement statement, @Nonnull JavacNode annotationNode,
            @Nonnull JCExpression returnType) {
        Objects.requireNonNull(statement, "'statement' must not be null");
        Objects.requireNonNull(annotationNode, "'annotationNode' must not be null");
        Objects.requireNonNull(returnType, "'returnType' must not be null");
        if (statement instanceof JCReturn) {
            JCReturn tree = (JCReturn) statement;
            JCExpression expr = tree.expr;
            JavacTreeMaker maker = annotationNode.getTreeMaker();
            String resultName = "$result_" + RESULT_COUNTER.getAndIncrement();
            Name declName = annotationNode.toName(resultName);
            JCVariableDecl decl = maker.VarDef(maker.Modifiers(Flags.PARAMETER), declName, returnType, expr);
            JCReturn ret = maker.Return(maker.Ident(declName));
            JCStatement nullcheck = JavacHandlerUtil.generateNullCheck(maker, maker.Ident(declName), declName,
                    annotationNode, "the return value must not be null");
            generateBy(decl, annotationNode);
            generateBy(nullcheck, annotationNode);
            generateBy(ret, annotationNode);
            return Arrays.asList(decl, nullcheck, ret);
        } else {
            Optional<Processor<JCTree.JCStatement>> processor = getProcessor(statement);
            if (processor.isPresent()) {
                return processor.get()
                        .process(statement, annotationNode, returnType);
            } else {
                return Collections.singleton(statement);
            }
        }
    }

    @Nonnull
    public final Collection<JCStatement> parse(@Nonnull Collection<JCStatement> statements,
            @Nonnull JavacNode annotationNode, @Nonnull JCExpression returnType) {
        Objects.requireNonNull(statements, "'statements' must not be null");
        Objects.requireNonNull(annotationNode, "'annotationNode' must not be null");
        Objects.requireNonNull(returnType, "'returnType' must not be null");
        Collection<JCTree.JCStatement> result = new ArrayList<>();
        for (JCTree.JCStatement stmt : statements) {
            result.addAll(parseStatement(stmt, annotationNode, returnType));
        }
        return result;
    }

    protected void parse(@Nonnull JCBlock block, @Nonnull JavacNode annotationNode, @Nonnull JCExpression returnType) {
        Objects.requireNonNull(block, "'block' must not be null");
        Objects.requireNonNull(annotationNode, "'annotationNode' must not be null");
        Objects.requireNonNull(returnType, "'returnType' must not be null");
        BLOCK_PROCESSOR.process(block, annotationNode, returnType);
    }

    protected JCStatement collect(@Nonnull JavacTreeMaker TreeMaker, @Nonnull Collection<JCStatement> stmts) {
        if (stmts.size() == 1) {
            return stmts.iterator()
                    .next();
        } else {
            List<JCStatement> from = List.from(stmts.toArray(new JCStatement[0]));
            return TreeMaker.Block(0, from);
        }
    }

    protected void generateBy(JCTree node, JavacNode annotationNode) {
        JavacHandlerUtil.recursiveSetGeneratedBy(node, annotationNode);
    }

    private static void registerProcessor(@Nonnull Processor<? extends JCTree> processor) {
        Objects.requireNonNull(processor, "'processor' must not be null");
        PROCESSORS.put(processor.getSupportedType(), processor);
    }

    protected static <P extends JCTree> Optional<Processor<P>> getProcessor(@Nonnull P codeBlock) {
        Objects.requireNonNull(codeBlock, "'codeBlock' must not be null");
        Processor<P> processor = (Processor<P>) PROCESSORS.get(codeBlock.getClass());
        return Optional.ofNullable(processor);
    }

}
