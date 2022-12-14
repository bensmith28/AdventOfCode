package Year2022

import Year2022.Day10.Noop.parseInstruction
import util.asResourceFile

object Day10 {
    @JvmStatic
    fun main(args: Array<String>) {
        val instructions = "/Year2022/Day10.txt".asResourceFile().readLines().map { it.parseInstruction() }
        val part1 = instructions.readSignalStrengths().sum()
        println("Part 1: $part1")
        val part2 = instructions.readPixels().asScreen()
        println("Part2:")
        println(part2)
    }

    fun Iterable<Instruction>.readSignalStrengths(): Sequence<Int> {
        return sequence {
            val printPoints = generateSequence(20) { it + 40 }.iterator()
            var nextPrint = printPoints.next()
            var register = 1
            var index = 0
            this@readSignalStrengths.forEach { instruction ->
                index += instruction.cycles
                when (instruction) {
                    is Noop -> Unit
                    is AddX -> register += instruction.value
                }
                if (index + 2 >= nextPrint) {
                    yield((nextPrint) * register)
                    nextPrint = printPoints.next()
                }
            }
        }
    }

    fun Iterable<Instruction>.readPixels(): Sequence<Pixel> {
        return sequence {
            var register = 1
            var cycle = 0

            fun checkPrint(): Pixel {
                cycle += 1
                val center = ((cycle - 1) % 40)
                return when (register) {
                    in center - 1 .. center + 1 -> Lit
                    else -> Dark
                }
            }

            this@readPixels.forEach { instruction ->
                when (instruction) {
                    is Noop -> {
                        yield(checkPrint())
                    }

                    is AddX -> {
                        yield(checkPrint())
                        yield(checkPrint())
                        register += instruction.value
                    }
                }
            }
        }
    }

    sealed interface Instruction {
        val cycles: Int
        fun String.parseInstruction(): Instruction {
            return when {
                this.startsWith("noop") -> Noop
                this.startsWith("addx") -> AddX(this.split(' ').last().toInt())
                else -> error("bad instruction: $this")
            }
        }
    }

    object Noop : Instruction {
        override val cycles = 1

        override fun toString() = "noop"
    }

    data class AddX(
        val value: Int
    ) : Instruction {
        override val cycles = 2
    }

    sealed interface Pixel {
        val printChar: String
    }

    object Lit : Pixel {
        override val printChar = "#"
    }

    object Dark : Pixel {
        override val printChar = "."
    }

    fun Sequence<Pixel>.asScreen(): String {
        return this.chunked(40) { line ->
            line.joinToString("") { it.printChar }
        }.joinToString("\n")
    }
}