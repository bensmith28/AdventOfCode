package Year2020

import java.io.File

object Day9 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day9.txt")
    val windowDepth = 25

    @JvmStatic
    fun main(args: Array<String>) {
        val codes = input.readLines().map { it.toLong() }

        val firstError = codes.windowed(windowDepth + 1).first { window ->
            val preamble = window.take(windowDepth)
            val subject = window.last()

            preamble.none { a -> preamble.minus(a).contains(subject - a) }
        }.last()

        println("First error is $firstError")

        val weaknessWindow = codes.indices.asSequence().map { start ->
            (start+1..codes.size).asSequence().map { end ->
                codes.subList(start, end)
            }
        }.flatten().first { it.sum() == firstError }

        val weakness = weaknessWindow.minOrNull()!! + weaknessWindow.maxOrNull()!!

        println("The weakness is $weakness")
    }
}
