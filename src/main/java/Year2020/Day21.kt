package Year2020

import java.io.File

object Day21 {
    @JvmStatic
    fun main(args: Array<String>) {
        val foods = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day21.txt")
            .readLines().map { Food.parse(it) }

        val safeIngredients = ingredientsThatCantBeAllergens(foods)

        val appearances = foods.sumBy { food ->
            food.ingredientNames.count { safeIngredients.contains(it) }
        }

        println("Safe ingredients appear $appearances times")

        val ingredients = identifyDangerousIngredients(foods)

        val shoppingList = ingredients.sortedBy { it.allergen }.joinToString(",") { it.name }

        println("Shopping List: $shoppingList")
    }

    fun ingredientsThatCantBeAllergens(foods: List<Food>): Set<String> {
        val allergens = foods.flatMap { it.allergenNames }.toSet()

        val possibleIngredients = allergens.map { allergen ->
            val foodsWithAllergen = foods.filter { it.allergenNames.contains(allergen) }
            val overlappingIngredients = foodsWithAllergen.drop(1)
                .fold(foodsWithAllergen.first().ingredientNames.toSet()) { set, food ->
                    set.intersect(food.ingredientNames)
                }
            allergen to overlappingIngredients
        }.toMap()

        val allIngredients = foods.flatMap { it.ingredientNames }.toSet()

        return allIngredients.minus(possibleIngredients.values.flatten().toSet())
    }

    fun identifyDangerousIngredients(foods: List<Food>): Set<Ingredient> {
        val safeIngredients = ingredientsThatCantBeAllergens(foods)
        val processedFoods = foods.map { it.copy(ingredientNames = it.ingredientNames.minus(safeIngredients)) }

        val dangerousIngredients = foods.flatMap { f -> f.ingredientNames.map { i -> Ingredient(i) } }
            .filter { i -> !safeIngredients.contains(i.name) }.toSet()

        val allergens = processedFoods.flatMap { it.allergenNames }.toSet()

        while(dangerousIngredients.any { it.allergen == null }) {
            allergens.minus(dangerousIngredients.mapNotNull { it.allergen }).forEach { a ->
                val remainingIngredients = dangerousIngredients
                    .filter { it.allergen == null }.map { it.name }.toSet()
                val foodsWithAllergen = processedFoods.filter { it.allergenNames.contains(a) }
                foodsWithAllergen.fold(remainingIngredients) { set, food ->
                        set.intersect(food.ingredientNames)
                    }.singleOrNull()?.let { ingredient ->
                        dangerousIngredients.single { it.name == ingredient }.allergen = a
                    }
            }
        }

        return dangerousIngredients
    }

    data class Food(val ingredientNames: List<String>, val allergenNames: List<String>) {
        companion object {
            fun parse(line: String): Food {
                val match = "([\\w ]+ )\\(contains (.*)\\)".toRegex().matchEntire(line)
                    ?: throw IllegalArgumentException("Bad food match: $line")

                return Food(
                    match.groupValues[1].split(" ").filter { it.isNotBlank() },
                    match.groupValues[2].split(", ").filter { it.isNotBlank() }
                )
            }
        }
    }

    data class Ingredient(val name: String, var allergen: String? = null) {
        val isDangerous = allergen != null
    }

}