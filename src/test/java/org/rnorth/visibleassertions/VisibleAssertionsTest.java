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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.concurrent.Callable;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.rnorth.visibleassertions.VisibleAssertions.*;

/**
 * @author rnorth
 */
public class VisibleAssertionsTest {

    private ByteArrayOutputStream stdOutBuffer;

    @Before
    public void setup() {
        stdOutBuffer = new ByteArrayOutputStream();
        AnsiSupport.writer = new PrintStream(stdOutBuffer);
    }

    @After
    public void outputForDebugging() {
        System.out.println("CAPTURED STDOUT:");
        System.out.println(getCapturedStdOut());
    }

    @Test
    public void testTrueAssertion() {
        assertTrue("it should be true", true);
        assert getCapturedStdOut().contains("✔ it should be true");
    }

    @Test
    public void testFalseAssertion() {
        try {
            assertTrue("it should be true", false);
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be true");
    }

    @Test
    public void testNullEqualsAssertion() {
        assertEquals("it should be equal", null, null);
        assert getCapturedStdOut().contains("✔ it should be equal");
    }

    @Test
    public void testEqualsAssertion() {
        assertEquals("it should be equal", "A", "A");
        assert getCapturedStdOut().contains("✔ it should be equal");
    }

    @Test
    public void testOneNullEqualsAssertion() {
        try {
            assertEquals("it should be equal", null, "A");
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be equal");
        assert getCapturedStdOut().contains("'A' does not equal expected null");
    }

    @Test
    public void testOneOtherNullEqualsAssertion() {
        try {
            assertEquals("it should be equal", "A", null);
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be equal");
        assert getCapturedStdOut().contains("null does not equal expected 'A'");
    }

    @Test
    public void testNonEqualsAssertion() {
        try {
            assertEquals("it should be equal", "B", "A");
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be equal");
        assert getCapturedStdOut().contains("'A' does not equal expected 'B'");
    }

    @Test
    public void testNonEqualsAssertionForDifferentTypesSameValue() {
        try {
            assertEquals("it should be equal", 1L, 1);
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be equal");
        assert getCapturedStdOut().contains("'1' [java.lang.Integer] does not equal expected '1' [java.lang.Long]");
    }

    @Test
    public void testNonEqualsAssertionForDifferentTypesSameValueForNonBoxedNonPrimitiveTypes() {
        try {
            assertEquals("it should be equal", new BigDecimal(1), 1);
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be equal");
        assert getCapturedStdOut().contains("'1' [java.lang.Integer] does not equal expected '1' [java.math.BigDecimal]");
    }

    @Test
    public void testNotEqualsAssertion() {
        try {
            assertNotEquals("it should not be equal", "A", "A");
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should not be equal");
    }

    @Test
    public void testOneNullNotEqualsAssertion() {
        assertNotEquals("it should not be equal", null, "A");
        assert getCapturedStdOut().contains("✔ it should not be equal");
    }

    @Test
    public void testOneOtherNullNotEqualsAssertion() {
        assertNotEquals("it should not be equal", "A", null);
        assert getCapturedStdOut().contains("✔ it should not be equal");
    }

    @Test
    public void testNonNotEqualsAssertion() {
        assertNotEquals("it should not be equal", "B", "A");
        assert getCapturedStdOut().contains("✔ it should not be equal");
    }

    @Test(expected = RuntimeException.class)
    public void testDeliberateFailure() {
        try {
            int a = 7 / 0;
        } catch (Throwable e) {
            throw new RuntimeException("A generic exception which wraps the root cause", e);
        }
    }

    @Test
    public void testNullAssertion() {
        assertNull("a null thing should be null", null);
        assert getCapturedStdOut().contains("✔ a null thing should be null");
    }

    @Test
    public void testFailingNullAssertion() {
        try {
            assertNull("a null thing should be null", "a non-null thing");
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ a null thing should be null");
    }

    @Test
    public void testNotNullAssertion() {
        assertNotNull("a not-null thing should be not-null", "a non-null thing");
        assert getCapturedStdOut().contains("✔ a not-null thing should be not-null");
    }

    @Test
    public void testFailingNotNullAssertion() {
        try {
            assertNotNull("a not-null thing should be not-null", null);
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ a not-null thing should be not-null");
    }

    @Test
    public void testFail() {
        try {
            fail("a failure reason");
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ a failure reason");
    }

    @Test
    public void testSameAssertion() {
        Object o1 = "A";
        Object o2 = o1;
        assertSame("it should be the same", o2, o1);
        assert getCapturedStdOut().contains("✔ it should be the same");
    }

    @Test
    public void testFailingSameAssertion() {
        try {
            assertSame("it should be the same", "A", "B");
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be the same");
        assert getCapturedStdOut().contains("'B' is not the same (!=) as expected 'A'");
    }

    @Test
    public void testFailingSameAssertionForNullExpected() {
        try {
            assertSame("it should be the same", null, "B");
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be the same");
        assert getCapturedStdOut().contains("'B' is not the same (!=) as expected null");
    }

    @Test
    public void testFailingSameAssertionForNullActual() {
        try {
            assertSame("it should be the same", "A", null);
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be the same");
        assert getCapturedStdOut().contains("null is not the same (!=) as expected 'A'");
    }

    @Test
    public void testThatAssertionWithDescription() {
        assertThat("the string", "expected value", is(equalTo("expected value")));
        assert getCapturedStdOut().contains("✔ the string is \"expected value\"");
    }

    @Test
    public void testFailingThatAssertionWithDescription() {
        try {
            assertThat("the string", "actual value", is(equalTo("expected value")));
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ assertion on the string failed");
        assert getCapturedStdOut().contains("asserted that it is \"expected value\" but was \"actual value\"");
    }

    @Test
    public void testInfoStatement() {
        info("This is an informational message");
        assert getCapturedStdOut().contains("ℹ︎ This is an informational message");
    }

    @Test
    public void testWarnStatement() {
        warn("This is a warning message");
        assert getCapturedStdOut().contains("⚠︎ This is a warning message");
    }

    @Test
    public void testContextStatement() {
        context("This is the name of a new context");
        context("This is the name of an indented context", 2);
        assert getCapturedStdOut().contains("┈┈┈┈ This is the name of a new context ┈┈┈┈");
        assert getCapturedStdOut().contains("  ┈┈┈┈ This is the name of an indented context ┈┈┈┈┈");
    }

    @Test
    public void testAssertThrowsWhenNoException() {
        try {
            assertThrows("It throws a NPE", NullPointerException.class, new Callable() {
                public Object call() throws Exception {
                    // don't throw
                    return null;
                }
            });
            failIfReachedHere();
        } catch (AssertionError expected) {
        }

        assert getCapturedStdOut().contains("✘ It throws a NPE");
    }

    @Test
    public void testAssertThrowsWhenNoExceptionInRunnable() {
        try {
            assertThrows("It throws a NPE", NullPointerException.class, new Runnable() {
                public void run() {
                    // don't throw
                    return;
                }
            });
            failIfReachedHere();
        } catch (AssertionError expected) {
        }

        assert getCapturedStdOut().contains("✘ It throws a NPE");
    }

    @Test
    public void testAssertThrowsWhenWrongException() {

        try {
            assertThrows("It fails with a NPE", NullPointerException.class, new Callable() {
                public Object call() throws Exception {
                    // throw the wrong exception
                    throw new ArrayIndexOutOfBoundsException();
                }
            });
            failIfReachedHere();
        } catch (AssertionError expected) {
        }

        assert getCapturedStdOut().contains("✘ It fails with a NPE");
        assert getCapturedStdOut().contains("ArrayIndexOutOfBoundsException was thrown instead of NullPointerException");
    }

    private void failIfReachedHere() {
        throw new IllegalStateException();
    }

    private String getCapturedStdOut() {
        return stdOutBuffer.toString();
    }
}
