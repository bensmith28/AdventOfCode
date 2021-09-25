package Year2020

object Day15 {

    val initial = "0,20,7,16,1,18,15"
    //val initial = "0,3,6"

    @JvmStatic
    fun main(args: Array<String>) {
        val results = initialize(initial)
        var latest = initial.split(",").last().toInt()
        (results.size until 30000000).asSequence().forEach {
            if( it % 3000 == 0 ) println("${it/300000.0}% processed")
            latest = results.takeTurn(it + 1, latest)
        }

        //val (lastSaid, lastTurns) = results.maxBy { (_, turns) -> turns.max() ?: Int.MIN_VALUE }!!

        println("Last number said is $latest")
    }

    fun initialize(str: String): MutableMap<Int, Int> {
        return str.split(",")
            .map { it.toInt() }
            .mapIndexed { index: Int, value: Int ->
                value to index+1
            }.toMap().toMutableMap()
    }

    fun MutableMap<Int, Int>.takeTurn(thisTurn: Int, lastSaid: Int): Int {
        val turnLastSaid = this[lastSaid]
        val newSaid = turnLastSaid?.let { thisTurn - 1 - it } ?: 0
        this[lastSaid] = thisTurn - 1
        return newSaid
    }
}