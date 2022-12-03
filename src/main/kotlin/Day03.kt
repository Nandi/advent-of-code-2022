fun main() {
    val lines = loadResource("day03")
    println(checkRucksacks(lines))
    println(findBadges(lines))
}

private fun checkRucksacks(inventories: List<String>): Long {
    return inventories
        .map { it.splitInTwo() }
        .map { it.first.intersect(it.second).first() }
        .sumOf { it.getPriority() }
}

private fun findBadges(inventories: List<String>): Long {
    return inventories
        .chunked(3)
        .map { it.intersecting().first() }
        .sumOf { it.getPriority() }
}

private fun String.splitInTwo(): Pair<Set<Char>, Set<Char>> {
    return take(length / 2).toSet() to drop(length / 2).toSet()
}

private fun List<String>.intersecting(): Set<Char> {
    var current: Set<Char> = this[0].toSet()
    for (i in 1 until size) {
        current = current.intersect(this[i].toSet())
    }

    return current
}

private fun Char.getPriority() = if ("[a-z]".toRegex().matches("$this")) {
    code - 96L
} else {
    code - 38L
}