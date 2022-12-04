fun main() {
    val lines = loadResource("day04")
    println(identifyEncumberingRanges(lines))
    println(identifyOverlappingRanges(lines))
}

private fun identifyEncumberingRanges(ranges: List<String>): Long {
    return ranges
        .map { it.toRangePair() }
        .count { it.first.containsAll(it.second) || it.second.containsAll(it.first) }
        .toLong()
}

private fun identifyOverlappingRanges(ranges: List<String>): Long {
    return ranges
        .map { it.toRangePair() }
        .count { area -> area.first.any { area.second.contains(it) } }
        .toLong()
}

private fun String.toRangePair(): Pair<List<Int>, List<Int>> {
    val (first, last) = split(",")
        .map { it.split("-") }
        .map { (it[0].toInt()..it[1].toInt()).toList() }
    return first to last
}
