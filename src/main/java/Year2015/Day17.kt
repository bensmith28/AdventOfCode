package Year2015

import java.util.*

object Day17 {
    val containerSizes =
        listOf(50, 44, 11, 49, 42, 46, 18, 32, 26, 40, 21, 7, 18, 43, 10, 47, 36, 24, 22, 40).sortedDescending()
    val startingAmount = 150
//    val containerSizes = listOf(20, 15, 10, 5, 5)
//    val startingAmount = 25

    @JvmStatic
    fun main(args: Array<String>) {
        val containers = containerSizes.map { Container(it) }
        var workingSolutions = mutableSetOf<List<Container>>()

        containers.forEach { nextContainer ->
            workingSolutions.addAll(workingSolutions.map { it.plus(nextContainer) })
            workingSolutions.add(listOf(nextContainer))
            workingSolutions = workingSolutions.filter { it.sum() <= startingAmount }.toMutableSet()
        }

        val actualSolutions = workingSolutions.filter { it.sum() == startingAmount }.toSet()

        val minContainerCount = actualSolutions.minByOrNull { it.size }?.size ?: -1
        val solutionsAtMin = actualSolutions.filter { it.size == minContainerCount }

        println("Found ${actualSolutions.size} solutions")
        println("There are ${solutionsAtMin.size} solutions that use $minContainerCount containers")
    }
}

private fun Collection<Container>.sum(): Int = this.sumOf { it.capacity }

data class Container(val capacity: Int, val name: String = UUID.randomUUID().toString())
