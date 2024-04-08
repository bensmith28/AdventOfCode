package Year2023

import util.asResourceFile
import kotlin.math.pow

object Day4 {
    @JvmStatic
    fun main(args: Array<String>) {
        val cards = "/Year2023/Day4.txt".asResourceFile().readLines().map { it.toCard() }

        val part1 = cards.sumOf { it.score }

        println("Part 1: $part1")

        val part2 = collect(cards)

        println("Part 2: $part2")
    }

    fun collect(cards: List<Card>): Int {
        val hands = cards.associate { it.id to 1 }.toMutableMap()

        cards.sortedBy { it.id }.forEach { card ->
            val wonIds = card.id+1..card.id+card.winCount
            wonIds.forEach { wonId ->
                if(hands.contains(wonId)) hands[wonId] = hands[wonId]!! + hands[card.id]!!
            }
        }

        return hands.values.sum()
    }

    data class Card(
        val id: Int,
        val winners: List<Int>,
        val numbers: List<Int>
    ) {
        val score by lazy {
            when(val count = winners.intersect(numbers.toSet()).size) {
                0 -> 0
                else -> 2.0.pow((count - 1).toDouble()).toInt()
            }
        }

        val winCount = winners.intersect(numbers.toSet()).count()
    }

    val cardPattern = """^Card\s+(\d+): ([\d\s]+) \| ([\d\s]+)$""".toRegex()
    fun String.toCard() = cardPattern.matchEntire(this)?.let { match ->
        Card(
            id = match.groupValues[1].toInt(),
            winners = match.groupValues[2].split("\\s+".toRegex()).filter { s -> s.isNotBlank() }.map { n -> n.toInt() },
            numbers = match.groupValues[3].split("\\s+".toRegex()).filter{ s -> s.isNotBlank() }.map { n -> n.toInt() }
        )
    } ?: error("Pattern did not match")
}