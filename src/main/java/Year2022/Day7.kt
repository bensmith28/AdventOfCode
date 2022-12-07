package Year2022

import util.asResourceFile

object Day7 {
    data class Directory(
        val name: String,
        val parent: Directory? = null,
        val children: MutableList<Directory> = mutableListOf(),
        val contents: MutableMap<String, Long> = mutableMapOf()
    ) {
        fun mkdir(name: String) {
            children.add(Directory(name, this))
        }

        fun found(name: String, size: Long) {
            contents[name] = size
        }

        fun size(): Long {
            return children.sumOf { it.size() } + contents.values.sum()
        }

        fun scan(): List<Directory> {
            return children.flatMap { it.scan() } + children
        }

        override fun toString(): String {
            return "$name ${size()}"
        }
    }

    private val cdRootPattern = """\$ cd /""".toRegex()
    private val cdUpPattern = """\$ cd \.\.""".toRegex()
    private val cdPattern = """\$ cd (.*)""".toRegex()

    private val lsPattern = """\$ ls""".toRegex()
    private val dirPattern = """dir (.*)""".toRegex()
    private val contentPattern = """(\d+) (.*)""".toRegex()
    fun crawl(console: List<String>): Directory {
        val root = Directory("/")
        var currentDir = root

        console.forEach { consoleLine ->
            when {
                consoleLine.matches(cdRootPattern) -> currentDir = root
                consoleLine.matches(cdUpPattern) -> currentDir = currentDir.parent ?: root
                consoleLine.matches(cdPattern) -> currentDir = currentDir.children.first {
                    it.name == cdPattern.matchEntire(consoleLine)!!.groupValues[1]
                }
                consoleLine.matches(lsPattern) -> Unit
                consoleLine.matches(dirPattern) -> currentDir.mkdir(
                    dirPattern.matchEntire(consoleLine)!!.groupValues[1]
                )
                consoleLine.matches(contentPattern) -> contentPattern.matchEntire(consoleLine)!!
                    .destructured.let { (size, name) ->
                        currentDir.found(name, size.toLong())
                    }
                else -> error("Bad line parse: $consoleLine")
            }
        }

        return root
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val input = "/Year2022/Day7.txt".asResourceFile().readLines()
        val root = crawl(input)

        val part1 = root.scan().filter { it.size() < 100000L }

        println("Part 1: ${part1.sumOf { it.size() }}")

        val totalSize = 70000000L
        val neededFree = 30000000L
        val delta = neededFree - (totalSize - root.size())

        val target = root.scan().sortedBy { it.size() }.first { it.size() >= delta }

        println("Need to find $delta")
        println("Part 2: $target")
    }
}