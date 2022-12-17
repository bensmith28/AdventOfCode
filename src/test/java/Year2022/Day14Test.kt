package Year2022

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day14Test {
    val input = "/Year2022/Day14-sample.txt".asResourceFile().readLines()

    @Test
    fun `part 1`() {
        val uut = Day14.Scan.parse(input)
        assertEquals(
            24,
            uut.fillSand()
        )
    }

    @Test
    fun `part 2`() {
        val uut = Day14.Scan.parse(input)
        uut.addFloor()
        assertEquals(
            93,
            uut.fillSand()
        )
    }
}