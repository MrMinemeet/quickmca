package util

import java.io.RandomAccessFile

/**
 * Reads a byte from the file, and performs a bitwise AND operation with 0xFF.
 */
fun RandomAccessFile.readByteAsInt() = this.read() and 0xFF