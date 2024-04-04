package Year2015

import java.io.File

object Day23 {
    val program = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day23.txt")
        .readLines().map { Instruction.parse(it) }

    @JvmStatic
    fun main(args: Array<String>) {
        val registers = mutableMapOf(
            "a" to 1,
            "b" to 0
        )

        generateSequence(0) { cursor ->
            val ins = program[cursor]
            when(ins.command) {
                "hlf" -> {
                    registers[ins.register] = registers[ins.register]!! / 2
                    cursor + 1
                }
                "tpl" -> {
                    registers[ins.register] = registers[ins.register]!! * 3
                    cursor + 1
                }
                "inc" -> {
                    registers[ins.register] = registers[ins.register]!! + 1
                    cursor + 1
                }
                "jmp" -> {
                    cursor + ins.argument
                }
                "jie" -> {
                    if(registers[ins.register]!! % 2 == 0) cursor + ins.argument
                    else cursor + 1
                }
                "jio" -> {
                    if(registers[ins.register]!! == 1) cursor + ins.argument
                    else cursor + 1
                }
                else -> throw IllegalArgumentException("Bad command at $cursor: ${ins.command}")
            }
        }.first { it < 0 || it >= program.size }

        println(registers)
    }
}

data class Instruction(val command: String, val register: String, val argument: Int) {
    companion object{
        fun parse(line: String): Instruction {
            val match = "(\\w{3}) ([ab])?(?:, )?\\+?(\\-?\\d+)?".toRegex().matchEntire(line)
                ?: throw IllegalArgumentException("Bad Match: $line")
            return Instruction(
                command = match.groupValues[1],
                register = match.groupValues[2],
                argument = match.groupValues[3].toIntOrNull() ?: 0
            )
        }
    }
}