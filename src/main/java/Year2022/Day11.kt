package Year2022

import util.asResourceFile
import kotlin.test.assertEquals

object Day11 {
    data class Monkey(
        val id: Int,
        val items: MutableList<Long>,
        private val operation: (item: Long) -> Long,
        private val worryReducer: (worry: Long) -> Long = { worry -> worry / 3 },
        val test: (item: Long) -> Int,
        val worryReductionFactor: Long
    ) {
        var inspections = 0L
            private set

        fun inspect(item: Long): Long {
            inspections++
            return worryReducer(operation(item))
        }

        companion object {
            private val pattern by lazy {
                """
                  Monkey (\d+):
                    Starting items: ([\d, ]+)
                    Operation: new = old ([\+*](?: old)?)(?: (\d+))?
                    Test: divisible by (\d+)
                      If true: throw to monkey (\d+)
                      If false: throw to monkey (\d+)
                """.trimIndent().toRegex()
            }

            fun parse(lines: List<String>): Monkey {
                val (idString, startListString, operatorString, operationValueString, testValueString, trueTargetString, falseTargetString) =
                    pattern.matchEntire(lines.filter { it.isNotBlank() }.joinToString("\n"))?.destructured
                        ?: error("Bad match on ${lines.first()}")
                val operationValue = operationValueString.toIntOrNull()
                val testValue = testValueString.toLong()
                val trueTarget = trueTargetString.toInt()
                val falseTarget = falseTargetString.toInt()
                return Monkey(
                    idString.toInt(),
                    startListString.split(", ").map { it.toLong() }.toMutableList(),
                    when (operatorString) {
                        "* old" -> { old -> old * old }
                        "+" -> { old -> (old + operationValue!!) }
                        "*" -> { old -> (old * operationValue!!) }
                        else -> error("Bad operator: $operatorString")
                    },
                    test = { worry -> if (worry % testValue == 0L) trueTarget else falseTarget },
                    worryReductionFactor = testValue
                )
            }

        }
    }

    fun List<String>.parseMonkeysPart1(): List<Monkey> = this.chunked(7) { Monkey.parse(it) }
    fun List<String>.parseMonkeysPart2(): List<Monkey> {
        val uncorrectedWorryMonkeys = this.chunked(7) { Monkey.parse(it) }
        val correctWorryFactor = uncorrectedWorryMonkeys.map { it.worryReductionFactor }.toSet()
            .fold(1L) { corrected, each -> corrected * each }
        return uncorrectedWorryMonkeys.map {
            it.copy(
                worryReducer = { worry -> worry % correctWorryFactor },
                worryReductionFactor = correctWorryFactor
            )
        }
    }

    fun List<Monkey>.doRound() {
        val monkeysById = this.associateBy { it.id }
        this.forEach { monkey ->
            monkey.items.forEach { item ->
                val newWorry = monkey.inspect(item)
                val target = monkey.test(newWorry)
                monkeysById[target]!!.items.add(newWorry)
            }
            monkey.items.clear()
        }
    }

    fun List<Monkey>.getMonkeyBusiness() =
        this.map { it.inspections }.sortedDescending().take(2).let { (first, second) -> first * second }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2022/Day11.txt".asResourceFile().readLines()
        val part1Monkeys = input.parseMonkeysPart1()

        repeat(20) { part1Monkeys.doRound() }
        val part1 = part1Monkeys.getMonkeyBusiness()
        println("Part 1: $part1")

        val part2Monkeys = input.parseMonkeysPart2()
        repeat(10000) { part2Monkeys.doRound() }
        val part2 = part2Monkeys.getMonkeyBusiness()
        println("Part 2: $part2")
    }
}