package Year2016

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day11Test {
    private val steps = listOf(
        Day11.sample,
        Day11.MappedState(mapOf(
            1 to Day11.Floor(Day11.MicroChip('L')),
            2 to Day11.Floor(Day11.MicroChip('H'), Day11.Generator('H')),
            3 to Day11.Floor(Day11.Generator('L')),
            4 to Day11.Floor()
        ), 2, 1),
        Day11.MappedState(mapOf(
            1 to Day11.Floor(Day11.MicroChip('L')),
            2 to Day11.Floor(),
            3 to Day11.Floor(Day11.MicroChip('H'), Day11.Generator('H'), Day11.Generator('L')),
            4 to Day11.Floor()
        ), 3, 2),
        Day11.MappedState(mapOf(
            1 to Day11.Floor(Day11.MicroChip('L')),
            2 to Day11.Floor(Day11.MicroChip('H')),
            3 to Day11.Floor(Day11.Generator('H'), Day11.Generator('L')),
            4 to Day11.Floor()
        ), 2, 3),
        Day11.MappedState(mapOf(
            1 to Day11.Floor(Day11.MicroChip('L'), Day11.MicroChip('H')),
            2 to Day11.Floor(),
            3 to Day11.Floor(Day11.Generator('H'), Day11.Generator('L')),
            4 to Day11.Floor()
        ), 1, 4),
        Day11.MappedState(mapOf(
            1 to Day11.Floor(),
            2 to Day11.Floor(Day11.MicroChip('L'), Day11.MicroChip('H')),
            3 to Day11.Floor(Day11.Generator('H'), Day11.Generator('L')),
            4 to Day11.Floor()
        ), 2, 5)
    )

    @Test fun `all samples are not fried`() {
        steps.forEachIndexed { index, state ->
            assertFalse(state.isFried, "Sample $index is fried")
        }
    }

    @Test fun `all samples not fried, pair encoded`() {
        steps.map { Day11.PairEncodedState(it) }.forEachIndexed { index, state ->
            assertFalse(state.isFried, "Sample $index is fried")
        }
    }

    @Test fun `sample steps`() {
        steps.windowed(2).forEach { (now, next) ->
            assertTrue(now.getMoves().contains(next)) {
                now.getMoves().joinToString("\n\n", "\n", "\n") +
                        "\nFailed looking for step ${now.steps + 1}\n"
            }
        }
    }

    @Test fun `sample steps, pair encoded`() {
        steps.map { Day11.PairEncodedState(it) }.windowed(2).forEach { (now, next) ->
            assertTrue(now.getMoves().contains(next)) {
                now.getMoves().joinToString("\n\n", "\n", "\n") +
                        "\nFailed looking for step ${now.steps + 1}\n" +
                        "Expected:\n" +
                        "$next\n"
            }
        }
    }
}