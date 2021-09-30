package Year2016

import util.asResourceFile

object Day10 {
    val input = "/Year2016/Day10.txt".asResourceFile().readLines()
    val direct = mutableMapOf<Int, Int>()
    val bots = mutableMapOf<Int, Bot>()
    val outputs = mutableMapOf<Int, MutableList<Int>>()

    val DIRECT = """value (\d+) goes to bot (\d+)""".toRegex()
    val ACTION = """bot (\d+) gives low to (bot|output) (\d+) and high to (bot|output) (\d+)""".toRegex()

    fun getBot(id :Int) = bots.getOrPut(id) { Bot(id) }
    fun getOutput(id: Int) = outputs.getOrPut(id) { mutableListOf() }

    @JvmStatic
    fun main(args: Array<String>) {
        var important: Int = -1
        val (directs, actions) = input.partition { it matches DIRECT }
        actions.forEach { actionLine ->
            val match = ACTION.matchEntire(actionLine)!!
            val actor = match.groupValues[1].toInt()
            val lowTargetType = TargetType.valueOf(match.groupValues[2])
            val lowTargetId = match.groupValues[3].toInt()
            val highTargetType = TargetType.valueOf(match.groupValues[4])
            val highTargetId = match.groupValues[5].toInt()
            bots.getOrPut(actor) { Bot(actor) }.apply {
                action = {
                    println("Bot $id evaluating $chips")
                    if( chips.containsAll(setOf(61, 17))) {
                        println("IMPORTANT RESULT: I'm bot $id")
                        important = id
                    }
                    val (lowChip, highChip) = chips.sorted()
                    when(lowTargetType) {
                        TargetType.bot -> getBot(lowTargetId).give(lowChip)
                        TargetType.output -> getOutput(lowTargetId).add(lowChip)
                    }
                    when(highTargetType) {
                        TargetType.bot -> getBot(highTargetId).give(highChip)
                        TargetType.output -> getOutput(highTargetId).add(highChip)
                    }
                    chips.clear()
                }
            }
        }
        directs.forEach { directLine ->
            val match = DIRECT.matchEntire(directLine)!!
            val (chip, target) = match.groupValues.mapNotNull { it.toIntOrNull() }
            getBot(target).give(chip)
        }

        println("Results:")
        outputs.entries.sortedBy { it.key }.forEach { (id, chips) ->
            println("  $id: $chips")
        }
        println("Bot $important did the important thing")
        println("Product is ${outputs.getValue(0).single() * outputs.getValue(1).single() * outputs.getValue(2).single()}")
    }

    class Bot(val id: Int, val chips: MutableList<Int> = mutableListOf(), var action: () -> Unit = {}) {
        fun give(chip: Int) {
            chips.add(chip)
            println("Bot $id got chip $chip ($chips)")
            if(chips.size >= 2) {
                println("Bot $id full, doing a thing")
                action()
            }
        }
    }

    enum class TargetType {
        bot, output
    }
}