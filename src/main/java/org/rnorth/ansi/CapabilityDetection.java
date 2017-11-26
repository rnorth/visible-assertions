package org.rnorth.ansi;

import com.sun.jna.Platform;

/**
 * Simple terminal capability detection.
 */
public class CapabilityDetection {
    private static final int STDOUT_FILENO = 1;

    private CapabilityDetection() {
        // Utility class - don't allow construction;
    }

    public static boolean isUnderIDEA() {
        // Rough check for whether or not we're running in IDEA
        String classPath = System.getProperty("java.class.path");
        return classPath.contains("idea_rt.jar");
    }

    public static boolean isUnderMaven() {
        // Rough check for whether or not we're running in a Maven build
        return  findClass("org.apache.maven.surefire.booter.ForkedBooter") ||
                findClass("org.codehaus.plexus.classworlds.launcher.Launcher");
    }

    public static boolean isUnderGradle() {
        // Rough check for whether or not we're running in a Gradle build
        for (String key : System.getenv().keySet()) {
            if (key.startsWith("JAVA_MAIN_CLASS")) {
                final String value = System.getenv(key);
                if (value != null && value.contains("org.gradle")) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean findClass(String className) {
        try {
            Class.forName(className);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static boolean isTty() {
        // Don't attempt to check on Windows
        if (Platform.isWindows()) {
            return false;
        }

        // Check native isatty using JNA. If we fail for any reason, assume no TTY and carry on.
        try {
            return CLib.INSTANCE.isatty(STDOUT_FILENO) != 0;
        } catch (Throwable ignored) {
            return false;
        }
    }
}

