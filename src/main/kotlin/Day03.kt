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
        .map { it.toSet() }
        .reduce { acc, chars -> acc.intersect(chars) }
        .first()
        .sumOf { it.getPriority() }
}

private fun String.splitInTwo(): Pair<Set<Char>, Set<Char>> {
    return take(length / 2).toSet() to drop(length / 2).toSet()
}

private fun Char.getPriority() =
    if (this in 'a'..'z') {
        code - 96L
    } else {
        code - 38L
    }