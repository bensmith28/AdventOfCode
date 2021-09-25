package Year2020

import java.io.File

object Day11 {

    val initialLayout = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day11.txt")
        .let { Layout2.parse(it.readLines()) }

    @JvmStatic
    fun main(args: Array<String>) {
        val steps = generateSequence(initialLayout as Layout) { last ->
            last.getNextLayout()
        }.windowed(2).takeWhile { (last, current) -> last != current }

        val (_, last) = steps.toList().last()
        println("Stable at ${last.countOccupied()}")
    }
    
    class Layout2(grid: Array<CharArray>): Layout(grid) {
        companion object {
            fun parse(lines: List<String>): Layout2 {
                return Layout2(Layout.parse(lines).grid)
            }
        }
        override val tolerance: Int = 5
        override val weightFunction = this::countOccupiedNeighborsInSight

        override fun getNextLayout(): Layout2 {
            return Layout2(getNextGrid())
        }

        private fun goUp(x: Int, y: Int) = x to y - 1
        private fun goDown(x: Int, y: Int) = x to y + 1
        private fun goRight(x: Int, y: Int) = x + 1 to y
        private fun goLeft(x: Int, y: Int) = x - 1 to y
        private fun goUpRight(x: Int, y: Int) = x + 1 to y - 1
        private fun goDownRight(x: Int, y: Int) = x + 1 to y + 1
        private fun goDownLeft(x: Int, y: Int) = x - 1 to y + 1
        private fun goUpLeft(x: Int, y: Int) = x -1 to y - 1
        private fun Pair<Int,Int>.isOnGrid(): Boolean {
            val x = this.first
            val y = this.second
            return x >= 0 && x < grid[0].size && y >= 0 && y < grid.size
        }
        private val directions = listOf(
            ::goUp, ::goDown, ::goRight, ::goLeft, ::goUpRight, ::goDownRight, ::goDownLeft, ::goUpLeft
        )
        fun countOccupiedNeighborsInSight(x: Int, y: Int): Int {
            return directions.map { getNext ->
                generateSequence(getNext(x,y)) { (x0, y0) ->
                    getNext(x0, y0)
                }.takeWhile { it.isOnGrid() }.map { (x,y) -> getSeat(x,y) }.firstOrNull { seat ->
                    seat != floor
                } ?: empty
            }.filter { it == occupied }.count()
        }
    }

    open class Layout(val grid: Array<CharArray>) {

        open val tolerance: Int = 4
        open val weightFunction: (x: Int, y: Int) -> Int = this::countOccupiedNeighbors

        protected val occupied = '#'
        protected val empty = 'L'
        protected val floor = '.'

        companion object {
            fun parse(lines: List<String>): Layout {
                val grid = lines.map {
                    it.toCharArray()
                }.toTypedArray()
                return Layout(grid)
            }
        }

        protected fun getNextGrid(): Array<CharArray> {
            val nextGrid = grid.mapIndexed { y, row ->
                row.mapIndexed { x, light ->
                    val weight = weightFunction(x,y)
                    val seat = grid[y][x]
                    when {
                        seat == empty && weight == 0 -> occupied
                        seat == occupied && weight >= tolerance -> empty
                        else -> seat
                    }
                }.toCharArray()
            }.toTypedArray()
            return nextGrid
        }

        open fun getNextLayout(): Layout {
            return Layout(getNextGrid())
        }

        fun countOccupied(): Int {
            return grid.sumBy { row -> row.count { spot -> spot == occupied } }
        }

        fun countOccupiedNeighbors(x: Int, y: Int): Int {
            return listOf(x+1 to y+1, x+1 to y, x+1 to y-1,
                x to y+1, x to y-1,
                x-1 to y+1, x-1 to y, x-1 to y-1)
                .map { (x0,y0) -> getSeat(x0,y0) }.filter { it == occupied }.count()
        }

        protected fun getSeat(x: Int, y: Int): Char {
            return if(x < 0 || x >= grid[0].size || y < 0 || y >= grid.size) {
                floor
            } else {
                grid[y][x]
            }
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Layout

            if (!grid.contentDeepEquals(other.grid)) return false

            return true
        }

        override fun hashCode(): Int {
            return grid.contentDeepHashCode()
        }

        fun print() {
            grid.forEach {
                println(it.joinToString(""))
            }
        }
    }
}