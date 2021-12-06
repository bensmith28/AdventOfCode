package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals

class Day6Test {
    private val sampleFish = "/Year2021/Day6-sample.txt".asResourceFile().readLines()
        .single().split(",").map { Day6.Fish.labelled(it.toInt()) }

    @Test fun `produce fish`() {
        val startingFish = Day6.Fish.labelled(3)

        assertEquals(
            6,
            startingFish.produceFishUntil(40).count()
        )
    }

    @Test fun `produce fish why broken`() {
        val startingFish = Day6.Fish.bornOn(4)

        assertEquals(
            1,
            startingFish.produceFishUntil(15).count()
        )
    }

    @Test fun `produce more fish`() {
        assertEquals(
            4,
            Day6.countFish(listOf(Day6.Fish.labelled(3)), 15)
        )
    }

    @Test fun `part 1 sample`() {
        assertEquals(
            26,
            Day6.countFish(sampleFish, 18)
        )
        assertEquals(
            5934,
            Day6.countFish(sampleFish, 80)
        )
    }

    @Test fun `part 2 sample`() {
        assertEquals(
            26984457539,
            Day6.countFish(sampleFish, 256)
        )
    }
}