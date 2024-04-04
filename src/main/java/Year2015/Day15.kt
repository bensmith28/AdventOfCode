package Year2015

import java.io.File
import java.lang.Integer.max
import kotlin.math.pow
import kotlin.math.roundToInt

object Day15 {
    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day15.txt")
    val cookieBatch = 1000
    val batches = 100
    val variation = 0.5

    @JvmStatic
    fun main(args: Array<String>) {
        val ingredients = input.readLines().map { Ingredient.fromString(it) }

        println("Ingredients:")
        ingredients.forEach { ing ->
            println("  $ing")
        }

        val startingCookies = (1..cookieBatch).map { i ->
            Cookie.randomize(ingredients)
        }

        val finalBatch = (1..batches).fold(startingCookies) { cookies, iteration ->
            cookies.sortedBy { it.score }.takeLast(cookieBatch / 20).flatMap { c ->
                c.generateMoreCookies(20, variation.pow(iteration/10))
            }
        }

        val bestCookie = finalBatch.maxByOrNull { it.score } ?: throw IllegalStateException("No Cookies!")

        println("winning cookie is         ${bestCookie.score}")

        val startingHealthyCookie = (1..cookieBatch).map { i ->
            Cookie.randomize(ingredients) { c -> c.calories == 500}
        }

        val finalHealthyBatch = (1..batches).fold(startingHealthyCookie) { cookies, iteration ->
            cookies.sortedBy { it.score }.takeLast(cookieBatch / 20).flatMap { c ->
                c.generateMoreCookies(20, variation.pow(iteration/10)) { c -> c.calories == 500 }
            }
        }

        val bestHealthyCookie = finalHealthyBatch.maxByOrNull { it.score } ?: throw IllegalStateException("No Cookies!")

        println("winning healthy cookie is ${bestHealthyCookie.score}")
    }
}

data class Ingredient(
    val name: String,
    val capacity: Int,
    val durability: Int,
    val flavor: Int,
    val texture: Int,
    val calories: Int
) {

    companion object {
        fun fromString(line: String): Ingredient {
            val match =
                "(\\w+): capacity ([-\\d]+), durability ([-\\d]+), flavor ([-\\d]+), texture ([-\\d]+), calories ([-\\d])"
                    .toRegex().matchEntire(line) ?: throw IllegalArgumentException("Bad ingredient match: $line")
            return Ingredient(
                name = match.groupValues[1],
                capacity = match.groupValues[2].toInt(),
                durability = match.groupValues[3].toInt(),
                flavor = match.groupValues[4].toInt(),
                texture = match.groupValues[5].toInt(),
                calories = match.groupValues[6].toInt()
            )
        }
    }
}

data class Cookie(
    val ingredients: Map<Ingredient, Int>
) {
    companion object {
        fun randomize(ingredients: List<Ingredient>, constraint: (Cookie) -> Boolean = { true }) =
            generateSequence {
                ingredients.map { ingredient ->
                    ingredient to (Math.random() * 1000)
                }.toMap().let { Cookie(it.evenTsps()) }
            }.first { constraint(it) }

        private fun <T> Map<T, Double>.evenTsps(): Map<T, Int> {
            val denom = this.values.sum()
            val mostAmounts = this.minus(this.keys.first())
                .mapValues { (_, p) -> (p / denom * 100).roundToInt() }
            return mostAmounts.plus(keys.first() to 100 - mostAmounts.values.sum())
        }
    }

    val score  = ingredients.let { ingredients ->
        val capacity = max(ingredients.map { (i, amount) -> i.capacity * amount }.sum(), 0)
        val durability = max(ingredients.map { (i, amount) -> i.durability * amount }.sum(), 0)
        val flavor = max(ingredients.map { (i, amount) -> i.flavor * amount }.sum(), 0)
        val texture = max(ingredients.map { (i, amount) -> i.texture * amount }.sum(), 0)

        capacity * durability * flavor * texture
    }

    val calories = ingredients.map { (i, amount) -> i.calories * amount }.sum()

    fun generateMoreCookies(count: Int, variability: Double, constraint: (Cookie) -> Boolean = { true }): List<Cookie> {
        return generateSequence(this) {
            ingredients.mapValues { (_, tsps) ->
                tsps / 100.0 + (Math.random() - .5) * variability
            }.let { Cookie(it.evenTsps()) }
        }.filter { constraint(it) }.take(count).toList()
    }

}
