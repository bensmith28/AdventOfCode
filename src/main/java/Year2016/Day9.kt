package Year2016

import java.io.File

object Day9 {
    @JvmStatic
    fun main(args: Array<String>) {
        val lines = File("/home/benyamin888/IdeaProjects/AdventOfCode/src/main/resources/Year2016/Day9.txt")
            .readLines()

        val lengthOfDecompressed = lines.map { decompress(it) }
            .sumBy { it.length }

        println("Decompressed to $lengthOfDecompressed characters V1")

        val lengthOfDecompressedV2 = lines.fold(0L) { sum, nextLine ->
            sum + decompressedLength(Segment.parseToSegments(nextLine))
        }

        println("Decompressed to $lengthOfDecompressedV2 characters with V2")

        // 202349275 too low
        // 33425637
        // 388901861 too low
    }

    tailrec fun decompress(line: String, cursor: Int = 0, version2: Boolean = false): String {
        val match = "\\((\\d+)x(\\d+)\\)".toRegex().find(line, cursor)
            ?: return line

        val length = match.groupValues[1].toInt()
        val reps = match.groupValues[2].toInt()
        val replaceSource = line.substring(
            match.range.last  + 1,
            match.range.last + length + 1
        )
        val replaceWith = (1..reps).joinToString("") { replaceSource }

        val nextLine = line.replaceRange(
            match.range.first..match.range.last + length,
            replaceWith
        )
        val nextCursor = if(version2) 0
        else match.range.first + replaceWith.length

        return decompress(nextLine, nextCursor, version2)
    }

    private fun List<Segment>.literalLength() = this.fold(0L) { sum, s -> sum + s.length }

    fun decompressedLength(segments: List<Segment>): Long {
        if(segments.isEmpty()) return 0
        val(leadLength, dropSize) = when(val lead = segments.first()) {
            is Literal -> lead.length to 1
            is Marker -> {
                val targetSegments = segments.drop(1).fold(emptyList<Segment>()) { targetSegments, next ->
                    if( targetSegments.literalLength() < lead.target ) targetSegments.plus(next)
                    else targetSegments
                }
                /*val (splitIntoTarget, remainder) = if( targetSegments.literalLength() != lead.target.toLong() ) {
                    val notInTarget = targetSegments.last()
                    if( notInTarget is Marker )
                        throw IllegalArgumentException("WTF, Mate, Again?!")
                    val targetNeeds = lead.target - targetSegments.literalLength()
                    Literal(targetNeeds) to Literal(notInTarget.length-targetNeeds)
                } else {
                    Literal(0) to Literal(0)
                }*/
                lead.reps * decompressedLength(targetSegments/*.plus(splitIntoTarget)*/) /*+ remainder.length*/ to targetSegments.size + 1
            }
            else -> throw IllegalArgumentException("Bad segment type")
        }

        return leadLength + decompressedLength(segments.drop(dropSize))
    }

    interface Segment {
        val length: Long

        companion object {
            fun parseToSegments(line: String): List<Segment> {
                return "(\\w*)(\\((\\d+)x(\\d+)\\))?".toRegex().findAll(line).toList().flatMap { match ->
                    val litString = match.groupValues[1]
                    val literals = if (!litString.isBlank()) {
                        Literal.generate(litString.length)
                    } else emptyList()
                    val length = match.groupValues[2].length.toLong()
                    val target = match.groupValues[3].toIntOrNull()
                    val reps = match.groupValues[4].toIntOrNull()
                    val marker = if (target != null && reps != null) {
                        Marker(length, target, reps)
                    } else null
                    literals.plus(marker).filterNotNull()
                }
            }
        }
    }

    data class Marker(override val length: Long, val target: Int, val reps: Int): Segment

    class Literal: Segment {
        override val length = 1L

        override fun toString() = "Literal"

        override fun equals(other: Any?): Boolean {
            return other is Literal
        }

        override fun hashCode(): Int = 1

        companion object {
            fun generate(count: Int): List<Literal> {
                return (1..count).map { Literal() }
            }
        }
    }
}