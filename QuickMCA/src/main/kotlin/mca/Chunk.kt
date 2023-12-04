package mca

import java.io.BufferedInputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.RandomAccessFile
import util.CompressionType
import util.readByteAsInt


class Chunk(private val lastUpdate: Int) {
	@OptIn(ExperimentalStdlibApi::class)
	fun loadChunk(file: RandomAccessFile, offset: Int, sectors: Byte): Chunk {
		// Move to the offset
		file.seek(4096 * offset.toLong())

		// Get length of remaining chunk data as 4 bytes
		var actualChunkDataLength: Int = file.readByteAsInt() shl 24
		actualChunkDataLength = actualChunkDataLength or file.readByteAsInt() shl 16
		actualChunkDataLength = actualChunkDataLength or file.readByteAsInt() shl 8
		actualChunkDataLength = actualChunkDataLength or file.readByteAsInt()

		// Minecraft adds padding to a multiple of 4096B in length
		val padding = 4096 - (actualChunkDataLength % 4096)

		println("Chunk is $actualChunkDataLength bytes long and contains $padding bytes of padding")

		// Get compression type
		val compressionType = CompressionType.getFromID(file.readByte())
			?: throw IOException("Failed to determine compression type")

		// Read chunk data from file, and decompress it on the fly
		val bis = BufferedInputStream(compressionType.decompress(FileInputStream(file.fd)))
		val data = ByteArray(actualChunkDataLength)
		for(i in 0..<actualChunkDataLength) {
			data[i] = bis.read().toByte()
		}





		TODO("Return actual chunk instance")
	}
}