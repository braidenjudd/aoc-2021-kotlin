import java.io.File
import java.math.BigInteger
import java.security.MessageDigest
import java.util.stream.IntStream
import kotlin.streams.toList

/**
 * Reads lines from the given input txt file.
 */
fun readInput(name: String) = File("src", "$name.txt").readLines()

/**
 * Converts string to md5 hash.
 */
fun String.md5(): String = BigInteger(1, MessageDigest.getInstance("MD5").digest(toByteArray())).toString(16)

fun <V, T : Collection<V>> List<T>.transpose(): List<List<V>> {
    val numberOfColumns = this.first().size
    val columns = IntRange(0, numberOfColumns - 1).map { mutableListOf<V>() }.toMutableList()

    this.forEach { row ->
        row.forEachIndexed { index, item ->
            columns[index].add(item)
        }
    }

    return columns
}

fun List<String>.asColumns(): List<String> = this
    .map { it.toList() }
    .transpose()
    .map { it.joinToString("") }

fun String.toListOfInts(): List<Int> = this
    .split(Regex("\\s+"))
    .filter { it.isNotBlank() }
    .map { value -> value.toInt() }

inline fun <reified T> T.checkAndPrint(expected: T) {
    check(this == expected)
    println(this)
}

fun String.toIntList() = this.split(",").map { it.toInt() }
