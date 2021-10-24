package main.kotlin.Year2015

import java.io.File

object Day8 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day8.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = input.readLines().map { Line(it) }
        val codeToMemory = lines.sumOf { line ->
            line.codeCount - line.memoryCount
        }
        val encodedToCode = lines.sumOf { line ->
            line.encodedCount - line.codeCount
        }

        println("Difference code to memory is $codeToMemory")
        println("Difference encoded to original is $encodedToCode")
    }

    class Line(val original: String) {
        val simpleEscapes =  "\\\\(\\\\|\\\")".toRegex().findAll(original).toList().size
        val hexEscapes = "\\\\x[0-9a-f]{2}".toRegex().findAll(original).toList().size
        val endQuotes = 2
        val codeCount = original.length
        val memoryCount = original.length - simpleEscapes - hexEscapes * 3 - endQuotes
        val encoded = original.replace("\\", "\\\\").replace("\"", "\\\"").let { "\"$it\"" }
        val encodedCount = encoded.length
    }
}