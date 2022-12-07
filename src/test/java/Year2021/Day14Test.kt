package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals

class Day14Test {
    val sample = "/Year2021/Day14-sample.txt".asResourceFile().readLines()

    @Test fun parsing() {
        val result = Day14.PolymerTemplate.parse(sample)

        assertEquals("NNCB", result.base)
        assertEquals(16, result.rules.size)
        assertEquals('C', result.rules["CN"])
    }

    @Test fun `single step`() {
        val uut = Day14.PolymerTemplate.parse(sample)
        assertEquals(
            "NCNBCHB",
            uut.step(1).joinToString("")
        )
    }

    @Test fun `two steps`() {
        val uut = Day14.PolymerTemplate.parse(sample)
        assertEquals(
            "NBCCNBBBCBHCB",
            uut.step(2).joinToString("")
        )
    }

    @Test fun `three steps`() {
        val uut = Day14.PolymerTemplate.parse(sample)
        assertEquals(
            "NBBBCNCCNBBNBNBBCHBHHBCHB",
            uut.step(3).joinToString("")
        )
    }

    @Test fun `four steps`() {
        val uut = Day14.PolymerTemplate.parse(sample)
        assertEquals(
            "NBBNBNBBCCNBCNCCNBBNBBNBBBNBBNBBCBHCBHHNHCBBCBHCB",
            uut.step(4).joinToString("")
        )
    }

    @Test fun `part 1 score`() {
        val template = Day14.PolymerTemplate.parse(sample)
        assertEquals(
            1588,
            template.score(10)
        )
    }

    @Test fun `part 2 score`() {
        val uut = Day14.PolymerTemplate.parse(sample)
        assertEquals(
            2188189693529,
            uut.score(40)
        )
    }
}
