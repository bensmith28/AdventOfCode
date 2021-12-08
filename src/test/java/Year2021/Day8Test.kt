package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class Day8Test {
    val sample1 = "/Year2021/Day8-sample-1.txt".asResourceFile().readLines().single()
    val sample2 = "/Year2021/Day8-sample-2.txt".asResourceFile().readLines()

    @Test fun parsing() {
        val note = Day8.Notes.parse(sample1)

        assertTrue(note.patterns.contains("acedgfb"))
        assertTrue(note.patterns.contains("ab"))
        assertTrue(note.outputs.contains("cdfeb"))
        assertEquals(4, note.outputs.size)
    }

    @Test fun `part 1`() {
        assertEquals(
            26,
            Day8.countSimples(sample2.map { Day8.Notes.parse(it) })
        )
    }

    @Test fun `part 2`() {
        assertEquals(
            5353,
            Day8.Notes.parse(sample1).output
        )
        assertEquals(
            61229,
            sample2.sumOf { Day8.Notes.parse(it).output }
        )
    }
}