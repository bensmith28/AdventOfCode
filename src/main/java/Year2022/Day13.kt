package Year2022

import util.asResourceFile

object Day13 {
    val input = "/Year2022/Day13.txt".asResourceFile().readLines()

    @JvmStatic
    fun main(args: Array<String>) {
        val packets = parseInput(input)
        val part1 = compareOrders(packets)
        println("Part 1: $part1")

        val part2 = decoderKey(packets)
        println("Part 2: $part2")
    }

    fun String.splitPackets(): List<String> {
        var i = 1
        val ret = mutableListOf<String>()
        while (i < this.length) {
            when {
                this[i].isDigit() -> {
                    val nextSep = this.findNextSeparator(i)
                    ret.add(this.substring(i until nextSep).removeSuffix("]"))
                    i = nextSep + 1
                }
                this[i] == '[' -> {
                    val closer = this.findCloser(i)
                    ret.add(this.substring(i..closer))
                    i = closer + 1
                }
                this[i] == ',' || this[i] == ']' -> {
                    i++
                }
            }
        }
        return ret
    }

    fun String.findCloser(opener: Int): Int {
        var depth = 0
        check(this[opener] == '[') { "$opener not an opener of $this" }
        for (i in opener until this.length) {
            when (this[i]) {
                '[' -> depth++
                ']' -> depth--
            }
            if (depth == 0) return i
        }
        error("Could not find closer")
    }

    fun String.findNextSeparator(start: Int): Int {
        for (i in start until this.length) {
            if (this[i] == ',') return i
        }
        return this.length
    }

    fun String.parsePacket(): Packet = when {
        this.toIntOrNull() != null -> ConcretePacket(this.toInt())
        else -> ListPacket(this.splitPackets().map { it.parsePacket() })
    }

    sealed interface Packet : Comparable<Packet>

    data class ListPacket(val packets: List<Packet>) : Packet {
        override fun compareTo(other: Packet): Int = when (other) {
            is ConcretePacket -> compareLists(ListPacket(listOf(other)))
            is ListPacket -> compareLists(other)
        }

        private fun compareLists(other: ListPacket): Int {
            val size = maxOf(packets.size, other.packets.size)
            for (i in 0 until size) {
                val mine = packets.getOrNull(i)
                val theirs = other.packets.getOrNull(i)
                if (mine == null) return -1
                else if (theirs == null) return 1
                else if (mine.compareTo(theirs) != 0) return mine.compareTo(theirs)
            }
            return 0
        }
    }

    data class ConcretePacket(val value: Int) : Packet {
        override fun compareTo(other: Packet): Int = when (other) {
            is ConcretePacket -> value.compareTo(other.value)
            is ListPacket -> ListPacket(listOf(this)).compareTo(other)
        }
    }

    fun parseInput(lines: List<String>): List<Packet> = lines.filter(String::isNotBlank).map { it.parsePacket() }

    fun compareOrders(packets: List<Packet>): Int = packets.windowed(2, 2).mapIndexed { i, (a, b) ->
        (i + 1) to (a < b)
    }.filter { it.second }.sumOf { it.first }

    fun decoderKey(packets: List<Packet>): Int {
        val divA = ListPacket(listOf(ListPacket(listOf(ConcretePacket(2)))))
        val divB = ListPacket(listOf(ListPacket(listOf(ConcretePacket(6)))))

        val sorted = packets.plus(listOf(divA, divB)).sorted()

        return (sorted.indexOf(divA) + 1) * (sorted.indexOf(divB) + 1)
    }
}