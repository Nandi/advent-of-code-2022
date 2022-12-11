import java.util.LinkedList

fun main() {
    val commands = loadResource("day10").map {
        if (it == "noop") Cmd.Noop()
        else Cmd.Add(it.split(" ")[1].toInt())
    }
    compute(commands)
}

private fun compute(commands: List<Cmd>, cycle: Long = 300) {
    val clock = Clock()
    val cpu = CPU(commands)
    val signal = Signal()
    val screen = Screen()

    while (clock.cycle <= cycle) {
        clock.tick()
        signal.measure(clock.cycle, cpu.registry)
        screen.draw(clock.cycle, cpu.registry)
        cpu.tick()
    }

    println(signal)
    println(screen)
}

private class Clock {
    var cycle = 0L
        private set

    fun tick() {
        cycle++
    }
}

private class CPU(commands: List<Cmd>) {
    private val commands = LinkedList(commands)
    private var currentCommand: Cmd? = null
    var registry: Long = 1
        private set

    fun tick() {
        if (currentCommand == null) {
            currentCommand = commands.poll() ?: return
        }

        currentCommand!!.cycles--
        execute()
    }

    fun execute() {
        val command = currentCommand ?: return
        if (command.cycles != 0) return

        when (command) {
            is Cmd.Add -> registry += command.amount
            is Cmd.Noop -> {}
        }

        currentCommand = null
    }
}

private class Screen(private val width: Int = 40, height: Int = 6) {
    private var lineNumber = 0
    private val lines = Array(height) { CharArray(width) { '.' } }

    fun draw(cycle: Long, registry: Long) {
        if (lineNumber > lines.lastIndex) return
        lines[lineNumber][((cycle - 1) % width).toInt()] = pixel((cycle - 1) % width, registry)

        if (cycle % width == 0L) {
            lineNumber++
        }
    }

    private fun pixel(pos: Long, registry: Long): Char {
        return if (pos in registry - 1..registry + 1) '#'
        else '.'
    }

    override fun toString(): String {
        return buildString {
            lines.map { it.joinToString("") }
                .forEach { append(it).append("\n") }
        }
    }
}


private class Signal(initial: Long = 20, private val interval: Long = 40) {
    private var nextMeasureCycle = initial
    val signalStrength = mutableListOf<Long>()


    fun measure(cycle: Long, registry: Long) {
        if (cycle == nextMeasureCycle) {
            signalStrength += (nextMeasureCycle * registry)
            nextMeasureCycle += interval
        }
    }

    override fun toString(): String {
        return "${signalStrength.sum()}"
    }
}

sealed class Cmd(var cycles: Int) {
    class Noop : Cmd(1)
    data class Add(val amount: Int) : Cmd(2)
}