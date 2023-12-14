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
package com.github.drkunibar.lombok.javac.data;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Test;
import com.github.drkunibar.lombok.javac.data.DataTestClass.Fields;

public class DataTest {

    @Test
    void constructWithNull_throwsException() {
        // when
        Throwable throwable = catchThrowable(() -> new DataTestClass(null));
        // then
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                .hasMessage("name is marked non-null but is null");
    }

    @Test
    void constructWithValue_noException() {
        // when
        DataTestClass data = new DataTestClass("foo");
        // then
        assertThat(data).hasFieldOrPropertyWithValue(Fields.name, "foo");
    }

    @Test
    void dataTestClass_setNameWithNull_throwsException() {
        // given
        DataTestClass data = new DataTestClass("foo");
        // when
        @SuppressWarnings("null")
        Throwable throwable = catchThrowable(() -> data.setName(null));
        // then
        assertThat(throwable).isInstanceOf(NullPointerException.class)
                .hasMessage("name is marked non-null but is null");
    }
}
