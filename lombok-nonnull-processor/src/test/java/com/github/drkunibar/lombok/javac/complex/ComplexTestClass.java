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

import java.util.function.Supplier;
import javax.annotation.Nonnull;

public class ComplexTestClass {

    @Nonnull
    public String getValue(boolean nullResult) {
        if (nullResult) {
            return null;
        } else {
            return "foo";
        }
    }

    @Nonnull
    public String getValueTernary(boolean nullResult) {
        return nullResult ? null : "foo";
    }

    @Nonnull
    @SuppressWarnings("null")
    public String getFromSupplier(Supplier<String> supplier) {
        return supplier.get();
    }

    @Nonnull
    public String getWhileLoop(String result, int count) {
        int counter = 0;
        while (++counter < 5) {
            if (counter > count) {
                return result;
            }
        }
        return result;
    }

    @Nonnull
    public String getDoWhileLoop(String result, int count) {
        int i = 0;
        do {
            if (i > count) {
                return result;
            }
        } while (++i < 5);
        return result;
    }

    @Nonnull
    public String getForLoop(String result, int count) {
        for (int i = 0; i < 5; i++) {
            return result;
        }
        return result;
    }

    @Nonnull
    public String getForEnhanceLoop(String result, boolean inLoop) {
        for (Boolean b : new Boolean[] { Boolean.FALSE, Boolean.TRUE }) {
            if (inLoop && b) {
                return result;
            }
        }
        return result;
    }

    @Nonnull
    public String getCase(String result, int caseValue) {
        switch (caseValue) {
        case 1:
            return result;
        case 2:
        case 3:
            return result;
        default:
            return result;
        }
    }

    @Nonnull
    @SuppressWarnings("UseSpecificCatch")
    public String getTry(String result, int divider) {
        try {
            if (1 / divider > Integer.MAX_VALUE) {
                // Nothing
            }
            return result;
        } catch (Exception e) {
            return result;
        }
    }

    @Nonnull
    @SuppressWarnings({ "FinallyDiscardsException", "UseSpecificCatch" })
    public String getTryFinaly(String result, int divider) {
        try {
            if (1 / divider > Integer.MAX_VALUE) {
                // Nothing
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            return result;
        }
    }

}
