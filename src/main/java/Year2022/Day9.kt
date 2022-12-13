package Year2022

import Year2021.Coordinate
import Year2021.Day15
import Year2022.Day9.Instruction.Companion.parseInstruction
import util.asResourceFile

object Day9 {
    val input = "/Year2022/Day9.txt".asResourceFile().readLines()

    @JvmStatic
    fun main(args: Array<String>) {
        val part1Rope = Rope()
        val instructions = input.map { it.parseInstruction() }
        instructions.forEach { part1Rope += it }
        println("Part 1: ${part1Rope.locationHistory.size}")

        val part2Rope = Rope(10)
        instructions.forEach { part2Rope += it }
        println("Part 2: ${part2Rope.locationHistory.size}")
    }

    operator fun Coordinate.plus(move: Day15.Move): Coordinate =
        this.first + move.deltaX to this.second + move.deltaY

    data class Instruction(
        val move: Day15.Move,
        val magnitude: Int
    ) {
        companion object {
            private val parsePattern = """([UDLR]) (\d+)""".toRegex()
            private fun String.parseMove(): Day15.Move = when (this) {
                "U" -> Day15.Move.UP
                "D" -> Day15.Move.DOWN
                "L" -> Day15.Move.LEFT
                "R" -> Day15.Move.RIGHT
                else -> error("Bad move letter: $this")
            }

            fun String.parseInstruction() = parsePattern.matchEntire(this)!!.destructured.let { (move, magnitude) ->
                Instruction(
                    move.parseMove(),
                    magnitude.toInt()
                )
            }
        }
    }

    class Rope(
        val length: Int = 2
    ) {
        var tailLocation: Coordinate = 0 to 0
            set(value) {
                locationHistory += value
                field = value
            }
        val locationHistory: MutableSet<Coordinate> = mutableSetOf(tailLocation)

        val headLocations: MutableList<Coordinate> = MutableList(length) { tailLocation }

        operator fun plusAssign(instruction: Instruction) {
            repeat(instruction.magnitude) { this += instruction.move }
        }

        operator fun plusAssign(move: Day15.Move) {
            headLocations[0] = headLocations[0] + move
            for (i in 0 until headLocations.size - 1) {
                val lead = headLocations[i]
                val follow = headLocations[i + 1]

                when {
                    // Check for big diagonals first
                    lead + Day15.Move.DOWN + Day15.Move.DOWN + Day15.Move.LEFT + Day15.Move.LEFT == follow->
                        headLocations[i + 1] = lead + Day15.Move.DOWN + Day15.Move.LEFT
                    lead + Day15.Move.DOWN + Day15.Move.DOWN + Day15.Move.RIGHT + Day15.Move.RIGHT == follow ->
                        headLocations[i + 1] = lead + Day15.Move.DOWN + Day15.Move.RIGHT
                    lead + Day15.Move.UP + Day15.Move.UP + Day15.Move.LEFT + Day15.Move.LEFT == follow ->
                        headLocations[i + 1] = lead + Day15.Move.UP + Day15.Move.LEFT
                    lead + Day15.Move.UP + Day15.Move.UP + Day15.Move.RIGHT + Day15.Move.RIGHT == follow ->
                        headLocations[i + 1] = lead + Day15.Move.UP + Day15.Move.RIGHT
                    // The rest follow a more generalizable pattern
                    lead.first < (follow + Day15.Move.LEFT).first -> headLocations[i + 1] = lead + Day15.Move.RIGHT
                    lead.first > (follow + Day15.Move.RIGHT).first -> headLocations[i + 1] = lead + Day15.Move.LEFT
                    lead.second < (follow + Day15.Move.UP).second -> headLocations[i + 1] = lead + Day15.Move.DOWN
                    lead.second > (follow + Day15.Move.DOWN).second -> headLocations[i + 1] = lead + Day15.Move.UP
                }
            }
            tailLocation = headLocations.last()
        }
    }
}