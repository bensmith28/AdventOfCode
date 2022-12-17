package Year2022

import Year2021.*
import Year2021.Day15.move
import util.asResourceFile

object Day12 {
    val input = "/Year2022/Day12.txt".asResourceFile().readLines()

    fun List<String>.parseMap(): Grid = this.map { line ->
        line.map { char ->
            when (char) {
                'S' -> 0
                'E' -> 'z'.code - 'a'.code
                else -> char.code - 'a'.code
            }
        }.toIntArray()
    }.toTypedArray()

    fun List<String>.findChar(char: Char): Coordinate = this.mapIndexed { y, row ->
        val x = row.indexOf(char)
        x to y
    }.single { it.first != -1 }

    fun Grid.isValid(start: Coordinate, dest: Coordinate) = dest.first >= 0 && dest.second >= 0 &&
            dest.second < this.size && dest.first < this.first().size &&
            this[dest] <= this[start] + 1

    fun findBestRouteScore(heightGrid: Grid, start: Coordinate, end: Coordinate): Int {
        val bestAttempts = Array(heightGrid.size) { IntArray(heightGrid.first().size) { Int.MAX_VALUE } }

        val attempts = ArrayDeque<Year2021.Day15.Attempt>()
        attempts.add(Year2021.Day15.Attempt(start))

        while(attempts.isNotEmpty()) {
            val attempt = attempts.removeFirst()
            if( attempt.score < bestAttempts[attempt.location]) {
                bestAttempts[attempt.location] = attempt.score
                Year2021.Day15.Move.values().forEach { move ->
                    val newLocation = attempt.location.move(move)
                    if(heightGrid.isValid(attempt.location, newLocation)) {
                        attempts.add(
                            Year2021.Day15.Attempt(
                                newLocation,
                                attempt.score + 1
                            )
                        )
                    }
                }
            }
        }

        return bestAttempts[end]
    }

    fun Grid.findAllStartingPoints() = this.indices.flatMap { y -> this[y].indices.map { x -> x to y } }
        .filter { this[it] == 0 }

    @JvmStatic
    fun main(args: Array<String>) {
        val grid = input.parseMap()
        val start = input.findChar('S')
        val end = input.findChar('E')

        val part1 = findBestRouteScore(grid, start, end)
        println("Part 1: $part1")

        val starts = grid.findAllStartingPoints()
        val part2 = starts.minOf { alternateStart -> findBestRouteScore(grid, alternateStart, end) }

        println("Part 2: $part2")
    }
}