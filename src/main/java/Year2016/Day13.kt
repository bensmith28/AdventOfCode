package Year2016

import kotlin.math.abs

object Day13 {
    val input = 1352

    @JvmStatic
    fun main(args: Array<String>) {
        val maze = Maze(input)
        val steps = maze.shortestPath(target = 31 to 39)
        val places = maze.placesInSteps(50)
        println(steps)
        println(places)
    }

    class Maze(private val favoriteNumber: Int) {
        fun at(x: Int, y: Int): Space {
            val test = (x * x + 3 * x + 2 * x * y + y + y * y + favoriteNumber).let {
                it.toString(2).count { c -> c == '1' } % 2
            }
            return when {
                x < 0 || y < 0 -> Space.wall
                test == 0 -> Space.space
                else -> Space.wall
            }
        }

        class Step(val place: Pair<Int, Int>, val step: Int = 0)

        fun shortestPath(start: Pair<Int, Int> = 1 to 1, target: Pair<Int, Int>): Int {
            val been = mutableSetOf<Pair<Int, Int>>()
            val queue = mutableListOf(Step(start))
            while (queue[0].place != target) {
                if (!been.add(queue[0].place)) {
                    queue.removeAt(0)
                    continue
                }

                val (x0, y0) = queue[0].place
                val moves = listOf(x0 + 1 to y0, x0 - 1 to y0, x0 to y0 + 1, x0 to y0 - 1)
                    .filter { (x, y) -> at(x, y) == Space.space && !been.contains(x to y) }
                    .map { Step(it, queue[0].step + 1) }
                queue.removeAt(0)
                queue.addAll(moves)
                queue.sortWith(Comparator { a, b ->
                    val (xa, ya) = a.place
                    val (xb, yb) = b.place
                    val (xt, yt) = target
                    val xad = abs(xa - xt)
                    val yad = abs(ya - yt)
                    val xbd = abs(xb - xt)
                    val ybd = abs(yb - yt)
                    val ascore = xad * xad + yad * yad
                    val bscore = xbd * xbd + ybd * ybd

                    ascore - bscore
                })
            }
            return queue[0].step
        }

        fun placesInSteps(maxSteps: Int, start: Pair<Int, Int> = 1 to 1): Int {
            val been = mutableSetOf<Pair<Int, Int>>()
            val queue = mutableListOf(Step(start))
            while (queue.isNotEmpty()) {
                if (queue[0].step > maxSteps || !been.add(queue[0].place)) {
                    queue.removeAt(0)
                    continue
                }

                val (x0, y0) = queue[0].place
                val moves = listOf(x0 + 1 to y0, x0 - 1 to y0, x0 to y0 + 1, x0 to y0 - 1)
                    .filter { (x, y) -> at(x, y) == Space.space && !been.contains(x to y) }
                    .map { Step(it, queue[0].step + 1) }
                queue.removeAt(0)
                queue.addAll(moves)
            }
            return been.size
        }
    }

    enum class Space(val c: Char) {
        wall('#'), space('.')
    }
}