package Year2021

import util.asResourceFile

object Day8 {
    @JvmStatic
    fun main(args: Array<String>) {
        val notes = "/Year2021/Day8.txt".asResourceFile().readLines().map { Notes.parse(it) }

        val part1 = countSimples(notes)
        println("Part 1: $part1")

        val part2 = notes.sumOf { it.output }
        println("Part 2: $part2")
    }

    fun countSimples(notes: List<Notes>): Int = notes.sumOf {
        it.outputs.count { o -> o.matches(it.one) ||o.matches(it.four) || o.matches(it.seven) || o.matches(it.eight) }
    }

    data class Notes(val patterns: List<String>, val outputs: List<String>) {
        companion object {
            private val matcher = """([\w\s]+) \| ([\w\s]+)""".toRegex()
            fun parse(line: String): Notes {
                val (p, o) = matcher.matchEntire(line)?.destructured
                    ?: throw IllegalArgumentException("Bad input: $line")

                return Notes(
                    p.split(" ").map(String::trim),
                    o.split(" ").map(String::trim)
                )
            }
        }

        private fun String.patternToRegex() = this.let { "[$it]{${it.length}}".toRegex() }

        val one = patterns.single { it.length == 2 }.patternToRegex()
        private val fourContent = patterns.single { it.length == 4 }
        val four = fourContent.patternToRegex()
        private val sevenContent = patterns.single { it.length == 3 }
        val seven = sevenContent.patternToRegex()
        val eight = patterns.single { it.length == 7 }.patternToRegex()

        val nine = patterns.single { it.length == 6 && fourContent.all { c -> it.contains(c) } }.patternToRegex()
        val zero = patterns.single { it.length == 6 && sevenContent.all { c -> it.contains(c) } && !fourContent.all { c -> it.contains(c) } }.patternToRegex()
        private val sixContent = patterns.single { it.length == 6 && !it.matches(nine) && !it.matches(zero) }
        val six = sixContent.patternToRegex()

        val three = patterns.single { it.length == 5 && sevenContent.all { c -> it.contains(c) } }.patternToRegex()
        val five = patterns.single { it.length == 5 && sixContent.count { c -> it.contains(c) } == 5}.patternToRegex()
        val two = patterns.single { it.length == 5 && !it.matches(three) && !it.matches(five) }.patternToRegex()

        val output = outputs.joinToString("") { pattern ->
            when {
                pattern.matches(zero) -> "0"
                pattern.matches(one) -> "1"
                pattern.matches(two) -> "2"
                pattern.matches(three) -> "3"
                pattern.matches(four) -> "4"
                pattern.matches(five) -> "5"
                pattern.matches(six) -> "6"
                pattern.matches(seven) -> "7"
                pattern.matches(eight) -> "8"
                pattern.matches(nine) -> "9"
                else -> throw IllegalArgumentException("No good pattern match")
            }
        }.toInt()

    }
}