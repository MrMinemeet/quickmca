package mca

import java.io.File
import java.io.RandomAccessFile
import util.readByteAsInt

/**
 * Performs read and write operations of region files (r.X.Y.mca)
 */
object IORegion {
	private const val SECTOR_SIZE_BYTE: Int = 4096  // Byte
	private const val SECTOR_SIZE: Int = SECTOR_SIZE_BYTE / 1024 // KiB

	fun readRegion(regionFile: File): Region {
		if (regionFile.isDirectory)
			throw IllegalArgumentException("File is can't be a directory")
		if (!regionFile.name.endsWith(".mca"))
			throw IllegalArgumentException("The file must end with '.mca' !")

		val (rPosX, rPosY) = regionPositionFromName(regionFile.name)

		RandomAccessFile(regionFile, "r").use { file ->
			// Get Data from header (first 8KiB)
			val headerData = loadHeader(file)
			println(headerData)
		}

		return Region(rPosX, rPosY)
	}

	/**
	 * Reads the header (first 8KiB) of a region file. The header contains the offset of each chunk in the file.
	 * The offset represents the position of the chunk in the file.
	 * @param file The region file
	 * @return The header data
	 * @see <a href="https://minecraft.wiki/w/Region_file_format#Header">Region file format (Header)</a>
	 */
	private fun loadHeader(file: RandomAccessFile): HeaderData {
		// Retrieve chunk offsets
		val offsets = IntArray(CHUNKS_IN_REGION)
		val sectorCounts = ByteArray(CHUNKS_IN_REGION)
		val timestamps = IntArray(CHUNKS_IN_REGION)

		// Header begins at 0
		file.seek(0L)

		/* Read the 1024 chunks header data
		 * Each chunk has 8KiB of data split into two equal sized parts.
		 * The first chunk contains 3 byte of offset and one for sector
		 */
		for(i in offsets.indices) {
			// Read the first three byte for the offset
			var offset: Int = file.readByteAsInt() shl 16 // Read and shift 16 bit to the left
			offset = offset or (file.readByteAsInt() shl 8) // Read and shift 8 bit to the left
			offsets[i] = offset or (file.readByteAsInt()) // Just read, no shift

			// Read 4th byte as sector
			sectorCounts[i] = file.readByte()
		}
		// The second chunk contains a timestamp
		for (i in offsets.indices) {
			timestamps[i] = file.readInt()
		}
		return HeaderData(offsets, sectorCounts, timestamps)
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

/**
 * Holds the data of a region file header
 */
private data class HeaderData(
	val offsets: IntArray,
	val sectorCounts: ByteArray,
	val timestamps: IntArray
) {
	override fun equals(other: Any?): Boolean {
		if (this === other) return true
		if (other !is HeaderData) return false

		if (!offsets.contentEquals(other.offsets) ||
			!sectorCounts.contentEquals(other.sectorCounts) ||
			!timestamps.contentEquals(other.timestamps)
		) return false

		return true
	}

	override fun hashCode(): Int {
		var result = offsets.contentHashCode()
		result = 31 * result + sectorCounts.contentHashCode()
		result = 31 * result + timestamps.contentHashCode()
		return result
	}

	override fun toString(): String {
		return """
			Offset: ${offsets.joinToString(", ")}
			Sector count: ${sectorCounts.joinToString(", ")}
			Timestamps: ${timestamps.joinToString(", ")}
		""".trimIndent()
	}
}