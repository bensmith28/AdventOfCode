package Year2016

import util.asResourceFile

object Day15 {

    @JvmStatic
    fun main(args: Array<String>) {
        val discs = "/Year2016/Day15.txt".asResourceFile().readLines().map { Disc.fromLine(it) }
        val firstAlign = findAlignment(discs)

        println(firstAlign)

        val set2 = discs.plus(Disc(7, 11, 0))

        println(findAlignment(set2))

    }

    fun findAlignment(discs: Collection<Disc>): Int {
        val base = discs.maxBy { it.size }!!
        return generateSequence(base.firstAlignedAt) { it + base.size }
            .first { discs.all { d -> (it - d.firstAlignedAt) % d.size == 0 } }
    }

    data class Disc(val id: Int, val size: Int, val startingPosition: Int) {
        val firstAlignedAt = (size - startingPosition - id).let {
            if(it < 0) it + size
            else it
        }

        companion object{
            private val matcher = """Disc #(\d+) has (\d+) positions; at time=0, it is at position (\d+)."""
                .toRegex()

            fun fromLine(line: String): Disc {
                val (id, size, start) = matcher.matchEntire(line)?.destructured
                    ?: throw IllegalArgumentException()
                return Disc(id.toInt(), size.toInt(), start.toInt())
            }
        }
    }
}