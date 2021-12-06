package Year2021

import util.asResourceFile

object Day6 {

    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2021/Day6.txt".asResourceFile().readLines()
            .single().split(",").map { Fish.labelled(it.toInt()) }

        val part1 = countFish(input, 80)
        println("Part 1: $part1")

        val part2 = countFish(input, 256)
        println("Part 2: $part2")
    }

    fun countFish(fish: List<Fish>, days: Int): Long {
        val answers = mutableMapOf<Fish, Long>()

        fun Fish.countProgeny(day: Int): Long = answers.getOrPut(this) {
            1 + produceFishUntil(day).sumOf { it.countProgeny(day) }
        }

        return fish.sumOf { f -> f.countProgeny(days) }
    }

    data class Fish(val adultOn: Int){

        companion object {
            fun bornOn(day: Int) = Fish(day + YOUTH)
            fun labelled(untilBirth: Int) = Fish(untilBirth - GESTATION + 1)
            private const val YOUTH = 2
            private const val GESTATION = 7
        }

        fun produceFishUntil(day: Int) = generateSequence(day) { it - 1 }.filter { (it - adultOn) % GESTATION == 0 }
            .takeWhile { it > adultOn }.map { bornOn(it) }
    }
}