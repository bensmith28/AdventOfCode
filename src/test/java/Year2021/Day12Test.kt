package Year2021

import org.junit.jupiter.api.Test
import util.asResourceFile
import kotlin.test.assertEquals

class Day12Test {

    @Test fun `sample 1`() {
        val sample1 = "/Year2021/Day12-sample-1.txt".asResourceFile().readLines()
        val routes = Day12.Routes.parse(sample1)
        assertEquals(
            10,
            Day12.findAllPaths(routes)
        )
    }

    @Test fun `sample 2`() {
        val sample2 = "/Year2021/Day12-sample-2.txt".asResourceFile().readLines()
        val routes = Day12.Routes.parse(sample2)
        assertEquals(
            19,
            Day12.findAllPaths(routes)
        )
    }

    @Test fun `sample 3`() {
        val sample3 = "/Year2021/Day12-sample-3.txt".asResourceFile().readLines()
        val routes = Day12.Routes.parse(sample3)
        assertEquals(
            226,
            Day12.findAllPaths(routes)
        )
    }

    @Test fun `part 2 sample 1`() {
        val sample1 = "/Year2021/Day12-sample-1.txt".asResourceFile().readLines()
        val routes = Day12.Routes.parse(sample1)
        assertEquals(
            36,
            Day12.findAllPaths(routes, 2)
        )
    }
}