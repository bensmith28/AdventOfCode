package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day6Test {
    val sample = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2016/Day6-sample.txt")
        .readLines()

    /*@Test*/ fun `test sample`() {
        val expected = "easter"
        val actual = Day6.correctErrors(sample)

        assertEquals(expected, actual)
    }

    @Test fun `test sample other profile`() {
        val expected = "advent"
        val actual = Day6.correctErrors(sample)

        assertEquals(expected, actual)
    }
}