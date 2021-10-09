package Year2016

import org.junit.jupiter.api.Assertions.*
import Year2016.Day16.applyDragon
import Year2016.Day16.fillDisk
import Year2016.Day16.checksum
import Year2016.Day16.fillAndSum
import org.junit.jupiter.api.Test

internal class Day16Test {
    @Test fun `dragon curve samples`() {
        assertEquals("100", "1".applyDragon().joinToString(""))
        assertEquals("001", "0".applyDragon().joinToString(""))
        assertEquals("11111000000", "11111".applyDragon().joinToString(""))
        assertEquals("1111000010100101011110000", "111100001010".applyDragon().joinToString(""))
    }

    @Test fun `fill disk sample`() {
        assertEquals(
            "10000011110010000111",
            fillDisk("10000", 20)
        )
    }

    @Test fun `checksum sample`() {
        assertEquals("100", "110010110100".checksum())
    }

    @Test fun `all the steps sample`() {
        assertEquals(
            "01100",
            fillAndSum("10000", 20)
        )
        assertEquals(
            "01110011101111011",
            fillAndSum("11110010111001001", 272)
        )
    }

    @Test fun `multiple dragons`() {
        assertEquals(
            "1".applyDragon().joinToString("").applyDragon().joinToString(""),
            "1".applyDragon(2).joinToString("")
        )
        assertEquals(
            "1".applyDragon().joinToString("").applyDragon().joinToString("").applyDragon().joinToString(""),
            "1".applyDragon(3).joinToString("")
        )
    }
}