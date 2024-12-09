package day5

import common.*

fun main() {
    val input = readResource("day5/input").split(Regex("""\n\n"""))
    require(input.size == 2) { "Unexpected input size" }

    val (rules, inputPages) = input

    val printPages = inputPages
        .lineSequence()
        .filter { it.isNotEmpty() }
        .map { it.split(",") }
        .map { it.map { it.toInt() } }
        .toList()

    val comparator = buildComparator(rules)

    val result = printPages
        .filter { it.isSorted(comparator) }
        .sumOf { it[it.indices.last / 2] }

    println(result)
}

private fun buildComparator(rules: String): Comparator<Int> {
    val orderings = rules
        .lineSequence()
        .map { it.split("|") }
        .map { it.map { it.toInt() } }
        .pairs()
        .toMultiMap()

    return Comparator { a, b ->
        if (orderings[a]?.contains(b) == true) {
            -1
        } else if (orderings[b]?.contains(a) == true) {
            1
        } else {
            0
        }
    }
}