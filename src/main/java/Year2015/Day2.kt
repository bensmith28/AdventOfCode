package main.kotlin.Year2015

import java.io.File

object Day2 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day2.txt")
    val matcher = "(\\d+)x(\\d+)x(\\d+)".toRegex()

    @JvmStatic
    fun main(args: Array<String>) {
        val presents = input.readLines().map { line ->
            val match = matcher.matchEntire(line) ?: throw IllegalArgumentException("Cannot parse: $line")
            Present(match.groupValues[1].toInt(), match.groupValues[2].toInt(), match.groupValues[3].toInt())
        }
        val paperNeeded = presents.sumOf { it.paperNeeded }
        val ribbonNeeded = presents.sumOf { it.ribbonNeeded }

        println("You need $paperNeeded square feet of wrapping paper")
        println("You need $ribbonNeeded feet of ribbon")
    }
}

class Present(l: Int, w: Int, h: Int) {
    val paperNeeded = 2 * l * w + 2 * w * h + 2 * h * l + (listOf(l * w, w * h, h * l).minOrNull() ?: 0)
    val ribbonNeeded = (listOf(2 * (l + w), 2 * (w + h), 2 * (h + l)).minOrNull() ?: 0) + l * w * h
}
