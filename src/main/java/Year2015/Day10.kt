package Year2015

object Day10 {
    val input = "1321131112"

    private fun String.transform(): String {
        var result = ""
        var remaining = this
        while(remaining.isNotEmpty()) {
            val leading = remaining[0]
            val prefix = remaining.takeWhile { it == leading }
            result += prefix.length
            result += leading
            remaining = remaining.removePrefix(prefix)
        }
        return result
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val result = (1..50).fold(input) { s, i ->
            print("$i,")
            s.transform()
        }
        println("Result: $result")
        println("Length: ${result.length}")
    }
}