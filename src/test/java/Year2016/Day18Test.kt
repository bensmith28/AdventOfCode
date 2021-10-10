package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day18Test {
    @Test fun `sample 5x3`() {
        val expected = """
            ..^^.
            .^^^^
            ^^..^
        """.trimIndent()

        val actual = generateSequence("..^^.") {
            Day18.nextRow(it)
        }.take(3).joinToString("\n")

        assertEquals(expected, actual)
    }

    @Test fun `sample 10x10`() {
        val expected = """
            .^^.^.^^^^
            ^^^...^..^
            ^.^^.^.^^.
            ..^^...^^^
            .^^^^.^^.^
            ^^..^.^^..
            ^^^^..^^^.
            ^..^^^^.^^
            .^^^..^.^^
            ^^.^^^..^^
        """.trimIndent()

        val actual = generateSequence(".^^.^.^^^^") {
            Day18.nextRow(it)
        }.take(10).joinToString("\n")

        assertEquals(expected, actual)
    }

    @Test fun `count safe tiles in sample`() {
        assertEquals(38, Day18.countSafeTiles(".^^.^.^^^^", 10))
    }
}