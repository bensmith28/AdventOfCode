package main.kotlin.Year2015

object Day11 {
    val input = "hxbxxyzz".inc() //"hxbxwxba"

    fun String.inc(): String {
        return this.foldRight("") { c, acc ->
            val next = when(val n = c + 1) {
                'i' -> 'j'
                'l' -> 'm'
                'o' -> 'p'
                else -> n
            }
            when {
                acc.isBlank() -> next + ""
                acc.first() == '{' -> next + acc.replaceFirst('{', 'a')
                else -> c + acc
            }
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        var count = 0
        val valid = generateSequence(input) { password ->
            password.inc()
        }.first { password ->
            if( count % 1000000 == 0) { println(count) }
            count++
            listOf(
                password.matches(".*([a-z])(?=\\1).+([a-z])(?=\\2).*".toRegex()),
                password.windowed(3).any { w ->
                    w[1] == w[0] + 1 && w[2] == w[0] + 2
                }
            ).all { it }
        }

        println("Next valid password: $valid")
    }
}