package Year2016

import java.io.File

object Day8 {
    @JvmStatic
    fun main(args: Array<String>) {
        val instructions = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2016/Day8.txt")
            .readLines().map { Instruction.parse(it) }

        val screen = Screen()
        instructions.forEach { it.apply(screen) }

        println(screen.toString())
        println("${screen.litPixels} pixels lit")
    }

    class Instruction(val command: String, val arg1: Int, val arg2: Int) {
        companion object {
            fun parse(line: String): Instruction {
                val match = "(rect |rotate row y=|rotate column x=)(\\d+)(?:x| by )(\\d+)"
                    .toRegex().matchEntire(line)
                    ?: throw IllegalArgumentException("Bad instruction match: $line")
                return Instruction(
                    match.groupValues[1],
                    match.groupValues[2].toInt(),
                    match.groupValues[3].toInt()
                )
            }
        }

        fun apply(screen: Screen) {
            when(command) {
                "rect " -> screen.rect(arg1, arg2)
                "rotate row y=" -> repeat(arg2) { screen.rotateRow(arg1) }
                "rotate column x=" -> repeat(arg2) { screen.rotateColumn(arg1) }
            }
        }
    }

    class Screen(val width: Int = 50, val height: Int = 6) {
        val pixels = (0 until height).map {
            (0 until width).map { false }.toMutableList()
        }.toMutableList()

        val litPixels get() = pixels.sumBy { row -> row.filter { pixel -> pixel }.count() }

        fun rect(w: Int, h: Int) {
            (0 until w).forEach { x ->
                (0 until h).forEach { y ->
                    pixels[y][x] = true
                }
            }
        }

        private fun List<Boolean>.rotate() =
            listOf(this.last()).plus(this.take(this.size - 1)).toMutableList()

        fun rotateRow(y: Int) {
            pixels[y] = pixels[y].rotate()
        }

        fun rotateColumn(x: Int) {
            pixels.map { it[x] }.rotate().forEachIndexed { y, pixel ->
                pixels[y][x] = pixel
            }
        }

        override fun toString(): String {
            return pixels.joinToString("\n") { row ->
                row.joinToString("") { pixel ->
                    when(pixel) {
                        true -> "#"
                        else -> "."
                    }
                }
            }
        }
    }
}