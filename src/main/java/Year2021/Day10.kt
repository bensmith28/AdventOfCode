package Year2021

import util.asResourceFile

object Day10 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2021/Day10.txt".asResourceFile().readLines()

        val syntaxScore = input.syntaxScore()
        println("Part 1: $syntaxScore")

        val autocompleteScore = input.autocompleteScore()
        println("Part 2: $autocompleteScore")
    }

    private val openToClose = mapOf(
        '(' to ')',
        '[' to ']',
        '{' to '}',
        '<' to '>'
    )
    private val closeToOpen = openToClose.map { (o, c) -> c to o }.toMap()
    private fun Char.isCloser() = openToClose.values.contains(this)

    fun String.firstIllegal(): Char? {
        val openers = mutableListOf<Char>()
        this.forEach { c ->
            if( c.isCloser() ) {
                if( closeToOpen[c] == openers.last() ) openers.removeLast()
                else return c
            } else {
                openers.add(c)
            }
        }
        return null
    }

    fun String.autoComplete(): String {
        val openers = mutableListOf<Char>()
        this.forEach { c ->
            if( c.isCloser() ) {
                if( closeToOpen[c] == openers.last() ) openers.removeLast()
                else return ""
            } else {
                openers.add(c)
            }
        }
        return openers.reversed().map { openToClose[it]!! }.joinToString("")
    }

    fun List<String>.syntaxScore() = this.sumOf {
        when(it.firstIllegal()) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0L
        }
    }

    fun String.autocompleteScore() = this.autoComplete().fold(0L) { score, c ->
        when(c) {
            ')' -> 5 * score + 1
            ']' -> 5 * score + 2
            '}' -> 5 * score + 3
            '>' -> 5 * score + 4
            else -> score
        }
    }

    fun List<String>.autocompleteScore(): Long {
        val scores = this.map { it.autocompleteScore() }.filter { it != 0L }
        return scores.sorted()[scores.size / 2]
    }
}