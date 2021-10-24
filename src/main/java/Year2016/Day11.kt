package Year2016

import java.util.*
import kotlin.Comparator

object Day11 {
    val sample = MappedState(
        mapOf(
            1 to Floor(MicroChip('H'), MicroChip('L')),
            2 to Floor(Generator('H')),
            3 to Floor(Generator('L')),
            4 to Floor()
        ), 1
    )

    val input = MappedState(
        mapOf(
            1 to Floor(Generator('S'), MicroChip('S'), Generator('P'), MicroChip('P')),
            2 to Floor(Generator('T'), Generator('R'), MicroChip('R'), Generator('C'), MicroChip('C')),
            3 to Floor(MicroChip('T')),
            4 to Floor()
        ), 1
    )

    val part2 = MappedState(
        mapOf(
            1 to Floor(
                Generator('S'), MicroChip('S'), Generator('P'), MicroChip('P'),
                Generator('E'), MicroChip('E'), Generator('D'), MicroChip('D')
            ),
            2 to Floor(Generator('T'), Generator('R'), MicroChip('R'), Generator('C'), MicroChip('C')),
            3 to Floor(MicroChip('T')),
            4 to Floor()
        ), 1
    )

    val part3 = PairEncodedState(part2)


    @JvmStatic
    fun main(args: Array<String>) {
        val queue = mutableListOf<State>(part3)
        val tried = mutableSetOf<String>()

        var tests = 0
        while (!queue[0].isWinner) {
            if (tests % 10000 == 0) println("Tested $tests")
            tests++
            if (tried.add(queue[0].toString())) {
                queue.addAll(queue[0].getMoves())
            }
            queue.removeAt(0)
        }

        println(queue[0])
        println("Found in ${queue[0].steps} steps")
    }

    data class Floor(val stuff: SortedSet<Thing>) {
        constructor(vararg things: Thing) : this(things.toSortedSet())

        val isFried: Boolean
            get() {
                val chips = stuff.filterIsInstance<MicroChip>().map { it.element }
                val generators = stuff.filterIsInstance<Generator>().map { it.element }
                return generators.isNotEmpty() && !chips.all { c -> generators.contains(c) }
            }
    }

    interface Thing : Comparable<Thing> {
        val element: Char
        override fun compareTo(other: Thing): Int {
            return this.toString().compareTo(other.toString())
        }
    }

    private fun <T> List<T>.combinations(): List<List<T>> = this.windowed(2)
        .plusElement(emptyList()).plus(this.map { listOf(it) })

    data class MicroChip(override val element: Char) : Thing {
        override fun toString(): String {
            return "${element}M"
        }
    }

    data class Generator(override val element: Char) : Thing {
        override fun toString(): String {
            return "${element}G"
        }
    }

    interface State {
        val steps: Int
        val isWinner: Boolean
        val isFried: Boolean
        fun getMoves(): List<State>
    }

    data class PairEncodedState(private val elevator: Char, private val pairs: String,
                                override val steps: Int): State {

        constructor(mappedState: MappedState) : this(
            mappedState.elevator.toString().single(),
            mappedState.floors.let { floors ->
                mutableMapOf<Char, String>().apply {
                    floors.forEach { (level, floor) ->
                        floor.stuff.forEach { thing ->
                            compute(thing.element) { _, pair ->
                                val existing = pair ?: "00"
                                when (thing) {
                                    is MicroChip -> "$level${existing[1]}"
                                    is Generator -> "${existing[0]}$level"
                                    else -> throw IllegalArgumentException()
                                }
                            }
                        }
                    }
                }
            }.values.sorted().joinToString(":"),
            mappedState.steps)

        override val isWinner: Boolean
            get() = pairs.split(":").all { it[0].code == 4 && it[1].code == 4 }

        override val isFried: Boolean
            get() {
                val split = pairs.split(":")
                return split.all {
                    val m = it[0]
                    val g = it[1]

                    m != g && split.any { p -> m == p[1] }
                }
            }

        override fun getMoves(): List<State> {
            val pairs = this.pairs.split(":")
            val destinations = setOf(elevator.toString().toInt() + 1, elevator.toString().toInt() - 1).intersect(1..4)
            val chips = pairs.withIndex().filter { it.value[0] == elevator }
            val generators = pairs.withIndex().filter { it.value[1] == elevator }

            return destinations.flatMap { d ->
                chips.combinations().flatMap { c ->
                    generators.combinations().map { g ->
                        Triple(d, c, g)
                    }
                }
            }.filter { (_, c, g) -> (c + g).size in 1..2 }.map { (d, c, g) ->
                val new = pairs.toMutableList()
                c.forEach { new[it.index] = d.toString() + new[it.index].drop(1) }
                g.forEach { new[it.index] = new[it.index].dropLast(1) + d.toString() }

                PairEncodedState(d.toString().single(), new.sorted().joinToString(":"), this.steps + 1)
            }.filter { !it.isFried }
        }

        override fun toString(): String {
            return "$elevator-$pairs"
        }

        override fun equals(other: Any?): Boolean {
            return this.toString() == other.toString()
        }

        override fun hashCode(): Int {
            return toString().hashCode()
        }
    }

    data class MappedState(val floors: Map<Int, Floor>, val elevator: Int, override val steps: Int = 0) : State {
        override val isFried = floors.values.any { it.isFried }
        override val isWinner = floors.minus(4).values.all { it.stuff.isEmpty() }

        override fun getMoves(): List<MappedState> {
            val chips = floors[elevator]?.stuff?.filterIsInstance<MicroChip>() ?: emptyList()
            val generators = floors[elevator]?.stuff?.filterIsInstance<Generator>() ?: emptyList()
            val destinations = setOf(elevator + 1, elevator - 1).intersect(floors.keys)

            return destinations.flatMap { d ->
                chips.combinations().flatMap { c ->
                    generators.combinations().map { g ->
                        Triple(d, c, g)
                    }
                }
            }.filter { (_, c, g) -> (c + g).size in 1..2 }.map { (d, c, g) ->
                val from = floors.getValue(elevator).let {
                    it.copy(stuff = (it.stuff - c - g).filterNotNull().toSortedSet())
                }
                val to = floors.getValue(d).let {
                    it.copy(stuff = (it.stuff + c + g).filterNotNull().toSortedSet())
                }
                copy(
                    floors = floors.plus(d to to).plus(elevator to from),
                    elevator = d,
                    steps = steps + 1
                )

            }.filter { !it.isFried }
        }

        override fun toString(): String {
            return floors.toSortedMap(Comparator { a, b -> b - a }).entries.joinToString("\n") { (id, f) ->
                val e = if (elevator == id) 'E' else '.'
                "F$id $e  ${f.stuff.joinToString("  ")}"
            }
        }

        override fun equals(other: Any?): Boolean {
            return this.toString() == other.toString()
        }

        override fun hashCode(): Int {
            return this.toString().hashCode()
        }
    }
}
