package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day14Test {
    @Test fun `test sample`() {
        val instructions = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day14-sample.txt")
            .readLines().map { Day14.Instruction.parse(it) }

        val expected = 165L
        val memory = Day14.processProgram(instructions)
        val actual = Day14.sumMemory(memory)

        assertEquals(expected, actual)
    }
}