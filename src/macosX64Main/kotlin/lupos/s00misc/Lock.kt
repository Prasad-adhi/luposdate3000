package lupos.s00misc

import kotlin.jvm.JvmField
import kotlinx.cinterop.cValue
import kotlinx.coroutines.CoroutineScope
import lupos.s04logicalOperators.Query
import platform.posix.pthread_mutex_init
import platform.posix.pthread_mutex_lock
import platform.posix.pthread_mutex_t
import platform.posix.pthread_mutex_unlock


class Lock {
    val mutex = cValue<pthread_mutex_t>()

    constructor() {
        pthread_mutex_init(mutex, null)
    }

    inline suspend fun <T> withWriteLockSuspend(crossinline action: suspend () -> T): T {
        try {
            pthread_mutex_lock(mutex)
            return action()
        } finally {
            pthread_mutex_unlock(mutex)
        }
    }

    inline fun <T> withWriteLock(crossinline action: suspend CoroutineScope.() -> T): T {
        var res: T? = null
        CoroutinesHelper.runBlock {
            withWriteLockSuspend {
                res = action()
            }
        }
        return res!!
    }
}
//pthread_mutex_destroy
