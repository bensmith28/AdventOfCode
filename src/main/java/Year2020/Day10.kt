package Year2020

import java.io.File

object Day10 {
    val adapters = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day10.txt")
        .readLines().map { it.toInt() }

    @JvmStatic
    fun main(args: Array<String>) {
        val source = 0
        //val device = adapters.max()!! + 3
        //val endpoints = listOf(source, device)
        val allAdapters = adapters.plus(source).sorted()

       /* val jumps = allAdapters.windowed(2) { (first, second) ->
            second - first
        }.groupBy { it }

        jumps.forEach { (jumpDistance, jumps) ->
            println("${jumps.size} jumps of $jumpDistance")
        }

        val answer = jumps[1]!!.size * jumps[3]!!.size
        println("Answer: $answer")*/

        val chokepoints = allAdapters.windowed(2).filter { (first, second) ->
            val outOfFirst = adapters.count { next -> first in next - 3 until next }
            val intoSecond = adapters.count { prior -> prior in second - 3 until second }
            outOfFirst == 1 && intoSecond == 1
        }

        fun countChoices(start: Int, end: Int): Long {
            if( start == end ) return 1
            val adaptersInSegment = allAdapters.filter { start <= it && it <= end }
            val chainGenerator = (2..adaptersInSegment.size).asSequence().map { adapterCount ->
                // Generate every possible parcel with the right count
                (2..adapterCount).asSequence().fold(sequenceOf(listOf(start))) { chains, _ ->
                    chains.map { chain ->
                        val top = chain.max()!!
                        adaptersInSegment.minus(chain).filter { adapter ->
                            adapter > top && adapter <= top + 3
                        }.map { adapter ->
                            chain.plus(adapter)
                        }
                    }.flatten()
                }
            }.flatten().filter { it.max() == end }

            return chainGenerator.count().toLong()
        }

        val firstSegmentChoices = countChoices(0, chokepoints.first().first())
        val lastSegmentChoices = countChoices(chokepoints.last().last(), allAdapters.last())
        val endpointChoices = firstSegmentChoices * lastSegmentChoices

        val chainCount = chokepoints.windowed(2).fold(endpointChoices) { choicesSoFar, subjects ->
            val start = subjects.first()[1]!!
            val end = subjects[1]!!.first()
            val choices = countChoices(start, end)
            //println("$choices choices between $start and $end")
            choicesSoFar * choices
        }

        /*println(chokepoints)*/
        println("Found $chainCount chains")


        /*val chainGenerator = (2..allAdapters.size).asSequence().map { adapterCount ->
            // Generate every possible parcel with the right count
            (2..adapterCount).asSequence().fold(sequenceOf(listOf(source))) { chains, _ ->
                chains.map { chain ->
                    val top = chain.max()!!
                    allAdapters.minus(chain).filter { adapter ->
                        adapter > top && adapter <= top + 3
                    }.map { adapter ->
                        chain.plus(adapter)
                    }
                }.flatten()
            }
        }.flatten().filter { it.max() == device }*/

        /*println("All Chains:")
        chainGenerator.forEach { chain ->
            println(chain)
        }*/

        /*println("Found ${chainGenerator.count()} chains")*/
    }
}