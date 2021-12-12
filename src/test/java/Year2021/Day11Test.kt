package Year2021

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class Day11Test {
    @Test
    fun `simple grid sample`() {
        val cave = Day11.Cave.parse("/Year2021/Day11-sample-1.txt")

        assertEquals(9, cave.step())
        assertEquals(0, cave.step())
    }

    @Test
    fun `slightly bigger grid sample`() {
        val cave = Day11.Cave.parse("/Year2021/Day11-sample-2.txt")

        assertEquals(0, cave.step())
        assertEquals(35, cave.step())
        assertEquals(45, cave.step())
    }

    @Test
    fun `part 1 sample`() {
        val cave = Day11.Cave.parse("/Year2021/Day11-sample-2.txt")
        assertEquals(1656, cave.step(100))
    }

    @Test
    fun `part 2 sample`() {
        val cave = Day11.Cave.parse("/Year2021/Day11-sample-2.txt")
        assertEquals(195, Day11.findSync(cave))
    }
}