package Year2022

import util.asResourceFile

object Day4 {
    private val inputPattern = """(\d+)-(\d+),(\d+)-(\d+)""".toRegex()
    fun String.parse(): Pair<IntRange, IntRange> {
        val (start1, end1, start2, end2) = inputPattern.matchEntire(this)?.destructured
            ?: throw IllegalArgumentException("Bad input: $this")
        return (start1.toInt()..end1.toInt()) to (start2.toInt()..end2.toInt())
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val pairs = "/Year2022/Day4.txt".asResourceFile().readLines().map { it.parse() }

        val part1 = pairs.count {
            val set1 = it.first.toSet()
            val set2 = it.second.toSet()
            val intersect = it.first.intersect(it.second)
            intersect == set1 || intersect == set2
        }

        println("Part 1: $part1")

        val part2 = pairs.count {
            it.first.intersect(it.second).isNotEmpty()
        }

        println("Part 2: $part2")
    }

}