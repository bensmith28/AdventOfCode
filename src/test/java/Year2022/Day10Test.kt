package Year2022

import Year2022.Day10.Noop.parseInstruction
import Year2022.Day10.asScreen
import Year2022.Day10.readPixels
import Year2022.Day10.readSignalStrengths
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day10Test {
    private val input = "/Year2022/Day10-sample.txt".asResourceFile().readLines()

    @Test
    fun `part 1 sample`() {
        val instructions = input.map { it.parseInstruction() }
        val actual = instructions.readSignalStrengths().sum()
        assertEquals(13140, actual)
    }

    @Test
    fun `part 2 sample`() {
        val instructions = input.map { it.parseInstruction() }
        val expected = """
            ##..##..##..##..##..##..##..##..##..##..
            ###...###...###...###...###...###...###.
            ####....####....####....####....####....
            #####.....#####.....#####.....#####.....
            ######......######......######......####
            #######.......#######.......#######.....
        """.trimIndent()
        val actual = instructions.readPixels().asScreen()
        assertEquals(expected, actual)
    }
}