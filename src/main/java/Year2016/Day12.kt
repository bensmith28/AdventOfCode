package Year2016

import util.asResourceFile

object Day12 {
    val sample = "/Year2016/Day12-sample.txt".asResourceFile().readLines()
    val input = "/Year2016/Day12.txt".asResourceFile().readLines()

    @JvmStatic
    fun main(args: Array<String>) {
        val lines = input.mapIndexed { i, l -> i to l }.toMap()

        println("Part 1:")
        println(evaluate(lines))
        println("\nPart 2:")
        println(evaluate(lines, Registers(c = 1)))
    }

    fun evaluate(lines: Map<Int, String>, reg: Registers = Registers()): Registers {
        var current = 0
        while(lines.containsKey(current)) {
            current += lines.getValue(current).let { l ->
                val cmd = l.toCommand()
                cmd.action(reg, l)
            }
        }
        return reg
    }

    class Registers(var a: Int = 0, var b: Int = 0, var c: Int = 0, var d: Int = 0) {
        override fun toString(): String {
            return """
                a: $a
                b: $b
                c: $c
                d: $d
            """.trimIndent()
        }
    }

    enum class Command(val matcher: Regex, val action: (reg: Registers, line: String) -> Int) {
        cpy("""cpy ([a-d])?(\d+)? ([a-d])""".toRegex(), { reg, line ->
            val match = cpy.matcher.matchEntire(line) ?: throw kotlin.IllegalArgumentException()
            val v = when(match.groupValues[1]) {
                "a" -> reg.a
                "b" -> reg.b
                "c" -> reg.c
                "d" -> reg.d
                else -> match.groupValues[2].toIntOrNull() ?: throw kotlin.IllegalArgumentException()
            }
            when(match.groupValues[3]) {
                "a" -> reg.a = v
                "b" -> reg.b = v
                "c" -> reg.c = v
                "d" -> reg.d = v
            }

            1
        }),
        inc("""inc ([a-d])""".toRegex(), { reg, line ->
            val match = inc.matcher.matchEntire(line) ?: throw kotlin.IllegalArgumentException()
            when(match.groupValues[1]) {
                "a" -> reg.a++
                "b" -> reg.b++
                "c" -> reg.c++
                "d" -> reg.d++
            }

            1
        }),
        dec("""dec ([a-d])""".toRegex(), { reg, line ->
            val match = dec.matcher.matchEntire(line) ?: throw kotlin.IllegalArgumentException()
            when(match.groupValues[1]) {
                "a" -> reg.a--
                "b" -> reg.b--
                "c" -> reg.c--
                "d" -> reg.d--
            }

            1 }),
        jnz("""jnz (-?[a-d0-9]+) (-?\d+)""".toRegex(), { reg, line ->
            val match = jnz.matcher.matchEntire(line) ?: throw kotlin.IllegalArgumentException()
            val test = when(match.groupValues[1]) {
                "a" -> reg.a
                "b" -> reg.b
                "c" -> reg.c
                "d" -> reg.d
                else -> match.groupValues[1].toInt()
            }
            if(test != 0) match.groupValues[2].toInt()
            else 1
        })
    }

    private fun String.toCommand() = Command.values().first { this.startsWith(it.name) }
}