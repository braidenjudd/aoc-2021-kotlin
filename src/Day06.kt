fun main() {
    fun countFishAfterDays(input: List<String>, daysToEvolve: Int): Long {
        var school = Array(9) { 0L }.toMutableList()
        val initialFish = input[0].toIntList()

        initialFish.forEach {
            school[it] = school[it] + 1
        }

        for (i in 1..daysToEvolve) {
            school = mutableListOf(
                school[1],
                school[2],
                school[3],
                school[4],
                school[5],
                school[6],
                school[7] + school[0],
                school[8],
                school[0],
            )
        }

        return school.sum()
    }

    fun part1(input: List<String>): Long = countFishAfterDays(input, 80)

    fun part2(input: List<String>): Long = countFishAfterDays(input, 256)

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day06_test")
    part1(testInput).checkAndPrint(5934)
    part2(testInput).checkAndPrint(26984457539)

    val input = readInput("day06")
    part1(input).checkAndPrint(352872)
    part2(input).checkAndPrint(1604361182149)
}
