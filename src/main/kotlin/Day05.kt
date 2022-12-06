fun main() {
    val lines = loadResource("day05")

    val finalPlacement9000 = moveCrates(initialCratePosition(lines.take(8)), lines.drop(10).map { it.toInstruction() })
    println(finalPlacement9000)

    val finalPlacement9001 = moveCrates9001(initialCratePosition(lines.take(8)), lines.drop(10).map { it.toInstruction() })
    println(finalPlacement9001)
}

private fun initialCratePosition(map: List<String>): Array<ArrayDeque<Char>> {
    val array = Array(9) { ArrayDeque<Char>() }
    val positions = listOf(1, 5, 9, 13, 17, 21, 25, 29, 33)

    for (row in map.reversed()) {
        positions.forEachIndexed { index, pos ->
            if (row[pos] in 'A'..'Z') array[index].add(row[pos])
        }
    }

    return array
}

private fun moveCrates(initial: Array<ArrayDeque<Char>>, instructions: List<Instruction>): String {

    for (instruction in instructions) {
        repeat((1..instruction.count).count()) {
            val crate = initial[instruction.from - 1].removeLast()
            initial[instruction.to - 1].add(crate)
        }
    }

    return initial
        .map { it.removeLast() }
        .joinToString("")
}

private fun moveCrates9001(initial: Array<ArrayDeque<Char>>, instructions: List<Instruction>): String {
    for (instruction in instructions) {
        (1..instruction.count)
            .map { initial[instruction.from - 1].removeLast() }
            .reversed()
            .forEach { crate ->
                initial[instruction.to - 1].add(crate)
            }
    }

    return initial
        .map { it.removeLast() }
        .joinToString("")

}

private fun String.toInstruction(): Instruction {
    val match = """move (\d+) from (\d) to (\d)""".toRegex().matchEntire(this)!!
    return Instruction(match.groupValues[1].toInt(), match.groupValues[2].toInt(), match.groupValues[3].toInt())

}

private data class Instruction(val count: Int, val from: Int, val to: Int)
