fun main() {
    fun String.extractMagnitudeFromCommand(command: String): Int {
        return this.removePrefix("$command ").toInt()
    }

    fun part1(input: List<String>): Int {
        var x = 0
        var y = 0

        input.forEach { command ->
            when {
                command.startsWith("forward") -> x += command.extractMagnitudeFromCommand("forward")
                command.startsWith("up") -> y -= command.extractMagnitudeFromCommand("up")
                command.startsWith("down") -> y += command.extractMagnitudeFromCommand("down")
            }
        }

        return x * y
    }

    fun part2(input: List<String>): Int {
        var x = 0
        var y = 0
        var aim = 0

        input.forEach { command ->
            when {
                command.startsWith("forward") -> {
                    val magnitude = command.extractMagnitudeFromCommand("forward")
                    x += magnitude
                    y += aim * magnitude
                }
                command.startsWith("up") -> aim -= command.extractMagnitudeFromCommand("up")
                command.startsWith("down") -> aim += command.extractMagnitudeFromCommand("down")
            }
        }

        return x * y
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day02_test")
    println(part1(testInput))
    println(part2(testInput))

    val input = readInput("day02")
    println(part1(input))
    println(part2(input))
}
