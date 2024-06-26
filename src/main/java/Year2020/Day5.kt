package main.kotlin.Year2020

import java.io.File

object Day5 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day5.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val seats = input.readLines().map { line ->
            Seat(line)
        }

        println("Highest Seat ID: ${seats.maxByOrNull { it.id }?.id}")

        val mine = seats.sortedBy { it.id }.windowed(2, 1, false).single { (first, second) ->
            second.id - first.id > 1
        }.let { (first, second) ->
            (second.id + first.id) / 2
        }

        println("My Seat ID is: $mine")
    }
}

class Seat(passName: String ) {
    val id = passName
        .replace("[FL]".toRegex(), "0")
        .replace("[BR]".toRegex(), "1")
        .toInt(2)
}
