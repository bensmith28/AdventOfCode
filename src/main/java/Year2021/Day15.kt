package Year2021

import util.asResourceFile

object Day15 {
    val input = "/Year2021/Day15.txt".asResourceFile().readLines()

    fun List<String>.parseMap(): Array<IntArray> = this.map { line ->
        line.map { it.digitToInt() }.toIntArray()
    }.toTypedArray()

    operator fun Grid.get(location: Coordinate): Int = this[location.first][location.second]
    operator fun Grid.set(location: Coordinate, value: Int) {
        this[location.first][location.second] = value
    }

    class ExpandableGrid(
        private val grid: Array<IntArray>,
        expansionFactor: Int = 1
    ) {
        operator fun get(location: Coordinate): Int {
            val xSkips = location.first / grid.first().size
            val x = location.first % grid.first().size
            val ySkips = location.second / grid.size
            val y = location.second % grid.size
            var ret = grid[x][y] + xSkips + ySkips
            while(ret > 9) ret -= 9
            return ret
        }

        fun isValid(location: Coordinate) = location.first >= 0 && location.second >= 0 &&
                location.second < height && location.first < width

        val height = grid.size * expansionFactor
        val width = grid.first().size * expansionFactor
    }
    private fun Coordinate.move(move: Move) = (this.first + move.deltaX) to (this.second + move.deltaY)

    enum class Move(val deltaX: Int, val deltaY: Int) {
        UP(0, -1),
        DOWN(0, 1),
        LEFT(-1, 0),
        RIGHT(1, 0)
    }

    class Attempt(
        val location: Coordinate = 0 to 0,
        val score: Int = 0
    )

    fun findBestRouteScore(chitonGrid: ExpandableGrid): Int {
        val bestAttempts = Array(chitonGrid.height) { IntArray(chitonGrid.width) { Int.MAX_VALUE } }

        val attempts = ArrayDeque<Attempt>()
        attempts.add(Attempt())

        while(attempts.isNotEmpty()) {
            val attempt = attempts.removeFirst()
            if( attempt.score < bestAttempts[attempt.location]) {
                bestAttempts[attempt.location] = attempt.score
                Move.values().forEach { move ->
                    val newLocation = attempt.location.move(move)
                    if(chitonGrid.isValid(newLocation)) {
                        attempts.add(Attempt(
                            newLocation,
                            attempt.score + chitonGrid[newLocation]
                        ))
                    }
                }
            }
        }

        return bestAttempts.last().last()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val chitonGrid = ExpandableGrid(input.parseMap())

        val part1 = findBestRouteScore(chitonGrid)
        println("Part 1: $part1")

        val reallyBigChitonGrid = ExpandableGrid(input.parseMap(), 5)
        val part2 = findBestRouteScore(reallyBigChitonGrid)
        println("Part 2: $part2")
    }
}

typealias Grid = Array<IntArray>
typealias Coordinate = Pair<Int,Int>