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
import lupos.optimizer.physical.PhysicalOptimizer
import lupos.endpoint.LuposdateEndpoint
import lupos.operator.arithmetik.noinput.AOPVariable
import simora.addQuerySender
import lupos.operator.base.Query
import lupos.result_format.EQueryResultToStreamExt
import lupos.shared.EIndexPatternExt
import lupos.shared.EQueryDistributionModeExt
import lupos.shared.Luposdate3000Config
import lupos.shared.Luposdate3000Instance
import lupos.shared.EPartitionModeExt
import lupos.shared.MemoryTable
import lupos.shared.EPredefinedPartitionSchemesExt
import lupos.shared.inline.File
import lupos.shared.inline.MyPrintWriter
import simora.SimulationRun
import lupos.simulator_db.luposdate3000.Package_Luposdate3000_TestingCompareGraphPackage
import lupos.simulator_db.luposdate3000.Package_Luposdate3000_TestingImportPackage
import lupos.simulator_db.luposdate3000.Package_Luposdate3000_TestingExecute
import lupos.simulator_db.luposdate3000.Application_Luposdate3000

import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.fail

public class DELETEINSERT7 {
    internal val query = "PREFIX     : <http://example.org/>  \n" +
        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>  \n" +
        "DELETE  \n" +
        "{ \n" +
        "  ?a foaf:knows [] . \n" +
        "} \n" +
        "INSERT \n" +
        "{ \n" +
        "  ?a foaf:knows [] . \n" +
        "} \n" +
        "WHERE \n" +
        "{ \n" +
        "  ?a foaf:name \"Alan\" .  \n" +
        "} \n" +
        ""

    @Test
    public fun `DELETE INSERT 7 - Thread - PartitionByID_O_AllCollations - true`() {
      var instance = Luposdate3000Instance()
      try{
        instance.LUPOS_BUFFER_SIZE = 128
        instance.LUPOS_PARTITION_MODE=EPartitionModeExt.Thread
        instance.predefinedPartitionScheme=EPredefinedPartitionSchemesExt.PartitionByID_O_AllCollations
        instance.useDictionaryInlineEncoding=true
        instance = LuposdateEndpoint.initializeB(instance)
        normalHelper(instance)
      }finally{
        LuposdateEndpoint.close(instance)
      }
    }
    internal fun normalHelper(instance:Luposdate3000Instance) {
        val buf = MyPrintWriter(false)
        var flag = false
        try {
            LuposdateEndpoint.evaluateSparqlToOperatorgraphA(instance, query)
        } catch (e: Throwable) {
            flag = true
        }
        if (!flag) {
            fail("expected failure")
        }
    }
}