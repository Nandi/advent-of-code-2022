import Direction.Companion.DOWN
import Direction.Companion.LEFT
import Direction.Companion.RIGHT
import Direction.Companion.UP

fun main() {
    val grid = loadResource("day08").map { it.map { c -> c.digitToInt() }.toIntArray() }.toTypedArray()
    println(locateVisibleTrees(grid, grid.size, grid[0].size))
    println(calculateBestView(grid, grid.size, grid[0].size))
}

private fun locateVisibleTrees(trees: Array<IntArray>, height: Int, width: Int): Long {
    var visibleTrees: Long = (width.toLong() * 2) + (height.toLong() * 2) - 4

    for (x in 1..width - 2) {
        for (y in 1..height - 2) {
            val horizontal = if (x < width / 2) listOf(LEFT, RIGHT) else listOf(RIGHT, LEFT)
            val vertical = if (y < height / 2) listOf(UP, DOWN) else listOf(DOWN, UP)

            val directions = listOf(horizontal[0], vertical[0], horizontal[1], vertical[1])

            for (direction in directions) {
                val visible = trees.isVisible(direction, x, y, trees[y][x])
                if (visible) {
                    visibleTrees++
                    break
                }
            }
        }
    }

    return visibleTrees
}

private fun calculateBestView(trees: Array<IntArray>, height: Int, width: Int): Long {
    var bestView = 0L

    for (x in 1..width - 2) {
        for (y in 1..height - 2) {
            val view = listOf(UP, LEFT, DOWN, RIGHT).productOf { trees.viewDistance(it, x, y, trees[y][x]) }
            if (view > bestView) bestView = view
        }
    }

    return bestView
}


private fun Array<IntArray>.isVisible(direction: Direction, x: Int, y: Int, initial: Int): Boolean {
    val (newX, newY) = direction.move(x, y)
    if (newX < 0 || newX > this[y].lastIndex) return true
    if (newY < 0 || newY > this.lastIndex) return true
    if (this[newY][newX] >= initial) return false
    return this.isVisible(direction, newX, newY, initial)
}

private fun Array<IntArray>.viewDistance(direction: Direction, x: Int, y: Int, initial: Int): Long {
    val (newX, newY) = direction.move(x, y)
    if (newX < 0 || newX > this[y].lastIndex) return 0
    if (newY < 0 || newY > this.lastIndex) return 0
    if (this[newY][newX] >= initial) return 1
    return 1 + this.viewDistance(direction, newX, newY, initial)
}

private data class Direction(val x: Int = 0, val y: Int = 0) {
    fun move(x: Int, y: Int): Pair<Int, Int> = (x + this.x) to (y + this.y)

    companion object {
        val UP = Direction(y = -1)
        val RIGHT = Direction(x = 1)
        val DOWN = Direction(y = 1)
        val LEFT = Direction(x = -1)
    }
}
