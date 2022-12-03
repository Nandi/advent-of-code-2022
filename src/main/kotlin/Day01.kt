fun main() {
    val lines = loadResource("day01")
    val calories = caloriesCounter(lines)
    println(calories)
    val topCarriers = topThreeCalorieCarriers(lines)
    println(topCarriers)
}

private fun caloriesPerElf(calories: List<String>): List<Long> {
    return calories
        .joinToString("|")
        .split("||")
        .map {
            it
                .split("|")
                .sumOf { k -> k.toLong() }
        }
}

private fun caloriesCounter(calories: List<String>): Long {
    return caloriesPerElf(calories).maxOf { it }
}

private fun topThreeCalorieCarriers(calories: List<String>): Long {
    return caloriesPerElf(calories).sorted().takeLast(3).sum()
}