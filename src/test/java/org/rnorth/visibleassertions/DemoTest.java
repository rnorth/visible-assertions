package org.rnorth.visibleassertions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

import static org.rnorth.visibleassertions.VisibleAssertions.*;

/**
 * This test doesn't actually do much, and doesn't have a real system under test.
 *
 * It is intended to show a few ways of using the VisibleAssertions.* methods.
 */
public class DemoTest {

    private boolean shouldFail = false;

    @Rule
    public TestWatcher testWatcher = new TestWatcher() {
        @Override
        protected void starting(Description description) {
            context(description.getMethodName());
        }
    };

    @Test
    public void testSuccessfulLoginAndAccessToCart() throws InterruptedException {

        info("Attempting login as testuser, password: opensesame");

        assertTrue("Clicking 'login' takes the user to the next page", true);

        assertEquals("The welcome screen shows the user's name", "John Doe", "John Doe");

        info("Navigating to the cart page");

        int itemsInCart = shouldFail ? 2 : 0;
        assertEquals("The number of items in the user's cart is initially zero", 0, itemsInCart);
    }

    @Test
    public void testLoginFailureWithWrongPassword() throws InterruptedException {

        info("Attempting login as testuser, password: badpassword");

        assertTrue("Clicking 'login' stays on the same page", true);

        assertTrue("An error message is shown", true);

    }
}
