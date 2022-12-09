package Year2022

import Year2021.Coordinate
import Year2021.Day15.parseMap
import Year2021.Grid
import util.asResourceFile

object Day8 {
    val input = "/Year2022/Day8.txt".asResourceFile().readLines().parseMap()

    operator fun Grid.get(location: Coordinate): Int = this[location.first][location.second]

    private fun Grid.allLocations(): List<Coordinate> = this.indices.flatMap { y -> this.first().indices.map { x ->
        x to y
    }}

    private fun Coordinate.findEdgewardTreesOn(grid: Grid): List<List<Coordinate>> {
        val height = grid.size
        val width = grid.first().size
        val (x,y) = this
        return buildList {
            add(buildList {
                (x - 1 downTo  0).forEach { x1 -> add(x1 to y) } // Left
            })
            add(buildList {
                (x + 1 until width).forEach { x1 -> add(x1 to y) } // Right
            })
            add(buildList {
                (y - 1 downTo 0).forEach { y1 -> add(x to y1) } // Up
            })
            add(buildList {
                (y + 1 until height).forEach { y1 -> add(x to y1) } // Down
            })
        }
    }

    private fun Coordinate.isVisibleIn(grid: Grid): Boolean {
        return this.findEdgewardTreesOn(grid).any { trees -> trees.all { other -> grid[other] < grid[this]}}
    }

    fun <T> Iterable<T>.takeUntil(func: (T) -> Boolean): List<T> {
        return buildList{
            val iter = this@takeUntil.iterator()
            while(iter.hasNext()) {
                val next = iter.next()
                add(next)
                if(func(next)) break
            }
        }
    }

    fun Coordinate.scenicScore(grid: Grid): Int {
        val myHeight = grid[this]
        val distances = findEdgewardTreesOn(grid).map { trees ->
            trees.takeUntil { tree -> grid[tree] >= myHeight }.count()
        }
        return distances.fold(1) { score, distance -> score * distance }
    }

    fun countAllVisibleTrees(grid: Grid): Int {
        return grid.allLocations().count { it.isVisibleIn(grid) }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val part1 = countAllVisibleTrees(input)
        println("Part 1: $part1")

        val part2 = input.allLocations().maxOf { it.scenicScore(input) }
        println("Part 2: $part2")
    }
}