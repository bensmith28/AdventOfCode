package Year2021

import util.asResourceFile

object Day9 {

    @JvmStatic
    fun main(args: Array<String>) {
        val map = CaveMap.parse("/Year2021/Day9.txt".asResourceFile().readLines())

        println("Part 1: ${map.getRisk()}")
        println("Part 2: ${map.basinScore()}")
    }

    class CaveMap(val grid: Map<Int, Map<Int, Int>>) {
        companion object {
            fun parse(lines: List<String>): CaveMap {
                val grid = mutableMapOf<Int,MutableMap<Int,Int>>()
                lines.forEachIndexed { y, line ->
                    line.windowed(1).forEachIndexed { x, tile ->
                        grid.getOrPut(x) { mutableMapOf() }[y] = tile.toInt()
                    }
                }
                return CaveMap(grid)
            }
        }

        private fun getLowPoints(): List<Pair<Int,Int>> {
            return grid.entries.flatMap { (x, column) ->
                column.filter { (y, height) -> getAdjacentHeight(x,y).all { adj -> adj > height } }.map { (y, _) -> x to y }
            }
        }

        fun getLowHeights(): List<Int> = getLowPoints().map { (x,y) -> grid.getValue(x).getValue(y) }

        fun getRisk(): Int = getLowHeights().sumOf { it + 1 }

        private fun getAdjacent(x: Int, y: Int): List<Pair<Int,Int>> {
            return listOf(
                x - 1 to y,
                x + 1 to y,
                x to y - 1,
                x to y + 1
            ).filter { (x1, y1) -> grid.containsKey(x1) && grid.getValue(x1).containsKey(y1) }
        }

        private fun getAdjacentHeight(x: Int, y: Int) = getAdjacent(x, y)
            .map { (x1, y1) -> grid.getValue(x1).getValue(y1) }

        private fun Pair<Int,Int>.flowsTo(): Pair<Int,Int> = getAdjacent(this.first, this.second)
            .minByOrNull { (x, y) -> grid.getValue(x).getValue(y) }!!

        fun findBasinSizes(): List<Int> {
            val result = getLowPoints().associateWith { mutableListOf<Pair<Int,Int>>() }
            grid.forEach { x, column ->
                column.filterValues { h -> h != 9 }.keys.forEach { y ->
                    generateSequence(x to y) { it.flowsTo() }.first { result.containsKey(it) }.also {
                        result[it]!!.add(x to y)
                    }
                }
            }
            return result.map { it.value.size }
        }

        fun basinScore() = findBasinSizes().sortedDescending().take(3).fold(1) { acc, size -> acc * size }
    }
}