package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day7Test {
    @Test fun `verify tls`() {
        val samples = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2016/Day7-sample.txt")
            .readLines()

        val expected = listOf(true, false, false, true, true)
        val actual = samples.map { Day7.supportsTls(it) }

        assertEquals(expected, actual)
    }

    @Test fun `verify ssl`() {
        val samples = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2016/Day7-sampleB.txt")
            .readLines()

        val expected = listOf(true, false, true, true, false, true, false)
        val actual = samples.map { Day7.supportsSsl(it) }

        assertEquals(expected, actual)
    }
}