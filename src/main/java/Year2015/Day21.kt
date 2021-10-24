package Year2015

import java.lang.Integer.max
import kotlin.math.ceil
import kotlin.math.roundToInt

object Day21 {
    val boss = Character(109, 8, 2)

    val weapons = listOf(
        Item("Dagger", ItemType.WEAPON, 8, 4, 0),
        Item("Shortsword", ItemType.WEAPON, 10, 5, 0),
        Item("Warhammer", ItemType.WEAPON, 25, 6, 0),
        Item("Longsword", ItemType.WEAPON, 40, 7, 0),
        Item("Greataxe", ItemType.WEAPON, 74, 8, 0)
    )

    val armor = listOf(
        Item("DummyArmor", ItemType.ARMOR, 0, 0, 0),
        Item("Leather", ItemType.ARMOR, 13, 0, 1),
        Item("Chainmail", ItemType.ARMOR, 31, 0, 2),
        Item("Splintmail", ItemType.ARMOR, 53, 0, 3),
        Item("Bandedmail", ItemType.ARMOR, 75, 0, 4),
        Item("Platemail", ItemType.ARMOR, 102, 0, 5)
    )

    val rings = listOf(
        Item("DummyRing1", ItemType.RING, 0, 0, 0),
        Item("DummyRing2", ItemType.RING, 0, 0, 0),
        Item("Damage+1", ItemType.RING, 25, 1, 0),
        Item("Damage+2", ItemType.RING, 50, 2, 0),
        Item("Damage+3", ItemType.RING, 100, 3, 0),
        Item("Defense+1", ItemType.RING, 20, 0, 1),
        Item("Defense+2", ItemType.RING, 40, 0, 2),
        Item("Defense+3", ItemType.RING, 80, 0, 3)
    )

    @JvmStatic
    fun main(args: Array<String>) {
        val test = didYouWin(
            Character(100, 9, 1),
            boss)
        println("Test case: $test")

        val weaponChoices = weapons.map { listOf(it) }
        val armorChoices = armor.map { listOf(it) }
        val ringChoices = rings.flatMap { r1 ->
            rings.map { r2 ->
                setOf(r1, r2).mapNotNull { it }
            }
        }.toSet()

        val cheapestWinner = weaponChoices.flatMap { weapons ->
            armorChoices.flatMap { armors ->
                ringChoices.map { rings ->
                    Character(100, 0, 0, weapons.plus(armors).plus(rings))
                }.filter { didYouWin(it, boss) }
            }
        }.minByOrNull { you -> you.cost } ?: throw IllegalStateException("NO WINNER ?!?!?!")

        println("You won with ${cheapestWinner.cost} gold!")

        val biggestLoser = weaponChoices.flatMap { weapons ->
            armorChoices.flatMap { armors ->
                ringChoices.map { rings ->
                    Character(100, 0, 0, weapons.plus(armors).plus(rings))
                }.filter { !didYouWin(it, boss) }
            }
        }.maxByOrNull { you -> you.cost } ?: throw IllegalStateException("NO WINNER ?!?!?!")

        println("You lost with ${biggestLoser.cost} gold!")
    }

    fun didYouWin(you: Character, boss: Character): Boolean {
        val yourDamage = max(you.damage - boss.armor, 1)
        val bossesDamage = max(boss.damage - you.armor, 1)

        val yourTurnsToWin = ceil(boss.hp.toDouble() / yourDamage).roundToInt()
        val bossTurnsToWin = ceil(you.hp.toDouble() / bossesDamage).roundToInt()
        return bossTurnsToWin > yourTurnsToWin
    }
}

class Character(val hp: Int, damage: Int, armor: Int, val items: List<Item> = emptyList()) {
    val damage = damage + items.sumOf { it.damage }
    val armor = armor + items.sumOf { it.armor }
    val cost = items.sumOf { it.cost }
}

data class Item(val name: String, val type: ItemType, val cost: Int, val damage: Int, val armor: Int)

enum class ItemType {
    WEAPON, ARMOR, RING
}
