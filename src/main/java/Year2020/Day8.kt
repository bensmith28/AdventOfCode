package Year2020

import java.io.File
import java.lang.IllegalStateException

object Day8 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day8.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val program = input.readLines().mapIndexed { index: Int, line: String ->
            index to Instruction.fromString(line)
        }.toMap()

        val (_, acc) = attemptExecute(program)

        println("Accumulated $acc before looping")

        val changeableLines = program.entries.filter { (index, ins) ->
            ins.command != "acc"
        }.map { (index, _) -> index }.toMutableList()
        val workingProgram = generateSequence {
            val index = changeableLines.first()
            val oldInstruction = program[index] ?: throw IllegalStateException("Only change existing lines: $index")
            val newInstruction = oldInstruction.copy(
                command = when(oldInstruction.command) {
                    "nop" -> "jmp"
                    else -> "nop"
                }
            )
            changeableLines.removeAt(0)
            program.plus(index to newInstruction)
        }.first {
            attemptExecute(it).first
        }

        val workingAcc = attemptExecute(workingProgram).second

        println("Working program produces $workingAcc")
    }

    fun attemptExecute(program: Map<Int, Instruction>): Pair<Boolean, Int> {
        val executed = mutableSetOf<Int>()
        var acc = 0
        var nextIndex = 0

        while(executed.add(nextIndex) && nextIndex < program.size) {
            val nextInstruction = program[nextIndex] ?: throw IllegalArgumentException("Bad next line: $nextIndex")
            nextIndex = when(nextInstruction.command) {
                "acc" -> {
                    acc += nextInstruction.arg
                    nextIndex + 1
                }
                "jmp" -> nextIndex + nextInstruction.arg
                "nop" -> nextIndex + 1
                else -> throw IllegalArgumentException("Bad command: ${nextInstruction.command}")
            }
        }

        return (nextIndex >= program.size) to acc
    }
}

data class Instruction(val command: String, val arg: Int) {
    companion object {
        fun fromString(line: String): Instruction {
            val match = "(acc|jmp|nop) \\+?(\\-?\\d+)".toRegex().matchEntire(line)
                ?: throw IllegalArgumentException("Bad instruction match: $line")
            return Instruction(
                match.groupValues[1],
                match.groupValues[2].toInt()
            )
        }
    }
}