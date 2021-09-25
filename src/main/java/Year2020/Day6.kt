package main.kotlin.Year2020

import java.io.File

object Day6 {
    val input = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2020/Day6-sample.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val groupAnswers = mutableListOf(mutableListOf<String>())

        input.forEachLine { line ->
            if(line.isNullOrBlank()) groupAnswers.add(mutableListOf())
            else groupAnswers.last().add(line)
        }

        val sum = groupAnswers.sumBy { group ->
            group.fold(group.first().toSet()) { questions, answers ->
                questions.intersect(answers.toSet())
            }.size
        }

        println("The sum is $sum")
    }
}