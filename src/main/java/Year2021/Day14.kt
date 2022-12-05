package Year2021

import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import util.asResourceFile
import java.util.concurrent.Executors
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
            val frequencies = Executors
                .newFixedThreadPool(Runtime.getRuntime().availableProcessors() - 2)
                .asCoroutineDispatcher()
                .use { coroutineContext ->
                    runBlocking(coroutineContext) {
                        val allElements = rules.values.toSet()
                        base.windowed(2).mapIndexed { i, twoLinks ->
                            async {
                                val frequencies = allElements.associateWith { 0L }.toMutableMap()
                                fun inc(link: Link) {
                                    frequencies[link.element] = frequencies.getValue(link.element) + 1
                                }

                                val links = PolymerTemplate(twoLinks, rules).step(n).iterator()
                                if (i != 0) links.next() // drop the repeated first link
                                links.forEach { element -> inc(element) }
                                frequencies.toMap()
                            }
                        }.map {
                            it.await()
                        }.reduce { acc, freq ->
                            (acc.keys + freq.keys).associateWith { key ->
                                val a = acc[key] ?: 0
                                val f = freq[key] ?: 0
                                a + f
                            }
                        }
                    }
                }

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
