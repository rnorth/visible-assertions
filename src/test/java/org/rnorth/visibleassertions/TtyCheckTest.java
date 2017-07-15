package org.rnorth.visibleassertions;

import jnr.posix.POSIXFactory;
import org.fusesource.jansi.internal.CLibrary;
import org.junit.Test;

import static org.rnorth.visibleassertions.VisibleAssertions.assertEquals;

public class TtyCheckTest {

    @Test
    public void checkConsistentTtyDetection() {
        assertEquals("jnr-posix and Jansi detect tty the same",
                POSIXFactory.getPOSIX().isatty(1),
                CLibrary.isatty(1));
    }
}
