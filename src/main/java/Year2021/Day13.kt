package Year2021

import util.asResourceFile

object Day13 {

    @JvmStatic
    fun main(args: Array<String>) {
        val (grid, folds) = "/Year2021/Day13.txt".asResourceFile().readLines().let { parse(it) }

        val foldedOnce = grid.fold(folds.first())
        println("Part 1: ${foldedOnce.dots.size}")

        val allFolds = folds.fold(grid) { g, next -> g.fold(next) }
        println("Part 2: \n$allFolds")
    }

    private val dotMatcher = """(\d+),(\d+)""".toRegex()
    private val foldMatcher = """fold along ([xy])=(\d+)""".toRegex()
    fun parse(lines: List<String>): Pair<Grid, List<Fold>> {
        val dots = mutableSetOf<Pair<Int,Int>>()
        val folds = mutableListOf<Fold>()
        lines.forEach { line ->
            dotMatcher.matchEntire(line)?.destructured?.let { (x, y) ->
                dots.add(x.toInt() to y.toInt())
            }
            foldMatcher.matchEntire(line)?.destructured?.let { (axis, location) ->
                folds.add(Fold(Fold.Axis.valueOf(axis.uppercase()), location.toInt()))
            }
        }
        return Grid(dots) to folds
    }

    class Grid(val dots: Set<Pair<Int,Int>>) {
        fun fold(fold: Fold): Grid = Grid(dots.map { (x, y) ->
            when(fold.axis) {
                Fold.Axis.Y -> x to (if(y > fold.location) 2 * fold.location - y else y)
                Fold.Axis.X -> (if(x > fold.location) 2 * fold.location - x else x) to y
            }
        }.toSet())

        override fun toString(): String {
            val horizontal = dots.maxOf { it.first }
            val vertical = dots.maxOf { it.second }
            return (0..vertical).joinToString("\n") { y ->
                (0..horizontal).joinToString("") { x ->
                    if(dots.contains(x to y)) "#" else "."
                }
            }
        }
    }

    class Fold(val axis: Axis, val location: Int) {
        enum class Axis { X, Y }
    }
}