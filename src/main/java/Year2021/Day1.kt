package Year2021

import util.asResourceFile

object Day1 {
    val input = "/Year2021/Day1.txt".asResourceFile().readLines().map { it.trim().toInt() }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: Depth increased ${howManyIncreases(input)} times")
        println("Part 2: Depth increased ${howManyIncreases(input, 3)} times")
    }

    fun howManyIncreases(depths: List<Int>, window: Int = 1) =
        depths.windowed(window).map { it.sum() }
            .windowed(2).count { (a, b) -> b > a }
}