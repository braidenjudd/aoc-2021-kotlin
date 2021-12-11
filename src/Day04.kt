class BingoBoard(numbers: List<String>) {
    var rows: MutableList<List<Int>>
    var columns: MutableList<List<Int>>

    init {
        rows = numbers.toIntLists()
        columns = rows.transpose().toMutableList()
    }

    fun play(number: Int): Boolean {
        rows.forEachIndexed { index, row ->
            if (row.contains(number)) {
                rows[index] = row.minus(number)

                if (rows[index].isEmpty()) return true
            }
        }

        columns.forEachIndexed { index, column ->
            if (column.contains(number)) {
                columns[index] = column.minus(number)

                if (columns[index].isEmpty()) return true
            }
        }

        return false
    }

    private fun List<String>.toIntLists() = this
        .map { row -> row.toListOfInts() }
        .toMutableList()

    fun getScore(lastCalled: Int) = rows.flatten().sum() * lastCalled

    override fun toString(): String {
        return rows.toString().plus("\n\n").plus(columns.toString())
    }
}

fun main() {
    fun part1(input: List<String>): Int {
        val numbersToPlay = input[0].split(",").map { it.toInt() }

        val boards = input.subList(2, input.size)
            .windowed(5, 6)
            .map { BingoBoard(it) }

        numbersToPlay.forEach { number ->
            boards.forEach { board ->
                if (board.play(number)) {
                    return board.getScore(number)
                }
            }
        }

        return 0
    }

    fun part2(input: List<String>): Int {
        val numbersToPlay = input[0].split(",").map { it.toInt() }

        val boards = input.subList(2, input.size)
            .windowed(5, 6)
            .map { BingoBoard(it) }
            .toMutableList()

        numbersToPlay.forEach { number ->
            val boardsThatHaveWon = mutableListOf<Int>()

            boards.forEachIndexed { index, board ->
                if (board.play(number)) {
                    boardsThatHaveWon.add(index)
                }

                if (boards.size - boardsThatHaveWon.size == 0) {
                    return board.getScore(number)
                }
            }

            boardsThatHaveWon.forEachIndexed { itemIndex, indexToRemove ->
                // as we remove items, the indexes shift by the number we have removed
                boards.removeAt(indexToRemove - itemIndex)
            }
        }

        return 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day04_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day04")
    println(part1(input))
    println(part2(input))
}
