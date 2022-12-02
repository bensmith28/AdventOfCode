package Year2021

import Year2021.Day14.weaveWith
import util.asResourceFile

object Day14 {
    @JvmStatic
    fun main(args: Array<String>) {
        val template = "/Year2021/Day14.txt".asResourceFile().readLines().let { PolymerTemplate.parse(it) }

        val tenStepScore = template.score(10)
        println("Part 1: $tenStepScore")

        val fourtyStepScore = template.score(40)
        println("Part 2: $fourtyStepScore")
    }

    class PolymerTemplate(val base: String, val rules: Map<String, Char>) {
        companion object {
            fun parse(lines: List<String>): PolymerTemplate {
                return PolymerTemplate(
                    lines.first(),
                    lines.drop(2).associate {
                        val (frame, insert) = it.split(" -> ")
                        frame to insert.single()
                    }
                )
            }
        }

        fun score(n: Int): Long {
            val frequencies = mutableMapOf<Char, Long>()
            fun inc(element: Char) {
                frequencies[element] = frequencies.getOrDefault(element, 0) + 1
            }

            var polymer: Sequence<Char> = base.asSequence()
            for(i in 1..n) {
                val insert = polymer.windowed(2).map { frame ->
                    rules[frame.asString()]!!
                }
                if(polymer.count() != insert.count() + 1) {
                    println("Uh oh")
                }
                polymer = polymer.weaveWith(insert)
            }
            polymer.forEach { element -> inc(element) }

            val least = frequencies.values.minOrNull()!!
            val most = frequencies.values.maxOrNull()!!

            return most - least
        }

        fun step(n: Int, base: Sequence<Char> = this.base.asSequence()): Sequence<Char> {
            return if(n == 0) base
            else sequence {
                step(n - 1, base).windowed(2, partialWindows = true).forEach { frame ->
                    val result = rules[frame.asString()]?.let { insert ->
                        sequenceOf(frame.first(), insert)
                    } ?: frame.asSequence()
                    yieldAll(result)
                }
            }
        }
    }

    fun Sequence<Char>.weaveWith(other: Sequence<Char>): Sequence<Char> =
        this.joinToString("").weaveWith(other.joinToString("")).asSequence()

    fun String.weaveWith(other: String): String {
        val iter = this.iterator()
        val otherIter = other.iterator()
        return sequence {
            yield(iter.next())
            while(iter.hasNext()) {
                if(!otherIter.hasNext()) {
                    println("Uh oh")
                }
                yield(otherIter.next())
                yield(iter.next())
            }
        }.joinToString("")
    }

    fun Iterable<Char>.asString() = this.joinToString("")
}