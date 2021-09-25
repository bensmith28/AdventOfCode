package Year2020

import java.io.File

object Day18 {

    val inputFile = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day18.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val sum = inputFile.readLines().fold(0L) { sum, line ->
            sum + Statement.parse(line).value
        }

        println("Sum: $sum")

        // 675830012 too low
    }

    interface Statement {
        val value: Long

        operator fun plus(other: Statement): Statement {
            return Literal(this.value + other.value)
        }

        operator fun times(other: Statement): Statement {
            return Literal(this.value * other.value)
        }

        companion object {
            fun parse(line: String): Statement {
                val (right, rightLength) = if (line.endsWith(")")) {
                    var open = 0
                    val str = line.takeLastWhile { c ->
                        if (c == ')') open++
                        if (c == '(') open--
                        open > 0
                    }.dropLast(1)
                    val statement = parse(str).let {
                        if(it is Expression) it.copy(forced = true) else it
                    }
                    statement to str.length + 2
                } else {
                    val str = line.takeLastWhile { c -> c != ' ' }
                    Literal(str.toLong()) to str.length
                }

                if (line.length <= rightLength) return right

                val operator = line.dropLast(rightLength + 1).last().let { Operator.valueOf(it) }

                val left = parse(line.dropLast(rightLength + 3))

                return if (operator == Operator.PLUS && left is Expression && !left.forced) {
                    Expression(
                        left.left,
                        Expression(left.right, right, Operator.PLUS),
                        left.operator
                    )
                } else {
                    Expression(left, right, operator)
                }
            }
        }
    }

    enum class Operator(val character: Char) {
        PLUS('+'),
        TIMES('*');

        companion object {
            fun valueOf(c: Char): Operator {
                return values().singleOrNull { it.character == c }
                    ?: throw IllegalArgumentException("Bad char: $c")
            }
        }
    }

    data class Literal(override val value: Long) : Statement {
        override fun toString() = value.toString()
    }

    data class Expression(
        val left: Statement,
        val right: Statement,
        val operator: Operator,
        val forced: Boolean = false
    ) : Statement {
        override val value = when (operator) {
            Operator.PLUS -> (left + right).value
            Operator.TIMES -> (left * right).value
        }

        override fun toString() = "($left ${operator.character} $right)"
    }
}