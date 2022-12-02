package Year2022

import util.asResourceFile

object Day1 {
    @JvmStatic
    fun main(args: Array<String>) {
        val elves = parseElves("/Year2022/Day1.txt".asResourceFile().readLines())
        println(elves)
        val sorted = elves.map { elf -> elf.sum() }.sortedDescending()
        println("Part 1: ${sorted.first()}")
        println("Part 2: ${sorted.take(3).sum()}")
    }

    fun parseElves(input: List<String>): List<List<Int>> {
        val elves = mutableListOf(mutableListOf<Int>())
        input.forEach { line ->
            if(line.isBlank()) elves.add(mutableListOf())
            else(elves.last().add(line.toInt()))
        }
        return elves
    }
}