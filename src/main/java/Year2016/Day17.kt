package Year2016

import Year2015.Day4.toHex


object Day17 {
    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: ${shortestPath("veumntbg")}")
        println("Part 2: ${longestPath("veumntbg").length}")
    }

    fun shortestPath(initial: String) =
        testedPath(initial) { paths -> paths.firstOrNull { p -> p.isFinished() }}

    fun longestPath(initial: String) =
        testedPath(initial) { paths ->
            paths.lastOrNull { p -> p.isFinished() }
        }

    fun testedPath(initial: String, test: (Sequence<String>) -> String?): String {
        return sequence {
            val queue = mutableListOf("")
            while(queue.isNotEmpty()) {
                val current = queue.removeAt(0)
                if( !current.isFinished() ) {
                    allowedDirections(initial, current).forEach { d ->
                        val next = current + d.c
                        queue.add(next)
                        yield(current + d.c)
                    }
                }
            }
        }.let(test) ?: "No valid path"
    }

    private val meansOpen = setOf('b', 'c', 'd', 'e', 'f')

    fun allowedDirections(initial: String, path: String = ""): Set<Direction> {
        val hash = Year2015.Day4.md5(initial + path).toHex().take(4)
        val (x, y) = path.toLocation()
        return sequence {
            if( hash[0] in meansOpen && y > 0 ) yield(Direction.UP)
            if( hash[1] in meansOpen && y < 3 ) yield(Direction.DOWN)
            if( hash[2] in meansOpen && x > 0 ) yield(Direction.LEFT)
            if( hash[3] in meansOpen && x < 3 ) yield(Direction.RIGHT)
        }.toSet()
    }

    enum class Direction(val c: Char) {
        UP('U'), DOWN('D'), LEFT('L'), RIGHT('R');
    }

    private fun String.toLocation() = this.fold(0 to 0) { (x, y), c ->
        when(Direction.values().first { it.c == c }) {
            Direction.UP -> x to y - 1
            Direction.DOWN -> x to y + 1
            Direction.LEFT -> x - 1 to y
            Direction.RIGHT -> x + 1 to y
        }
    }

    private fun String.isFinished() = this.toLocation() == 3 to 3
}