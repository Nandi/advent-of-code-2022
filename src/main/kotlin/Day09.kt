import kotlin.math.abs

fun main() {
    val movements = loadResource("day09").map {
        val (direction, count) = it.split(" ")
        Movement(direction.first(), count.toInt())
    }

    println(trackKnotMovement(movements))
    println(trackKnotMovement(movements, knots = 10))
}

private fun trackKnotMovement(movements: List<Movement>, knots: Int = 2): Long {
    val head = mutableListOf(Pos(0, 0))
    repeat(knots - 2) {
        head.add(Pos(0, 0))
    }
    val tail = Pos(0, 0)
    val visited = mutableListOf(tail)

    for (movement in movements) {
        repeat(movement.count) {
            head[0].moveKnot(movement.direction)
            head.forEachIndexed { i, knot ->
                if (i == 0) return@forEachIndexed
                else knot.follow(head[i - 1])
            }
            tail.follow(head.last())
            visited.add(tail.copy())
        }
    }

    return visited.distinct().size.toLong()
}

private fun Pos.follow(leader: Pos) {
    if (abs(x - leader.x) < 2 && abs(y - leader.y) < 2) return

    val directions = mutableListOf<Char>()

    if (y < leader.y) directions.add('U')
    else if (y > leader.y) directions.add('D')

    if (x < leader.x) directions.add('R')
    else if (x > leader.x) directions.add('L')

    directions.forEach(this::moveKnot)
}

private fun Pos.moveKnot(direction: Char) {
    when (direction) {
        'U' -> y += 1
        'R' -> x += 1
        'D' -> y -= 1
        'L' -> x -= 1
    }
}

private data class Pos(var x: Int, var y: Int)
private data class Movement(val direction: Char, val count: Int)