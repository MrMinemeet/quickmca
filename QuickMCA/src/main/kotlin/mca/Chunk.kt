package mca

import java.io.RandomAccessFile
import util.CompressionType
import util.readByteAsInt


class Chunk(private val lastUpdate: Int) {

	fun loadChunk(file: RandomAccessFile, offset: Int, sectors: Byte): Chunk {
		// Get length of remaining chunk data as 4 bytes
		var chunkLength: Int = file.readByteAsInt() shl 24
		chunkLength = chunkLength or file.readByteAsInt() shl 16
		chunkLength = chunkLength or file.readByteAsInt() shl 8
		chunkLength = chunkLength or file.readByteAsInt()

		// Get compression type
		var compressionType = CompressionType.getFromID(file.readByte())

		TODO("Load and decompress actual chunk data")

		TODO("Return actual chunk instance")
	}
}