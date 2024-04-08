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
        val seedToSoil: Map<Long,Long>,
        val soilToFertilizer: Map<Long,Long>,
        val fertilizerToWater: Map<Long,Long>,
        val waterToLight: Map<Long,Long>,
        val lightToTemperature: Map<Long,Long>,
        val temperatureToHumidity: Map<Long,Long>,
        val humidityToLocation: Map<Long,Long>
    ) {
        fun locationForSeed(seed: Long): Long {
            val soil = seedToSoil[seed] ?: seed
            val fertilizer = soilToFertilizer[soil] ?: soil
            val water = fertilizerToWater[fertilizer] ?: fertilizer
            val light = waterToLight[water] ?: water
            val temperature = lightToTemperature[light] ?: light
            val humidity = temperatureToHumidity[temperature] ?: temperature
            val location = humidityToLocation[humidity] ?: humidity
            return location
        }

        fun minLocation() = seeds.map { locationForSeed(it) }.minOrNull() ?: error("no seeds??")
    }

    fun List<String>.parseAlmanac(): Almanac {
        val lines = this.toMutableList()
        val seeds = lines.removeFirst().removePrefix("seeds: ").split(" ").map { it.toLong() }
        lines.removeFirst()
        check(lines.removeFirst() == "seed-to-soil map:")
        fun parseLine(s: String, map: MutableMap<Long,Long>) {
            val (destStart, sourceStart, rangeLength) =
                s.split(" ").map { it.toLong() }
            val delta = destStart - sourceStart
            (sourceStart until sourceStart+rangeLength).forEach { i ->
                map[i] = i + delta
            }
        }
        val sts = mutableMapOf<Long,Long>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), sts)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "soil-to-fertilizer map:")
        val stf = mutableMapOf<Long,Long>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), stf)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "fertilizer-to-water map:")
        val ftw = mutableMapOf<Long,Long>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), ftw)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "water-to-light map:")
        val wtl = mutableMapOf<Long,Long>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), wtl)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "light-to-temperature map:")
        val ltt = mutableMapOf<Long,Long>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), ltt)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "temperature-to-humidity map:")
        val tth = mutableMapOf<Long,Long>()
        while(lines.first().isNotBlank()) {
            parseLine(lines.removeFirst(), tth)
        }
        lines.removeFirst()
        check(lines.removeFirst() == "humidity-to-location map:")
        val htl = mutableMapOf<Long,Long>()
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