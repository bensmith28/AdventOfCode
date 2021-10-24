package Year2020

import java.io.File

object Day7 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day7.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val rules = input.readLines().map { Rule.fromString(it) }.associateBy { it.parent }
        println("Rules:")
        rules.values.forEach { r ->
            println("  $r")
        }

        fun Rule.canContain(color: String): Boolean {
            return children.any { (childColor, _) ->
                color == childColor || rules[childColor]?.canContain(color) == true
            }
        }

        fun Rule.mustContain(): Int {
            return children.entries.sumOf { (color, count) ->
                val childMustContain = rules[color]?.mustContain() ?: 0
                count * (childMustContain + 1)
            }
        }

        val canContainShinyGold = rules.values.filter { it.canContain("shiny gold") }

        println("${canContainShinyGold.size} colors can contain shiny gold")

        val shinyGoldMustContain = rules["shiny gold"]?.mustContain() ?: throw IllegalStateException()

        println("shiny gold bags must contain $shinyGoldMustContain bags")
    }
}

data class Rule(
    val parent: String,
    val children: Map<String, Int>
) {

    companion object {
        private val pattern = "(\\w+ \\w+) bags contain (.*)".toRegex()
        private val childPattern = "(\\d+ )?(\\w+ \\w+) bags?".toRegex()

        fun fromString(line: String): Rule {
            val match = pattern.matchEntire(line) ?: throw IllegalArgumentException("Bad match: $line")
            val parent = match.groupValues[1]
            val children = childPattern.findAll(match.groupValues[2]).map { childMatch ->
                val count = when {
                    childMatch.groupValues[1].isBlank() -> 0
                    else -> childMatch.groupValues[1].trim().toInt()
                }
                val color = childMatch.groupValues[2]
                color to count
            }.toMap().minus("no other")

            return Rule(parent, children)
        }
    }
}