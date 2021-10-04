package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day14Test {

    @Test fun `sample hashing`() {
        assertTrue(Day14.genPad(18, salt = "abc").contains("cc38887a5"))
        assertTrue(Day14.genPad(39, salt = "abc").contains("eee"))
        assertTrue(Day14.genPad(816, salt = "abc").contains(("eeeee")))
    }

    @Test fun `get nth key`() {
        assertEquals(39, Day14.getNthKey(1, "abc"))
        assertEquals(92, Day14.getNthKey(2, "abc"))
        assertEquals(22728, Day14.getNthKey(64, "abc"))
    }

    @Test fun `test stretch`() {
        assertEquals(Day14.genPad(0, salt = "abc"), "577571be4de9dcce85a041ba0410f29f")
        assertEquals(Day14.genPad(0, 1, "abc"), "eec80a0c92dc8a0777c619d9bb51e910")
        assertEquals(Day14.genPad(0, 2, "abc"), "16062ce768787384c81fe17a7a60c7e3")
        assertEquals(Day14.genPad(0, 2016, "abc"), "a107ff634856bb300138cac6568c0f24")

    }
}