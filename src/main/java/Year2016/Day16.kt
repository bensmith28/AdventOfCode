package Year2016

object Day16 {

    @JvmStatic
    fun main(args: Array<String>) {
        val part1 = fillAndSum("11110010111001001", 272)
        println("Part 1: $part1")

        val part2 = fillAndSum("11110010111001001", 35651584)
        println("Part 2: $part2")
    }

    fun fillAndSum(initial: String, size: Int): String {
        return symbolicDragon(stepsToFill(initial.length, size))
            .expand(initial).take(size)
            .checksum(stepsToCheck(size)).joinToString("")
    }

    private fun stepsToFill(initialLength: Int, size: Int) =
        generateSequence(initialLength) { it * 2 + 1 }
        .takeWhile { it < size }.count()
    private fun stepsToCheck(size: Int) =
        generateSequence(size) { it / 2 }
        .takeWhile { it % 2 == 0 }.count()

    // b d
    // p q
    private fun Sequence<Char>.invert() = this.map { when(it) {
        '0' -> '1'
        '1' -> '0'
        'b' -> 'q'
        'q' -> 'b'
        else -> throw IllegalArgumentException("Bad char $it")
    } }.toList().reversed().asSequence()

    private fun symbolicDragon(n: Int = 1): Sequence<Char> {
        return when {
            n < 0 -> throw IllegalArgumentException()
            n == 0 -> sequenceOf('b')
            else -> symbolicDragon(n - 1).let {
                sequence {
                    yieldAll(it)
                    yield('0')
                    yieldAll(it.invert())
                }
            }
        }
    }

    private fun Sequence<Char>.expand(initial: String): Sequence<Char> {
        val b = initial.asSequence()
        val q = b.invert()
        val zero = sequenceOf('0')
        val one = sequenceOf('1')
        return this.map { c -> when(c) {
            'b' -> b
            'q' -> q
            '0' -> zero
            '1' -> one
            else -> throw IllegalArgumentException()
        } }.flatten()
    }

    fun Sequence<Char>.checksum(n: Int = 1): Sequence<Char> {
        return if( n > 1 ) {
            this.checksum(n - 1)
        } else {
            this
        }.windowed(2,2) {
            if ( it.first() == it.last() ) '1'
            else '0'
        }
    }

    fun String.applyDragon(n: Int = 1) = symbolicDragon(n).expand(this)

    fun fillDisk(initial: String, size: Int): String {
        return initial.applyDragon(stepsToFill(initial.length, size))
            .take(size)
            .joinToString("")
    }

    fun String.checksum(): String {
        if( this.length % 2 != 0 ) throw IllegalArgumentException("can't sum an odd string")
        return this.asSequence().checksum(stepsToCheck(this.length)).joinToString("")
    }
}