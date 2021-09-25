package Year2015

import java.lang.Integer.max

object Day22 {

    @JvmStatic
    fun main(args: Array<String>) {

        val attempts = generateSequence {
            val boss = Boss()
            val you = Wizard()

//            println("You: ${you.hp} hp, ${you.mana} mana")
//            println("Boss: ${boss.hp}, ${boss.baseDamage} damage")
//            println()

            val casts = mutableMapOf<Int, Spell>()
            var turn = 0
            fun Map<Int, Spell>.getActiveSpells() =
                this.filter { (castOn, spell) -> castOn + spell.duration > turn }.values
            fun Map<Int, Spell>.applyActiveSpells() =
                this.getActiveSpells().forEach { spell -> spell.effect(you, boss) }

            while (you.hp > 0 && boss.hp > 0 && you.spells.any { it.cost < you.mana }) {
                // Your turn
                turn++
                you.hp--
                if( you.hp <= 0 ) break
                val activeSpells = casts.getActiveSpells()
                val spell = you.spells.minus(activeSpells).shuffled().first {
                    it.cost < you.mana
                }
                you.mana -= spell.cost
                casts[turn] = spell
                casts.applyActiveSpells()
                boss.restore()

                if( boss.hp <= 0 ) break

                // Bosses turn
                turn++
                casts.applyActiveSpells()
                you.hp -= boss.damage
                boss.restore()
            }

            val youWon = you.hp > 0 && boss.hp <= 0

            /*if( youWon && you.spentMana == 1309 ) {
                println()
                println("$turn turns")
                println(casts.map { "${it.key}:${it.value.name}" })
            }*/

            youWon to you.spentMana
        }.filter { (youWon, _) -> youWon }.map { (_, spentMana) -> spentMana }.take(10000)

        println("Best Attempt: ${attempts.min()}")
        // 907 too low
        // 1288 too high
        // Right answer: 953

        // Part 2
        // 1196 too low
        // 1309 too high

    }
}

data class Boss(var hp: Int = 55, val baseDamage: Int = 8) {
    var damage = baseDamage
    fun restore() {
        damage = baseDamage
    }


}

class Wizard {
    var hp = 50
    var spentMana = 0
            private set

    var mana = 500
        set(value) {
            if( value < field ) spentMana += field - value
            field = value
        }

    val spells = listOf(
        Spell("Magic Missile", 53, 1) { you, boss ->
            boss.hp -= 4
        },
        Spell("Drain", 73, 1) { you, boss ->
            you.hp += 2
            boss.hp -= 2
        },
        Spell("Shield", 113, 6) { _, boss ->
            boss.damage = max(boss.damage - 7, 1)
        },
        Spell("Poison", 173, 6) { _, boss ->
            boss.hp -= 3
        },
        Spell("Recharge", 229, 5) { you, _ ->
            you.mana += 101
        }
    )
}

data class Spell(val name: String, val cost: Int, val duration: Int, val effect: (Wizard, Boss) -> Unit)