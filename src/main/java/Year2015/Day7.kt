package main.kotlin.Year2015

import java.io.File
import java.lang.IllegalStateException

object Day7 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day7b.txt")

    val expressions = input.readLines().map { Expression.parse(it) }.associateBy { it.destination }
    val results = mutableMapOf<String, UShort>()

    fun evaluate(wire: String): UShort? {
        val prior = results[wire]
        return when {
            prior != null -> prior
            wire.matches("\\d+".toRegex()) -> wire.toUShort()
            else -> expressions[wire]?.evaluate() ?: throw IllegalArgumentException("Cannot find source for $wire")
        }.also {
            results[wire] = it
        }

    }

    @JvmStatic
    fun main(args: Array<String>) {
        listOf("d", "e", "f", "g", "h", "i", "x", "y")

        listOf("a").forEach { wire ->
            val value = evaluate(wire)
            println("$wire: $value")
        }
    }

    interface Expression {
        val destination: String
        fun evaluate(): UShort

        companion object {
            fun parse(line: String): Expression {
                return if (Pass.matches(line)) Pass(line)
                else Instruction(line)
            }
        }
    }

    class Pass(line: String): Expression {
        companion object {
            val pattern = "(\\w+) -> (\\w+)".toRegex()
            fun matches(line: String) = pattern.matches(line)
        }

        val source: String
        override val destination: String

        init {
            val match = pattern.matchEntire(line) ?: throw IllegalArgumentException("Bad pass match: $line")
            source = match.groupValues[1]
            destination = match.groupValues[2]
        }

        override fun evaluate() = evaluate(this.source)
            ?: throw IllegalStateException("Could not evaluate ${this.source}")
    }

    class Instruction(val line: String): Expression {
        companion object {
            private val pattern = "(\\w+)? ?(AND|OR|LSHIFT|RSHIFT|NOT) (\\w+) -> (\\w+)".toRegex()
        }

        val left: String
        val right: String
        val command: String
        override val destination: String
        val sources: List<String>

        init {
            val match = pattern.matchEntire(line) ?: throw IllegalArgumentException("Bad instruct match: $line")
            left = match.groupValues[1]
            command = match.groupValues[2]
            right = match.groupValues[3]
            destination = match.groupValues[4]
            sources = when (command) {
                "AND" -> listOf(left, right)
                "OR" -> listOf(left, right)
                "LSHIFT" -> listOf(left)
                "RSHIFT" -> listOf(left)
                "NOT" -> listOf(right)
                else -> throw IllegalArgumentException("Bad Command for sources: $command")
            }
        }

        override fun evaluate(): UShort {
            return when (command) {
                "AND" -> {
                    val left = evaluate(left)
                    val right = evaluate(right)
                    if (right != null && left != null) {
                        left.and(right)
                    } else {
                        throw IllegalStateException("Could not evaluate sources $right and $left")
                    }
                }
                "OR" -> {
                    val left = evaluate(left)
                    val right = evaluate(right)
                    if (right != null && left != null) {
                        left.or(right)
                    } else {
                        throw IllegalStateException("Could not evaluate sources $right and $left")
                    }
                }
                "LSHIFT" -> {
                    val source = evaluate(left)
                    val distance = right.toInt()
                    source?.toUInt()?.shl(distance)?.toUShort() ?: throw IllegalStateException("Could not evaluate source $left")

                }
                "RSHIFT" -> {
                    val source = evaluate(left)
                    val distance = right.toInt()
                    source?.toUInt()?.shr(distance)?.toUShort() ?: throw IllegalStateException("Could not evaluate source $left")
                }
                "NOT" -> {
                    val source = evaluate(right)
                    source?.inv() ?: throw IllegalStateException("Could not evaluate source $right")
                }
                else -> throw IllegalArgumentException("Bad Command: $command")
            }

        }

    }
}