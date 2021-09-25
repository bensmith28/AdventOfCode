package Year2020

import java.io.File

object Day22 {

    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day22.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val players = Player.parse(input.readLines())
        val score = playCombat(players)

        println("Winning Score: $score")
    }

    fun playCombat(players: List<Player>): GameResult {
        val rounds = mutableSetOf<GameRound>()
        while ( players.count { p -> p.deck.isNotEmpty() } != 1) {
            if (!rounds.add(GameRound(players.map { it.copy() }))) {
                val winner = players.first()
                return GameResult(winner.name, winner.getScore())
            }

            if (players.all { p -> p.deck.size - 1 >= p.deck.first() }) {
                // Play recursive game
                val result = playCombat(players.map { p ->
                    p.copy(deck = p.deck.drop(1).take(p.deck.first()).toMutableList())
                })
                val losingCards = players.filter { it.name != result.winnerName }.map { it.deck.first() }
                players.forEach { p ->
                    if(p.name == result.winnerName) {
                        val winningCard = p.deck.first()
                        p.deck.add(winningCard)
                        p.deck.addAll(losingCards)
                    }
                    p.deck.removeAt(0)
                }
            } else {
                // Play normal hand
                val hand = players.map { it.deck.first() }.sortedDescending()
                val winningCard = hand.first()
                players.forEach { p ->
                    if (p.deck.first() == winningCard) {
                        p.deck.addAll(hand)
                    }
                    p.deck.removeAt(0)
                }
            }
        }

        val winner = players.single { p -> p.deck.isNotEmpty() }

        return GameResult(winner.name, winner.getScore())
    }

    data class Player(val name: String, val deck: MutableList<Int> = mutableListOf()) {
        companion object {
            fun parse(lines: List<String>): List<Player> {
                return lines.fold(emptyList()) { players, line ->
                    if(line.endsWith(":")) {
                        players.plus(Player(line.removeSuffix(":")))
                    } else if (line.matches("\\d+".toRegex())) {
                        players.last().deck.add(line.toInt())
                        players
                    } else {
                        players
                    }
                }
            }
        }

        fun getScore() = deck.reversed().foldIndexed(0) { index, score, card ->
            score + (index + 1) * card
        }
    }

    data class GameResult(val winnerName: String, val score: Int)

    data class GameRound(val players: List<Player>)
}