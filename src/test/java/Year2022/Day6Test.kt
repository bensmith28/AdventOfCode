package Year2022

import Year2022.Day6.findStartMarker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class Day6Test {
    @Nested inner class `find start of packet marker` {
        @Test fun `sample 1`() {
            assertEquals(
                7,
                "mjqjpqmgbljsphdztnvjfqwrcgsmlb".findStartMarker()
            )
        }

        @Test fun `sample 2`() {
            assertEquals(
                5,
                "bvwbjplbgvbhsrlpgdmjqwftvncz".findStartMarker()
            )
        }

        @Test fun `sample 3`() {
            assertEquals(
                6,
                "nppdvjthqldpwncqszvftbrmjlhg".findStartMarker()
            )
        }

        @Test fun `sample 4`() {
            assertEquals(
                10,
                "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".findStartMarker()
            )
        }

        @Test fun `sample 5`() {
            assertEquals(
                11,
                "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".findStartMarker()
            )
        }
    }

    @Nested inner class `find start of message marker` {
        @Test fun `sample 1`() {
            assertEquals(
                19,
                "mjqjpqmgbljsphdztnvjfqwrcgsmlb".findStartMarker(14)
            )
        }

        @Test fun `sample 2`() {
            assertEquals(
                23,
                "bvwbjplbgvbhsrlpgdmjqwftvncz".findStartMarker(14)
            )
        }

        @Test fun `sample 3`() {
            assertEquals(
                23,
                "nppdvjthqldpwncqszvftbrmjlhg".findStartMarker(14)
            )
        }

        @Test fun `sample 4`() {
            assertEquals(
                29,
                "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg".findStartMarker(14)
            )
        }

        @Test fun `sample 5`() {
            assertEquals(
                26,
                "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw".findStartMarker(14)
            )
        }
    }

}