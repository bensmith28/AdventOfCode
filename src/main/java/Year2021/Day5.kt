package Year2021

import util.asResourceFile
import kotlin.math.max
import kotlin.math.min

object Day5 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = "/Year2021/Day5.txt".asResourceFile().readLines().map { Line.parse(it) }

        val part1 = VentMap(lines.filter { it.isHorizontal || it.isVertical }).overlapped.size

        println("Part1: $part1")

        val part2 = VentMap(lines).overlapped.size

        println("Part2: $part2")
    }

    class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        companion object {
            private val pattern = """(\d+),(\d+) -> (\d+),(\d+)""".toRegex()
            fun parse(raw: String): Line {
                val (x1, y1, x2, y2) = pattern.matchEntire(raw)!!.destructured
                return Line(
                    x1.toInt(), y1.toInt(), x2.toInt(), y2.toInt()
                )
            }
        }

        val isHorizontal = y1 == y2
        val isVertical = x1 == x2
        val points: List<Pair<Int, Int>> = when {
            isHorizontal -> if(x1 < x2) { x1..x2 } else { x1 downTo x2 }.map { it to y1 }
            isVertical -> if(y1 < y2) { y1..y2 } else { y1 downTo y2 }.map { x2 to it }
            else -> {
                val xs = if(x1 < x2) x1..x2 else x1 downTo x2
                val ys = if(y1 < y2) y1..y2 else y1 downTo y2
                xs.zip(ys)
            }
        }
    }

    class VentMap(lines: List<Line>) {
        val overlapped: Set<Pair<Int,Int>> = lines.flatMap { it.points }.let { points ->
            val overlapped = mutableSetOf<Pair<Int,Int>>()
            val found = mutableSetOf<Pair<Int,Int>>()

            points.forEach { p ->
                if( !found.add(p) ) overlapped.add(p)
            }
            overlapped
        }
    }

}