package Year2020

import java.io.File

object Day17 {

    val inputFile = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day17.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val dimension3 = PocketDimension.parse(inputFile.readLines(), 3)

        val final3d = (1..6).fold(dimension3) { d, _ -> d.step() }
        val cubeCount3d = final3d.powerCubes.size

        println("Cube Count: $cubeCount3d")

        val dimension4 = PocketDimension.parse(inputFile.readLines(), 4)

        val final4d = (1..6).fold(dimension4) { d, _ -> d.step() }
        val cubeCount4d = final4d.powerCubes.size

        println("Hypercube Count: $cubeCount4d")
    }

    class PocketDimension(val powerCubes: Set<PowerCube>) {
        companion object {
            fun parse(lines: List<String>, dimensions: Int): PocketDimension {
                val cubes = lines.mapIndexed { y, line ->
                    line.mapIndexedNotNull { x, c ->
                        when (c) {
                            '#' -> {
                                val params = listOf(x,y).plus((2 until dimensions).map { 0 }).toIntArray()
                                PowerCube(*params)
                            }
                            else -> null
                        }
                    }
                }.flatten().toSet()

                return PocketDimension(cubes)
            }
        }

        fun step(): PocketDimension {
            val staysActive = powerCubes.filter { cube ->
                val activeNeigborCount = cube.countActiveNeighbors()
                activeNeigborCount == 2 || activeNeigborCount == 3
            }.toSet()

            val inactiveNeigbors = powerCubes.flatMap { it.getNeighborCubes() }.toSet()
                .minus(powerCubes)
            val becomesActive = inactiveNeigbors.filter { cube ->
                cube.countActiveNeighbors() == 3
            }.toSet()

            return PocketDimension(staysActive.plus(becomesActive))
        }

        private fun PowerCube.countActiveNeighbors() =
            this.getNeighborCubes().intersect(powerCubes).count()
    }

    data class PowerCube(val coordinates: Map<Int, Int>) {

        constructor(vararg coords: Int) : this(coords.mapIndexed { d, value -> d to value }.toMap())

        fun getNeighborCubes(): Set<PowerCube> {
            return coordinates.entries.fold(listOf(emptyMap<Int, Int>())) { inProgress, (d, value) ->
                inProgress.flatMap { map ->
                    (value-1..value+1).map { v ->
                        map.plus(d to v)
                    }
                }
            }.filter { it != this.coordinates }.map { PowerCube(it) }.toSet()
        }
    }
}