fun main() {
    val characters = loadResource("day06").first()
    println(locateStartMarker(characters.toList(), 4))
    println(locateStartMarker(characters.toList(), 14))
}

private fun locateStartMarker(signal: List<Char>, size: Int): Long {
    var position = size - 1
    var found = false

    signal.windowed(size, 1, true) {
        if (found) return@windowed
        position++
        if (it.distinct().size == it.size) found = true
    }

    return position.toLong()
}

