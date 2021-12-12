package Year2021

import util.asResourceFile

object Day11 {
    @JvmStatic
    fun main(args: Array<String>) {
        val cave = Cave.parse("/Year2021/Day11.txt")
        val part1 = cave.step(100)
        println("Part 1: $part1")

        val secondCave = Cave.parse("/Year2021/Day11.txt")
        val steps = findSync(secondCave)
        println("Part 2: $steps")
    }

    fun findSync(cave: Cave): Int {
        var stepCount = 0
        while( !cave.isSynced() ) {
            stepCount++
            cave.step()
        }
        return stepCount
    }


    class Cave(grid: Map<Int, Map<Int, Int>>) {
        companion object {
            fun parse(resource: String = "/Year2021/Day11.txt") =
                resource.asResourceFile().readLines().let { parse(it) }

            fun parse(lines: List<String>): Cave {
                val grid = mutableMapOf<Int, MutableMap<Int, Int>>()
                lines.forEachIndexed { y, row ->
                    row.forEachIndexed { x, c ->
                        grid.getOrPut(x) { mutableMapOf() }[y] = "$c".toInt()
                    }
                }
                return Cave(grid)
            }
        }

        val grid: List<Octo> = grid.flatMap { (x, column) -> column.map { (y, charge) -> Octo(charge, x to y) } }

        fun step(count: Int = 1): Int {
            return (1..count).sumOf {
                grid.forEach { octo -> octo.bump() }
                grid.count { octo -> octo.relax()}
            }
        }

        fun isSynced(): Boolean = grid.all { octo -> octo.charge == 0 }

        inner class Octo(var charge: Int, val location: Pair<Int,Int>) {
            fun bump() {
                charge++
                if(charge == 10) {
                    //flash
                    val (x,y) = location
                    val adj = listOf(
                        x - 1 to y - 1,
                        x - 1 to y,
                        x - 1 to y + 1,
                        x to y - 1,
                        x to y + 1,
                        x + 1 to y - 1,
                        x + 1 to y,
                        x + 1 to y + 1
                    )

                    grid.filter { octo -> adj.contains(octo.location) }.forEach { octo -> octo.bump() }
                }
            }

            fun relax(): Boolean {
                return if(charge > 9) {
                    charge = 0
                    true
                } else false
            }
        }
    }
}