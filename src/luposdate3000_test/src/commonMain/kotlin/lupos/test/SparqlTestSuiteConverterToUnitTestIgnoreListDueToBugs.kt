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
package lupos.test
internal object SparqlTestSuiteConverterToUnitTestIgnoreListDueToBugs {
// grep -rl "failure message" ./src/luposdate3000_launch_code_gen_test/build/test-results/jvmTest | sed "s/.xml$/\" to \"Bug\",/g" | sed "s/.*\./\"/g" | sort | uniq
    internal val ignoreListDueToBugs = mapOf(
        "resourcesbtc029sparql1198" to "Bug",
        "resourcesbtc030sparql867" to "Bug",
        "resourcesmyqueriesoptional2sparql4" to "Bug",
        "resourcesmyqueriesoptional10sparql4" to "Bug",
        "resourcesmyqueriesoptional11sparql5" to "Bug",
        "resourcesmyqueriesoptional1sparql5" to "Bug",
        "resourcesmyqueriesoptional20sparql5" to "Bug",
        "resourcesmyqueriesoptional22sparql5" to "Bug",
        "resourcesmyqueriesoptional26sparql5" to "Bug",
        "resourcesmyqueriesoptional29sparql4" to "Bug",
        "resourcesmyqueriesoptional31sparql4" to "Bug",
        "resourcesmyqueriesoptional32sparql4" to "Bug",
        "resourcesmyqueriesoptional37sparql4" to "Bug",
        "resourcesmyqueriesoptional44sparql5" to "Bug",
        "resourcesmyqueriesoptional45sparql4" to "Bug",
        "resourcesmyqueriesoptional47sparql4" to "Bug",
        "resourcesmyqueriesoptional49sparql4" to "Bug",
        "resourcesmyqueriesoptional8sparql4" to "Bug",
        "resourcesmyqueriesoptional9sparql4" to "Bug",
        "resourcesmyqueriesx3sparql4" to "Bug",
        "resourcessp2bq10sparql21" to "Bug",
        "resourcessp2bq10sparql247" to "Bug",
        "resourcessp2bq11sparql21" to "Bug",
        "resourcessp2bq11sparql247" to "Bug",
        "resourcessp2bq12asparql247" to "Bug",
        "resourcessp2bq12b3sparql973" to "Bug",
        "resourcessp2bq12b4sparql247" to "Bug",
        "resourcessp2bq12b4sparql973" to "Bug",
        "resourcessp2bq12bsparql247" to "Bug",
        "resourcessp2bq12bsparql973" to "Bug",
        "resourcessp2bq1sparql700" to "Bug",
        "resourcessp2bq1sparql973" to "Bug",
        "resourcessp2bq2sparql973" to "Bug",
        "resourcessp2bq41sparql247" to "Bug",
        "resourcessp2bq67sparql973" to "Bug",
        "resourcessp2bq71sparql247" to "Bug",
        "resourcessp2bq71sparql700" to "Bug",
        "resourcessp2bq72sparql700" to "Bug",
        "resourcessp2bq92sparql21" to "Bug",
        "resourcesmyqueriesoptional35sparql5" to "Bug",
        "resourcesmyqueriesoptional37sparql5" to "Bug",
        "resourcesmyqueriesoptional38sparql5" to "Bug",
        "resourcesmyqueriesoptional3sparql5" to "Bug",
        "resourcesmyqueriesoptional40sparql5" to "Bug",
        "resourcesmyqueriesoptional56sparql5" to "Bug",
        "resourcesmyqueriesoptional7sparql4" to "Bug",
        "resourcessp2bq11sparql700" to "Bug",
        "resourcessp2bq12b3sparql700" to "Bug",
        "resourcessp2bq4sparql247" to "Bug",
        "resourcessp2bq5bsparql21" to "Bug",
        "resourcessp2bq68sparql973" to "Bug",
        "resourcessp2bq6sparql700" to "Bug",
        "resourcessp2bq6sparql973" to "Bug",
        "AVG" to "Bug",
        "AVGwithGROUPBY" to "Bug",
        "bnodesarenotexistentials" to "Bug",
        "bnodesarenotexistentialswithanswer" to "Bug",
        "BNODEstr" to "Bug",
        "BNODE" to "Bug",
        "RDFSinferencetestsubClassOfluposDuplicate1" to "Bug",
        "Calculatepropersubset" to "Bug",
        "CalculatewhichsetsaresubsetsofothersexcludeAsubsetOfA" to "Bug",
        "CalculatewhichsetsaresubsetsofothersincludeAsubsetOfA" to "Bug",
        "Calculatewhichsetshavethesameelements" to "Bug",
        "COALESCE" to "Bug",
        "CONCAT2" to "Bug",
        "COPY2" to "Bug",
        "COPY4" to "Bug",
        "COUNT10" to "Bug",
        "COUNT11" to "Bug",
        "RDFinferencetest" to "Bug",
        "COUNT9" to "Bug",
        "DELETEINSERT1b" to "Bug",
        "DELETEINSERT1c" to "Bug",
        "DELETEINSERT1" to "Bug",
        "DELETEINSERT2" to "Bug",
        "DELETEINSERT3b" to "Bug",
        "DELETEINSERT3" to "Bug",
        "DELETEINSERT4b" to "Bug",
        "DELETEINSERT4" to "Bug",
        "DELETEINSERT5b" to "Bug",
        "DELETEINSERT5" to "Bug",
        "DELETEINSERT6" to "Bug",
        "DELETEINSERT7b" to "Bug",
        "DELETEINSERT7" to "Bug",
        "DELETEINSERT8" to "Bug",
        "DELETEINSERT9" to "Bug",
        "ErrorinAVG" to "Bug",
        "Existswithingraphpattern" to "Bug",
        "Expressionhasvariablethatmaybeunbound" to "Bug",
        "Expressionmayreturnnovalue" to "Bug",
        "Expressionraiseanerror" to "Bug",
        "filteredsubclassquerywithhasChildsomeThingrestriction" to "Bug",
        "GraphspecificDELETE1" to "Bug",
        "GraphspecificDELETE1USING" to "Bug",
        "GraphspecificDELETE1WITH" to "Bug",
        "GraphspecificDELETE2" to "Bug",
        "GraphspecificDELETE2USING" to "Bug",
        "GraphspecificDELETE2WITH" to "Bug",
        "GraphspecificDELETEDATA1" to "Bug",
        "GraphspecificDELETEDATA2" to "Bug",
        "GraphspecificDELETEWHERE1" to "Bug",
        "GraphspecificDELETEWHERE2" to "Bug",
        "Group6" to "Bug",
        "Group7" to "Bug",
        "IFerrorpropogation" to "Bug",
        "INSERT02" to "Bug",
        "INSERTingthesamebnodewithINSERTDATAintotwodifferentGraphsisthesamebnode" to "Bug",
        "INSERTingthesamebnodewithtwoINSERTWHEREstatementwithinonerequestisNOTthesamebnodeevenifbothWHEREclauseshavetheemptysolutionmappingastheonlysolution" to "Bug",
        "INSERTingthesamebnodewithtwoINSERTWHEREstatementwithinonerequestisNOTthesamebnode" to "Bug",
        "INSERTsamebnodetwice" to "Bug",
        "IRIURI" to "Bug",
        "MAX" to "Bug",
        "MAXwithGROUPBY" to "Bug",
        "MedicaltemporalproximitybyexclusionNOTEXISTS" to "Bug",
        "MINwithGROUPBY" to "Bug",
        "MOVE2" to "Bug",
        "MOVE4" to "Bug",
        "NOW" to "Bug",
        "papersparqldlQ1rdfs" to "Bug",
        "papersparqldlQ1" to "Bug",
        "papersparqldlQ2" to "Bug",
        "papersparqldlQ3" to "Bug",
        "papersparqldlQ4" to "Bug",
        "papersparqldlQ5" to "Bug",
        "parentquerywithdistinguishedvariable" to "Bug",
        "parentquerywithhasChildexactly1Femalerestriction" to "Bug",
        "parentquerywithhasChildmax1Femalerestriction" to "Bug",
        "parentquerywithhasChildmin1Femalerestriction" to "Bug",
        "parentquerywithhasChildmin1restriction" to "Bug",
        "parentquerywithhasChildsomeFemalerestriction" to "Bug",
        "parentquerywithhasChildsomeThingrestriction" to "Bug",
        "plus1" to "Bug",
        "plus2" to "Bug",
        "PostqueryVALUESwith2objvars1row" to "Bug",
        "PostqueryVALUESwith2objvars1rowwithUNDEF" to "Bug",
        "PostqueryVALUESwith2objvars2rowswithUNDEF" to "Bug",
        "PostqueryVALUESwithobjvar1row" to "Bug",
        "PostqueryVALUESwithOPTIONALobjvar1row" to "Bug",
        "PostqueryVALUESwithpredvar1row" to "Bug",
        "ProtectfromerrorinAVG" to "Bug",
        "RDFSinferencetestrdfssubPropertyOfluposDuplicate1" to "Bug",
        "RDFSinferencetestcombiningsubPropertyOfanddomain" to "Bug",
        "RDFSinferencetestdomain" to "Bug",
        "RDFSinferencetestrange" to "Bug",
        "RDFSinferencetestrdfssubPropertyOf" to "Bug",
        "RDFSinferencetestsubClassOf" to "Bug",
        "RDFSinferencetestsubPropertyandinstances" to "Bug",
        "RDFSinferencetesttransitivityofsubClassOf" to "Bug",
        "RDFSinferencetesttransitivityofsubPropertyOf" to "Bug",
        "Reuseaprojectexpressionvariableinselect" to "Bug",
        "SERVICEtest7" to "Bug",
        "SHA1onUnicodedata" to "Bug",
        "simple1" to "Bug",
        "simple2" to "Bug",
        "simple3" to "Bug",
        "simple4" to "Bug",
        "simple5" to "Bug",
        "simple6" to "Bug",
        "simple7" to "Bug",
        "simple8" to "Bug",
        "SimpleDELETE1" to "Bug",
        "SimpleDELETE1USING" to "Bug",
        "SimpleDELETE1WITH" to "Bug",
        "SimpleDELETE2" to "Bug",
        "SimpleDELETE2USING" to "Bug",
        "SimpleDELETE2WITH" to "Bug",
        "SimpleDELETE3USING" to "Bug",
        "SimpleDELETE3WITH" to "Bug",
        "SimpleDELETE4" to "Bug",
        "SimpleDELETE4USING" to "Bug",
        "SimpleDELETE4WITH" to "Bug",
        "SimpleDELETE7" to "Bug",
        "SimpleDELETEDATA1" to "Bug",
        "SimpleDELETEDATA2" to "Bug",
        "SimpleDELETEDATA3" to "Bug",
        "SimpleDELETEDATA4" to "Bug",
        "SimpleDELETEWHERE1" to "Bug",
        "SimpleDELETEWHERE2" to "Bug",
        "SimpleDELETEWHERE4" to "Bug",
        "Simpleinsertdatanamed1" to "Bug",
        "Simpleinsertdatanamed2" to "Bug",
        "Simpleinsertdatanamed3" to "Bug",
        "sparqldl02rqsimplecombinedquery" to "Bug",
        "sparqldl03rqcombinedquerywithcomplexclassdescription" to "Bug",
        "sparqldl04rqbugfixingtest" to "Bug",
        "sparqldl05rqsimpleundistinguishedvariabletest" to "Bug",
        "sparqldl06rqcycleofundistinguishedvariables" to "Bug",
        "sparqldl07rqtwodistinguishedvariablesundist" to "Bug",
        "sparqldl08rqtwodistinguishedvariablesundist" to "Bug",
        "sparqldl09rqundistvarstest" to "Bug",
        "sparqldl10rqundistvarstest" to "Bug",
        "sparqldl11rqdomaintest" to "Bug",
        "sparqldl12rqrangetest" to "Bug",
        "sparqldl13rqsameAs" to "Bug",
        "sq11Subquerylimitperresource" to "Bug",
        "sq13Subqueriesdontinjectbindings" to "Bug",
        "sq14limitbyresource" to "Bug",
        "STRAFTERdatatyping" to "Bug",
        "STRAFTER" to "Bug",
        "STRBEFOREdatatyping" to "Bug",
        "STRBEFORE" to "Bug",
        "STRDT" to "Bug",
        "STRDTTypeErrors" to "Bug",
        "STRLANG" to "Bug",
        "STRLANGTypeErrors" to "Bug",
        "subclassquerywithhasChildsomeThingrestriction" to "Bug",
        "SubtractionwithMINUSfromafullyboundminuend" to "Bug",
        "SubtractionwithMINUSfromapartiallyboundminuend" to "Bug",
        "SUM" to "Bug",
        "TIMEZONE" to "Bug",
        "TZ" to "Bug",
        "Group4" to "Bug",
        "DROPGRAPH" to "Bug",
        "sq12SubqueryinCONSTRUCTwithbuiltins" to "Bug",
        "InlineVALUESgraphpattern" to "Bug",
        "COUNT4" to "Bug",
        "constructwhere03CONSTRUCTWHERE" to "Bug",
        "PostsubqueryVALUES" to "Bug",
        "DROPNAMED" to "Bug",
        "PostqueryVALUESwithsubjvar1row" to "Bug",
        "PostqueryVALUESwithsubjobjvars2rowswithUNDEF" to "Bug",
        "COUNT6" to "Bug",
        "SubsetsbyexclusionMINUS" to "Bug",
        "tvs02TSVResultFormat" to "Bug",
        "ADD5" to "Bug",
        "DELETEINSERT6b" to "Bug",
        "Literalwithlanguagetagtest" to "Bug",
        "Plainliteralswithlanguagetagarenotthesameasthesameliteralwithout" to "Bug",
        "DROPSILENTGRAPHiri" to "Bug",
    )
}
