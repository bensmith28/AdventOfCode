package Year2023

import util.asResourceFile

object Day5 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2023/Day5.txt".asResourceFile().readLines()
        val almanac = input.parseAlmanac()

        val part1 = almanac.minLocation()
        println("Part 1: $part1")
    }

    data class Almanac(
        val seeds: List<Long>,
        val seedToSoil: List<Conversion>,
        val soilToFertilizer: List<Conversion>,
        val fertilizerToWater: List<Conversion>,
        val waterToLight: List<Conversion>,
        val lightToTemperature: List<Conversion>,
        val temperatureToHumidity: List<Conversion>,
        val humidityToLocation: List<Conversion>
    ) {
        private operator fun List<Conversion>.get(key: Long) = this.asSequence()
            .map { it[key] }.firstOrNull { it != null } ?: key
        fun locationForSeed(seed: Long): Long {
            val soil = seedToSoil[seed]
            val fertilizer = soilToFertilizer[soil]
            val water = fertilizerToWater[fertilizer]
            val light = waterToLight[water]
            val temperature = lightToTemperature[light]
            val humidity = temperatureToHumidity[temperature]
            val location = humidityToLocation[humidity]
            return location
        }

        fun minLocation() = seeds.map { locationForSeed(it) }.minOrNull() ?: error("no seeds??")
    }
    
    class Conversion private constructor(
        val range: LongRange,
        val delta: Long
    ) {
        constructor(
            destStart: Long,
            sourceStart: Long,
            rangeLength: Long
        ): this(sourceStart until sourceStart+rangeLength, destStart - sourceStart)
        
        operator fun get(key: Long): Long? = if( key in range ) key + delta else null
    }

    fun List<String>.parseAlmanac(): Almanac {
        val lines = this.toMutableList()
        val seeds = lines.removeFirst().removePrefix("seeds: ").split(" ").map { it.toLong() }
        lines.removeFirst()
        check(lines.removeFirst() == "seed-to-soil map:")
        fun parseLine(s: String, list: MutableList<Conversion>) {
            val (destStart, sourceStart, rangeLength) =
                s.split(" ").map { it.toLong() }
            list += Conversion(destStart, sourceStart, rangeLength)
        }
        val sts = mutableListOf<Conversion>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), sts)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "soil-to-fertilizer map:")
        val stf = mutableListOf<Conversion>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), stf)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "fertilizer-to-water map:")
        val ftw = mutableListOf<Conversion>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), ftw)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "water-to-light map:")
        val wtl = mutableListOf<Conversion>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), wtl)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "light-to-temperature map:")
        val ltt = mutableListOf<Conversion>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), ltt)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "temperature-to-humidity map:")
        val tth = mutableListOf<Conversion>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), tth)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "humidity-to-location map:")
        val htl = mutableListOf<Conversion>()
        while(lines.isNotEmpty() && lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), htl)
        }
        return Almanac(
            seeds = seeds,
            seedToSoil = sts,
            soilToFertilizer = stf,
            fertilizerToWater = ftw,
            waterToLight = wtl,
            lightToTemperature = ltt,
            temperatureToHumidity = tth,
            humidityToLocation = htl
        )
    }
}