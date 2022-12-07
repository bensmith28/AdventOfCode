package Year2022

import Year2022.Day5.Instruction.Companion.parseInstruction
import util.asResourceFile

object Day5 {
    val input = "/Year2022/Day5.txt".asResourceFile().readLines()

    fun stackLines(input: List<String> = Day5.input) = input.filter { it.contains('[') }

    val idPattern = """^[ \d]+$""".toRegex()
    fun stackIdLine(input: List<String> = Day5.input) = input.first { idPattern.matchEntire(it) != null }

    fun instructionLines(input: List<String> = Day5.input) = input.filter { it.startsWith("move") }

    val test = """(?:\[([A-Z])\]|   ) (?:\[([A-Z])\]|   )""".toRegex()

    fun List<String>.parseStacks(): Ship {
        require((this.first().length + 1) % 4 == 0) { "Bad Width of input"}
        val width = (this.last().length + 1) / 4
        val ship = (0 .. width).map { ArrayDeque<Crate>() }
        val pattern = (1 .. width ).joinToString("") { """(?:\[([A-Z])\]|   ) """ }.removeSuffix(" ").toRegex()
        this.forEach { line ->
            val paddedLine = line.padEnd(width * 4 - 1)
            pattern.matchEntire(paddedLine)!!.groupValues.drop(1).mapIndexed { stackId, crateId ->
                if(crateId.isNotBlank()) ship[stackId + 1].addLast(crateId.single())
            }
        }

        return ship
    }

    fun Ship.doInstruction(instruction: Instruction) {
        repeat(instruction.quantity) {
            this[instruction.destination].addFirst(this[instruction.source].removeFirst())
        }
    }

    fun Ship.doInstruction9001(instruction: Instruction) {
        val segment = this[instruction.source].take(instruction.quantity)
        repeat(instruction.quantity) { this[instruction.source].removeFirst() }
        this[instruction.destination].addAll(0, segment)
    }

    fun Ship.readTops() = this.map { it.firstOrNull()?: "" }.joinToString("").trim()

    fun List<String>.parseInstructions(): List<Instruction> = this.map { it.parseInstruction() }

    data class Instruction(
        val quantity: Int,
        val source: Int,
        val destination: Int
    ) {
        companion object {
            private val instructionPattern = """move (\d+) from (\d+) to (\d+)""".toRegex()
            fun String.parseInstruction(): Instruction {
                val (q, s, d) = instructionPattern.matchEntire(this)!!.destructured
                return Instruction(q.toInt(), s.toInt(), d.toInt())
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val ship1 = stackLines().parseStacks()
        val instructions = instructionLines().parseInstructions()

        instructions.forEach { instruction ->
            ship1.doInstruction(instruction)
        }

        println("Part 1: ${ship1.readTops()}")

        val ship2 = stackLines().parseStacks()

        instructions.forEach { instruction ->
            ship2.doInstruction9001(instruction)
        }

        println("Part 2: ${ship2.readTops()}")
    }
}

private typealias Crate = Char
private typealias Stack = ArrayDeque<Crate>
private typealias Ship = List<Stack>