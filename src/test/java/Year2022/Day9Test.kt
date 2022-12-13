package Year2022

import Year2022.Day9.Instruction.Companion.parseInstruction
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day9Test {
    val sampleA = "/Year2022/Day9-sample.txt".asResourceFile().readLines().map { it.parseInstruction() }
    val sampleB = "/Year2022/Day9-sampleB.txt".asResourceFile().readLines().map { it.parseInstruction() }

    @Test
    fun `part 1 sample`() {
        val uut = Day9.Rope()
        sampleA.forEach { uut += it }
        assertEquals(13, uut.locationHistory.size)
    }

    @Test
    fun `part 2 sample A`() {
        val uut = Day9.Rope(10)
        sampleA.forEach { uut += it }
        assertEquals(1, uut.locationHistory.size)
    }

    @Test
    fun `part 2 sample B`() {
        val uut = Day9.Rope(10)
        sampleB.forEach { uut += it }
        assertEquals(36, uut.locationHistory.size)
    }

    @Test
    fun `part 2 sample B partial`() {
        val uut = Day9.Rope(10)
        sampleB.take(2).forEach { uut += it }
        assertEquals(3 to -3, uut.headLocations[6])
    }
}