package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class Day23Test {
    val sampleInput = "389125467"

    @Test fun `test print state`() {
        val expected = "25467389"
        val actual = Day23.GameState.parse(sampleInput).printState()

        assertEquals(expected, actual)
    }

    @Test fun `run sample 10 steps`() {
        //5 (8) 3  7  4  1  9  2  6
        val expectedPrint = "92658374"
        val game = Day23.GameState.parse(sampleInput)
        game.step(10)

        assertEquals(expectedPrint, game.printState())
    }

    @Test fun `run sample 100 steps`() {
        val expected = "67384529"
        val game = Day23.GameState.parse(sampleInput)
        game.step(100)
        val actual = game.printState()

        assertEquals(expected, actual)
    }

    @Test fun `run special case sequential`() {
        val initial = Day23.GameState((1..20).toList().toTypedArray())
        initial.step(100)

        TODO()
    }

    @Test fun `1000000 cups, 1000000 moves`() {
        val expected = 149245887792L

        val game = Day23.GameState.parse(sampleInput).cupsToOneMillion()
        game.step(10000000)

        val indexOfOne = game.cups.indexOf(1)
        val indexOfNext = (indexOfOne + 1) % game.cups.size
        val indexOfNextNext = (indexOfOne + 2) % game.cups.size
        val actual = indexOfNext * indexOfNextNext

        assertEquals(expected, actual)
    }
}