package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day15Test {
    val sample = "/Year2016/Day15-sample.txt".asResourceFile().readLines()
    val discs = sample.map { Day15.Disc.fromLine(it) }.associateBy { it.id }

    @Test fun `test parsing`() {
        val samples = sample.map {
            Day15.Disc.fromLine(it)
        }

        assertEquals(Day15.Disc(1, 5, 4), samples.first())
    }

    @Test fun `starting alignment`() {
        assertEquals(0, discs.getValue(1).firstAlignedAt)
        assertEquals(1, discs.getValue(2).firstAlignedAt)
    }

    @Test fun `test first align`() {
        assertEquals(5, Day15.findAlignment(discs.values))

        val sampleB = "/Year2016/Day15-sample-b.txt".asResourceFile()
            .readLines().map { Day15.Disc.fromLine(it) }
        assertEquals(768, Day15.findAlignment(sampleB))
    }
}