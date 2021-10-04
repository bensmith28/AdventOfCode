package Year2016

import Year2015.Day4.md5
import Year2015.Day4.toHex

object Day5 {
    val input = "ojvtpuvg"

    @JvmStatic
    fun main(args: Array<String>) {
        val pass = findPassword(input, Day5::hexParser2)
        println("Password is: $pass")
    }

    fun hexParser1( hexes: Sequence<String> ) =
        hexes.take(8).map { hash -> hash[5] }.joinToString("")

    fun hexParser2(hexes: Sequence<String>): String {
        val positions = mutableMapOf<Int, Char>()
        hexes.filter { "00000[0-7].*".toRegex().matches(it) }
            .takeWhile { positions.size < 8 }
            .forEach { hash ->
                val position = hash[5].toString().toInt()
                val char = hash[6]
                positions.putIfAbsent(position, char)
            }

        return positions.toSortedMap().values.joinToString("")
    }

    fun findPassword(doorId: String,
                     hexParser: (Sequence<String>) -> String = Day5::hexParser1
    ) = generateSequence(0) { i ->
            i + 1
        }.map { i ->
            md5("$doorId$i").toHex()
        }.filter {
            it.matches("00000.*".toRegex())
        }.let { hexParser(it) }
}