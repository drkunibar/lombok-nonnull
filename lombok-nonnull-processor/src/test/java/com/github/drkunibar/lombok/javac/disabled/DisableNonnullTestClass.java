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
package com.github.drkunibar.lombok.javac.disabled;

import javax.annotation.Nonnull;
import com.github.drkunibar.lombok.DisableNonNull;

public class DisableNonnullTestClass {

    @Nonnull
    private String name;

    public DisableNonnullTestClass() {
    }

    public DisableNonnullTestClass(@Nonnull @DisableNonNull String name) {
        this.name = name;
    }

    public @Nonnull @DisableNonNull String getName() {
        return name;
    }

    public void setName(@Nonnull @DisableNonNull String name) {
        this.name = name;
    }

}
