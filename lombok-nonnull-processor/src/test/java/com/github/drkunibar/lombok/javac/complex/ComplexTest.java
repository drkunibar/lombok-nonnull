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
package com.github.drkunibar.lombok.javac.complex;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ComplexTest {

    @Nested
    class GetValue {
        @Test
        void complexTestClass_getValueNull_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getValue(true));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getValueNonnull_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getValue(false);
            // then
            assertThat(value).isEqualTo("foo");
        }
    }

    @Nested
    class getValueTernary {
        @Test
        void complexTestClass_getValueTernaryNull_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getValueTernary(true));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getValueTernaryNonnull_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getValueTernary(false);
            // then
            assertThat(value).isEqualTo("foo");
        }
    }

    @Nested
    class getValueFromSupplier {
        @Test
        void complexTestClass_getFromNullSupplier_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getFromSupplier(() -> null));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getFromSupplier_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getFromSupplier(() -> "foo");
            // then
            assertThat(value).isEqualTo("foo");
        }
    }

    @Nested
    class GetWhileLoop {
        @Test
        void complexTestClass_getWhileLoopNull_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getWhileLoop(null, 2));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getWhileLoopNonnull_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getWhileLoop("foo", 2);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getWhileLoopNullBehindLoop_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getWhileLoop(null, 6));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getWhileLoopNonnullBehindLoop_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getWhileLoop("foo", 6);
            // then
            assertThat(value).isEqualTo("foo");
        }
    }

    @Nested
    class GetDoWhileLoop {
        @Test
        void complexTestClass_getDoWhileLoopNull_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getDoWhileLoop(null, 2));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getDoWhileLoopNonnull_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getDoWhileLoop("foo", 2);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getDoWhileLoopNullBehindLoop_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getDoWhileLoop(null, 6));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getDoWhileLoopNonnullBehindLoop_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getDoWhileLoop("foo", 6);
            // then
            assertThat(value).isEqualTo("foo");
        }
    }

    @Nested
    class GetForLoop {
        @Test
        void complexTestClass_getForLoopNull_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getForLoop(null, 2));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getForLoopNonnull_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getForLoop("foo", 2);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getForLoopNullBehindLoop_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getForLoop(null, 6));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getForLoopNonnullBehindLoop_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getForLoop("foo", 6);
            // then
            assertThat(value).isEqualTo("foo");
        }
    }

    @Nested
    class GetForEnhanceLoop {
        @Test
        void complexTestClass_getForEnhanceLoopNull_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getForEnhanceLoop(null, true));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getForEnhanceLoopNonnull_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getForEnhanceLoop("foo", true);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getForEnhanceLoopNullBehindLoop_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getForEnhanceLoop(null, false));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getForEnhanceLoopNonnullBehindLoop_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getForEnhanceLoop("foo", false);
            // then
            assertThat(value).isEqualTo("foo");
        }
    }

    @Nested
    class GetCase {
        @Test
        void complexTestClass_getCaseNull1_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getCase(null, 1));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getCaseNonnull1_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getCase("foo", 1);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getCaseNull2_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getCase(null, 2));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getCaseNonnull2_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getCase("foo", 2);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getCaseNullDefault_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getCase(null, -1));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getCaseNonnullDefault_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getCase("foo", -1);
            // then
            assertThat(value).isEqualTo("foo");
        }
    }

    @Nested
    class GetTry {
        @Test
        void complexTestClass_getTryNull0_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getTry(null, 0));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getTryNonnull0_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getTry("foo", 0);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getTryNull1_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getTry(null, 1));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getTryNonnull1_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getTry("foo", 1);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getTryFinallyNull0_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getTryFinaly(null, 0));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getTryFinallyNull0_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getTryFinaly("foo", 0);
            // then
            assertThat(value).isEqualTo("foo");
        }

        @Test
        void complexTestClass_getTryFinallyNull1_throwsException() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            Throwable throwable = catchThrowable(() -> data.getTryFinaly(null, 1));
            // then
            assertThat(throwable).isInstanceOf(NullPointerException.class)
                    .hasMessage("the return value must not be null");
        }

        @Test
        void complexTestClass_getTryFinallyNull1_getCorrectValue() {
            // given
            ComplexTestClass data = new ComplexTestClass();
            // when
            String value = data.getTryFinaly("foo", 1);
            // then
            assertThat(value).isEqualTo("foo");
        }
    }
}
