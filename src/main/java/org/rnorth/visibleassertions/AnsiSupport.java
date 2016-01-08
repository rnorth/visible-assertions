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

import jline.TerminalFactory;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintStream;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * @author rnorth
 */
@SuppressWarnings("UseOfSystemOutOrSystemErr")
public class AnsiSupport {

    /**
     * Package visible for testing
     */
    static PrintStream writer = System.out;

    protected synchronized static void initialize() {

        try {
            Class.forName("com.intellij.rt.execution.application.AppMain");
            // Running in IntelliJ - disable ANSI output
            Ansi.setEnabled(false);
        } catch (ClassNotFoundException e) {
            // Not running in IntelliJ - assume TTY detection works correctly
        }

        AnsiConsole.systemInstall();
    }

    public static void ansiPrintf(String s, Object... args) {
        writer.printf(ansi().render(s).toString(), args);
    }

    public static int terminalWidth() {
        return TerminalFactory.get().getWidth();
    }

}
