package Year2023

import util.asResourceFile

object Day3 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2023/Day3.txt".asResourceFile().readLines().joinToString("\n")
        val parts = input.findPartNumbers()
        val symbols = input.findSymbols()

        val sumOfParts = parts.filter { p -> symbols.any { s -> p.adjacentTo(s) } }.sumOf { it.number }

        println("Part 1: $sumOfParts")

        val gearsRatios = findGearRatios(
                parts,
                symbols
        )

        val sumOfGearRatios = gearsRatios.sum()

        println("Part 2: $sumOfGearRatios")
    }

    fun findGearRatios(
            parts: List<PartNumber>,
            symbols: List<Pair<Int,Int>>
    ): List<Int> {
        val gearsRatios = symbols.mapNotNull { s ->
            val adj = parts.filter { p -> p.adjacentTo(s) }

            if(adj.size == 2) {
                adj.first().number * adj.last().number
            } else null
        }

        return gearsRatios
    }

    data class PartNumber(
            val number: Int,
            val location: Pair<Pair<Int,Int>, Pair<Int,Int>>
    ) {
        fun adjacentTo(symbol: Pair<Int, Int>) =
                location.first.first - 1 <= symbol.first &&
                        location.second.first + 1 >= symbol.first &&
                        location.first.second - 1 <= symbol.second &&
                        location.second.second + 1 >= symbol.second
    }

    private val partPattern = """\d+""".toRegex()
    fun String.findPartNumbers() = this.split("\n").flatMapIndexed { i, line ->
        partPattern.findAll(line).map {
            PartNumber(
                    it.value.toInt(),
                    (it.range.first to i) to (it.range.last to i)
            )
        }
    }

    private val symbolPattern = """[^.\d]""".toRegex()
    fun String.findSymbols() = this.split("\n").flatMapIndexed { i, line ->
        symbolPattern.findAll(line).map {
            it.range.first to i
        }
    }
}