package Year2020

import java.io.File
import kotlin.math.abs

object Day12 {

    val instructions = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day12.txt")
        .readLines().flatMap { line ->
            line.split(",").map { Instruction.parse(it.trim()) }
        }

    @JvmStatic
    fun main(args: Array<String>) {
        val finalDestination = getDestination(instructions)
        println("Ferry is ${finalDestination.distanceFromStart} blocks away")

        // 2075 too high p1
    }

    fun getDestination(instructions: List<Instruction>): Destination {
        return instructions.fold(Destination()) { start, ins ->
            getNextDestination(start, ins)
        }
    }

    fun getNextDestination(start: Destination, instruction: Instruction): Destination {
        var distanceNorth = start.distanceNorth
        var distanceEast = start.distanceEast
        var waypointDistanceNorth = start.waypointDistanceNorth
        var waypointDistanceEast = start.waypointDistanceEast

        when(instruction.command) {
            "N" -> waypointDistanceNorth += instruction.argument
            "S" -> waypointDistanceNorth -= instruction.argument
            "E" -> waypointDistanceEast += instruction.argument
            "W" -> waypointDistanceEast -= instruction.argument
            "L", "R" -> {
                val turnDegrees = when(instruction.command) {
                    "L" -> instruction.argument
                    "R" -> 360 - instruction.argument
                    else -> throw IllegalStateException()
                }
                val (cos, sin) = when(turnDegrees) {
                    0 -> 1 to 0
                    90 -> 0 to 1
                    180 -> -1 to 0
                    270 -> 0 to -1
                    else -> throw IllegalStateException("Bad turn")
                }
                waypointDistanceEast = start.waypointDistanceEast * cos - start.waypointDistanceNorth * sin
                waypointDistanceNorth = start.waypointDistanceNorth * cos + start.waypointDistanceEast * sin
            }
            "F" -> {
                distanceNorth += instruction.argument * waypointDistanceNorth
                distanceEast += instruction.argument * waypointDistanceEast
            }
            else -> throw IllegalArgumentException("Bad turn: ${instruction.command}")
        }

        return Destination(distanceNorth, distanceEast, waypointDistanceNorth, waypointDistanceEast)
    }

    data class Destination(
        val distanceNorth: Int = 0,
        val distanceEast: Int = 0,
        val waypointDistanceNorth: Int = 1,
        val waypointDistanceEast: Int = 10
    ) {
        val distanceFromStart = abs(distanceNorth) + abs(distanceEast)
    }

    data class Instruction(val command: String, val argument: Int) {
        companion object {
            fun parse(string: String): Instruction {
                val match = "(N|S|E|W|L|R|F)(\\d+)".toRegex().matchEntire(string)
                    ?: throw IllegalArgumentException("Bad Instruction Match: $string")

                return Instruction(match.groupValues[1], match.groupValues[2].toInt())
            }
        }
    }
}