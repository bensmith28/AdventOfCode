package Year2015

import java.io.File

object Day19 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day19.txt")
    val starter = "e"

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = input.readLines()
        val transforms = lines.take(lines.size - 1).filter { it.isNotBlank() }.map {
            Transform.fromString(it)
        }.sortedBy { it.ratio }
        val medicine = lines.last()

        val steps = generateSequence(sequenceOf(medicine)) { molecules ->
            /*molecules.map { molecule ->
                transforms.map { t ->
                    molecule.reverse(t)
                }
            }.flatten().flatten()*/
            molecules.map { molecule ->
                val t = transforms.first { molecule.contains(it.output) }
                molecule.reverse(t)
            }.flatten().toSet().asSequence()
        }.takeWhile { testSequence ->
            testSequence.none { it == starter }
        }.fold(0) { acc, _ ->
            /*if( acc % 100 == 0 ) */println("$acc steps")
            acc + 1
        }

        println("Found medicine in $steps steps")
    }

    private fun String.reverse(t: Transform): Iterable<String> {
        return t.output.toRegex().findAll(this).map { match ->
            this.replaceRange(match.range, t.input)
        }.asIterable()
    }

    private fun String.apply(t: Transform): Iterable<String> {
        return t.input.toRegex().findAll(this).map { match ->
            this.replaceRange(match.range, t.output)
        }.asIterable()
    }
}

data class Transform(val input: String, val output: String) {
    companion object {
        fun fromString(line: String): Transform {
            val match = "(\\w+) => (\\w+)".toRegex().matchEntire(line)
                ?: throw IllegalArgumentException("Bad transform match: $line")
            return Transform(
                match.groupValues[1],
                match.groupValues[2]
            )
        }
    }

    val ratio = 1.0 * input.length / output.length
}