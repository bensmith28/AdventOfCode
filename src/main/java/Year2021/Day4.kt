package Year2021

import util.asResourceFile

object Day4 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2021/Day4.txt".asResourceFile().readLines()

        val game = Game.parse(input)

        val part1 = game.findFirstWinner().let { (n, b) -> n * b.score() }
        println("Part 1: $part1")

        val part2 = game.findLastWinner().let { (n, b) -> n * b.score() }
        println("Part2: $part2")
    }

    class Game(val numbers: List<Int>, val boards: List<Board>) {
        companion object {
            fun parse(lines: List<String>): Game {
                val numbers = lines.first().split(",").map { it.toInt() }

                val boardLines = mutableListOf<List<String>>()
                val inProgress = mutableListOf<String>()
                lines.drop(2).plus("").forEach {
                    if (it.isBlank()) {
                        boardLines.add(inProgress.toList())
                        inProgress.clear()
                    } else inProgress.add(it)
                }
                val boards = boardLines.map { Board.parse(it) }

                return Game(numbers, boards)
            }
        }

        fun findFirstWinner(): Pair<Int, Board> {
            return numbers.asSequence().firstNotNullOf { n ->
                boards.firstOrNull { b -> b.also { it.playNumber(n) }.isWinner() }?.let { b -> n to b }
            }
        }

        fun findLastWinner(): Pair<Int, Board> {
            val boardsRemaining = boards.toMutableList()
            numbers.forEach { n ->
                boardsRemaining.forEach { it.playNumber(n) }
                if( boardsRemaining.singleOrNull()?.isWinner() == true ) return n to boardsRemaining.single()
                boardsRemaining.removeIf { it.isWinner() }
            }

            throw IllegalStateException("No winner")
        }
    }

    class Board private constructor(private val grid: List<List<Spot>>) {
        companion object {
            fun parse(lines: List<String>): Board {
                return lines.map { line ->
                    line.trim().split("""\s+""".toRegex()).map { Spot(it.trim().toInt()) }
                }.let { grid -> Board(grid) }
            }
        }

        private class Spot(val number: Int, var played: Boolean = false)

        fun playNumber(number: Int) {
            grid.flatten().forEach { spot ->
                if (spot.number == number) spot.played = true
            }
        }

        fun isWinner(): Boolean {
            if (grid.any { row -> row.all { spot -> spot.played } }) return true
            if (grid.first().indices.any { column -> grid.all { row -> row[column].played } }) return true
            //if (grid.first().indices.all { i -> grid[i][i].played }) return true
            //if (grid.first().indices.all { i -> grid[i][grid[i].indices.last - i].played }) return true

            return false
        }

        fun score(): Int {
            return grid.flatten().filter { spot -> !spot.played }.sumOf { spot -> spot.number }
        }
    }
}