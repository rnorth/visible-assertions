package org.rnorth.ansi;

import static org.rnorth.ansi.CapabilityDetection.*;

/**
 * A simple ANSI colour output formatter. It is quite simplistic in implementation, and less efficient than it could be.
 *
 * It does, however, work, and is easy to use.
 */
public class AnsiLite {

    // Color code strings from:
    // http://www.topmudsites.com/forums/mud-coding/413-java-ansi.html
    static final String RESET = "\u001B[0m";

    static final String HIGH_INTENSITY = "\u001B[1m";
    static final String LOW_INTENSITY = "\u001B[2m";
    static final String REVERSE_VIDEO = "\u001B[7m";

    static final String UNDERLINE = "\u001B[4m";

    static final String BLACK = "\u001B[30m";
    static final String RED = "\u001B[31m";
    static final String GREEN = "\u001B[32m";
    static final String YELLOW = "\u001B[33m";
    static final String BLUE = "\u001B[34m";
    static final String MAGENTA = "\u001B[35m";
    static final String CYAN = "\u001B[36m";
    static final String WHITE = "\u001B[37m";

    static final String BACKGROUND_BLACK = "\u001B[40m";
    static final String BACKGROUND_RED = "\u001B[41m";
    static final String BACKGROUND_GREEN = "\u001B[42m";
    static final String BACKGROUND_YELLOW = "\u001B[43m";
    static final String BACKGROUND_BLUE = "\u001B[44m";
    static final String BACKGROUND_MAGENTA = "\u001B[45m";
    static final String BACKGROUND_CYAN = "\u001B[46m";
    static final String BACKGROUND_WHITE = "\u001B[47m";

    private final String code;
    private final Object[] s;

    private AnsiLite(String code, Object... s) {
        this.code = code;
        this.s = s;
    }

    public static AnsiLite underline(Object... s) {
        return new AnsiLite(UNDERLINE, s);
    }

    public static AnsiLite black(Object... s) {
        return new AnsiLite(BLACK, s);
    }

    public static AnsiLite red(Object... s) {
        return new AnsiLite(RED, s);
    }

    public static AnsiLite green(Object... s) {
        return new AnsiLite(GREEN, s);
    }

    public static AnsiLite yellow(Object... s) {
        return new AnsiLite(YELLOW, s);
    }

    public static AnsiLite blue(Object... s) {
        return new AnsiLite(BLUE, s);
    }

    public static AnsiLite magenta(Object... s) {
        return new AnsiLite(MAGENTA, s);
    }

    public static AnsiLite cyan(Object... s) {
        return new AnsiLite(CYAN, s);
    }

    public static AnsiLite white(Object... s) {
        return new AnsiLite(WHITE, s);
    }

    public static AnsiLite bgBlack(Object... s) {
        return new AnsiLite(BACKGROUND_BLACK, s);
    }

    public static AnsiLite bgRed(Object... s) {
        return new AnsiLite(BACKGROUND_RED, s);
    }

    public static AnsiLite bgGreen(Object... s) {
        return new AnsiLite(BACKGROUND_GREEN, s);
    }

    public static AnsiLite bgYellow(Object... s) {
        return new AnsiLite(BACKGROUND_YELLOW, s);
    }

    public static AnsiLite bgBlue(Object... s) {
        return new AnsiLite(BACKGROUND_BLUE, s);
    }

    public static AnsiLite bgMagenta(Object... s) {
        return new AnsiLite(BACKGROUND_MAGENTA, s);
    }

    public static AnsiLite bgCyan(Object... s) {
        return new AnsiLite(BACKGROUND_CYAN, s);
    }

    public static AnsiLite bgWhite(Object... s) {
        return new AnsiLite(BACKGROUND_WHITE, s);
    }

    public static AnsiLite bright(Object... s) {
        return new AnsiLite(HIGH_INTENSITY, s);
    }

    public static AnsiLite dim(Object... s) {
        return new AnsiLite(LOW_INTENSITY, s);
    }

    public static AnsiLite reverse(Object... s) {
        return new AnsiLite(REVERSE_VIDEO, s);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (Object o : this.s) {
            if (isEnabled()) {
                sb.append(this.code);
                sb.append(o);
                sb.append(RESET);
            } else {
                sb.append(o);
            }
        }
        return sb.toString();
    }

    private boolean isEnabled() {

        /* Allow ANSI support to be forced on or off (by setting -Dvisibleassertions.ansi.enabled=true|false) */
        if (System.getProperty("visibleassertions.ansi.enabled") != null) {
            return Boolean.getBoolean("visibleassertions.ansi.enabled");
        }

        /* Emulate behaviour of Jansi library for compatibility */
        if (Boolean.getBoolean("jansi.strip")) {
            return false;
        }

        if (Boolean.getBoolean("jansi.passthrough")) {
            return true;
        }

        if (Boolean.getBoolean("ansi.passthrough")) {
            return true;
        }

        if (Boolean.getBoolean("jansi.force")) {
            return true;
        }

        return isUnderIDEA() || isUnderMaven() || isUnderGradle() || isTty();
    }
}
