package Year2022

import Year2022.Day3.getMisplacedItem
import Year2022.Day3.priority
import Year2022.Day3.sumOfBadges
import Year2022.Day3.sumOfPriorities
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import util.asResourceFile

internal class Day3Test {
    val rucksacks = "/Year2022/Day3-sample.txt".asResourceFile().readLines()

    @Test
    fun `validate char priorities`() {
        assertEquals(1, 'a'.priority())
        assertEquals(16, 'p'.priority())
        assertEquals(38, 'L'.priority())
    }

    @Test
    fun `sample first bad rucksacks bad item`() {
        assertEquals('p', getMisplacedItem(rucksacks.first()))
    }

    @Test
    fun `sample part 1`() {
        assertEquals(
            157,
            sumOfPriorities(rucksacks)
        )
    }

    @Test
    fun `sample part 2`() {
        assertEquals(
            70,
            sumOfBadges(rucksacks)
        )
    }
}