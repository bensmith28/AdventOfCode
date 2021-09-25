package Year2016

import java.io.File

object Day2 {
    val patterns = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2016/Day2.txt")
        .readLines()

    @JvmStatic
    fun main(args: Array<String>) {
        val code = findButtons(patterns, Keypad.Actual).joinToString("")
        println("Bathroom code is $code")
    }

    fun findButtons(patterns: List<String>, keypad: Keypad): List<Char> {
        return patterns.fold(listOf()) { buttons, pattern ->
            buttons.plus(findButton(buttons.lastOrNull() ?: '5', pattern, keypad))
        }
    }

    fun findButton(start: Char, pattern: String, keypad: Keypad): Char {
        return pattern.fold(start) { currentButton, instruction ->
            keypad.move(currentButton, instruction)
        }
    }

    class Keypad private constructor(val rows: List<String>) {
        companion object {
            val Standard = Keypad(listOf(
                "     ",
                " 123 ",
                " 456 ",
                " 789 ",
                "     "
            ))
            val Actual = Keypad(listOf(
                "       ",
                "   1   ",
                "  234  ",
                " 56789 ",
                "  ABC  ",
                "   D   ",
                "       "
            ))
        }

        fun move(start: Char, instruction: Char): Char {
            val rowIndex = rows.indexOfFirst { it.contains(start) }
            val colIndex = rows[rowIndex].indexOf(start)

            val next = when(instruction) {
                'U' -> rows[rowIndex-1][colIndex]
                'D' -> rows[rowIndex+1][colIndex]
                'L' -> rows[rowIndex][colIndex-1]
                'R' -> rows[rowIndex][colIndex+1]
                else -> throw IllegalArgumentException("Bad move")
            }

            return if( next == ' ' ) start
            else next
        }
    }
}