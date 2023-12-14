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

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class DisableNonnullTest {

    @Test
    @SuppressWarnings("ResultOfObjectAllocationIgnored")
    void constructDisableNonnullTestWithNull_noException() {
        // when
        new DisableNonnullTestClass(null);
        // then
        // ... no exception - looks good
    }

    @Test
    void constructDisableNonnullTestWithNull_getName_getNullValue() {
        // given
        DisableNonnullTestClass data = new DisableNonnullTestClass(null);
        // when
        String name = data.getName();
        // then
        assertThat(name).isNull();
    }

    @Test
    @SuppressWarnings("null")
    void constructDisableNonnullTestWithNull_setNameNull_getNullValue() {
        // given
        DisableNonnullTestClass data = new DisableNonnullTestClass(null);
        // when
        data.setName(null);
        String name = data.getName();
        // then
        assertThat(name).isNull();
    }
}
