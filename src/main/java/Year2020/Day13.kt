package Year2020

object Day13 {
    val currentTime = 1001171L
    val input = "17,x,x,x,x,x,x,41,x,x,x,37,x,x,x,x,x,367,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,x,19,x,x,x,23,x,x,x,x,x,29,x,613,x,x,x,x,x,x,x,x,x,x,x,x,13"
    val busIds = input.split(",").filter { "\\d+".toRegex().matches(it) }.map { it.toInt() }

    val busSchedule = parseToSchedule(input)

    @JvmStatic
    fun main(args: Array<String>) {
        val departureTime = generateSequence(currentTime) { it + 1 }
            .first { time -> busIds.any { id -> time % id == 0L }}
        val busId = busIds.single { id -> departureTime % id == 0L }

        println("Depart at $departureTime on bus $busId (${(departureTime - currentTime) * busId})")

        // 367431592 Too high

        val scheduleTime = findFirstTimeForSchedule(busSchedule)

        println("First scheduled time is: $scheduleTime")

        // First scheduled time is: -9222654843456267014
        //                           655966906676153 is too high
        //                           100000000000000
        //                           247086664214628
    }

    fun parseToSchedule(line: String): List<BusRecord> {
        return line.split(",").mapIndexed { index, busIdString ->
            index to busIdString
        }.filter { (_, idString) ->
            "\\d+".toRegex().matches(idString)
        }.map { (index, s) -> BusRecord(s.toLong(), index.toLong()) }
    }

    fun findFirstTimeForSchedule(schedule: List<BusRecord>): Long {
        val (intersect, _) = schedule.drop(1).fold(schedule[0].let { it.busId - it.offset to listOf(it.busId) }) {
                (base, deltaFactors), (busId, offset) ->
            val delta = deltaFactors.fold(1L) { product, f -> product * f }
            val targetModulo = generateSequence(busId - offset) { it + busId }.first { it >= 0 }
            val nextBase = generateSequence(base) { it + delta }
                .first { it % busId == targetModulo }
            nextBase to deltaFactors.plus(busId)
        }

        return intersect
    }

    fun Int.primeFactors(): Set<Int> {
        if( this == 1 ) return setOf(1)
        val nextFactor = generateSequence(2) { it + 1 }.first { this % it == 0 }
        return (this / nextFactor).primeFactors().plus(nextFactor)
    }

    data class BusRecord(val busId: Long, val offset: Long)
}