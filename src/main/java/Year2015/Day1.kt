package main.kotlin.Year2015

import java.io.File
object Day1 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day1.txt")

    fun main() {
        var currentFloor = 0
        var position = 1
        val positionsInBasement = mutableListOf<Int>()
        input.forEachLine { line ->
            line.forEach {
                if (it == '(') currentFloor++
                else currentFloor--

                if (currentFloor < 0) positionsInBasement.add(position)
                position++
            }
        }

        println("Santa ended up on floor $currentFloor")
        println("Santa first went into the basement at step ${positionsInBasement.minOrNull()}")
    }
}
