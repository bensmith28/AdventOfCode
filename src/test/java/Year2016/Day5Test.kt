package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day5Test {
    @Test fun `test sample`() {
        val sampleDoorId = "abc"
        val expected = "18f47a30"
        val actual = Day5.findPassword(sampleDoorId)

        assertEquals(expected, actual)
    }

    @Test fun `test second sample`() {
        val sampleDoorId = "abc"
        val expected = "05ace8e3"
        val actual = Day5.findPassword(sampleDoorId, Day5::hexParser2)

        assertEquals(expected, actual)
    }
}