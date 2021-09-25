package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day4Test {
    @Test fun `sample room 1`() {
        val input = "aaaaa-bbb-z-y-x-123[abxyz]"
        val roomEntry = Day4.RoomEntry.parse(input)

        val expectedChecksum = "abxyz"

        assertEquals(expectedChecksum, roomEntry.correctChecksum)
        assertEquals(expectedChecksum, roomEntry.entryChecksum)
        assertTrue(roomEntry.isReal)
    }

    @Test fun `sample room 2`() {
        val input = "a-b-c-d-e-f-g-h-987[abcde]"
        val roomEntry = Day4.RoomEntry.parse(input)

        val expectedChecksum = "abcde"

        assertEquals(expectedChecksum, roomEntry.correctChecksum)
        assertEquals(expectedChecksum, roomEntry.entryChecksum)
        assertTrue(roomEntry.isReal)
    }

    @Test fun `sample room 3`() {
        val input = "not-a-real-room-404[oarel]"
        val roomEntry = Day4.RoomEntry.parse(input)

        val expectedChecksum = "oarel"

        assertEquals(expectedChecksum, roomEntry.correctChecksum)
        assertEquals(expectedChecksum, roomEntry.entryChecksum)
        assertTrue(roomEntry.isReal)
    }

    @Test fun `sample room 4`() {
        val input = "totally-real-room-200[decoy]"
        val roomEntry = Day4.RoomEntry.parse(input)

        // tt
        // ooo
        // aa
        // lll
        // y
        // rr
        // e
        // m
        // 2
        // 00
        val expectedChecksum = "loart"

        assertEquals(expectedChecksum, roomEntry.correctChecksum)
        assertEquals("decoy", roomEntry.entryChecksum)
        assertFalse(roomEntry.isReal)
    }

    @Test fun decryption() {
        val roomEntry = Day4.RoomEntry.parse("qzmt-zixmtkozy-ivhz-343[aaaaa]")

        assertEquals(
            "very encrypted name",
            roomEntry.decryptedName
        )
    }
}