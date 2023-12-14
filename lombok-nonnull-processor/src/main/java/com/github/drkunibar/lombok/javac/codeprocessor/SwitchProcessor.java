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
import com.sun.tools.javac.tree.JCTree.JCSwitch;
import lombok.javac.JavacNode;

@MetaInfServices(Processor.class)
public class SwitchProcessor extends CodeProcessor implements Processor<JCSwitch> {

    @Override
    public Collection<JCTree.JCStatement> process(JCSwitch code, JavacNode annotationNode,
            JCTree.JCExpression returnType) {
        code.cases.stream()
                .forEach(c -> parseStatement(c, annotationNode, returnType));
        return Collections.singleton(code);
    }

    @Override
    public Class<JCSwitch> getSupportedType() {
        return JCSwitch.class;
    }

}
