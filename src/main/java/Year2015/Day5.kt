package main.kotlin.Year2015

import java.io.File

object Day5 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day5.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val niceCount1 = input.readLines().filter { line ->
            listOf(
                line.matches(".*[aeiou].*[aeiou].*[aeiou].*".toRegex()),
                line.matches(".*(.)(?=\\1).*".toRegex()),
                !line.matches(".*(ab|cd|pq|xy).*".toRegex())
            ).all { it }
        }.size

        val niceCount2 = input.readLines().filter { line ->
            listOf(
                line.matches(".*(.{2})(?=.*\\1).*".toRegex()),
                line.matches(".*(.)(?=.\\1).*".toRegex())
            ).all { it }
        }.size

        println("There are $niceCount1 nice strings, version 1")
        println("There are $niceCount2 nice strings, version 2")
    }
}