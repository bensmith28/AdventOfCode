package Year2020

import Year2020.Day13.findFirstTimeForSchedule
import Year2020.Day13.parseToSchedule
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import Year2020.Day13.primeFactors

internal class Day13Test {
    @Test fun `prime factorization 4`() {
        val expected = setOf(1, 2)
        val actual = 4.primeFactors()
        assertEquals(expected, actual)
    }

    @Test fun `prime factorization 23`() {
        val expected = setOf(1, 23)
        val actual = 23.primeFactors()
        assertEquals(expected, actual)
    }

    @Test fun `prime factorization 12`() {
        val expected = setOf(1, 2, 3)
        val actual = 12.primeFactors()
        assertEquals(expected, actual)
    }

    @Test fun `sample schedule 1`() {
        val schedule = parseToSchedule("7,13,x,x,59,x,31,19")
        val expected = 1068781L
        val actual = findFirstTimeForSchedule(schedule)

        assertEquals(expected, actual)
    }

    @Test fun `sample schedule 2`() {
        val schedule = parseToSchedule("17,x,13,19")
        val expected = 3417L
        val actual = findFirstTimeForSchedule(schedule)

        assertEquals(expected, actual)
    }

    @Test fun `sample schedule 3`() {
        val schedule = parseToSchedule("67,7,59,61")
        val expected = 754018L
        val actual = findFirstTimeForSchedule(schedule)

        assertEquals(expected, actual)
    }

    @Test fun `sample schedule 4`() {
        val schedule = parseToSchedule("67,x,7,59,61")
        val expected = 779210L
        val actual = findFirstTimeForSchedule(schedule)

        assertEquals(expected, actual)
    }

    @Test fun `sample schedule 5`() {
        val schedule = parseToSchedule("67,7,x,59,61")
        val expected = 1261476L
        val actual = findFirstTimeForSchedule(schedule)

        assertEquals(expected, actual)
    }

    @Test fun `sample schedule 6`() {
        val schedule = parseToSchedule("1789,37,47,1889")
        val expected = 1202161486L
        val actual = findFirstTimeForSchedule(schedule)

        assertEquals(expected, actual)
    }
}