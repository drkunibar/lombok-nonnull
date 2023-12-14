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

import java.util.Collection;
import java.util.Collections;
import org.kohsuke.MetaInfServices;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.tree.JCTree.JCTry;
import lombok.javac.JavacNode;

@MetaInfServices(Processor.class)
public class TryProcessor extends CodeProcessor implements Processor<JCTry> {

    @Override
    public Collection<JCTree.JCStatement> process(JCTry code, JavacNode annotationNode,
            JCTree.JCExpression returnType) {
        code.body = parseToBlock(code.body, annotationNode, returnType);
        code.catchers.stream()
                .forEach(c -> parseStatement(c.body, annotationNode, returnType));
        if (code.finalizer != null) {
            code.finalizer = parseToBlock(code.finalizer, annotationNode, returnType);
        }
        return Collections.singleton(code);
    }

    @Override
    public Class<JCTry> getSupportedType() {
        return JCTry.class;
    }

}
