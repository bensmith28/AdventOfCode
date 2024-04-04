package Year2020

import java.io.File

object Day16 {
    val inputFile = File("/Users/ben/code/AdventOfCode/src/main/resources/Year2020/Day16.txt")

    @JvmStatic
    fun main(args: Array<String>) {
        val doc = Document.parse(inputFile)

        val scanningError = getScanningError(doc)

        println("Scanning error: $scanningError")

        val yourTicket = parseYourTicket(doc)

        println("Your Ticket: $yourTicket")

        val departureFields = yourTicket.filterKeys { it.startsWith("departure") }
        val answer = departureFields.entries.fold(1L) { product, (_, fieldValue) ->
            product * fieldValue
        }

        println("Your departure fields: ")
        departureFields.forEach { (name, value) ->
            println("  $name: $value")
        }

        println("Your Answer is: $answer")
    }

    fun getScanningError(doc: Document): Int {
        val invalidFields = doc.otherTickets.flatMap { it.fields }.filter { field ->
            doc.rules.none { rule -> rule.isValid(field) }
        }

        return invalidFields.sum()
    }

    fun parseYourTicket(doc: Document): Map<String, Int> {
        val validOthers = doc.otherTickets.filter { ticket ->
            ticket.fields.all { field -> doc.rules.any { rule ->
                rule.isValid(field)
            } }
        }

        val ruleMap = mutableMapOf<Int, Rule>()
        while(ruleMap.size < doc.rules.size) {
            doc.yourTicket.fields.indices.forEach { index ->
                val fieldsFromOtherTickets = validOthers.map { ot ->
                    ot.fields[index]
                }

                doc.rules.minus(ruleMap.values).singleOrNull { rule ->
                    fieldsFromOtherTickets.all { field -> rule.isValid(field) }
                }?.also { rule ->
                    ruleMap[index] = rule
                }
            }
        }

        return ruleMap.map { (index, rule) ->
            rule.title to doc.yourTicket.fields[index]
        }.toMap()
    }

    data class Document(val rules: List<Rule>, val yourTicket: Ticket, val otherTickets: List<Ticket>) {
        companion object {
            fun parse(file: File): Document {
                val lines = file.readLines()

                val rules = lines.takeWhile { it.isNotBlank() }.map { Rule.parse(it) }

                val yourTicket = lines.drop(rules.size + 2)
                    .takeWhile { it.isNotBlank() }
                    .last().let { Ticket.parse(it) }

                val otherTickets = lines.drop(rules.size + 5).map { Ticket.parse(it) }

                return Document(
                    rules,
                    yourTicket,
                    otherTickets
                )
            }
        }
    }

    data class Ticket(val fields: List<Int>) {
        companion object {
            fun parse(line: String): Ticket {
                return Ticket(
                    line.split(",").map { it.toInt() }
                )
            }
        }
    }

    data class Rule(val title: String, val validRanges: List<IntRange>) {
        companion object {
            fun parse(line: String): Rule {
                val match = "([\\w\\s]+): (\\d+)-(\\d+) or (\\d+)-(\\d+)".toRegex().matchEntire(line)
                    ?: throw IllegalArgumentException("Bad rule match: $line")
                val title = match.groupValues[1]
                val lowerA = match.groupValues[2].toInt()
                val upperA = match.groupValues[3].toInt()
                val lowerB = match.groupValues[4].toInt()
                val upperB = match.groupValues[5].toInt()

                return Rule(
                    title,
                    listOf(lowerA..upperA, lowerB..upperB)
                )

            }
        }

        fun isValid(field: Int) = validRanges.any { it.contains(field) }
    }
}