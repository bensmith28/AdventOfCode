package Year2022

import util.asResourceFile

object Day2 {
    enum class Play(val score: Int, val beats: () -> Play, val losesTo: () -> Play) {
        ROCK(1, { SCISSORS }, { PAPER }),
        PAPER(2, { ROCK }, { SCISSORS }),
        SCISSORS(3, { PAPER }, { ROCK });

        fun roundScore(other: Play): Int {
            return when (other) {
                this.beats() -> 6
                this -> 3
                else -> 0
            }
        }
    }

    data class Instruction(
        val ifTheyPlayed: Play,
        val youShouldPlay: Play
    ) {
        companion object {
            val linePattern = """(A|B|C) (X|Y|Z)""".toRegex()

            private fun parseThem(them: String) = when(them) {
                "A" -> Play.ROCK
                "B" -> Play.PAPER
                "C" -> Play.SCISSORS
                else -> throw IllegalArgumentException()
            }
            fun parsePart1(input: String): Instruction {
                val (themChar, youChar) = linePattern.matchEntire(input)!!.destructured
                val them = parseThem(themChar)
                val you = when(youChar) {
                    "X" -> Play.ROCK
                    "Y" -> Play.PAPER
                    "Z" -> Play.SCISSORS
                    else -> throw IllegalArgumentException()
                }
                return Instruction(them, you)
            }

            fun parsePart2(input: String): Instruction {
                val (themChar, youChar) = linePattern.matchEntire(input)!!.destructured
                val them = parseThem(themChar)
                val you = when(youChar) {
                    "X" -> them.beats()
                    "Y" -> them
                    "Z" -> them.losesTo()
                    else -> throw IllegalArgumentException()
                }
                return Instruction(them, you)
            }
        }

        fun roundScore() = youShouldPlay.roundScore(ifTheyPlayed) + youShouldPlay.score
    }

    fun List<Instruction>.score() = this.sumOf { it.roundScore() }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2022/Day2.txt".asResourceFile().readLines()
        val guide1 = input.map { Instruction.parsePart1(it) }
        println("Score 1: ${guide1.score()}")
        val guide2 = input.map { Instruction.parsePart2(it) }
        println("Score 2: ${guide2.score()}")
    }
}