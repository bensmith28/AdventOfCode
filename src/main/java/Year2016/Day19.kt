package Year2016

object Day19 {
    val sample = 5
    val input = 3017957

    @JvmStatic
    fun main(args: Array<String>) {
        val elves = (1..sample).toMutableList()

        var turn = 0
        var remaining = elves.size
        while(remaining > 1) {
            val gap = remaining / 2
            val i2 = (turn + elves.size / 2 + (elves.size - remaining)) % elves.size
            val index = generateSequence(turn + 1) { (it + 1) % elves.size }
                .filter { elves[it] != -1 }.take(gap).last()
            //println("Removing ${elves[index]}")
            elves[index] = -1
            remaining -= 1
            do {
                turn = (turn + 1) % elves.size
            } while( elves[turn] == -1)
            /*if( turn % 1000 == 0 ) {
                val nextElf = elves[turn]
                elves.removeAll(setOf(-1))
                turn = elves.indexOf(nextElf)
                println("We're at turn $turn")
            }*/
            if( turn == 0 ) {
                println("Finished a round, pruning")
                elves.removeAll(setOf(1))
            }
        }

        println(elves.filter { it != -1 })
    }
}