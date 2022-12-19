package Year2022

import util.asResourceFile

object Day16 {
    @JvmStatic
    fun main(args: Array<String>) {
        val valves = "/Year2022/Day16.txt".asResourceFile().readLines().map { Valve.parse(it) }

        val part1 = findGreatestPressureRelease(valves)
        println("Part 1: $part1")

        val part2 = findGreatestPressureReleaseWithHelp(valves)
        println("Part 2: $part2")
    }

    fun List<Valve>.generateDistanceMap(): Map<String, Map<String, Int>> {
        val ret = mutableMapOf<String, MutableMap<String, Int>>()

        this.forEach { origin ->
            val destinationMap = origin.tunnels.associateWith { 1 }.toMutableMap()
            var distance = 2
            val secondaryOrigins = origin.tunnels.toMutableSet()
            while (destinationMap.size < this.size - 1) {
                val secondaryDestinations = this.filter { it.id in secondaryOrigins }.flatMap { it.tunnels }
                    .filter { it != origin.id }.toSet()
                secondaryDestinations.forEach { destination ->
                    destinationMap.putIfAbsent(destination, distance)
                }
                secondaryOrigins.clear()
                secondaryOrigins.addAll(secondaryDestinations)
                distance++
            }
            ret[origin.id] = destinationMap
        }

        return ret
    }

    fun findGreatestPressureRelease(valves: List<Valve>): Int {
        val valveMap = valves.associateBy { it.id }
        val distanceMap = valves.generateDistanceMap()
        val bestAttempts = mutableMapOf<String, Attempt>()
        val attempts = ArrayDeque<Attempt>()
        attempts.add(Attempt("AA", 30))

        while (attempts.isNotEmpty()) {
            val attempt = attempts.removeFirst()
            if (attempt.timeRemaining >= 0 && attempt > bestAttempts.getOrDefault(attempt.key, Attempt.WORST)) {
                bestAttempts[attempt.key] = attempt
                distanceMap[attempt.location]!!.filterKeys { it !in attempt.valvesOpen }
                    .forEach { (destination, distance) ->
                        attempts.add(
                            Attempt(
                                location = destination,
                                timeRemaining = attempt.timeRemaining - (distance + 1),
                                unSortedValvesOpen = attempt.unSortedValvesOpen + destination,
                                pressureReleased = attempt.pressureReleased + attempt.flowRate * (distance + 1),
                                flowRate = attempt.flowRate + valveMap[destination]!!.flowRate
                            )
                        )
                    }
            }
        }

        return bestAttempts.values.maxOf { it.score }
    }

    fun findGreatestPressureReleaseWithHelp(valves: List<Valve>): Int {
        val valveMap = valves.associateBy { it.id }
        val distanceMap = valves.generateDistanceMap()
        var bestAttempt = Attempt.WORST
        val attempts = ArrayDeque<Attempt>()
        distanceMap["AA"]!!.forEach { (helperDestination, travelTime) ->
            attempts.add(
                Attempt(
                    location = "AA",
                    timeRemaining = 26,
                    helperDestination = helperDestination,
                    helperTimeToOpen = travelTime + 1,
                )
            )
        }
        val bestFlow = valves.fold(0) { acc, valve -> acc + valve.flowRate }
        fun Attempt.cutoff() = pressureReleased + bestFlow * timeRemaining

        while (attempts.isNotEmpty()) {
            val attempt = attempts.removeLast()
            if( attempt.score > bestAttempt.score ) bestAttempt = attempt
            if (attempt.timeRemaining >= 0 && attempt.cutoff() >= bestAttempt.cutoff() - 5) {
                distanceMap[attempt.location]!!
                    .filterKeys { it != attempt.helperDestination && it !in attempt.valvesOpen }
                    .forEach { (destination, distance) ->
                        val myTimeToOpen = distance + 1
                        val closestDestination =
                            if (myTimeToOpen < attempt.helperTimeToOpen) destination
                            else attempt.helperDestination
                        val furthestDestination =
                            if (myTimeToOpen >= attempt.helperTimeToOpen) destination
                            else attempt.helperDestination
                        val timeDelta = minOf(myTimeToOpen, attempt.helperTimeToOpen)
                        val timeRemainder = maxOf(myTimeToOpen, attempt.helperTimeToOpen) - timeDelta
                        attempts.add(
                            Attempt(
                                location = closestDestination,
                                timeRemaining = attempt.timeRemaining - timeDelta,
                                helperDestination = furthestDestination,
                                helperTimeToOpen = timeRemainder,
                                unSortedValvesOpen = attempt.unSortedValvesOpen + closestDestination,
                                pressureReleased = attempt.pressureReleased + attempt.flowRate * (timeDelta),
                                flowRate = attempt.flowRate + valveMap[closestDestination]!!.flowRate
                            )
                        )
                    }
            }
        }

        return bestAttempt.score
    }


    data class Valve(
        val id: String,
        val flowRate: Int,
        val tunnels: Set<String>
    ) {
        companion object {
            private val pattern =
                """Valve (\w+) has flow rate=(\d+); tunnels? leads? to valves? ((?:\w+(?:, )?)+)""".toRegex()

            fun parse(line: String): Valve {
                val (id, flowString, tunnelsString) = pattern.matchEntire(line)?.destructured
                    ?: error("Bad match: $line")
                return Valve(
                    id,
                    flowString.toInt(),
                    tunnelsString.split(", ").toSet()
                )
            }
        }
    }

    data class Attempt(
        val location: String,
        val timeRemaining: Int,
        val helperDestination: String = "",
        val helperTimeToOpen: Int = 0,
        val unSortedValvesOpen: Set<String> = emptySet(),
        val pressureReleased: Int = 0,
        val flowRate: Int = 0
    ) : Comparable<Attempt> {
        val valvesOpen = unSortedValvesOpen.toSortedSet()
        val key: String = timeRemaining.toString()
        val score = pressureReleased + timeRemaining * flowRate

        override fun compareTo(other: Attempt): Int =
            if (score != other.score) score.compareTo(other.score)
            else timeRemaining.compareTo(other.timeRemaining)

        companion object {
            val WORST = Attempt("", 0, "", 0, emptySet(), 0, 0)
        }
    }


}