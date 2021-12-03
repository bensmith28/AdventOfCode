package Year2021

import util.asResourceFile

object Day3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2021/Day3.txt".asResourceFile().readLines()
        val gamma = gamma(input)
        val epsilon = epsilon(gamma)
        val product = product(gamma, epsilon)

        println("""
            Part 1:
              gamma:   $gamma
              epsilon: $epsilon
              product: $product
        """.trimIndent())

        val oxy = findOxygenGeneratorRating(input)
        val co2 = findCO2ScrubberRating(input)
        val product2 = product(oxy, co2)

        println("""
            Part 2:
              oxy:     $oxy
              co2:     $co2
              product: $product2
        """.trimIndent())
    }

    fun gamma(input: List<String>): String {
        val initial = input.first().map { 0 }
        val totals = input.fold(initial) { counts, next ->
            counts.mapIndexed { i, count ->
                if (next[i] == '1') count + 1
                else count
            }
        }
        return totals.map { count ->
            if( count * 2 >= input.size ) '1'
            else '0'
        }.joinToString("")
    }

    fun epsilon(gamma: String) = gamma.map { c ->
        when(c) {
            '1' -> '0'
            else -> '1'
        }
    }.joinToString("")

    private fun findRating(input: List<String>, rater: (List<String>) -> String): String {
        return generateSequence(0 to input) { (index, list) ->
            val compareChar = rater(list)[index]
            index + 1 to list.filter { rating ->
                rating[index] == compareChar
            }
        }.first { (_, list) -> list.size == 1 }.second.single()
    }

    fun findOxygenGeneratorRating(input: List<String>) = findRating(input, ::gamma)
    fun findCO2ScrubberRating(input: List<String>) = findRating(input) { list -> epsilon(gamma(list)) }

    fun product(gamma: String, epsilon: String) = gamma.toInt(2) * epsilon.toInt(2)
}