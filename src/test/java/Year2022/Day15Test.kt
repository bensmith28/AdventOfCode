package Year2022

import Year2022.Day15.findBeacon
import Year2022.Day15.knownEmptyOnRow
import Year2022.Day15.tuningFrequency
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day15Test {
    val input = "/Year2022/Day15-sample.txt".asResourceFile().readLines()

    @Test
    fun `test known empty`() {
        val uut = Day15.Sensor(
            8 to 7,
            2 to 10
        )
        val actual = uut.knownEmptyOnRow(10)
        assertEquals(
            12,
            actual.size
        )
    }

    @Test
    fun `test covered segment`() {
        val uut = Day15.Sensor(
            8 to 7,
            2 to 10
        )
        val location = 3 to 11
        assertEquals(
            11,
            uut.widthOfCoveredSegment(location)
        )
    }

    @Test
    fun `test covered segment past edge left of center`() {
        val uut = Day15.Sensor(
            8 to 7,
            2 to 10
        )
        val location = 5 to 11
        assertEquals(
            9,
            uut.widthOfCoveredSegment(location)
        )
    }

    @Test
    fun `test covered segment past edge right of center`() {
        val uut = Day15.Sensor(
            8 to 7,
            2 to 10
        )
        val location = 9 to 11
        assertEquals(
            5,
            uut.widthOfCoveredSegment(location)
        )
    }

    @Test
    fun `this shouldn't be happening`() {
        val uut = Day15.Sensor(
            20 to 14,
            25 to 17
        )
        val target = 14 to 11
        assertFalse(
            uut.covers(target)
        )
        assertEquals(
            0,
            uut.widthOfCoveredSegment(target)
        )
    }

    @Test
    fun `part 1`() {
        val sensors = input.map { Day15.Sensor.parse(it) }

        assertEquals(
            26,
            sensors.knownEmptyOnRow(10).size
        )
    }

    @Test
    fun `part 2`() {
        val sensors = input.map { Day15.Sensor.parse(it) }

        val beacon = sensors.findBeacon(20)
        assertEquals(
            14 to 11,
            beacon
        )
        assertEquals(
            56000011,
            beacon.tuningFrequency()
        )
    }
}