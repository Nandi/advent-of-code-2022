import java.util.LinkedList
import java.util.Queue

fun main() {
    // Skip root, created manually
    val commands = loadResource("day07").drop(1)
    val root = commandInterpreter(LinkedList(commands))
    val filteredDirectories = filterDirectoryBySize(root as Directory)
    println(filteredDirectories.sumOf { it.totalSize() })
    println(freeSpaceAnalyzer(root).totalSize())
}

private fun commandParser(command: String): Command {
    if (!command.startsWith("$")) throw Exception("$command is not  command")
    return when (command.drop(2).take(2)) {
        "ls" -> Command.Ls
        "cd" -> Command.Cd(command.drop(5))
        else -> throw Exception("Unknown command: $command")
    }
}

private fun commandInterpreter(commands: Queue<String>): Node {
    val root = Directory("/", null)
    var current: Directory = root

    while (commands.peek() != null) {
        val line = commands.poll()

        when (val command = commandParser(line)) {
            is Command.Cd -> {
                current = command.execute(current)
            }

            Command.Ls -> {
                while (commands.peek() != null && !commands.peek().startsWith("$")) {
                    val file = commands.poll()
                    current.addChild(Node.of(file, current))
                }
            }
        }
    }

    return root
}

private fun filterDirectoryBySize(root: Directory, cutoff: Long = 100_000L): List<Directory> {
    return root.getAllSubDirs().filter { it.totalSize() <= cutoff }
}

private fun freeSpaceAnalyzer(root: Directory, needed: Long = 30_000_000L, total: Long = 70_000_000L): Directory {
    val used = root.totalSize()
    val toFree = needed - (total - used)

    return root.getAllSubDirs().filter { it.totalSize() >= toFree }.minBy { it.totalSize() }
}

sealed interface Command {
    object Ls : Command
    data class Cd(val path: String) : Command {
        fun execute(current: Directory): Directory {
            return if (path == "..") {
                current.parent!!
            } else {
                val directory = current.getDir(path)
                current.addChild(directory) as Directory
            }
        }
    }
}

sealed class Node(val name: String, val parent: Directory? = null) {
    companion object {
        fun of(input: String, current: Directory): Node {
            return if (input.startsWith("dir ")) {
                Directory.of(input, current)
            } else {
                File.of(input, current)
            }
        }
    }
}

class File(name: String, parent: Directory?, val size: Long) : Node(name, parent) {
    companion object {
        fun of(input: String, current: Directory): File {
            val (size, name) = input.split(" ")
            return File(name, current, size.toLong())
        }
    }
}

class Directory(name: String, parent: Directory?) : Node(name, parent) {
    private val children = mutableListOf<Node>()

    fun addChild(child: Node): Node {
        if (!children.contains(child)) children.add(child)
        return child
    }

    private fun getAllDirs() = children.filterIsInstance<Directory>()

    fun getAllSubDirs(): List<Directory> {
        return getAllDirs() + getAllDirs().flatMap { it.getAllSubDirs() }
    }

    fun getDir(name: String): Directory {
        return getAllDirs().first { it.name == name }
    }

    fun totalSize(): Long {
        var size = 0L
        for (child in children) {
            size += when (child) {
                is Directory -> child.totalSize()
                is File -> child.size
            }
        }

        return size
    }

    companion object {
        fun of(input: String, current: Directory): Directory = Directory(input.drop(4), current)
    }
}
