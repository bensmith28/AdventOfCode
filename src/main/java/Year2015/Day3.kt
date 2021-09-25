package main.kotlin.Year2015

import java.io.File

object Day3 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day3.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        var santasPosition = Position()
        var robotsPosition = Position()
        val positions = mutableSetOf(Position())
        input.readLines().flatMap { line ->
            line.asIterable()
        }.forEachIndexed { index, c ->
            if(index % 2 == 0) {
                santasPosition = santasPosition.move(c)
                positions.add(santasPosition)
            } else {
                robotsPosition = robotsPosition.move(c)
                positions.add(robotsPosition)
            }
        }

        println("Santa and the robot visited ${positions.size} houses")
    }
}

data class Position(val x: Int = 0, val y: Int = 0) {
    fun move(move: Char): Position {
        return when(move) {
            '^' -> copy(y = y+1)
            'v' -> copy(y = y-1)
            '<' -> copy(x = x-1)
            '>' -> copy(x = x+1)
            else -> throw IllegalArgumentException("Bad move: $move")
        }
    }
}