package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile
import java.io.File

internal class Day6Test {
    val sample = "/Year2016/Day6-sample.txt".asResourceFile().readLines()

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