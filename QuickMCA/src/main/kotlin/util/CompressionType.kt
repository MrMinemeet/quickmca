package util

import java.io.InputStream
import java.io.OutputStream
import java.util.zip.DeflaterOutputStream
import java.util.zip.GZIPInputStream
import java.util.zip.GZIPOutputStream
import java.util.zip.InflaterInputStream

/**
 * Compression types of payload according to <a href="https://minecraft.wiki/w/Region_file_format#Payload">Region file format (Payload)</a>
 */
enum class CompressionType(
	val value: Byte,
	private val compressor: (OutputStream) -> OutputStream,
	private val decompressor: (InputStream) -> InputStream
) {
	GZIP(1, { GZIPOutputStream(it) }, { GZIPInputStream(it) }),
	ZLIB(2, { DeflaterOutputStream(it) }, { InflaterInputStream(it) }),
	UNCOMPRESSED(3, { data -> data }, { data -> data });

	fun compress(toCompress: OutputStream): OutputStream = compressor.invoke(toCompress)
	fun decompress(toDecompress: InputStream): InputStream = decompressor.invoke(toDecompress)

	companion object {
		/**
		 * Returns the enum entry matching the value.
		 */
		fun getFromID(value: Byte): CompressionType? = entries.firstOrNull { t -> t.value == value }
	}
}