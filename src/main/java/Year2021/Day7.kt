package Year2021

import util.asResourceFile
import kotlin.math.abs

object Day7 {
    @JvmStatic
    fun main(args: Array<String>) {
        val fleet =
            "/Year2021/Day7.txt".asResourceFile().readLines().single().split(",").map { it.toInt() }

        val part1 = minFuelToLineUp(fleet)
        println("Part 1: $part1")

        val part2 = minFuelToLineUp(fleet, Day7::increasingFuelToLocation)
        println("Part 2: $part2")
    }

    fun linearFuelToLocation(fleet: List<Int>, location: Int) = fleet.sumOf { l ->
        abs(l - location)
    }

    fun increasingFuelToLocation(fleet: List<Int>, location: Int) = fleet.sumOf { l ->
        (1..abs(l - location)).sum()
    }

    fun minFuelToLineUp(fleet: List<Int>, fuelFunction: (List<Int>, Int) -> Int = Day7::linearFuelToLocation): Int {
        val sorted = fleet.sorted()
        val median = sorted[fleet.size / 2]

        val fuelsForLocation = mutableMapOf<Int, Int>()
        fun fuelAt(location: Int) = fuelsForLocation.getOrPut(location) { fuelFunction(fleet, location) }

        val right = (median..sorted.last()).asSequence().windowed(2).takeWhile { (left, right) ->
            fuelAt(right) < fuelAt(left)
        }.lastOrNull()?.get(1) ?: median

        val left = (median downTo sorted.first()).asSequence().windowed(2).takeWhile { (right, left) ->
            fuelAt(left) < fuelAt(right)
        }.lastOrNull()?.get(1) ?: median

        return listOf(left, right).minOf { fuelAt(it) }
    }
}