package Year2021

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day2Test {
    private val input = "/Year2021/Day2-sample.txt".asResourceFile().readLines()

    @Test
    fun `part 1 sample`() {
        assertEquals(
            150,
            Day2.evalPosition(input.map { Day2.Command.fromLine(it) }).let { (hor, depth) -> hor * depth }
        )
    }

    @Test
    fun `part 2 sample`() {
        assertEquals(
            900,
            Day2.evalPositionWithAim(input.map { Day2.Command.fromLine(it) }).let { (hor, depth) -> hor * depth }
        )
    }
}