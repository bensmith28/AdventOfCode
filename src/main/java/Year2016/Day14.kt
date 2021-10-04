package Year2016

import Year2015.Day4.toHex

object Day14 {
    val input = "ihaygndm"

    @JvmStatic
    fun main(args: Array<String>) {
        val part1 = getNthKey(64)

        println("Part 1: $part1")

        val part2 = getNthKey(64, stretch = 2016)

        println("Part 2: $part2")
    }

    fun genPad(index: Int, stretch: Int = 0, salt: String = input): String {
        return (0..stretch).fold("$salt$index") { s, _ ->
            Year2015.Day4.md5(s).toHex()
        }
    }

    fun getNthKey(n: Int, salt: String = input, stretch: Int = 0): Int {
        val tries = mutableMapOf<Int, String>()

        fun attempt(i: Int): String {
            return tries.getOrPut(i) {
                if(i % 1000 == 0) print('.')
                genPad(i, stretch, salt)
            }
        }

        return generateSequence(0) { i ->
            tries.remove(i - 1)
            i + 1
        }.filter { index ->
            val c = attempt(index).inARow(3)
            c != null && (index+1..index+1001).any { i ->
                attempt(i).inARow(5, c) == c
            }
        }.take(n).last().also { println() }
    }

    private fun String.inARow(n: Int, c: Char? = null) =
        this.windowed(n).firstOrNull { w ->
            w.all { w0 -> w0 == c ?: w.first() }
        }?.first()
}