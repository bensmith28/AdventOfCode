package main.kotlin.Year2015

import java.io.File
import kotlin.math.pow
import kotlin.math.roundToInt

object Day9 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day9.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val graph = mutableMapOf<String, MutableMap<String, Int>>()

        input.forEachLine { line ->
            val match = "(\\w+) to (\\w+) = (\\d+)".toRegex().matchEntire(line) ?: throw IllegalArgumentException("Bad match: $line")
            val origin = match.groupValues[1]
            val destination = match.groupValues[2]
            val distance = match.groupValues[3].toInt()

            graph.getOrPut(origin) { mutableMapOf() }[destination] = distance
            graph.getOrPut(destination) { mutableMapOf() }[origin] = distance
        }

        val locations = graph.flatMap { (_, v) -> v.keys }.plus(graph.keys).toSet()

        println("Locations: $locations")

        fun attempt(): Int {
            return locations.shuffled().windowed(2).sumOf { (origin, destination) ->
                graph.getValue(origin).getValue(destination)
            }
        }

        (1..10).forEach { power ->
            val top = 10.0.pow(power).roundToInt()
            val result = (1..top).map { attempt() }.maxOrNull()
            println("Result $top: $result")
        }
    }
}
