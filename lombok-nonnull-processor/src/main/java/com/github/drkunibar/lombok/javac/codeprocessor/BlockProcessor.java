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
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.Nonnull;
import org.kohsuke.MetaInfServices;
import com.sun.tools.javac.tree.JCTree.JCBlock;
import com.sun.tools.javac.tree.JCTree.JCExpression;
import com.sun.tools.javac.tree.JCTree.JCStatement;
import com.sun.tools.javac.util.List;
import lombok.javac.JavacNode;

@MetaInfServices(Processor.class)
public class BlockProcessor extends CodeProcessor implements Processor<JCBlock> {

    @Override
    public Collection<JCStatement> process(@Nonnull JCBlock code, @Nonnull JavacNode annotationNode,
            @Nonnull JCExpression returnType) {
        Objects.requireNonNull(code, "'code' must not be null");
        Objects.requireNonNull(annotationNode, "'annotationNode' must not be null");
        Objects.requireNonNull(returnType, "'returnType' must not be null");
        Collection<JCStatement> stmts = new ArrayList<>(code.stats);
        Collection<JCStatement> processedStmts = parse(stmts, annotationNode, returnType);
        List<JCStatement> from = List.from(processedStmts.toArray(new JCStatement[0]));
        code.stats = from;
        return Collections.singleton(code);
    }

    @Override
    public Class<JCBlock> getSupportedType() {
        return JCBlock.class;
    }

}
