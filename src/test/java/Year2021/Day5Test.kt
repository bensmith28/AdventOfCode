package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

class Day5Test {
    private val input = "/Year2021/Day5-sample.txt".asResourceFile().readLines()

    @Test fun `parse lines`() {
        val lines = input.map { Day5.Line.parse(it) }

        assertEquals(10, lines.size)
    }

    @Test fun `line points sample 1`() {
        val sample = "1,1 -> 1,3"
        val expected = listOf(
            1 to 1, 1 to 2, 1 to 3
        )

        assertContentEquals(
            expected,
            Day5.Line.parse(sample).points
        )
    }

    @Test fun `line points sample 2`() {
        val sample = "9,7 -> 7,7"
        val expected = listOf(
            9 to 7, 8 to 7, 7 to 7
        )

        assertContentEquals(
            expected,
            Day5.Line.parse(sample).points
        )
    }

    @Test fun `part 1 sample`() {
        val map = Day5.VentMap(input.map { Day5.Line.parse(it) }.filter { it.isVertical || it.isHorizontal })

        assertEquals(
            5,
            map.overlapped.size
        )
    }

    @Test fun `line points sample 3`() {
        val sample = "1,1 -> 3,3"
        val expected = listOf(
            1 to 1, 2 to 2, 3 to 3
        )

        assertContentEquals(
            expected,
            Day5.Line.parse(sample).points
        )
    }

    @Test fun `line points sample 4`() {
        val sample = "9,7 -> 7,9"
        val expected = listOf(
            9 to 7, 8 to 8, 7 to 9
        )

        assertContentEquals(
            expected,
            Day5.Line.parse(sample).points
        )
    }

    @Test fun `part 2 sample`() {
        val map = Day5.VentMap(input.map { Day5.Line.parse(it) })

        assertEquals(
            12,
            map.overlapped.size
        )
    }
}