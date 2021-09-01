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
import lupos.shared.inline.File

public class PostqueryVALUESwithobjvar1row {
    internal val inputData = arrayOf(
        File("src/jvmTest/resources/PostqueryVALUESwithobjvar1row.input").readAsString(),
    )
    internal val inputGraph = arrayOf(
        "",
    )
    internal val inputType = arrayOf(
        ".ttl",
    )
    internal val targetData = File("src/jvmTest/resources/PostqueryVALUESwithobjvar1row.output").readAsString()
    internal val targetType = ".srx"
    internal val query = "# bindings with one element and one value in the object variable \n" +
        "PREFIX : <http://example.org/>  \n" +
        "SELECT ?s ?o \n" +
        "{ \n" +
        "  ?s ?p ?o . \n" +
        "} VALUES ?o { \n" +
        " :b \n" +
        "} \n" +
        ""
}
