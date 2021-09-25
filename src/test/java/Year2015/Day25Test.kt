package Year2015

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*

internal class Day25Test {
    @Test fun `1, 1`() {
        assertEquals(1, (1 to 1).toIndex())
    }

    @Test fun `1, 6`() {
        assertEquals(21, (1 to 6).toIndex())
    }

    @Test fun `6, 1`() {
        assertEquals(16, (6 to 1).toIndex())
    }

    @Test fun `4, 2`() {
        assertEquals(12, (4 to 2).toIndex())
    }

    @Test fun `code 1, 1`() {
        assertEquals(Day25.seed, Day25.generateCode((1 to 1).toIndex()))
    }

    @Test fun `code 4, 1`() {
        assertEquals(
            24592653,
            Day25.generateCode((4 to 1).toIndex())
        )
    }

    @Test fun `code 2, 5`() {
        assertEquals(
            15514188,
            Day25.generateCode((2 to 5).toIndex())
        )
    }

    @Test fun `code 6, 6`() {
        assertEquals(
            27995004,
            Day25.generateCode((6 to 6).toIndex())
        )
    }
}