/*
 * This file is part of the Luposdate3000 distribution (https://github.com/luposdate3000/luposdate3000).
 * Copyright (c) 2020-2021, Institute of Information Systems (Benjamin Warnke and contributors of LUPOSDATE3000), University of Luebeck
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, version 3.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package lupos.code_gen_test_00
import lupos.endpoint.LuposdateEndpoint
import lupos.shared.EPartitionModeExt
import lupos.shared.EPredefinedPartitionSchemesExt
import lupos.shared.Luposdate3000Instance
import lupos.shared.inline.MyPrintWriter
import lupos.shared.myPrintStackTraceAndThrowAgain
import kotlin.test.Test

public class syntaxupdate14ru {
    internal val query = "CREATE SILENT GRAPH <graph> \n" +
        ""

    @Test
    public fun `syntaxupdate14ru - None - Simple - true`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.None
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.Simple
            instance.useDictionaryInlineEncoding = true
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:59"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - None - Simple - false`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.None
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.Simple
            instance.useDictionaryInlineEncoding = false
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:75"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByIDTwiceAllCollations - true`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByIDTwiceAllCollations
            instance.useDictionaryInlineEncoding = true
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:91"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByIDTwiceAllCollations - false`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByIDTwiceAllCollations
            instance.useDictionaryInlineEncoding = false
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:107"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByID_1_AllCollations - true`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByID_1_AllCollations
            instance.useDictionaryInlineEncoding = true
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:123"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByID_1_AllCollations - false`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByID_1_AllCollations
            instance.useDictionaryInlineEncoding = false
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:139"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByID_2_AllCollations - true`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByID_2_AllCollations
            instance.useDictionaryInlineEncoding = true
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:155"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByID_2_AllCollations - false`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByID_2_AllCollations
            instance.useDictionaryInlineEncoding = false
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:171"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByID_O_AllCollations - true`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByID_O_AllCollations
            instance.useDictionaryInlineEncoding = true
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:187"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByID_O_AllCollations - false`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByID_O_AllCollations
            instance.useDictionaryInlineEncoding = false
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:203"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByID_S_AllCollations - true`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByID_S_AllCollations
            instance.useDictionaryInlineEncoding = true
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:219"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByID_S_AllCollations - false`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByID_S_AllCollations
            instance.useDictionaryInlineEncoding = false
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:235"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByKeyAllCollations - true`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByKeyAllCollations
            instance.useDictionaryInlineEncoding = true
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:251"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - PartitionByKeyAllCollations - false`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.PartitionByKeyAllCollations
            instance.useDictionaryInlineEncoding = false
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:267"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - Simple - true`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.Simple
            instance.useDictionaryInlineEncoding = true
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:283"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }

    @Test
    public fun `syntaxupdate14ru - Thread - Simple - false`() {
        var instance = Luposdate3000Instance()
        try {
            instance.LUPOS_BUFFER_SIZE = 128
            instance.LUPOS_PARTITION_MODE = EPartitionModeExt.Thread
            instance.predefinedPartitionScheme = EPredefinedPartitionSchemesExt.Simple
            instance.useDictionaryInlineEncoding = false
            instance = LuposdateEndpoint.initializeB(instance)
            normalHelper(instance)
        } catch (e: Throwable) {
            e.myPrintStackTraceAndThrowAgain(/*SOURCE_FILE_START*/"/src/luposdate3000/src/luposdate3000_code_gen_test_00/src/jvmTest/kotlin/lupos/code_gen_test_00/syntaxupdate14ru.kt:299"/*SOURCE_FILE_END*/) // otherwise this would be silently ignored
        } finally {
            LuposdateEndpoint.close(instance)
        }
    }
    internal fun normalHelper(instance: Luposdate3000Instance) {
        val buf = MyPrintWriter(false)
        val operator0 = LuposdateEndpoint.evaluateSparqlToOperatorgraphA(instance, query)
    }
}
