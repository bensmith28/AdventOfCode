package Year2021

import util.asResourceFile

object Day2 {

    @JvmStatic
    fun main(args: Array<String>) {
        val commands = "/Year2021/Day2.txt".asResourceFile().readLines().map { Command.fromLine(it) }

        val (horizontalPosition, depth) = evalPosition(commands)

        println("""
            Part 1
            Horizontal Position: $horizontalPosition
            Depth:               $depth
            Product:             ${horizontalPosition * depth}
            
        """.trimIndent())

        val position = evalPositionWithAim(commands)
        println("""
            Part 2
            Horizontal Position: ${position.horizontal}
            Depth:               ${position.depth}
            Product:             ${position.horizontal * position.depth}
        """.trimIndent())
    }

    fun evalPosition(commands: List<Command>) = commands.fold(0 to 0) { position, command ->
        val (hor, depth) = position
        when (command.dir) {
            Direction.FORWARD -> hor + command.mag to depth
            Direction.DOWN -> hor to depth + command.mag
            Direction.UP -> hor to depth - command.mag
        }
    }

    fun evalPositionWithAim(commands: List<Command>) = commands.fold(Position()) { position, command ->
        when(command.dir) {
            Direction.FORWARD -> position.copy(
                horizontal = position.horizontal + command.mag,
                depth = position.depth + position.aim * command.mag
            )
            Direction.DOWN -> position.copy(aim = position.aim + command.mag)
            Direction.UP -> position.copy(aim = position.aim - command.mag)
        }
    }

    class Command(val dir: Direction, val mag: Int) {
        companion object {
            fun fromLine(line: String) = line.trim().split(" ").let { (dir, mag) ->
                Command(Direction.parse(dir), mag.toInt())
            }
        }
    }

    enum class Direction {
        FORWARD, DOWN, UP;

        companion object {
            fun parse(s: String) = valueOf(s.uppercase())
        }
    }

    data class Position(val horizontal: Int = 0, val depth: Int = 0, val aim: Int = 0)
}