package lupos.s00misc

import kotlin.jvm.JvmField
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.sync.Mutex
import lupos.s04logicalOperators.Query
import lupos.s04logicalOperators.ResultIterator


class ReadWriteLock {
    @JvmField
    val allowNewReads = Mutex()
    @JvmField
    val allowNewWrites = Mutex()
    @JvmField
    var readers = 0L

    suspend inline fun <T> withReadLockSuspend(crossinline action: suspend () -> T): T {
        try {
            allowNewReads.lock()
            if (++readers == 1L)
                allowNewWrites.lock()
        } finally {
            allowNewReads.unlock()
        }
        try {
            return action()
        } finally {
            try {
                allowNewReads.lock()
                if (--readers == 0L)
                    allowNewWrites.unlock()
            } finally {
                allowNewReads.unlock()
            }
        }
    }

    suspend inline fun <T> withWriteLockSuspend(crossinline action: suspend () -> T): T {
        try {
            allowNewWrites.lock()
            return action()
        } finally {
            allowNewWrites.unlock()
        }
    }

    inline fun <T> withReadLock(crossinline action: suspend CoroutineScope.() -> T): T {
        var res: T? = null
        CoroutinesHelper.runBlock {
            withReadLockSuspend {
                res = action()
            }
        }
        return res!!
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
