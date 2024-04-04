package Year2020

import java.io.File

object Day19 {
    @JvmStatic
    fun main(args: Array<String>) {
        val file = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day19.txt")
        val rules = parseRules(file)
        val test = getRegex(rules)

        val inputs = parseInputs(file)

        val count = inputs.filter {
            test.matches(it)
        }.count()

        println("$count lines match")

        val recursiveRules = parseRules(file, true)
        val recursiveTest = getRegex(recursiveRules)

        val recursiveCount = inputs.filter {
            recursiveTest.matches(it)
        }.count()

        println("$recursiveCount lines match the recursive rules")

    }

    fun getRegex(rules: Map<Int, Rule>) = rules.getValue(0).getPatternString(rules).toRegex()

    fun parseRules(file: File, recursive: Boolean = false): Map<Int, Rule> {
        return file.readLines().takeWhile { it.isNotBlank() }.map { line ->
            val match = "(\\d+): (.*)".toRegex().matchEntire(line)
                ?: throw IllegalArgumentException("Bad rule line match: $line")
            match.groupValues[1].toInt() to match.groupValues[2]
        }.toMap().mapValues { (index, text) ->
            when {
                index == 8 && recursive -> object: Rule {
                    override fun getPatternString(rules: Map<Int, Rule>): String {
                        return "((${rules.getValue(42).getPatternString(rules)})+)"
                    }

                }
                index == 11 && recursive -> {
                    (1..10).map { repeat ->
                        val fortyTwos = (1..repeat).map { 42 }
                        val thirtyOnes = (1..repeat).map { 31 }
                        CompoundRule(fortyTwos.plus(thirtyOnes))
                    }.let { AlternateRule(it) }
                }
                else -> parseRuleText(text)
            }
        }
    }

    private fun parseRuleText(text: String): Rule {
        return LiteralRule.parseOrNull(text)
            ?: CompoundRule.parseOrNull(text)
            ?: AlternateRule.parseOrNull(text)
            ?: throw IllegalArgumentException("Could not parse to rule: $text")
    }

    fun parseInputs(file: File): List<String> {
        return file.readLines().dropWhile { it.isNotBlank() }.drop(1)
    }

    interface Rule {
        fun getPatternString(rules: Map<Int, Rule>): String
    }

    data class LiteralRule(val content: String): Rule {
        override fun getPatternString(rules: Map<Int, Rule>): String =
            content

        companion object {
            fun parseOrNull(text: String): LiteralRule? =
                "\\\"(\\w)\\\"".toRegex().matchEntire(text)?.let { match ->
                    LiteralRule(match.groupValues[1])
                }
        }
    }

    data class CompoundRule(val rules: List<Int>): Rule {
        override fun getPatternString(rules: Map<Int, Rule>): String =
            this.rules.joinToString("") { id ->
                val r = rules.getValue(id).getPatternString(rules)
                "($r)"
            }

        companion object {
            fun parseOrNull(text: String): CompoundRule? =
                "(\\d+ ?)+".toRegex().matchEntire(text)?.let { match ->
                    val rules = match.value.split(" ").map { it.toInt() }
                    CompoundRule(rules)
                }
        }
    }

    data class AlternateRule(val rules: List<Rule>): Rule {
        override fun getPatternString(rules: Map<Int, Rule>): String =
            //"(${a.getPatternString(rules)})|(${b.getPatternString(rules)})"
            this.rules.joinToString("|", "(", ")") { rule ->
                "(${rule.getPatternString(rules)})"
            }

        companion object {
            fun parseOrNull(text: String): AlternateRule? =
                "([\\d ]+) \\| ([\\d ]+)".toRegex().matchEntire(text)?.let { match ->
                    val a = parseRuleText(match.groupValues[1])
                    val b = parseRuleText(match.groupValues[2])
                    AlternateRule(listOf(a, b))
                }
        }
    }
}