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
        assert getCapturedStdOut().contains("'A' does not equal expected 'null'");
    }

    @Test
    public void testOneOtherNullEqualsAssertion() {
        try {
            assertEquals("it should be equal", "A", null);
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be equal");
        assert getCapturedStdOut().contains("'null' does not equal expected 'A'");
    }

    @Test
    public void testNotEqualsAssertion() {
        try {
            assertEquals("it should be equal", "B", "A");
            failIfReachedHere();
        } catch (AssertionError expected) {
        }
        assert getCapturedStdOut().contains("✘ it should be equal");
        assert getCapturedStdOut().contains("'A' does not equal expected 'B'");
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
            assertThrows(NullPointerException.class, new Callable() {
                public Object call() throws Exception {
                    // don't throw
                    return null;
                }
            });
            failIfReachedHere();
        } catch (AssertionError expected) {
        }

        assert getCapturedStdOut().contains("✘ Expected exception (NullPointerException) was not thrown");
    }

    @Test
    public void testAssertThrowsWhenWrongException() {

        try {
            assertThrows(NullPointerException.class, new Callable() {
                public Object call() throws Exception {
                    // throw the wrong exception
                    throw new ArrayIndexOutOfBoundsException();
                }
            });
            failIfReachedHere();
        } catch (AssertionError expected) {
        }

        assert getCapturedStdOut().contains("✘ Expected exception (NullPointerException) was not thrown");
        assert getCapturedStdOut().contains("ArrayIndexOutOfBoundsException was thrown instead!");
    }

    private void failIfReachedHere() {
        throw new IllegalStateException();
    }

    private String getCapturedStdOut() {
        return stdOutBuffer.toString();
    }
}
