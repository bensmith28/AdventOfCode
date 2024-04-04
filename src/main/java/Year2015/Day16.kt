package Year2015

import java.io.File

object Day16 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day16.txt")

    @JvmStatic
    fun main(args: Array<String>) {

        /*
        children: 3
        cats: 7
        samoyeds: 2
        pomeranians: 3
        akitas: 0
        vizslas: 0
        goldfish: 5
        trees: 3
        cars: 2
        perfumes: 1
         */
        val baseline = Profile(mapOf(
            "children" to 3,
            "cats" to 7,
            "samoyeds" to 2,
            "pomeranians" to 3,
            "akitas" to 0,
            "vizslas" to 0,
            "goldfish" to 5,
            "trees" to 3,
            "cars" to 2,
            "perfumes" to 1
        ))

        val profiles = input.readLines().map { Profile(it) }

        val theRightSue = profiles.single { it.matches(baseline) }

        println("The right Sue is: ${theRightSue.name}")
    }
}

class Profile {
    val name: String
    val characteristics: Map<String, Int>

    constructor(line: String) {
        val match = "(Sue \\d+): (.*)".toRegex().matchEntire(line) ?: throw IllegalArgumentException("Bad Profile match: $line")
        name = match.groupValues[1]
        characteristics = "(\\w+): (\\d+)".toRegex().findAll(match.groupValues[2]).map { cmatch ->
            cmatch.groupValues[1] to cmatch.groupValues[2].toInt()
        }.toMap()

    }

    constructor(characteristics: Map<String, Int>) {
        name = "Baseline"
        this.characteristics = characteristics
    }

    fun matches(baseline: Profile): Boolean {
        return characteristics.entries.all { (characteristic, count) ->
            when(characteristic) {
                "cats", "trees" -> baseline.characteristics.getValue(characteristic) < count
                "pomeranians", "goldfish" -> baseline.characteristics.getValue(characteristic) > count
                else -> baseline.characteristics[characteristic] == count
            }
        }
    }
}