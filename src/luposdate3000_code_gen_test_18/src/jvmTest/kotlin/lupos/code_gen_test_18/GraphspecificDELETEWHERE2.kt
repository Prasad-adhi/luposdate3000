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
package lupos.code_gen_test_18
import lupos.shared.inline.File

public class GraphspecificDELETEWHERE2 {
    internal val inputData = arrayOf(
        File("src/jvmTest/resources/GraphspecificDELETEWHERE2.input").readAsString(),
        File("src/jvmTest/resources/GraphspecificDELETEWHERE2.input1").readAsString(),
        File("src/jvmTest/resources/GraphspecificDELETEWHERE2.input2").readAsString(),
    )
    internal val inputGraph = arrayOf(
        "",
        "http://example.org/g2",
        "http://example.org/g3",
    )
    internal val inputType = arrayOf(
        ".ttl",
        ".ttl",
        ".ttl",
    )
    internal val outputData = arrayOf(
        File("src/jvmTest/resources/GraphspecificDELETEWHERE2.output0").readAsString(),
        File("src/jvmTest/resources/GraphspecificDELETEWHERE2.output1").readAsString(),
        File("src/jvmTest/resources/GraphspecificDELETEWHERE2.output2").readAsString(),
    )
    internal val outputGraph = arrayOf(
        "",
        "http://example.org/g2",
        "http://example.org/g3",
    )
    internal val outputType = arrayOf(
        ".ttl",
        ".ttl",
        ".ttl",
    )
    internal val query = "PREFIX     : <http://example.org/>  \n" +
        "PREFIX foaf: <http://xmlns.com/foaf/0.1/>  \n" +
        "DELETE WHERE  \n" +
        "{ \n" +
        "  GRAPH <http://example.org/g2> { ?c foaf:name \"Chris\" } \n" +
        "} \n" +
        ""
}
