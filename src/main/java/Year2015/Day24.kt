package Year2015

import java.io.File

object Day24 {
    val packages = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2015/Day24.txt")
        .readLines().map { it.toInt() }

    @JvmStatic
    fun main(args: Array<String>) {
        if( packages.sum() % 4 != 0 ) throw IllegalArgumentException("Well, crap. Now What? ${packages.sum()}")
        val goalParcelWeight = packages.sum() / 4

        val parcelGenerator = (1..packages.size).asSequence().map { packageCount ->
            // Generate every possible parcel with the right count
            (2..packageCount).asSequence().fold(packages.map { listOf(it) }.asSequence()) { parcels, _ ->
                parcels.map { parcel ->
                    packages.minus(parcel).map { pkg ->
                        parcel.plus(pkg)
                    }
                }.flatten()
            }
        }.flatten()

        val minParcelSize = parcelGenerator.first { parcel ->
            parcel.sum() == goalParcelWeight
        }.size

        println("Smallest parcel size: $minParcelSize")

        val bestQuantum = parcelGenerator.takeWhile { parcel ->
            parcel.size <= minParcelSize
        }.filter { parcel ->
            parcel.size == minParcelSize && parcel.sum() == goalParcelWeight
        }.map { parcel ->
            parcel.fold(1.toLong()) { score, pkg -> score * pkg }
        }.minOrNull()

        println("Best quantum score: $bestQuantum")

    }
}
