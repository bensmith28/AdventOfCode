package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day8Test {
    @Test fun `test sample`() {
        val instructions = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2016/Day8-sample.txt")
            .readLines().map { Day8.Instruction.parse(it) }

        val screen = Day8.Screen(7, 3)
        instructions.forEach { it.apply(screen) }

        val expected =
            """
                .#..#.#
                #.#....
                .#.....
            """.trimIndent()
        val actual = screen.toString()

        assertEquals(expected, actual)
    }
}