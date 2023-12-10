package nbt

/**
 * Data types for the [NBT Format](https://minecraft.wiki/w/NBT_format#Data_types)
 */
enum class DataType {
	/**
	 * A signed 8-bit integer, ranging from -128 to 127 (inclusive).
	 */
	Byte,

	/**
	 * NBT has no boolean data type, but byte value 0 and 1 can be represented as true, false. When a byte field is used as a boolean value, icon is shown.
	 */
	Bool,

	/**
	 * A signed 16-bit integer, ranging from -32,768 to 32,767 (inclusive).
	 */
	Short,

	/**
	 * A signed 32-bit integer, ranging from -2,147,483,648 and 2,147,483,647 (inclusive).
	 */
	Int,

	/**
	 * A signed 64-bit integer, ranging from -9,223,372,036,854,775,808 to 9,223,372,036,854,775,807 (inclusive).
	 */
	Long,

	/**
	 * A 32-bit, single-precision floating-point number, ranging from -3.4E+38 to +3.4E+38.
	 *
	 * See [IEEE floating point](https://en.wikipedia.org/wiki/IEEE_floating_point) for details.
	 */
	Float,

	/**
	 * A 64-bit, double-precision floating-point, ranging from -1.7E+308 to +1.7E+308.
	 *
	 * See [IEEE floating point](https://en.wikipedia.org/wiki/IEEE_floating_point) for details.
	 */
	Double,

	/**
	 * A sequence of characters.
	 */
	NBTString,

	/**
	 * An ordered list of tags. The tags must be of the same type, determined by the first tag in the list.
	 */
	NBTList,

	/**
	 * An ordered list of attribute-value pairs.
	 *
	 * Each tag can be of any type.
	 */
	Compound,

	/**
	 * An ordered list of 8-bit integers. Note that [B;1b,2b,3b] and [1b,2b,3b] are considered as different type, the second one is a [DataType.List].
	 */
	ByteArray,

	/**
	 * An ordered list of 32-bit integers. Note that [I;1,2,3] and [1,2,3] are considered as different type, the second one is a [DataType.List].
	 */
	IntArray,

	/**
	 * An ordered list of 64-bit integers. Note that [L;1l,2l,3l] and [1l,2l,3l] are considered as different type, the second one is a [DataType.List].
	 */
	LongArray
}