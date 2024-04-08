package Year2023

import Year2023.Day5.parseAlmanac
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

class Day5Test {
    val sample = "/Year2023/Day5-sample.txt".asResourceFile().readLines()

    @Test fun testParse() {
        val actual = sample.parseAlmanac()

        assertNotNull(actual) // passed visual inspection
    }

    @Test fun locationForSeed() {
        val uut = sample.parseAlmanac()
        assertEquals(82, uut.locationForSeed(79))
        assertEquals(43, uut.locationForSeed(14))
        assertEquals(86, uut.locationForSeed(55))
        assertEquals(35, uut.locationForSeed(13))
    }

    @Test fun part1() {
        val uut = sample.parseAlmanac()
        assertEquals(35, uut.minLocation())
    }
}