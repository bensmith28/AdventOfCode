package main.kotlin.Year2015

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.JsonNodeType
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import java.io.File

object Day12 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2015/Day12.txt")

    val objectMapper = jacksonObjectMapper()

    @JvmStatic
    fun main(args: Array<String>) {
        val sum = input.readLines().flatMap { line ->
            "-?\\d+".toRegex().findAll(line).asIterable()
        }.fold(0) { sum, matchResult ->
            sum + matchResult.value.toInt()
        }

        println("Sum: $sum")

        val json = objectMapper.readTree(input) ?: throw IllegalArgumentException("Bad read")

        val ignoreReds = json.sumIgnoreReds()

        println("Ignoring Reds: $ignoreReds")
    }
}

private fun JsonNode.sumIgnoreReds(): Int {
    return this.sumOf { child ->
        when {
            child.nodeType == JsonNodeType.STRING -> 0
            child.nodeType == JsonNodeType.NUMBER -> child.intValue()
            child.nodeType == JsonNodeType.OBJECT && child.any { subChild ->
                subChild.nodeType == JsonNodeType.STRING && subChild.textValue() == "red"
            } -> 0
            else -> child.sumIgnoreReds()
        }
    }
}
