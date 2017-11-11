import org.fusesource.jansi.internal.CLibrary;
import org.junit.Test;
import org.rnorth.ansi.CapabilityDetection;

import static org.rnorth.visibleassertions.VisibleAssertions.assertEquals;
import static org.rnorth.visibleassertions.VisibleAssertions.info;

public class TtyCheckTest {

    @Test
    public void checkConsistentTtyDetection() {
        info("isTty=" + CapabilityDetection.isTty());
        info("isUnderMaven=" + CapabilityDetection.isUnderMaven());
        info("isUnderIDEA=" + CapabilityDetection.isUnderIDEA());

        assertEquals("JNA and Jansi detect tty the same",
                CapabilityDetection.isTty() ? 1 : 0,
                CLibrary.isatty(1));
    }
}
