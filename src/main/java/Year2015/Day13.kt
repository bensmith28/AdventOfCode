package Year2015

import java.io.File
import kotlin.math.max

object Day13 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day13.txt")

    private fun Int.factorial(): Int {
        var current = this
        var fact = 1
        while(current > 1) {
            fact *= current--
        }
        return fact
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val rules = input.readLines().map { Rule.fromString(it) }
        val people = rules.flatMap { listOf(it.subj, it.obj) }.toSet().map {
            Person(it, rules)
        }.plus(Person("Myself", rules))

        var bestHappiness = Int.MIN_VALUE
        for( i in 1..people.size.factorial()*100) {
            val table = Table(people.shuffled())
            bestHappiness = max(table.netHappiness, bestHappiness)
        }

        println("Rules: $rules")
        println("Best Happiness: $bestHappiness")
    }
}

data class Rule(val subj: String, val obj: String, val delta: Int) {

    companion object {
        private val pattern = "(\\w+) would (gain|lose) (\\d+) happiness units by sitting next to (\\w+)."
            .toRegex()
        fun fromString(line: String): Rule {
            val match = pattern.matchEntire(line) ?: throw IllegalArgumentException("Bad rule match")
            val subj = match.groupValues[1]
            val mult = when(match.groupValues[2]) {
                "gain" -> 1
                "lose" -> -1
                else -> throw IllegalArgumentException("Bad gain/lose match")
            }
            val delta = match.groupValues[3].toInt() * mult
            val obj = match.groupValues[4]

            return Rule(subj, obj, delta)
        }
    }
}

class Table(people: List<Person>) {
    val netHappiness = (people.indices).sumOf { index ->
        val neighbor1 = when(index) {
            0 -> people.size - 1
            else -> index - 1
        }.let { people[index].checkHappiness(people[it].name) }
        val neighbor2 = when(index) {
            people.size - 1 -> 0
            else -> index + 1
        }.let { people[index].checkHappiness(people[it].name) }
        neighbor1 + neighbor2
    }
}

class Person(val name: String, rules: List<Rule>) {
    private val rules = rules.filter { it.subj == name }.associateBy { it.obj }

    fun checkHappiness(neighbor: String): Int {
        return rules[neighbor]?.delta ?: 0
    }
}