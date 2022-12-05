package Year2021

import util.asResourceFile
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

object Day14 {
    @OptIn(ExperimentalTime::class)
    @JvmStatic
    fun main(args: Array<String>) {
        val template = "/Year2021/Day14.txt".asResourceFile().readLines().let { PolymerTemplate.parse(it) }

        measureTime {
            val tenStepScore = template.score(10)
            println("Part 1: $tenStepScore")
        }.also {
            println("Part 1 in $it")
        }

        measureTime {
            val fourtyStepScore = template.score(20)
            println("Part 2: $fourtyStepScore")
        }.also {
            println("Part 2 in $it")
        }
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
            fun inc(link: Link) {
                frequencies[link.element] = frequencies.getOrDefault(link.element, 0) + 1
            }

            step(n).forEach { element -> inc(element) }

            val least = frequencies.values.minOrNull()!!
            val most = frequencies.values.maxOrNull()!!

            return most - least
        }

        data class Link(
            val element: Char,
            val step: Int
        ) {
            override fun toString(): String {
                return "$element"
            }
        }

        fun step(n: Int): Sequence<Link> {
            return sequence {
                val chain = ArrayDeque(base.map { Link(it, 0) })
                while (chain.size > 1) {
                    val tip = chain.removeFirst()
                    var next = chain.first().element
                    (tip.step + 1..n).forEach { step ->
                        next = rules["" + tip.element + next]!!
                        chain.addFirst(Link(next, step))
                    }
                    yield(tip)
                }
                yieldAll(chain)
            }
        }
    }
}
