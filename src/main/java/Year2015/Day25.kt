package Year2015

object Day25 {
    // To continue, please consult the code grid in the manual.  Enter the code at row 2978, column 3083.

    val seed = 20151125L

    @JvmStatic
    fun main(args: Array<String>) {
        println(generateCode((2978 to 3083).toIndex()))

        // 18361853 too high
        // 2650453
    }

    fun generateCode(index: Int): Long {
        return (2..index).fold(seed) { last,  index ->
            last * 252533 % 33554393
        }
    }
}

fun Pair<Int, Int>.toIndex(): Int {
    val (row, col) = this
    return (2 until row + col).fold(1) { i, r ->
        i + r - 1
    } + col - 1
}