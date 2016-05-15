// Copyright (c) Philipp Wagner. All rights reserved.
// Licensed under the MIT license. See LICENSE file in the project root for full license information.

package utils;

import org.junit.Assert;

public class TestUtils {

    @FunctionalInterface
    public interface Action {
        void invoke();
    }

    public static void assertThrows(Action action, Class<?> expectedException) {
        Throwable throwable = null;
        try {
            action.invoke();
        } catch(Throwable t) {
            throwable = t;
        }
        if(throwable == null) {
            Assert.assertEquals(expectedException, null);
        } else {
            Assert.assertEquals(expectedException, throwable.getClass());
        }
    }

    public static void assertDoesNotThrow(Action action) {
        assertThrows(action, null);
    }
}
