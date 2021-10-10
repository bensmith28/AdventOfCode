package Year2016

object Day18 {
    val input = "^.^^^.^..^....^^....^^^^.^^.^...^^.^.^^.^^.^^..^.^...^.^..^.^^.^..^.....^^^.^.^^^..^^...^^^...^...^."

    @JvmStatic
    fun main(args: Array<String>) {
        println("Part 1: ${countSafeTiles(input, 40)}")
        println("Part 2: ${countSafeTiles(input, 400000)}")
    }

    fun nextRow(row: String): String {
        return row.indices.map { i ->
            val left = row.getOrNull(i - 1) ?: '.'
            val center = row[i]
            val right = row.getOrNull(i + 1) ?: '.'

            when {
                left != right -> '^'
                else -> '.'
            }
        }.joinToString("")
    }

    fun countSafeTiles(input: String, rows: Int): Int {
        return generateSequence(input) {
            nextRow(it)
        }.map { row -> row.count { c -> c == '.' } }.take(rows).sum()
    }
}