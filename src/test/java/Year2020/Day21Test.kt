package Year2020

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

internal class Day21Test {
    val sampleInputFile = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day21-sample.txt")

    @Test fun `test food parsing`() {
        val foods = sampleInputFile.readLines().map { Day21.Food.parse(it) }

        assertEquals(4, foods.size)
    }

    @Test fun `test finding safe ingredients`() {
        val foods = sampleInputFile.readLines().map { Day21.Food.parse(it) }

        val safe = Day21.ingredientsThatCantBeAllergens(foods)

        assertEquals(
            setOf("kfcds", "nhms", "sbzzf", "trh"),
            safe
        )
    }

    @Test fun `test find dangerous ingredients`() {
        val foods = sampleInputFile.readLines().map { Day21.Food.parse(it) }

        val expected = setOf(
            Day21.Ingredient("mxmxvkd", "dairy"),
            Day21.Ingredient("sqjhc", "fish"),
            Day21.Ingredient("fvjkl", "soy")
        )
        val actual = Day21.identifyDangerousIngredients(foods)

        assertEquals(expected, actual)
    }
}