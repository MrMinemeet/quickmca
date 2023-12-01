package mca

/** Chunks held per dimension */
const val CHUNKS_PER_REGION_DIM: Byte = 32
/** Chunks held per region */
const val CHUNKS_IN_REGION: Int = CHUNKS_PER_REGION_DIM * CHUNKS_PER_REGION_DIM

/**
 * Stores a block of 32x32 chunks
 */
class Region(val posX: Int, val posY: Int) {
	constructor (position: Pair<Int, Int>): this(position.first, position.second)

	private val chunks = arrayOfNulls<Chunk?>(CHUNKS_IN_REGION)

	operator fun set(chunkPosX: Int, chunkPosY: Int, chunk: Chunk) {
		chunks[chunkPosX * chunkPosY] = chunk
	}

	operator fun get(chunkPosX: Int, chunkPosY: Int): Chunk? {
		return chunks[chunkPosX * chunkPosY]
	}

	/**
	 * Provides a comparison of two regions based on their position
	 * Comparison is based on the X and Y position in a 2D grid like fashion.
	 * Starting from the top left and moving right. After the row is complete, the next row is started.
	 */
	operator fun compareTo(that: Region): Int {
		val cmpX = this.posX.compareTo(that.posX)
		return if (cmpX != 0) {
			cmpX
		} else {
			this.posY.compareTo(that.posY)
		}
	}
}