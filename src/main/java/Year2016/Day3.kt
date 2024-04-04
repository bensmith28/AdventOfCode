package Year2016

import java.io.File

object Day3 {

    val input = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2016/Day3.txt")

    val triangles = input.readLines().map { Triangle.parse(it) }

    val columnTriangles = input.readLines().let { Triangle.parseColumns(it) }

    @JvmStatic
    fun main(args: Array<String>) {
        println("There are ${triangles.filter { it.isPossible }.count()} possible triangles")
        println("There are ${columnTriangles.filter { it.isPossible }.count()} possible column triangles")

        // 1627 is too high
    }

    data class Triangle(val a: Int, val b: Int, val c: Int) {
        companion object {
            fun parse(line: String): Triangle {
                val match = "\\s*(\\d+)\\s+(\\d+)\\s+(\\d+)\\s*".toRegex().matchEntire(line)
                    ?: throw IllegalArgumentException("Bad triangle match: $line")

                return Triangle(
                    match.groupValues[1].toInt(),
                    match.groupValues[2].toInt(),
                    match.groupValues[3].toInt()
                )
            }

            fun parseColumns(lines: List<String>): List<Triangle> {
                return lines.windowed(3, 3).flatMap { group ->
                    val t1 = parse(group[0])
                    val t2 = parse(group[1])
                    val t3 = parse(group[2])

                    listOf(
                        Triangle(t1.a, t2.a, t3.a),
                        Triangle(t1.b, t2.b, t3.b),
                        Triangle(t1.c, t2.c, t3.c)
                    )
                }
            }
        }

        val isPossible = listOf(a,b,c).sorted().let { sides ->
            sides[0] + sides[1] > sides[2]
        }
    }
}