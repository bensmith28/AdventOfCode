package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day9Test {
    @Test fun `test ADVENT`() {
        val expected = "ADVENT"
        val actual = Day9.decompress("ADVENT")

        assertEquals(expected, actual)
    }

    @Test fun `test A(1x5)BC`() {
        val expected = "ABBBBBC"
        val actual = Day9.decompress("A(1x5)BC")

        assertEquals(expected, actual)
    }

    @Test fun `test A(2x2)BCD(2x2)EFG`() {
        val expected = "ABCBCDEFEFG"
        val actual = Day9.decompress("A(2x2)BCD(2x2)EFG")

        assertEquals(expected, actual)
    }

    @Test fun `test (6x1)(1x3)A`() {
        val expected = "(1x3)A"
        val actual = Day9.decompress("(6x1)(1x3)A")

        assertEquals(expected, actual)
    }

    @Test fun `test X(8x2)(3x3)ABCY`() {
        val expected = "X(3x3)ABC(3x3)ABCY"
        val actual = Day9.decompress("X(8x2)(3x3)ABCY")

        assertEquals(expected, actual)
    }

    @Test fun `test v2 (3x3)XYZ`() {
        val expected = "XYZXYZXYZ".length.toLong()
        val actual = Day9.decompressedLength(Day9.Segment.parseToSegments("(3x3)XYZ"))

        assertEquals(expected, actual)
    }

    @Test fun `test v2 X(8x2)(3x3)ABCY`() {
        val expected = "XABCABCABCABCABCABCY".length.toLong()
        val actual = Day9.decompressedLength(Day9.Segment.parseToSegments("X(8x2)(3x3)ABCY"))

        assertEquals(expected, actual)
    }

    @Test fun `test v2 lots of 'A'`() {
        val expectedLength = 241920L
        val segments = Day9.Segment.parseToSegments("(27x12)(20x12)(13x14)(7x10)(1x12)A")
        val actualLength = Day9.decompressedLength(segments)

        assertEquals(expectedLength, actualLength)
    }

    @Test fun `test v2 another long one`() {
        val expectedLength = 445L
        val actualLength = Day9.decompressedLength(Day9.Segment.parseToSegments(
            "(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN"
        ))

        assertEquals(expectedLength, actualLength)
    }

    @Test fun `validate segments`() {
        val expected = listOf(
            Day9.Marker(6, 25, 3),
            Day9.Marker( 5,3, 3),
            Day9.Literal.generate(3),
            Day9.Marker(5, 2, 3),
            Day9.Literal.generate(2),
            Day9.Marker(5, 5, 2),
            Day9.Literal.generate(6),
            Day9.Marker(6, 18, 9),
            Day9.Marker(5, 3, 2),
            Day9.Literal.generate(3),
            Day9.Marker(5, 5, 7),
            Day9.Literal.generate(5)
        ).flatMap {
            if( it is List<*> ) it
            else listOf(it)
        }

        val actual = Day9.Segment.parseToSegments("(25x3)(3x3)ABC(2x3)XY(5x2)PQRSTX(18x9)(3x2)TWO(5x7)SEVEN")

        assertEquals(expected, actual)
    }
}