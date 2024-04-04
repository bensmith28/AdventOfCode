package Year2016

import java.io.File

object Day6 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2016/Day6.txt")
            .readLines()
        val message = correctErrors(input)
        println("Message: $message")
    }

    fun correctErrors(lines: List<String>): String {
        val columns = lines.first().indices.map { mutableListOf<Char>() }
        lines.forEach { l ->
            l.forEachIndexed { index, c ->
                columns[index].add(c)
            }
        }
        return columns.map {
            Column(it).signal
        }.joinToString("")
    }

    class Column(noise: List<Char>) {
        val signal = noise.fold(emptyMap<Char, Int>()) { profile, c ->
            profile.plus(
                c to ((profile[c] ?: 0) + 1)
            )
        }.entries.minByOrNull { (_, count) -> count }?.key!!
    }
}
