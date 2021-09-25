package Year2015

import java.io.File

object Day18 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day18.txt")
    val steps = 100
//    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day18-sample.txt")
//    val steps = 5

    @JvmStatic
    fun main(args: Array<String>) {
        val initialGrid = input.readLines().mapIndexed { y, line ->
            line.mapIndexed { x, c ->
                when {
                    x == 0 && y == 0 -> true
                    x == 0 && y == line.length - 1 -> true
                    x == line.length - 1 && y == 0 -> true
                    x == line.length - 1 && y == line.length - 1 -> true
                    c == '.' -> false
                    c == '#' -> true
                    else -> throw IllegalArgumentException("Bad Light Char: $c")
                }
            }
        }

        val finalGrid = (1..steps).fold(initialGrid) { lastGrid, i ->
            lastGrid.mapIndexed { y, row ->
                row.mapIndexed { x, light ->
                    val onNeighbors = lastGrid.countNeighbors(x,y)
                    when {
                        x == 0 && y == 0 -> true
                        x == 0 && y == row.size - 1 -> true
                        x == lastGrid.size - 1 && y == 0 -> true
                        x == lastGrid.size - 1 && y == row.size - 1 -> true
                        light && (onNeighbors == 2 || onNeighbors == 3) -> true
                        !light && onNeighbors == 3 -> true
                        else -> false
                    }
                }
            }
        }

        val finalCount = finalGrid.flatten().filter { it }.size

        println("Final Count: $finalCount")
    }

    private fun List<List<Boolean>>.countNeighbors(x: Int, y: Int): Int {
        return listOf(x+1 to y+1, x+1 to y, x+1 to y-1,
            x to y+1, x to y-1,
            x-1 to y+1, x-1 to y, x-1 to y-1)
            .map { (x0,y0) -> this.getLight(x0,y0) }.filter { it }.size
    }

    private fun List<List<Boolean>>.getLight(x: Int, y: Int): Boolean {
        return if(x < 0 || x >= this.size || y < 0 || y >= this.first().size) {
            false
        } else {
            this[y][x]
        }
    }

    private fun List<List<Boolean>>.print(indent: String = "  ") {
        this.forEach { row ->
            val line = row.map { light -> if(light) '#' else '.' }.joinToString("")
            println("$indent$line")
        }
    }
}