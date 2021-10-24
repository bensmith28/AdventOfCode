package Year2015

object Day20 {
    val minimumPresents = 29000000

    @JvmStatic
    fun main(args: Array<String>) {
        val first = generateSequence(700000) { it + 1 }
            .first { presentsForAddress(it) >= minimumPresents }

        println("Found something: $first")
    }

    fun presentsForAddress(address: Int) = (1..address/2).plus(address)
        .filter { elf -> elf * 50 >= address && address % elf == 0 }
        .sumOf { elf -> elf * 11 }
}