package org.rnorth.ansi;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * JNA accessor for native CLib.
 */
interface CLib extends Library {
    CLib INSTANCE = (CLib) Native.loadLibrary("c", CLib.class);

    int isatty(int fd);
}
