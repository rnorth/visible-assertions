package org.rnorth;

import org.rnorth.ansi.CapabilityDetection;

import static org.rnorth.ansi.AnsiLite.green;
import static org.rnorth.visibleassertions.VisibleAssertions.info;

/**
 * Simple class for manual testing (direct execution)
 */
public class TtyCheck {

    public static void main(String[] args) {
        info("isTty=" + CapabilityDetection.isTty());
        info("isUnderMaven=" + CapabilityDetection.isUnderMaven());
        info("isUnderIDEA=" + CapabilityDetection.isUnderIDEA());

        System.out.println(green("This text will be green if ANSI output is enabled"));
    }
}
