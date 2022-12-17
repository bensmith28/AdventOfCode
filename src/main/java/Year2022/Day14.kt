package Year2022

import Year2021.Coordinate
import util.asResourceFile

object Day14 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2022/Day14.txt".asResourceFile().readLines()
        val scan = Scan.parse(input)

        val part1 = scan.fillSand()
        println("Part 1: $part1")

        val scan2 = Scan.parse(input)
        scan2.addFloor()
        val part2 = scan2.fillSand()
        println("Part 2: $part2")
    }

    enum class SandMove(override val deltaX: Int, override val deltaY: Int): Movement {
        DOWN(0, 1),
        DOWN_LEFT(-1, 1),
        DOWN_RIGHT(1, 1)
    }

    class Scan(
        private val grid: MutableMap<Coordinate, Cell>
    ) {
        fun addFloor() {
            val y = grid.filterValues { it == Cell.ROCK }.keys.maxOf { it.second } + 2
            val x1 = grid.filterValues { it == Cell.ROCK }.keys.minOf { it.first } - y
            val x2 = grid.filterValues { it == Cell.ROCK }.keys.maxOf { it.first } + y
            (x1..x2).forEach { x ->
                grid[x to y] = Cell.ROCK
            }
        }

        fun fillSand(): Int {
            val abyss = grid.filterValues { it == Cell.ROCK }.keys.maxOf { it.second } + 1
            var count = 0
            var target = dropPoint
            while(grid[dropPoint] != Cell.SAND && target.second < abyss) {
                when {
                    grid[target + SandMove.DOWN] == null -> target += SandMove.DOWN
                    grid[target + SandMove.DOWN_LEFT] == null -> target += SandMove.DOWN_LEFT
                    grid[target + SandMove.DOWN_RIGHT] == null -> target += SandMove.DOWN_RIGHT
                    else -> {
                        count++
                        grid[target] = Cell.SAND
                        target = dropPoint
                    }
                }
            }
            return count
        }

        companion object {
            val dropPoint = 500 to 0
            fun parse(input: List<String>): Scan {
                val grid: MutableMap<Coordinate, Cell> = mutableMapOf()
                input.forEach { line ->
                    val coordinates = line.split(" -> ")
                        .map { coordString -> coordString.split(",").let { it[0].toInt() to it[1].toInt() } }
                        .iterator()

                    var start = coordinates.next()
                    while (coordinates.hasNext()) {
                        val end = coordinates.next()
                        when {
                            start.first == end.first ->
                                (minOf(start.second, end.second)..maxOf(start.second, end.second)).forEach { y ->
                                    grid[start.first to y] = Cell.ROCK
                                }
                            start.second == end.second ->
                                (minOf(start.first, end.first)..maxOf(start.first, end.first)).forEach { x ->
                                    grid[x to start.second] = Cell.ROCK
                                }
                        }
                        start = end
                    }
                }
                return Scan(grid)
            }
        }
    }

    enum class Cell {
        SAND, ROCK
    }
}

interface Movement {
    val deltaX: Int
    val deltaY: Int
}

operator fun Coordinate.plus(move: Movement): Coordinate = (this.first + move.deltaX) to (this.second + move.deltaY)