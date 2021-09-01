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
package lupos.code_gen_test_12
import lupos.shared.inline.File

public class parentquerywithhasChildsomeThingrestriction {
    internal val inputData = arrayOf(
        File("src/jvmTest/resources/parentquerywithhasChildsomeThingrestriction.input").readAsString(),
    )
    internal val inputGraph = arrayOf(
        "",
    )
    internal val inputType = arrayOf(
        ".ttl",
    )
    internal val targetData = File("src/jvmTest/resources/parentquerywithhasChildsomeThingrestriction.output").readAsString()
    internal val targetType = ".srx"
    internal val query = "PREFIX owl: <http://www.w3.org/2002/07/owl#>  \n" +
        "PREFIX : <http://example.org/test#> \n" +
        "SELECT *  \n" +
        "WHERE { ?parent a [ \n" +
        "       a owl:Restriction ; \n" +
        "       owl:onProperty :hasChild ; \n" +
        "       owl:someValuesFrom owl:Thing ] . } \n" +
        ""
}
