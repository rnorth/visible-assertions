/*
 * Copyright 2013 Deloitte Digital and Richard North
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.rnorth.visibleassertions;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import java.util.concurrent.Callable;

/**
 * @author rnorth
 */
public class VisibleAssertions extends AnsiSupport {

    private static final String TICK_MARK = "\u2714";
    private static final String CROSS_MARK = "\u2718";
    private static final String INFO_MARK = "\u2139\ufe0e";
    private static final String WARN_MARK = "\u26a0\ufe0e";
    private static final String CONTEXT_MARK = "\u2508";

    // Utility class, hidden constructor
    protected VisibleAssertions() {

    }

    public static void info(String message) {
        initialize();
        ansiPrintf("        @|white,bold " + INFO_MARK + " " + message + " |@\n");
    }

    public static void warn(String message) {
        initialize();
        ansiPrintf("        @|yellow,bold " + WARN_MARK + " " + message + " |@\n");
    }

    public static void context(CharSequence context) {
        context(context, 0);
    }

    public static void context(CharSequence context, int indent) {
        initialize();

        StringBuffer sb = new StringBuffer();
        for (int i=0; i<indent; i++) sb.append(" ");
        for (int i=0; i<4; i++) sb.append(CONTEXT_MARK);

        sb.append(" ");
        sb.append(context);

        int terminalWidth = terminalWidth();
        sb.append(" ");
        for (int i=sb.length(); i<terminalWidth; i++) {
            sb.append(CONTEXT_MARK);
        }

        ansiPrintf("@|faint " + sb.toString() + " |@\n");
    }

    public static void assertTrue(String message, boolean value) {
        if (value) {
            pass(message);
        } else {
            fail(message, null);
        }
    }

    public static void assertFalse(String message, boolean value) {
        if (!value) {
            pass(message);
        } else {
            fail(message, null);
        }
    }

    public static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) {
            pass(message);
        } else if (expected != null && expected.equals(actual)) {
            pass(message);
        } else {
            fail(message, "'" + actual + "' does not equal expected '" + expected + "'");
        }
    }

    public static void assertNull(String message, Object o) {
        if (o == null) {
            pass(message);
        } else {
            fail(message, "'" + o + "' is not null");
        }
    }

    public static void assertNotNull(String message, Object o) {
        if (o != null) {
            pass(message);
        } else {
            fail(message, null);
        }
    }

    public static void assertSame(String message, Object expected, Object actual) {
        if (expected == actual) {
            pass(message);
        } else {
            fail(message, "'" + actual + "' is not the same (!=) as expected '" + expected + "'");
        }
    }

    public static void fail(String message) {
        fail(message, null);
    }

    public static <T> void assertThat(String whatTheObjectIs, T actual, Matcher<? super T> matcher) {
        Description description = new StringDescription();
        if (matcher.matches(actual)) {
            description.appendText(whatTheObjectIs);
            description.appendText(" ");
            matcher.describeTo(description);
            pass(description.toString());
        } else {
            description.appendText("asserted that it ")
                    .appendDescriptionOf(matcher)
                    .appendText(" but ");
            matcher.describeMismatch(actual, description);
            fail("assertion on " + whatTheObjectIs + " failed", description.toString());
        }
    }

    public static void assertThrows(Class<? extends Exception> throwableClass, Callable callable) {
        try {
            callable.call();
            fail("Expected exception (" + throwableClass.getSimpleName() + ") was not thrown");
        } catch (Exception e) {
            if (!e.getClass().equals(throwableClass)) {
                fail("Expected exception (" + throwableClass.getSimpleName() + ") was not thrown", e.getClass().getSimpleName() + " was thrown instead!");
            }
        }
    }

    private static void pass(String message) {
        initialize();
        ansiPrintf("        @|green " + TICK_MARK + " " + message + " |@\n");
    }

    private static void fail(String message, String hint) {
        initialize();
        ansiPrintf("        @|red " + CROSS_MARK + " " + message + " |@\n");

        if (hint == null) {
            throw new AssertionError(message);
        } else {
            ansiPrintf("            @|yellow " + hint + " |@\n");
            throw new AssertionError(message + ": " + hint);
        }

    }
}
