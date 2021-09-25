package Year2020

import java.io.File
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong

object Day14 {
    val instructions = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day14.txt")
        .readLines().map { Instruction.parse(it) }

    @JvmStatic
    fun main(args: Array<String>) {
        val memory = processProgramPart2(instructions)
        val sum = sumMemory(memory)

        println("Sum of the memory is $sum")
    }

    fun processProgram(instructions: List<Instruction>): Map<Long, Long> {
        val addressSpace = mutableMapOf<Long, Long>()
        var mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

        fun Long.applyMask(): Long {
            val orMask = mask.replace('X', '0').toLong(2)
            val andMask = mask.replace('X', '1').toLong(2)

            return this.or(orMask).and(andMask)
        }

        instructions.forEach {  ins ->
            when(ins.command) {
                "mask" -> mask = ins.argument
                "mem" -> addressSpace[ins.address!!] = ins.argument.toLong().applyMask()
                else -> throw IllegalArgumentException("Bad command: ${ins.command}")
            }
        }

        return addressSpace
    }

    private fun Long.pow(power: Int): Long {
        return (0L..power).fold(1L) { acc, l -> acc * l }
    }

    fun processProgramPart2(instructions: List<Instruction>): Map<Long, Long> {
        val memory = mutableMapOf<Long, Long>()
        var mask = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"

        fun Long.getAddresses(): Sequence<Long> {
            val originalAddress = this.toString(2).let {
                (it.length until 36).joinToString("") { "0" } + it
            }

            return mask.asSequence().foldIndexed(sequenceOf("")) { index, addressStrings, c ->
                val nextChar = when(c) {
                    '0' -> sequenceOf(originalAddress.getOrElse(index) { '0' })
                    '1' -> sequenceOf('1')
                    'X' -> sequenceOf('0', '1')
                    else -> throw IllegalArgumentException("Bad mask char: ($c) $mask")
                }

                addressStrings.map { addressSoFar ->
                    nextChar.map { c ->
                        "$addressSoFar$c"
                    }
                }.flatten()
            }.map { it.toLong(2) }
        }

        instructions.forEach {  ins ->
            when(ins.command) {
                "mask" -> mask = ins.argument
                "mem" -> ins.address!!.getAddresses().forEach { address ->
                    memory[address] = ins.argument.toLong()
                }
                else -> throw IllegalArgumentException("Bad command: ${ins.command}")
            }
        }

        return memory
    }

    fun sumMemory(memory: Map<Long, Long>): Long {
        return memory.values.sum()
    }

    data class Instruction(val command: String, val address: Long?, val argument: String) {
        companion object {
            fun parse(line: String): Instruction {
                val match = "(mem|mask)(?:\\[(\\d+)\\])? = (\\w+)".toRegex().matchEntire(line)
                    ?: throw IllegalArgumentException("Bad instruction match: $line")
                return Instruction(
                    command = match.groupValues[1],
                    address =  match.groupValues[2].toLongOrNull(),
                    argument = match.groupValues[3]
                )
            }
        }
    }
}