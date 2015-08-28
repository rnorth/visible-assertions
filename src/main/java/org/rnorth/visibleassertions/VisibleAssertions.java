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
 * Assertions for use in Java tests, with contextual information on each assertion performed.
 *
 * Output is to stdout, and is coloured if the terminal supports it.
 *
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

    /**
     * Log an informational message.
     *
     * The output will be in white, following an 'i' symbol.
     *
     * @param message message to output
     */
    public static void info(String message) {
        initialize();
        ansiPrintf("        @|white,bold " + INFO_MARK + " " + message + " |@\n");
    }

    /**
     * Log a warning message.
     *
     * The output will be in yellow, following a '!' symbol.
     *
     * @param message message to output
     */
    public static void warn(String message) {
        initialize();
        ansiPrintf("        @|yellow,bold " + WARN_MARK + " " + message + " |@\n");
    }

    /**
     * Log a contextual message, in the style of a 'dividing line' in the test output.
     *
     * The output will be in grey, surrounded by a horizontal line the full width of the current terminal (or 80 chars).
     *
     * @param context contextual message to output.
     */
    public static void context(CharSequence context) {
        context(context, 0);
    }

    /**
     * Log a contextual message, in the style of a 'dividing line' in the test output.
     *
     * The output will be in grey, surrounded by a horizontal line the full width of the current terminal (or 80 chars).
     *
     * @param context contextual message to output
     * @param indent number of space characters to indent this line by
     */
    public static void context(CharSequence context, int indent) {
        initialize();

        StringBuilder sb = new StringBuilder();
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

    /**
     * Assert that a value is true.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param value value to test
     */
    public static void assertTrue(String message, boolean value) {
        if (value) {
            pass(message);
        } else {
            fail(message, null);
        }
    }

    /**
     * Assert that a value is false.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param value value to test
     */
    public static void assertFalse(String message, boolean value) {
        if (!value) {
            pass(message);
        } else {
            fail(message, null);
        }
    }

    /**
     * Assert that an actual value is equal to an expected value.
     *
     * Equality is tested with the standard Object equals() method, unless both values are null.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param expected the expected value
     * @param actual the actual value
     */
    public static void assertEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) {
            pass(message);
        } else if (expected != null && expected.equals(actual)) {
            pass(message);
        } else {
            fail(message, "'" + actual + "' does not equal expected '" + expected + "'");
        }
    }

    /**
     * Assert that an actual value is not equal to an expected value.
     *
     * Equality is tested with the standard Object equals() method, unless both values are null.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param expected the expected value
     * @param actual the actual value
     */
    public static void assertNotEquals(String message, Object expected, Object actual) {
        if (expected == null && actual == null) {
            fail(message);
        } else if (expected != null && expected.equals(actual)) {
            fail(message);
        } else {
            pass(message);
        }
    }

    /**
     * Assert that a value is null.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param o value to test
     */
    public static void assertNull(String message, Object o) {
        if (o == null) {
            pass(message);
        } else {
            fail(message, "'" + o + "' is not null");
        }
    }

    /**
     * Assert that a value is not null.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param o value to test
     */
    public static void assertNotNull(String message, Object o) {
        if (o != null) {
            pass(message);
        } else {
            fail(message, null);
        }
    }

    /**
     * Assert that an actual value is the same object as an expected value.
     *
     * Sameness is tested with the == operator.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param expected the expected value
     * @param actual the actual value
     */
    public static void assertSame(String message, Object expected, Object actual) {
        if (expected == actual) {
            pass(message);
        } else {
            fail(message, "'" + actual + "' is not the same (!=) as expected '" + expected + "'");
        }
    }

    /**
     * Just fail with an AssertionError, citing a given message.
     *
     * A red cross will be shown.
     *
     * @param message message to display alongside the red cross
     */
    public static void fail(String message) {
        fail(message, null);
    }

    /**
     * Assert using a Hamcrest matcher.
     *
     * @param whatTheObjectIs what is the thing being tested, in a logical sense
     * @param actual the actual value
     * @param matcher a matcher to check the actual value against
     * @param <T> class of the actual value
     */
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

    /**
     * Assert that a given callable throws an exception of a particular class.
     *
     * The assertion passes if the callable throws exactly the same class of exception (not a subclass).
     *
     * If the callable doesn't throw an exception at all, or if another class of exception is thrown, the assertion
     * fails.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param exceptionClass the expected exception class
     * @param callable a Callable to invoke
     */
    public static <T> void assertThrows(String message, Class<? extends Exception> exceptionClass, Callable<T> callable) {
        T result;
        try {
            result = callable.call();
            fail(message, "No exception was thrown (expected " + exceptionClass.getSimpleName() + " but '" + result + "' was returned instead)");
        } catch (Exception e) {
            if (!e.getClass().equals(exceptionClass)) {
                fail(message, e.getClass().getSimpleName() + " was thrown instead of " + exceptionClass.getSimpleName());
            }
        }
    }

    /**
     * Assert that a given runnable throws an exception of a particular class.
     *
     * The assertion passes if the runnable throws exactly the same class of exception (not a subclass).
     *
     * If the runnable doesn't throw an exception at all, or if another class of exception is thrown, the assertion
     * fails.
     *
     * If the assertion passes, a green tick will be shown. If the assertion fails, a red cross will be shown.
     *
     * @param message message to display alongside the assertion outcome
     * @param exceptionClass the expected exception class
     * @param runnable a Runnable to invoke
     */
    public static void assertThrows(String message, Class<? extends Exception> exceptionClass, Runnable runnable) {
        try {
            runnable.run();
            fail(message, "No exception was thrown (expected " + exceptionClass.getSimpleName() + ")");
        } catch (Exception e) {
            if (!e.getClass().equals(exceptionClass)) {
                fail(message, e.getClass().getSimpleName() + " was thrown instead of " + exceptionClass.getSimpleName());
            }
        }
    }

    /**
     * Indicate that something passed.
     * @param message message to display alongside a green tick
     */
    public static void pass(String message) {
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
