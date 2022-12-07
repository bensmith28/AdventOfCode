package Year2022

import util.asResourceFile

object Day3 {
    val rucksacks = "/Year2022/Day3.txt".asResourceFile().readLines()

    fun divideGroups(rucksacks: List<String>): List<List<String>> = rucksacks.windowed(3, 3)

    fun findBadge(group: List<String>) = group.fold(group.first().toSet()) { possibleBadges, nextRucksack ->
        possibleBadges.intersect(nextRucksack.toSet())
    }.single()

    fun sumOfBadges(rucksacks: List<String> = Day3.rucksacks) =
        divideGroups(rucksacks).sumOf { findBadge(it).priority() }

    fun getMisplacedItem(rucksack: String): Char {
        val firstHalf = rucksack.substring(0, rucksack.length / 2)
        val secondHalf = rucksack.substring(rucksack.length / 2)
        return firstHalf.toSet().intersect(secondHalf.toSet()).single()
    }

    fun Char.priority(): Int = when (this) {
        in 'a'..'z' -> this.code - 'a'.code + 1
        in 'A'..'Z' -> this.code - 'A'.code + 27
        else -> throw IllegalArgumentException("Bad character")
    }

    fun sumOfPriorities(rucksacks: List<String> = Day3.rucksacks) = rucksacks
        .sumOf { getMisplacedItem(it).priority() }

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: ${sumOfPriorities()}")
        println("Part 2: ${sumOfBadges()}")
    }
}