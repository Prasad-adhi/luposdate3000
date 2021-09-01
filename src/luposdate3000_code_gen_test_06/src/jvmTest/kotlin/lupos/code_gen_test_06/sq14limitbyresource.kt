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
package lupos.code_gen_test_06
import lupos.shared.inline.File

public class sq14limitbyresource {
    internal val inputData = arrayOf(
        File("src/jvmTest/resources/sq14limitbyresource.input").readAsString(),
    )
    internal val inputGraph = arrayOf(
        "",
    )
    internal val inputType = arrayOf(
        ".ttl",
    )
    internal val targetData = File("src/jvmTest/resources/sq14limitbyresource.output").readAsString()
    internal val targetType = ".ttl"
    internal val query = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> \n" +
        "CONSTRUCT { \n" +
        "   ?person a foaf:Person ; \n" +
        "           foaf:name ?name ; \n" +
        "           foaf:homepage ?homepage ; \n" +
        "           foaf:mbox ?mbox . \n" +
        "} WHERE { \n" +
        "  { \n" +
        "    SELECT ?person ?name WHERE { \n" +
        "       ?person a foaf:Person ; \n" +
        "               foaf:name ?name . \n" +
        "      } ORDER BY ?name LIMIT 3 \n" +
        "  } \n" +
        "  ?person foaf:homepage ?homepage . \n" +
        "  OPTIONAL { ?person foaf:mbox ?mbox . }         \n" +
        "} \n" +
        ""
}
