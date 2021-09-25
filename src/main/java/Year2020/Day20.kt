package Year2020

import java.io.File

object Day20 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day20.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val tiles = Tile.parseAll(input.readLines())
        val solution = Solution(tiles)
        val corners = solution.findCorners()

        println(corners)

        val product = corners.fold(1L) { product, tile -> product * tile.id }

        println("Product: $product")

        val placedTiles = findSolution(solution)

        println("Placed the tiles")

        val finalImage = markDragonsInImage(placedTiles)

        val roughness = calculateRoughness(finalImage)

        println("Roughness: $roughness")
    }

    fun findSolution(solution: Solution): Solution {
        val solutionAttempts = mutableListOf(solution.getStarterSolution())

        while(!solutionAttempts.first().isSolved) {
            val parent = solutionAttempts.removeAt(0)
            val nextSolutions = parent.getNextSolutions()
            solutionAttempts.addAll(0, nextSolutions)
        }

        return solutionAttempts.first()
    }
    
//                          #
//        #    ##    ##    ###
//         #  #  #  #  #  #

    val monsterPattern = "..................#.#....##....##....###.#..#..#..#..#..#...".toRegex()
    const val monsterWidth = 20
    const val monsterHeight = 3

    fun markDragonsInImage(solution: Solution): List<List<Char>> {
        val solvedImage = solution.solvedPixels()
        return sequenceOf(
            false to 0,
            false to 1,
            false to 2,
            false to 3,
            true to 0,
            true to 1,
            true to 2,
            true to 3
        ).map { (flipped, rotation) ->
            val image = solvedImage.let {
                if( flipped ) it.flip()
                else it
            }.let {
                (0 until rotation).fold(it) { image, _ ->
                    image.rotate()
                }
            }

            val imageWidth = image.first().size
            val monsterCoords = image.windowed(monsterHeight).mapIndexed { y, vertFrame ->
                (0 until imageWidth- monsterWidth).mapNotNull { x ->
                    val frameString = vertFrame.flatMap { it.subList(x, x + monsterWidth) }.joinToString("")
                    if(frameString.matches(monsterPattern)) {
                        println("Here be monsters: $x, $y")
                        x to y
                    } else null
                }
            }.flatten()

            monsterCoords.fold(image) { image, (x, y) ->
                image.mapIndexed { y0, pixelRow ->
                    when(y0) {
                        y -> pixelRow.toMutableList().apply {
                            set(x + 18, 'O')
                        }
                        y + 1 -> pixelRow.toMutableList().apply {
                            set(x, 'O')
                            set(x + 5, 'O')
                            set(x + 6, 'O')
                            set(x + 11, 'O')
                            set(x + 12, 'O')
                            set(x + 17, 'O')
                            set(x + 18, 'O')
                            set(x + 19, 'O')
                        }
                        y + 2 -> pixelRow.toMutableList().apply {
                            set(x + 1, 'O')
                            set(x + 4, 'O')
                            set(x + 7, 'O')
                            set(x + 10, 'O')
                            set(x + 13, 'O')
                            set(x + 16, 'O')
                        }
                        else -> pixelRow
                    }
                }
            }
        }.first { image -> image.any { pixelRow -> pixelRow.contains('O') } }
    }

    fun calculateRoughness(image: List<List<Char>>): Int {
        return image.sumBy { pixelRow ->
            pixelRow.count { c -> c == '#' }
        }
    }

    private fun List<List<Char>>.flip(): List<List<Char>> = this.map { it.reversed() }

    private fun List<List<Char>>.rotate(): List<List<Char>> {
        return this.first().indices.map { row ->
            (this.size - 1 downTo 0).map { column ->
                this[column][row]
            }
        }
    }

    class Solution(
        val tiles: List<Tile>,
        val placements: List<Placement> = emptyList(),
        val edgeLength: Int = -1
    ) {

        val isSolved = placements.size == tiles.size

        fun getStarterSolution(): Solution {
            val starter = findCorners().first()
            val otherEdges = tiles.minus(starter).flatMap { it.edges.plus(it.edgesReversed) }
            val starterPlacement = Placement.getAllPlacements(starter)
                .first { !otherEdges.contains(it.north) && !otherEdges.contains(it.west) }
            val edgeLength = generateSequence(1) { it + 1 }.first { it * it == tiles.size }
            return Solution(tiles, listOf(starterPlacement), edgeLength)
        }

        fun getNextSolutions(): List<Solution> {
            val eastToMatch = if(placements.size % edgeLength != 0) {
                placements.last().east
            } else -1
            val southToMatch = if( placements.size >= edgeLength ) {
                placements[placements.size - edgeLength].south
            } else -1
            //val possibleTiles = tiles.minus(placements.map { it.tile }).filter { it.edges.plus(it.edgesReversed).contains(eastToMatch) }
            val possiblePlacements = tiles.minus(placements.map { it.tile }).flatMap { tile ->
                Placement.getAllPlacements(tile).filter { placement ->
                    (eastToMatch < 0 || placement.west == eastToMatch) &&
                            (southToMatch < 0 || placement.north == southToMatch)
                }
            }
            return possiblePlacements.map { p ->
                Solution(tiles, placements.plus(p), edgeLength)
            }
        }

        fun findCorners(): List<Tile> {
            val cornerTiles = tiles.filter { prospectiveEdge ->
                val notSelfEdges = tiles.minus(prospectiveEdge).flatMap { it.edgesReversed.plus(it.edges) }
                prospectiveEdge.edges.count { edge -> !notSelfEdges.contains(edge) }  == 2
            }
            return cornerTiles
        }

        fun solvedPixels(): List<List<Char>> {
            return (0 until edgeLength).flatMap { tileRow ->
                val placedInRow = (tileRow*edgeLength until (tileRow+1)*edgeLength).map { placements[it] }
                placedInRow.first().imagePixels.indices.map { pixelRow ->
                    placedInRow.fold(emptyList<Char>()) { pixelsInRow, next -> pixelsInRow.plus(next.imagePixels[pixelRow]) }
                }
            }
        }
    }

    data class Placement(val tile: Tile, val flipped: Boolean, val rotation: Int) {
        companion object {
            fun getAllPlacements(tile: Tile): List<Placement> {
                return (0..3).fold(emptyList<Placement>()) { list, rotation ->
                    list
                        .plus(Placement(tile, true, rotation))
                        .plus(Placement(tile, false, rotation))
                }
            }
        }

        val north = when(rotation) {
            0 -> if(flipped) tile.edgesReversed[0] else tile.edges[0]
            1 -> if(flipped) tile.edgesReversed[1] else tile.edgesReversed[3]
            2 -> if(flipped) tile.edges[2] else tile.edgesReversed[2]
            3 -> if(flipped) tile.edges[3] else tile.edges[1]
            else -> throw IllegalArgumentException("Bad Rotation: $rotation")
        }

        val east = when(rotation) {
            0 -> if(flipped) tile.edges[3] else tile.edges[1]
            1 -> if(flipped) tile.edgesReversed[0] else tile.edges[0]
            2 -> if(flipped) tile.edgesReversed[1] else tile.edgesReversed[3]
            3 -> if(flipped) tile.edges[2] else tile.edgesReversed[2]
            else -> throw IllegalArgumentException("Bad Rotation: $rotation")
        }

        val south = when(rotation) {
            0 -> if(flipped) tile.edgesReversed[2] else tile.edges[2]
            1 -> if(flipped) tile.edgesReversed[3] else tile.edgesReversed[1]
            2 -> if(flipped) tile.edges[0] else tile.edgesReversed[0]
            3 -> if(flipped) tile.edges[1] else tile.edges[3]
            else -> throw IllegalArgumentException("Bad Rotation: $rotation")
        }

        val west = when(rotation) {
            0 -> if(flipped) tile.edges[1] else tile.edges[3]
            1 -> if(flipped) tile.edgesReversed[2] else tile.edges[2]
            2 -> if(flipped) tile.edgesReversed[3] else tile.edgesReversed[1]
            3 -> if(flipped) tile.edges[0] else tile.edgesReversed[0]
            else -> throw IllegalArgumentException("Bad Rotation: $rotation")
        }

        val imagePixels: List<List<Char>> = tile.trimmedToImage.let {
            if( flipped ) it.flip()
            else it
        }.let {
            (0 until rotation).fold(it) { image, _ ->
                image.rotate()
            }
        }
    }

    data class Tile(val id: Int, val pixels: List<List<Char>>) {
        val edges: List<Int>
        val edgesReversed: List<Int>
        val trimmedToImage: List<List<Char>>

        init {
            val north = pixels.first()
            val south = pixels.last()
            val east = pixels.map { it.last() }
            val west = pixels.map { it.first() }
            edges = listOf(north.hash(), east.hash(), south.hash(), west.hash())
            edgesReversed = listOf(
                north.reversed().hash(),
                east.reversed().hash(),
                south.reversed().hash(),
                west.reversed().hash()
            )
            trimmedToImage = pixels.drop(1).dropLast(1).map { it.drop(1).dropLast(1) }
        }

        private fun List<Char>.hash() = this.map { c ->
            when (c) {
                '.' -> 0
                '#' -> 1
                else -> throw IllegalArgumentException("Bad pixel char: $c")
            }
        }.joinToString("").toInt(2)

        companion object {
            fun parseAll(lines: List<String>): List<Tile> {
                return lines.plus("").fold(emptyList<String>() to emptyList<Tile>()) { (lines, tiles), nextLine ->
                    if (nextLine.isNotBlank()) {
                        lines.plus(nextLine) to tiles
                    } else {
                        emptyList<String>() to tiles.plus(parse(lines))
                    }
                }.second
            }

            fun parse(lines: List<String>): Tile {
                val idMatch = "Tile (\\d+):".toRegex().matchEntire(lines.first())
                    ?: throw IllegalArgumentException("Bad Tile Id Match: ${lines.first()}")
                val pixels = lines.drop(1).map { it.map { c -> c } }
                return Tile(idMatch.groupValues[1].toInt(), pixels)
            }
        }
    }
}