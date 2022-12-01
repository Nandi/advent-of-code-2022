fun main() {
    val lines = loadResource("day01")
    val calories = caloriesCounter(lines)
    println(calories)
    val topCarriers = topThreeCalorieCarriers(lines)
    println(topCarriers)
}

private fun caloriesCounter(lines: List<String>): Long {
    var sum = 0L
    var max = 0L
    for (line in lines) {
        if (line.isBlank()) {
            if (sum > max) max = sum
            sum = 0
            continue
        }

        sum += line.toLong()
    }

    return max
}

private fun topThreeCalorieCarriers(lines: List<String>): Long {
    val elves = mutableListOf<Long>()
    var sum = 0L
    for (line in lines) {
        if (line.isBlank()) {
            elves.add(sum)
            sum = 0
            continue
        }

        sum += line.toLong()
    }

    elves.sort()

    return elves.takeLast(3).sum()
}