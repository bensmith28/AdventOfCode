package Year2015

import java.io.File
import java.util.Collections.min

object Day14 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day14.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val time = 2503
        val reindeer = input.readLines().map { Reindeer(it) }
        val winner = reindeer.maxByOrNull { it.calcDistance(time) } ?: throw IllegalStateException()

        println("${winner.name} won with a distance of ${winner.calcDistance(time)}")

        val scoreboard = mutableMapOf<String, Int>()
        for(i in 1..time) {
            val distances = reindeer.map { r ->
                r.name to r.calcDistance(i)
            }
            val bestDistance = distances.sortedBy { (_, distance) -> distance }.last().second
            distances.filter { (_, d) -> d == bestDistance }.forEach { (name, _) ->
                scoreboard[name] = scoreboard.getOrDefault(name, 0) + 1
            }
        }

        val (secondWinner, points) = scoreboard.entries.maxByOrNull { (_, points) -> points }!!

        println("In the second race, $secondWinner won with $points points")
    }
}

class Reindeer(line: String) {
    val name: String
    val speedKmPerSec: Int
    val workSec: Int
    val restSec: Int

    init {
        val match = "(\\w+) can fly (\\d+) km/s for (\\d+) seconds, but then must rest for (\\d+) seconds."
            .toRegex().matchEntire(line) ?: throw IllegalArgumentException("Bad Reindeer Match")
        name = match.groupValues[1]
        speedKmPerSec = match.groupValues[2].toInt()
        workSec = match.groupValues[3].toInt()
        restSec = match.groupValues[4].toInt()
    }

    fun calcDistance(time: Int): Int {
        val fullIntervals = time / (workSec + restSec)
        val remainingSeconds = time % (workSec + restSec)
        return (fullIntervals * workSec + Math.min(remainingSeconds, workSec)) * speedKmPerSec
    }
}
