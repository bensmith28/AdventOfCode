package Year2016

import java.io.File

object Day7 {
    @JvmStatic
    fun main(args: Array<String>) {
        val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2016/Day7.txt")
            .readLines()

        val tlsCount = input.filter { supportsTls(it) }.count()

        println("$tlsCount addresses support TLS")

        // Not 65

        val sslCount = input.filter { supportsSsl(it) }.count()

        println("$sslCount addresses support SSL")

        // 460 too high
        // 294 too high

    }

    fun supportsTls(address: String): Boolean {
        return "(?:(\\w)(?!\\1)(\\w))(?=\\2\\1)".toRegex().containsMatchIn(address)
                && !"\\[[^\\]]*(?:(\\w)(?!\\1)(\\w))(?=\\2\\1)[^\\]]*\\]".toRegex().containsMatchIn(address)
    }

    fun supportsSsl(address: String): Boolean {
        return "(?:(\\w)(?!\\1)(?<!\\[[^\\]]*)(\\w)\\1)(?=.*\\[[^\\]]*\\2\\1\\2[^\\]]*\\])".toRegex().containsMatchIn(address)
                || "(?:\\[[^\\]]*(\\w)(?!\\1)(\\w)\\1[^\\]]*\\])(?=.*(?:\\2\\1\\2))(?!.*\\[[^\\]]*\\2\\1\\2[^\\]]*\\])".toRegex().containsMatchIn(address)
    }
}