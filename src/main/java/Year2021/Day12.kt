package Year2021

import util.asResourceFile

object Day12 {
    @JvmStatic
    fun main(args: Array<String>) {
        val routes = "/Year2021/Day12.txt".asResourceFile().readLines().let { Routes.parse(it) }

        val part1 = findAllPaths(routes)
        println("Part 1: $part1")

        val part2 = findAllPaths(routes, 2)
        println("Part 2: $part2")
    }

    fun findAllPaths(routes: Routes, smallCaveLimit: Int = 1): Int {
        val paths = mutableSetOf(Path(listOf("start")))
        val done = mutableSetOf<Path>()
        while( paths.isNotEmpty() ) {
            val next = paths.first { !it.isDone() }
            paths.remove(next)
            routes.edges.getValue(next.rooms.last()).map { room ->
                next.moveTo(room)
            }.filter { it.isValid(smallCaveLimit) }.partition { it.isDone() }.let { (forDone, inProgress) ->
                done.addAll(forDone)
                paths.addAll(inProgress)
            }
        }
        return done.size
    }

    class Routes(val edges: Map<String, Set<String>>) {
        companion object {
            fun parse(lines: List<String>): Routes {
                val edges = mutableMapOf<String, MutableSet<String>>()
                lines.map {
                    val (start, end) = it.split("-")
                    edges.getOrPut(start) { mutableSetOf() }.add(end)
                    edges.getOrPut(end) { mutableSetOf() }.add(start)
                }
                return Routes(edges)
            }
        }
    }

    class Path(val rooms: List<String>) {
        fun isValid(smallCaveLimit: Int): Boolean {
            val visits = mutableMapOf<String, Int>()
            rooms.filter { it.lowercase() == it }.forEach { smallRoom ->
                visits[smallRoom] = (visits[smallRoom] ?: 0) + 1
            }

            return visits.getOrDefault("start", 0) <= 1 &&
                    visits.getOrDefault("end", 0) <= 1 &&
                    visits.filterValues { it > smallCaveLimit }.isEmpty() &&
                    visits.filterValues { it in 2..smallCaveLimit }.size <= 1
        }

        fun isDone(): Boolean {
            return rooms.first() == "start" && rooms.last() == "end"
        }

        fun moveTo(room: String) = Path(rooms + room)

        private val hash = rooms.joinToString(",")

        override fun equals(other: Any?): Boolean {
            return other is Path && hash == other.hash
        }

        override fun hashCode(): Int {
            return hash.hashCode()
        }

        override fun toString(): String {
            return hash
        }
    }
}