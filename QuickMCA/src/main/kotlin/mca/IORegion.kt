package mca

import java.io.File
import java.io.IOException
import java.io.RandomAccessFile
import util.readByteAsInt

/**
 * Performs read and write operations of region files (r.X.Y.mca)
 */
object IORegion {

	fun readRegion(regionFile: File): Region {
		if (regionFile.isDirectory)
			throw IllegalArgumentException("File is can't be a directory")
		if (!regionFile.name.endsWith(".mca"))
			throw IllegalArgumentException("The file must end with '.mca' !")

		val (rPosX, rPosY) = regionPositionFromName(regionFile.name)

		RandomAccessFile(regionFile, "r").use { inputFile ->
			// --- Get Data from header

			// Retrieve chunk offsets
			val headerData = loadHeader(inputFile)
		}


		return Region(rPosX, rPosY)
	}

	data class HeaderData(val offset: IntArray)

	/**
	 * Reads the header of a region file. The header contains the offset of each chunk in the file.
	 * The offset represents the position of the chunk in the file.
	 */
	private fun loadHeader(file: RandomAccessFile): HeaderData {
		val offsets = IntArray(CHUNKS_IN_REGION)

		// Move to beginning of region file
		file.seek(0)

		// Read the 1024 chunks, which consist of 4 byte each
		for(i in offsets.indices) {
			var offset: Int = file.readByteAsInt() shl 16 // Read and shift 16 bit to the left
			offset = offset or (file.readByteAsInt() shl 8) // Read and shift 8 bit to the left
			offsets[i] = offset or (file.readByteAsInt()) // Just rea, no shift

			val sector = file.readByte() // Skip the last byte (
			println("Offset: $offset, Sector: $sector")
		}

		return HeaderData(offsets)

	}

	/**
	 * Convert the name of a region file to a Pair which holds the position.
	 * E.g.: r.-3.4.mca -> Pair(-3, 4)
	 */
	private fun regionPositionFromName(name: String): Pair<Int, Int> {
		val splitName = name.split('.')
		return Pair(splitName[1].toInt(), splitName[2].toInt())
	}
}