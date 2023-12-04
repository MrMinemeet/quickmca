import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import mca.IORegion

fun main(args: Array<String>) {
	val executor = Executors.newFixedThreadPool(1)

	val startTime = System.currentTimeMillis()
	File("world/region").listFiles()
		?.forEach {
			executor.execute {
				IORegion.readRegion(it)
			}
		}

	executor.shutdown()
	executor.awaitTermination(1, TimeUnit.MINUTES)

	val endTime = System.currentTimeMillis()
	println("Time: ${endTime - startTime}ms")
}