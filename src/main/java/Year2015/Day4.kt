package main.kotlin.Year2015

import java.security.MessageDigest

object Day4 {
    val input = "bgvyzdsv"

    fun md5(str: String): ByteArray = MessageDigest.getInstance("MD5").digest(str.toByteArray(Charsets.UTF_8))
    fun ByteArray.toHex() = joinToString("") { "%02x".format(it) }

    @JvmStatic
    fun main(args: Array<String>) {
        val first = generateSequence(1) { i ->
            i + 1
        }.first { i ->
            if(i % 100000 == 0) println("Passing $i")
            md5("$input$i").toHex().matches("000000.*".toRegex())
        }

        println("First: $first")
    }
}