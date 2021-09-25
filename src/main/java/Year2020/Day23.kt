package Year2020

object Day23 {
    val input = "739862541"

    @JvmStatic
    fun main(args: Array<String>) {
        val game = GameState.parse(input)
        game.step(100)

        println("After 100 Moves: ${game.printState()}")
    }

    data class GameState(val cups: Array<Int>, var currentIndex: Int = 0) {
        companion object {
            fun parse(line: String): GameState {
                return GameState(line.map { "$it".toInt() }.toTypedArray())
            }
        }

        private val actions = mutableListOf<Insert>()

        fun cupsToOneMillion(): GameState {
            val allTheCups = cups.plus((cups.size+1..1000000).toList())
            return GameState(allTheCups)
        }

        fun step(moves: Int) {
            repeat(moves) { step() }
        }

        private fun step() {
            val first = cups[currentIndex]
            val removed = cups.sliceArray(listOf(
                (currentIndex + 1) % cups.size,
                (currentIndex + 2) % cups.size,
                (currentIndex + 3) % cups.size
            ))
            val destination = generateSequence(first - 1) {
                if(it <= 1) cups.size
                else it - 1
            }.first { it > 0 && !removed.contains(it) }.let { cups.indexOfFirst { c -> c == it } + 1 }

            if( currentIndex + 4 > cups.size) {
                val spaceAtEnd = cups.size - currentIndex - 1
                val segment = cups.sliceArray(3 - spaceAtEnd until destination).plus(removed)
                segment.copyInto(cups, currentIndex + 1, 0, spaceAtEnd)
                segment.copyInto(cups, 0, spaceAtEnd)
            } else if( currentIndex + 1 < destination ) {
                val segment = cups.sliceArray(currentIndex + 4 until destination )
                segment.plus(removed).copyInto(cups, currentIndex + 1)
            } else {
                val segment = cups.sliceArray(destination .. currentIndex)
                removed.plus(segment).copyInto(cups, destination)
                currentIndex += 3
            }
            currentIndex = (currentIndex + 1) % cups.size
        }

        private class Insert(val destination: Int, val content: Array<Int>)

        fun printState(): String {
            val indexOfOne = cups.indexOf(1)
            return cups.copyOfRange(indexOfOne + 1, cups.size)
                .plus(cups.copyOfRange(0, indexOfOne))
                .joinToString("")
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as GameState

            if (!cups.contentEquals(other.cups)) return false
            if (currentIndex != other.currentIndex) return false

            return true
        }

        override fun hashCode(): Int {
            var result = cups.contentHashCode()
            result = 31 * result + currentIndex
            return result
        }


    }
}