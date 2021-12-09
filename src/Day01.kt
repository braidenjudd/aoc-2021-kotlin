fun main() {
    fun part1(input: List<String>): Int {
        if (input.size < 2) return 0

        var last = input[0].toInt()
        var total = 0

        input.subList(1, input.size).forEach {
            if (it.toInt() > last) total++

            last = it.toInt()
        }

        return total
    }

    fun part2(input: List<String>): Int {
        val windowedList = mutableListOf<String>()

        input.forEachIndexed { i, s ->
            if (i + 2 < input.size) {
                windowedList.add(listOf(s, input[i + 1], input[i + 2]).sumOf { it.toInt() }.toString())
            }
        }

        return part1(windowedList)
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day01")
    println(part1(input))
    println(part2(input))
}
