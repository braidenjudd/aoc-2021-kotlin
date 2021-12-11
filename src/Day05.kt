import kotlin.math.abs

data class MapPoint(
    val x: Int,
    val y: Int
) {
    companion object {
        fun fromStringCoords(coords: String): MapPoint = MapPoint(
            coords.split(",")[0].toInt(),
            coords.split(",")[1].toInt(),
        )
    }

    override fun toString(): String {
        return "[${this.x}, ${this.y}]"
    }
}

class VentMap(
    private val maxXY: MapPoint,
) {
    companion object {
        fun getMapToFitLines(lines: List<Pair<MapPoint, MapPoint>>): VentMap {
            val allPoints = lines
                .map { listOf(it.first, it.second) }
                .flatten()

            val maxX = allPoints.maxOfOrNull { it.x } ?: 0
            val maxY = allPoints.maxOfOrNull { it.y } ?: 0

            return VentMap(MapPoint(maxX, maxY))
        }
    }

    private val map: Array<IntArray> = Array(maxXY.y + 1) { IntArray(maxXY.x + 1) { 0 } }

    fun addOnlyStraightLines(start: MapPoint, end: MapPoint) = addLine(start, end, false)

    fun addLines(start: MapPoint, end: MapPoint) = addLine(start, end, true)

    private fun addLine(start: MapPoint, end: MapPoint, allowDiagonal: Boolean) {
        if (start.x == end.x) {
            drawLine(
                start,
                end,
                abs(start.y - end.y),
                getCoordinateDirection(start.x, end.x),
                getCoordinateDirection(start.y, end.y)
            )
        }

        if (start.y == end.y) {
            drawLine(
                start,
                end,
                abs(start.x - end.x),
                getCoordinateDirection(start.x, end.x),
                getCoordinateDirection(start.y, end.y)
            )
        }

        if (allowDiagonal && abs(start.x - end.x) == abs(start.y - end.y)) {
            drawLine(
                start,
                end,
                abs(start.x - end.x),
                getCoordinateDirection(start.x, end.x),
                getCoordinateDirection(start.y, end.y)
            )
        }
    }

    private fun drawLine(start: MapPoint, end: MapPoint, steps: Int, xDir: Int, yDir: Int) {
        IntRange(0, steps).forEach { step ->
            map[start.y + (step * yDir)][start.x + (step * xDir)]++
        }
    }

    private fun getCoordinateDirection(start: Int, end: Int): Int =
        when {
            start > end -> -1
            end > start -> 1
            else -> 0
        }

    fun getPointsByCondition(condition: (Int) -> Boolean): List<MapPoint> {
        val points = mutableListOf<MapPoint>()

        map.forEachIndexed { y, row ->
            row.forEachIndexed { x, value ->
                if (condition(value)) {
                    points.add(MapPoint(x, y))
                }
            }
        }

        return points
    }

    override fun toString(): String {
        val sb = StringBuilder()

        map.forEach { row ->
            row.forEach {
                sb.append(it)
            }
            sb.append("\n")
        }

        return sb.toString()
    }
}

fun main() {
    fun List<String>.getLines(): List<Pair<MapPoint, MapPoint>> = this.map {
        val points = it.split(" -> ")
        val start = MapPoint.fromStringCoords(points[0])
        val end = MapPoint.fromStringCoords(points[1])
        Pair(start, end)
    }

    fun part1(input: List<String>): Int {
        val lines = input.getLines()
        val map = VentMap.getMapToFitLines(lines)

        lines.forEach {
            map.addOnlyStraightLines(it.first, it.second)
        }

        return map.getPointsByCondition { it >= 2 }.size
    }

    fun part2(input: List<String>): Int {
        val lines = input.getLines()
        val map = VentMap.getMapToFitLines(lines)

        lines.forEach {
            map.addLines(it.first, it.second)
        }

        return map.getPointsByCondition { it >= 2 }.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("day05_test")
    part1(testInput).checkAndPrint(5)
    part2(testInput).checkAndPrint(12)

    val input = readInput("day05")
    part1(input).checkAndPrint(4745)
    part2(input).checkAndPrint(18442)
}
