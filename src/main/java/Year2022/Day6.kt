package Year2022

import util.asResourceFile

object Day6 {
    fun String.findStartMarker(markerLength: Int = 4): Int =
        this.windowed(markerLength).asSequence().mapIndexed { i, segment ->
            (i + markerLength) to segment.toSet()
        }.firstOrNull { it.second.size == markerLength }?.first ?: error("No marker found")

    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2022/Day6.txt".asResourceFile().readLines().single()

        println("Part 1: ${input.findStartMarker()}")
        println("Part 2: ${input.findStartMarker(14)}")
    }
}