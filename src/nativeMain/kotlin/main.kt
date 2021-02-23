import com.github.nexus.actions.agent.NexusStagingClient
import kotlinx.coroutines.runBlocking
import kotlin.system.exitProcess

public fun main(args: Array<String>): Unit = runBlocking {
    require(args.size == 2)
    println(NexusStagingClient(username = args[0],  password = args[1]).getProfiles())
    /*
     * Prevent process exit(1) (caused by the coroutine context cancellation?)
     *
     * NOTE: Also, the program ends with:
     * Unfinished workers detected, 1 workers leaked!
     * Use `Platform.isMemoryLeakCheckerActive = false` to avoid this check.
     *
     * This leak is pretty harmless in fact. Warning will be be disabled in Kotlin 1.5
     * https://youtrack.jetbrains.com/issue/KT-43772#focus=Comments-27-4661042.0-0
     */
    exitProcess(0)
}