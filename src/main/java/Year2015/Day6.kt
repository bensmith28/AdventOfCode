package main.kotlin.Year2015

import java.io.File

object Day6 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day6-sample.txt")

    val grid = (0..999).map { x ->
        x to (0..999).map { y ->
            y to 0
        }.toMap().toMutableMap()
    }.toMap()

    val pattern = "(turn on|turn off|toggle) (\\d+),(\\d+) through (\\d+),(\\d+)".toRegex()

    @JvmStatic
    fun main(args: Array<String>) {
        input.forEachLine { line ->
            val match = pattern.matchEntire(line) ?: throw IllegalArgumentException("Bad match to: $line")
            val command = match.groupValues[1]
            val x1 = match.groupValues[2].toInt()
            val y1 = match.groupValues[3].toInt()
            val x2 = match.groupValues[4].toInt()
            val y2 = match.groupValues[5].toInt()

            val points = Rectangle(x1, y1, x2, y2).points
            when(command) {
                "turn on" -> points.forEach { (x, y) ->
                    grid.getValue(x)[y] = grid.getValue(x).getValue(y) + 1
                }
                "turn off" -> points.forEach { (x,y) ->
                    grid.getValue(x)[y] = listOf(grid.getValue(x).getValue(y) - 1, 0).max() ?: 0
                }
                "toggle" -> points.forEach { (x,y) ->
                    grid.getValue(x)[y] = grid.getValue(x).getValue(y) + 2
                }
            }
        }

        val brightness = grid.values.sumBy { column ->
            column.values.sum()
        }

        println("Total brightness is $brightness")
    }
}

class Rectangle(x1: Int, y1: Int, x2: Int, y2: Int) {
    val points = (x1..x2).flatMap { x ->
        (y1..y2).map { y ->
            x to y
        }
    }
}