package Year2016

import java.io.File
import kotlin.math.abs

object Day1 {

    val instructions = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2016/Day1.txt")
        .readLines().flatMap { line ->
            line.split(",").map { Instruction.parse(it.trim()) }
        }

    @JvmStatic
    fun main(args: Array<String>) {
        val finalDestination = getDestination(instructions)
        println("HQ is ${finalDestination.distanceFromStart} blocks away")

        // 376 too high
        // 252 is correct

        val repeatedLocation = findRepeatedDestination(instructions)

        println("Repeated location: $repeatedLocation")
        println("  distance of ${repeatedLocation.distanceFromStart} blocks")

        // 255 is too high
    }

    fun findRepeatedDestination(instructions: List<Instruction>): Destination {

        val instructionSteps = instructions.flatMap { ins ->
            val steps = (2..ins.distance).map { Instruction("Step", 1) }
            listOf(ins.copy(distance = 1)).plus(steps)
        }

        var location = Destination()
        val previousDestinations = mutableSetOf<Destination>()
        val insIter = instructionSteps.iterator()

        while(insIter.hasNext() && previousDestinations.add(location.copy(heading = 0))) {
            location = getNextDestination(location, insIter.next())
        }

        return location
    }


    fun getDestination(instructions: List<Instruction>): Destination {
        return instructions.fold(Destination()) { start, ins ->
            getNextDestination(start, ins)
        }
    }

    fun getNextDestination(start: Destination, instruction: Instruction): Destination {
        val heading = (when (instruction.turn) {
            "L" -> 270
            "R" -> 90
            "Step" -> 0
            else -> throw IllegalArgumentException("Bad turn: ${instruction.turn}")
        } + start.heading) % 360

        val distanceNorth = when (heading) {
            0 -> start.distanceNorth + instruction.distance
            180 -> start.distanceNorth - instruction.distance
            else -> start.distanceNorth
        }

        val distanceEast = when (heading) {
            90 -> start.distanceEast + instruction.distance
            270 -> start.distanceEast - instruction.distance
            else -> start.distanceEast
        }

        return Destination(heading, distanceNorth, distanceEast)
    }

    data class Destination(val heading: Int = 0, val distanceNorth: Int = 0, val distanceEast: Int = 0) {
        val distanceFromStart = abs(distanceNorth) + abs(distanceEast)
    }

    data class Instruction(val turn: String, val distance: Int) {
        companion object {
            fun parse(string: String): Instruction {
                val match = "(L|R)(\\d+)".toRegex().matchEntire(string)
                    ?: throw IllegalArgumentException("Bad Instruction Match: $string")

                return Instruction(match.groupValues[1], match.groupValues[2].toInt())
            }
        }
    }
}