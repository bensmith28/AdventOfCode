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
            println("Part 1 in $it") // should be 2851
        }

        measureTime {
            val fourtyStepScore = template.score(40)
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
            val initialExpansion = n/2
            val remainingExpansion = n - n/2
            val allElements = rules.values.toSet()
            val remainderCache = mutableMapOf<String, Sequence<Link>>()
            val frequencies = step(initialExpansion).windowed(2).map { twoLinks ->
                val frequencies = allElements.associateWith { 0L }.toMutableMap()
                fun inc(link: Link) {
                    frequencies[link.element] = frequencies.getValue(link.element) + link.weight
                }

                val template = PolymerTemplate(twoLinks.joinToString(""), rules)
                val isCached = template.base in remainderCache
                val links = if(isCached) {
                    remainderCache.getValue(template.base)
                } else {
                    template.step(remainingExpansion)
                        .drop(1) // drop the repeated first link, will capture the actual first link below
                }
                links.forEach { element -> inc(element) }

                if(!isCached) {
                    println("Caching ${template.base} (${100.0 * remainderCache.size / (allElements.size * allElements.size)}% cached)")
                    remainderCache[template.base] = frequencies.map { (element, weight) ->
                        Link(element, 0, weight)
                    }.asSequence()
                }

                frequencies.toMap()
            }.reduce { acc, freq ->
                (acc.keys + freq.keys).associateWith { key ->
                    val a = acc[key] ?: 0
                    val f = freq[key] ?: 0
                    a + f
                }
            }.let { withoutTipOfChain ->
                // count the tip of the chain
                withoutTipOfChain.plus(
                    base.first() to withoutTipOfChain.getOrDefault(base.first(), 0L) + 1
                )
            }

            val least = frequencies.values.minOrNull()!!
            val most = frequencies.values.maxOrNull()!!

            return most - least
        }

        data class Link(
            val element: Char,
            val step: Int,
            val weight: Long = 1L
        ) {
            override fun toString(): String {
                return (1..weight).map { element }.joinToString("")
            }
        }

        fun step(n: Int): Sequence<Link> {
            return sequence {
                val chain = ArrayDeque(base.map { Link(it, 0) })
                while (chain.size > 1) {
                    val tip = chain.removeFirst()
                    var next = chain.first().element
                    for (step in tip.step + 1..n) {
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
