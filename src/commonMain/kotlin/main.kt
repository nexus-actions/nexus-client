import com.github.nexus.actions.agent.NexusStagingClient


/**
 *
 * NOTE: the program ends with:
 * Unfinished workers detected, 1 workers leaked!
 * Use `Platform.isMemoryLeakCheckerActive = false` to avoid this check.
 *
 * This leak is pretty harmless in fact. Warning will be be disabled in Kotlin 1.5
 * https://youtrack.jetbrains.com/issue/KT-43772#focus=Comments-27-4661042.0-0
 */
fun main(args: Array<String>) = runBlocking {
    require(args.size == 2)
    println(NexusStagingClient(username = args[0],  password = args[1]).getProfiles())
}

expect fun runBlocking(action: suspend () -> Unit)