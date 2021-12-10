package Year2021

import Year2021.Day10.autoComplete
import Year2021.Day10.autocompleteScore
import Year2021.Day10.firstIllegal
import Year2021.Day10.syntaxScore
import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals

class Day10Test {
    val samples = "/Year2021/Day10-sample.txt".asResourceFile().readLines()

    @Test fun `find first illegal a`() {
        assertEquals(
            '}',
            "{([(<{}[<>[]}>{[]{[(<()>".firstIllegal()
        )
    }

    @Test fun `find first illegal b`() {
        assertEquals(
            ')',
            "[[<[([]))<([[{}[[()]]]".firstIllegal()
        )
    }

    @Test fun `find first illegal c`() {
        assertEquals(
            ']',
            "[{[{({}]{}}([{[{{{}}([]".firstIllegal()
        )
    }

    @Test fun `find first illegal d`() {
        assertEquals(
            ')',
            "[<(<(<(<{}))><([]([]()".firstIllegal()
        )
    }

    @Test fun `find first illegal e`() {
        assertEquals(
            '>',
            "<{([([[(<>()){}]>(<<{{".firstIllegal()
        )
    }

    @Test fun `count the corrupted lines`() {
        assertEquals(
            5,
            samples.count { it.firstIllegal() != null }
        )
    }

    @Test fun `syntax score`() {
        assertEquals(
            26397,
            samples.syntaxScore()
        )
    }

    @Test fun `autocomplete a`() {
        assertEquals(
            "}}]])})]",
            "[({(<(())[]>[[{[]{<()<>>".autoComplete()
        )
    }

    @Test fun `autocomplete score for a line`() {
        assertEquals(
            288957,
            "[({(<(())[]>[[{[]{<()<>>".autocompleteScore()
        )
    }

    @Test fun `autocomplete score for file`() {
        assertEquals(
            288957,
            samples.autocompleteScore()
        )
    }
}