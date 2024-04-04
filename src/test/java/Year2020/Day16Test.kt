package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day16Test {
    @Test fun `test sample scanning error`() {
        val doc = Day16.Document.parse(File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day16-sample.txt"))

        val expected = 71
        val actual = Day16.getScanningError(doc)

        assertEquals(expected, actual)
    }

    @Test fun `test ticket parsing`() {
        val doc = Day16.Document.parse(File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day16-sampleB.txt"))

        val expected = mapOf(
            "class" to 12,
            "row" to 11,
            "seat" to 13
        )
        val actual = Day16.parseYourTicket(doc)

        assertEquals(expected, actual)
    }
}