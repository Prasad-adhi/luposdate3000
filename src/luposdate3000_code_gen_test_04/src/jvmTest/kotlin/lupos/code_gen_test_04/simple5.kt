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
package lupos.code_gen_test_04
import lupos.shared.inline.File

public class simple5 {
    internal val inputData = arrayOf(
        File("src/jvmTest/resources/simple5.input").readAsString(),
    )
    internal val inputGraph = arrayOf(
        "",
    )
    internal val inputType = arrayOf(
        ".ttl",
    )
    internal val targetData = File("src/jvmTest/resources/simple5.output").readAsString()
    internal val targetType = ".srx"
    internal val query = "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> \n" +
        "PREFIX owl: <http://www.w3.org/2002/07/owl#>   \n" +
        "PREFIX : <http://example.org/test#> \n" +
        "SELECT ?x \n" +
        "WHERE {  \n" +
        "    ?x a [ \n" +
        "        a owl:Restriction ; \n" +
        "        owl:onProperty :p ; \n" +
        "        owl:someValuesFrom [ \n" +
        "            a owl:Class ; \n" +
        "            owl:unionOf ( :A :B ) \n" +
        "        ]  \n" +
        "    ] \n" +
        "} \n" +
        ""
}
