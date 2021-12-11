fun main() {
    fun part1(input: List<String>): Int {
        val columnarInput = input.asColumns()

        val gammaRate = columnarInput
            .joinToString("") { if (it.isMostlyOnes()) "1" else "0" }
            .toInt(2)

        val epsilonRate = columnarInput
            .joinToString("") { if (it.isMostlyOnes()) "0" else "1" }
            .toInt(2)

        return gammaRate * epsilonRate
    }

    fun part2(input: List<String>): Int {
        val o2Rating = reduceInputByCommonBit(input, { if (it.isMostlyOnes()) '1' else '0' }).toInt(2)
        val co2Rating = reduceInputByCommonBit(input, { if (it.isMostlyOnes()) '0' else '1' }).toInt(2)
        return o2Rating * co2Rating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day03_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day03")
    println(part1(input))
    println(part2(input))
}

private fun reduceInputByCommonBit(input: List<String>, evalFunction: (String) -> Char, columnIndex: Int = 0): String {
    val correctBit = evalFunction(input.asColumns()[columnIndex])
    val inputWithCorrectBit = input.filter { it[columnIndex] == correctBit }

    return if (columnIndex == input.first().length - 1 || inputWithCorrectBit.size == 1) {
        inputWithCorrectBit.first()
    } else {
        reduceInputByCommonBit(inputWithCorrectBit, evalFunction, columnIndex + 1)
    }
}

private fun String.isMostlyOnes(): Boolean =
    (this.filter { it == '1' }.length * 2) >= this.length
