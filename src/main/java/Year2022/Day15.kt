package Year2022

import Year2021.Coordinate
import util.asResourceFile
import kotlin.math.abs

object Day15 {
    private val searchAreaLength = 4000000
    @JvmStatic
    fun main(args: Array<String>) {
        val sensors = "/Year2022/Day15.txt".asResourceFile().readLines().map { Sensor.parse(it) }

        val part1 = sensors.knownEmptyOnRow(2000000).size
        println("Part 1: $part1")

        val part2 = sensors.findBeacon(searchAreaLength).tuningFrequency()
        println("Part 2: $part2")
    }

    class Sensor(
        val location: Coordinate,
        val beacon: Coordinate
    ) {
        companion object {
            private val pattern = """Sensor at x=(-?\d+), y=(-?\d+): closest beacon is at x=(-?\d+), y=(-?\d+)""".toRegex()
            fun parse(line: String): Sensor {
                val (x1, y1, x2, y2) = pattern.matchEntire(line)?.destructured
                    ?: error("Bad match: $line")
                return Sensor(
                    x1.toInt() to y1.toInt(),
                    x2.toInt() to y2.toInt()
                )
            }
        }

        val range = location - beacon

        fun knownEmptyOnRow(y: Int): Set<Coordinate> {
            val deltaY = abs(location.second - y)
            return ((location.first + deltaY - range) .. (location.first + range - deltaY))
                .map { x -> x to y }.toSet() - beacon
        }

        fun covers(other: Coordinate): Boolean = other - location <= range
        fun widthOfCoveredSegment(other: Coordinate): Int {
            return if(!covers(other)) 0
            else range - abs(other.second - location.second) + (location.first - other.first) + 1
        }
    }

    /**
     * Manhattan distance between two coordinates
     */
    operator fun Coordinate.minus(other: Coordinate): Int = abs(first - other.first) + abs(second - other.second)

    fun Collection<Sensor>.knownEmptyOnRow(y: Int): Set<Coordinate> =
        this.fold(emptySet()) { acc, nextSensor -> acc + nextSensor.knownEmptyOnRow(y) }

    fun Collection<Sensor>.findBeacon(searchAreaLength: Int): Coordinate {
        val seq = this.asSequence()
        var (x, y) = 0 to 0
        fun increment(distance: Int) {
            x += distance
            if(x > searchAreaLength) {
                x = 0
                y++
            }
        }
        while (y <= searchAreaLength) {
            val inc = seq.map { sensor -> sensor.widthOfCoveredSegment(x to y) }.firstOrNull { it > 0 }
                ?: return x to y
            increment(inc)
        }
        error("Could not find a solution")
    }

    fun Coordinate.tuningFrequency(): Long = this.first * 4000000L + this.second
}