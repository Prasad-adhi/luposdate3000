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

public class PositiveEXISTS2 {
    internal val inputData = arrayOf(
        File("src/jvmTest/resources/PositiveEXISTS2.input").readAsString(),
    )
    internal val inputGraph = arrayOf(
        "",
    )
    internal val inputType = arrayOf(
        ".ttl",
    )
    internal val targetData = File("src/jvmTest/resources/PositiveEXISTS2.output").readAsString()
    internal val targetType = ".srx"
    internal val query = "PREFIX :    <http://example/> \n" +
        "PREFIX  rdf:    <http://www.w3.org/1999/02/22-rdf-syntax-ns#> \n" +
        "# SPARQL 1.1 \n" +
        "SELECT * \n" +
        "WHERE \n" +
        "{ \n" +
        " ?set a :Set . \n" +
        " FILTER EXISTS { \n" +
        "  ?set :member 7 \n" +
        " } \n" +
        "} \n" +
        ""
}
