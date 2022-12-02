fun main() {
    val lines = loadResource("day02")
    println(rockPaperScissorsAnalysis(lines, strategy1))
    println(rockPaperScissorsAnalysis(lines, strategy2))
}

private fun rockPaperScissorsAnalysis(rounds: List<String>, strategy: Map<String, Int>): Long {
    return rounds
        .map { it.replace(" ", "") }
        .mapNotNull { strategy[it]?.toLong() }
        .sum()
}

val strategy1 = mapOf(
    "AX" to (3 + 1),
    "AY" to (6 + 2),
    "AZ" to (0 + 3),
    "BX" to (0 + 1),
    "BY" to (3 + 2),
    "BZ" to (6 + 3),
    "CX" to (6 + 1),
    "CY" to (0 + 2),
    "CZ" to (3 + 3),
)

val strategy2 = mapOf(
    "AX" to (0 + 3),
    "AY" to (3 + 1),
    "AZ" to (6 + 2),
    "BX" to (0 + 1),
    "BY" to (3 + 2),
    "BZ" to (6 + 3),
    "CX" to (0 + 2),
    "CY" to (3 + 3),
    "CZ" to (6 + 1),
)