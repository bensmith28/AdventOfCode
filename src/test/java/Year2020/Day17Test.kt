package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day17Test {
    private val sampleInput = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day17-sample.txt")

    @Test fun `test sample 3d`() {
        val dimension = Day17.PocketDimension.parse(sampleInput.readLines(), 3)

        val final = (1 .. 6).fold(dimension) { d, _ ->  d.step() }

        val expectedCubeCount = 112
        val actualCubeCount = final.powerCubes.size

        assertEquals(expectedCubeCount, actualCubeCount)
    }

    @Test fun `test sample 4d`() {
        val dimension = Day17.PocketDimension.parse(sampleInput.readLines(), 4)

        val final = (1 .. 6).fold(dimension) { d, _ ->  d.step() }

        val expectedCubeCount = 848
        val actualCubeCount = final.powerCubes.size

        assertEquals(expectedCubeCount, actualCubeCount)
    }
}