package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day19Test {

    val sampleInput = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day19-sample.txt")
    val rules = Day19.parseRules(sampleInput)
    val inputs = Day19.parseInputs(sampleInput)

    @Test fun `parse sample`() {
        assertEquals(6, rules.size)
        assertEquals(5, inputs.size)
    }

    @Test fun `evaluate sample`() {
        val test = Day19.getRegex(rules)

        val expected = 2
        val actual = inputs.filter {
            test.matches(it)
        }.count()

        assertEquals(expected, actual)
    }

    @Test fun `evaluate sample b not recursive`() {
        val sampleB = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day19-sampleB.txt")
        val rules = Day19.parseRules(
            sampleB,
            false
        )
        val inputs = Day19.parseInputs(sampleB)
        val test = Day19.getRegex(rules)

        val expected = 3
        val actual = inputs.filter {
            test.matches(it)
        }.count()

        assertEquals(expected, actual)
    }

    @Test fun `evaluate sample b recursive`() {
        val sampleB = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day19-sampleB.txt")
        val rules = Day19.parseRules(
            sampleB,
            true
        )
        val inputs = Day19.parseInputs(sampleB)
        val test = Day19.getRegex(rules)

        val expected = 12
        val actual = inputs.filter {
            test.matches(it)
        }.count()

        assertEquals(expected, actual)
    }
}