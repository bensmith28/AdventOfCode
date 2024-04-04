package Year2016

import java.io.File
import java.lang.IllegalArgumentException

object Day4 {
    val rooms = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2016/Day4.txt")
        .readLines().map { RoomEntry.parse(it) }

    @JvmStatic
    fun main(args: Array<String>) {
        val realRooms = rooms.filter { it.isReal }

        val sectorIds = realRooms.map { it.sectorId }
        println("Sector sum is ${sectorIds.sum()}")

        /*realRooms.forEach { room ->
            println(room.decryptedName)
        }*/

        val storageRoom = realRooms.single { room -> room.decryptedName == "northpole object storage"}

        println("Storage Room:")
        println("  $storageRoom")
    }

    data class RoomEntry(val label: String, val sectorId: Int, val entryChecksum: String) {
        companion object {
            fun parse(line: String): RoomEntry {
                val match = "([\\w\\-]+)\\-(\\d+)\\[(\\w+)\\]".toRegex().matchEntire(line)
                    ?: throw IllegalArgumentException("Bad room match: $line")
                return RoomEntry(
                    match.groupValues[1],
                    match.groupValues[2].toInt(),
                    match.groupValues[3]
                )
            }
        }

        val correctChecksum = label.filter { !"[\\-\\d]".toRegex().matches("$it") }.fold(mapOf<Char, Int>()) { map, c ->
            val count = (map[c] ?: 0) + 1
            map.plus(c to count)
        }.toList().sortedWith(Comparator { left, right ->
            val countCompare = right.second.compareTo(left.second)
            val charCompare = left.first.compareTo(right.first)
            if( countCompare != 0 ) countCompare
            else charCompare
        }).map { (c, _) -> c }.joinToString("").take(5)

        val isReal = correctChecksum == entryChecksum

        val decryptedName = label.map { c ->
            if( c == '-' ) ' '
            else (((c.code - 'a'.code + sectorId) % 26) + 'a'.code).toChar()
        }.joinToString("")
    }
}
