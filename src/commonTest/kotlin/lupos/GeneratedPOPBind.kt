package lupos

import lupos.s12p2p.P2P
import lupos.s14endpoint.EndpointImpl
import lupos.s00misc.*
import lupos.s15tripleStoreDistributed.*
import lupos.s02buildSyntaxTree.sparql1_1.*
import lupos.s03resultRepresentation.*
import lupos.s04arithmetikOperators.*
import lupos.s04arithmetikOperators.multiinput.*
import lupos.s04arithmetikOperators.noinput.*
import lupos.s04arithmetikOperators.singleinput.*
import lupos.s04logicalOperators.*
import lupos.s04logicalOperators.noinput.*
import lupos.s04logicalOperators.singleinput.*
import lupos.s04logicalOperators.singleinput.modifiers.*
import lupos.s08logicalOptimisation.*
import lupos.s09physicalOperators.*
import lupos.s09physicalOperators.multiinput.*
import lupos.s09physicalOperators.noinput.*
import lupos.s09physicalOperators.singleinput.*
import lupos.s09physicalOperators.singleinput.modifiers.*
import lupos.s11outputResult.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*


class GeneratedPOPBindTest {
    constructor(){
        P2P.knownClients.add(EndpointImpl.fullname)
    }
    fun setAggregationMode(node: OPBase, mode: Boolean, count: Int) {
        for (n in node.children)
            setAggregationMode(n, mode, count)
        if (node is AOPAggregation) {
            node.count = count
            node.collectMode = mode
            if (node.collectMode)
                node.a = null
        }
    }

    @TestFactory
    fun test() = listOf(
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("O12"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("O2"), AOPVariable("O1"))),
                        POPValues(dictionary, listOf(
                                "#p2396",
                                "O1",
                                "#p2397",
                                "O2",
                                "S"
                            ), listOf(
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2396" to "<http://www.example.org/p>",
                                    "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2397" to "<http://www.example.org/q>",
                                    "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "O12",
                            "#p2396",
                            "O1",
                            "#p2397",
                            "O2",
                            "S"
                        ), listOf(
                            mutableMapOf(
                                "O12" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2396" to "<http://www.example.org/p>",
                                "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2397" to "<http://www.example.org/q>",
                                "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/aggregates/agg08b.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("O12"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("O2"), AOPVariable("O1"))),
                        POPValues(dictionary, listOf(
                                "#p2658",
                                "O1",
                                "#p2659",
                                "O2",
                                "S"
                            ), listOf(
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                ),
                                mutableMapOf(
                                    "#p2658" to "<http://www.example.org/p>",
                                    "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p2659" to "<http://www.example.org/q>",
                                    "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "S" to "<http://www.example.org/s>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "O12",
                            "#p2658",
                            "O1",
                            "#p2659",
                            "O2",
                            "S"
                        ), listOf(
                            mutableMapOf(
                                "O12" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"0\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            ),
                            mutableMapOf(
                                "O12" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2658" to "<http://www.example.org/p>",
                                "O1" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p2659" to "<http://www.example.org/q>",
                                "O2" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "S" to "<http://www.example.org/s>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/aggregates/agg08b.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPAddition(AOPInteger(10), AOPVariable("o"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z" to "\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"13\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"14\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind06.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z2"),
                        POPExpression(dictionary, AOPAddition(AOPInteger(100), AOPVariable("o"))),
                        POPValues(dictionary, listOf(
                                "z",
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "z" to "\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "z" to "\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "z" to "\"13\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "z" to "\"14\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z2",
                            "z",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z2" to "\"101\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "z" to "\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z2" to "\"102\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "z" to "\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z2" to "\"103\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "z" to "\"13\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z2" to "\"104\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "z" to "\"14\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPAddition(AOPInteger(1), AOPVariable("o"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"5\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind08.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("nova"),
                        POPExpression(dictionary, AOPUndef()),
                        POPValues(dictionary, listOf(
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "nova",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "nova" to null,
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "nova" to null,
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "nova" to null,
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "nova" to null,
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind04.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPVariable("nova")),
                        POPValues(dictionary, listOf(
                                "nova",
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "nova" to null,
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "nova" to null,
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "nova" to null,
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "nova" to null,
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "nova",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z" to null,
                                "nova" to null,
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "nova" to null,
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "nova" to null,
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "nova" to null,
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind04.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("o"),
                        POPExpression(dictionary, AOPUndef()),
                        POPValues(dictionary, listOf(
                            ), listOf(
                                mutableMapOf(
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "o" to null
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/entailment/bind07.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPAddition(AOPInteger(1), AOPVariable("o"))),
                        POPValues(dictionary, listOf(
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "o" to null
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z" to null,
                                "o" to null
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/entailment/bind07.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPAddition(AOPInteger(2), AOPVariable("o"))),
                        POPValues(dictionary, listOf(
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "o" to null
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z" to null,
                                "o" to null
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/entailment/bind07.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPUndef()),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p7865",
                                "v"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "#p7865" to "<http://example.org/p>",
                                    "v" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p7865" to "<http://example.org/p>",
                                    "v" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "#p7865" to "<http://example.org/p>",
                                    "v" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "#p7865" to "<http://example.org/p>",
                                    "v" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "s",
                            "#p7865",
                            "v"
                        ), listOf(
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/s1>",
                                "#p7865" to "<http://example.org/p>",
                                "v" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/s2>",
                                "#p7865" to "<http://example.org/p>",
                                "v" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/s3>",
                                "#p7865" to "<http://example.org/p>",
                                "v" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/s4>",
                                "#p7865" to "<http://example.org/p>",
                                "v" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind10.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPUndef()),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p7934",
                                "v"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "#p7934" to "<http://example.org/p>",
                                    "v" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p7934" to "<http://example.org/p>",
                                    "v" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "#p7934" to "<http://example.org/p>",
                                    "v" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "#p7934" to "<http://example.org/p>",
                                    "v" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "s",
                            "#p7934",
                            "v"
                        ), listOf(
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/s1>",
                                "#p7934" to "<http://example.org/p>",
                                "v" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/s2>",
                                "#p7934" to "<http://example.org/p>",
                                "v" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/s3>",
                                "#p7934" to "<http://example.org/p>",
                                "v" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/s4>",
                                "#p7934" to "<http://example.org/p>",
                                "v" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind10.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPInteger(4)),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p8059",
                                "v"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "#p8059" to "<http://example.org/p>",
                                    "v" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p8059" to "<http://example.org/p>",
                                    "v" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "#p8059" to "<http://example.org/p>",
                                    "v" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "#p8059" to "<http://example.org/p>",
                                    "v" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "s",
                            "#p8059",
                            "v"
                        ), listOf(
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "#p8059" to "<http://example.org/p>",
                                "v" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "#p8059" to "<http://example.org/p>",
                                "v" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "#p8059" to "<http://example.org/p>",
                                "v" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "#p8059" to "<http://example.org/p>",
                                "v" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind11.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPInteger(4)),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p8123",
                                "v"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "#p8123" to "<http://example.org/p>",
                                    "v" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p8123" to "<http://example.org/p>",
                                    "v" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "#p8123" to "<http://example.org/p>",
                                    "v" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "#p8123" to "<http://example.org/p>",
                                    "v" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "s",
                            "#p8123",
                            "v"
                        ), listOf(
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "#p8123" to "<http://example.org/p>",
                                "v" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "#p8123" to "<http://example.org/p>",
                                "v" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "#p8123" to "<http://example.org/p>",
                                "v" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "#p8123" to "<http://example.org/p>",
                                "v" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/bind/bind11.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPAddition(AOPInteger(10), AOPVariable("o"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/p>",
                                    "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "o" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "_:1",
                                    "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "o" to "<http://www.w3.org/2002/07/owl#Ontology>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/p>",
                                "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "o" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>"
                            ),
                            mutableMapOf(
                                "z" to "\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"13\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"14\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "s" to "_:1",
                                "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "o" to "<http://www.w3.org/2002/07/owl#Ontology>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/entailment/bind06.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z2"),
                        POPExpression(dictionary, AOPAddition(AOPInteger(100), AOPVariable("o"))),
                        POPValues(dictionary, listOf(
                                "z",
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "z" to null,
                                    "s" to "<http://example.org/p>",
                                    "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "o" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>"
                                ),
                                mutableMapOf(
                                    "z" to "\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "z" to "\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "z" to "\"13\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "z" to "\"14\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "z" to null,
                                    "s" to "_:1",
                                    "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "o" to "<http://www.w3.org/2002/07/owl#Ontology>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z2",
                            "z",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z2" to null,
                                "z" to null,
                                "s" to "<http://example.org/p>",
                                "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "o" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>"
                            ),
                            mutableMapOf(
                                "z2" to "\"101\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "z" to "\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z2" to "\"102\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "z" to "\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z2" to "\"103\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "z" to "\"13\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z2" to "\"104\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "z" to "\"14\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z2" to null,
                                "z" to null,
                                "s" to "_:1",
                                "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "o" to "<http://www.w3.org/2002/07/owl#Ontology>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/entailment/bind02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPAddition(AOPInteger(1), AOPVariable("o"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/p>",
                                    "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "o" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/p>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "_:1",
                                    "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "o" to "<http://www.w3.org/2002/07/owl#Ontology>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "z" to null,
                                "s" to "<http://example.org/p>",
                                "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "o" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>"
                            ),
                            mutableMapOf(
                                "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to "\"5\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/p>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "s" to "_:1",
                                "p" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "o" to "<http://www.w3.org/2002/07/owl#Ontology>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/entailment/bind08.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("nova"),
                        POPExpression(dictionary, AOPUndef()),
                        POPValues(dictionary, listOf(
                                "s",
                                "o",
                                "#p17164",
                                "#o17164",
                                "p"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                    "p" to "<http://example.org/p>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                    "p" to "<http://example.org/p>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                    "p" to "<http://example.org/p>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                    "p" to "<http://example.org/p>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "nova",
                            "s",
                            "o",
                            "#p17164",
                            "#o17164",
                            "p"
                        ), listOf(
                            mutableMapOf(
                                "nova" to null,
                                "s" to "<http://example.org/s1>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                "p" to "<http://example.org/p>"
                            ),
                            mutableMapOf(
                                "nova" to null,
                                "s" to "<http://example.org/s2>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                "p" to "<http://example.org/p>"
                            ),
                            mutableMapOf(
                                "nova" to null,
                                "s" to "<http://example.org/s3>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                "p" to "<http://example.org/p>"
                            ),
                            mutableMapOf(
                                "nova" to null,
                                "s" to "<http://example.org/s4>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                "p" to "<http://example.org/p>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/entailment/bind04.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("z"),
                        POPExpression(dictionary, AOPVariable("nova")),
                        POPValues(dictionary, listOf(
                                "nova",
                                "s",
                                "o",
                                "#p17164",
                                "#o17164",
                                "p"
                            ), listOf(
                                mutableMapOf(
                                    "nova" to null,
                                    "s" to "<http://example.org/s1>",
                                    "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                    "p" to "<http://example.org/p>"
                                ),
                                mutableMapOf(
                                    "nova" to null,
                                    "s" to "<http://example.org/s2>",
                                    "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                    "p" to "<http://example.org/p>"
                                ),
                                mutableMapOf(
                                    "nova" to null,
                                    "s" to "<http://example.org/s3>",
                                    "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                    "p" to "<http://example.org/p>"
                                ),
                                mutableMapOf(
                                    "nova" to null,
                                    "s" to "<http://example.org/s4>",
                                    "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                    "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                    "p" to "<http://example.org/p>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "z",
                            "nova",
                            "s",
                            "o",
                            "#p17164",
                            "#o17164",
                            "p"
                        ), listOf(
                            mutableMapOf(
                                "z" to null,
                                "nova" to null,
                                "s" to "<http://example.org/s1>",
                                "o" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                "p" to "<http://example.org/p>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "nova" to null,
                                "s" to "<http://example.org/s2>",
                                "o" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                "p" to "<http://example.org/p>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "nova" to null,
                                "s" to "<http://example.org/s3>",
                                "o" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                "p" to "<http://example.org/p>"
                            ),
                            mutableMapOf(
                                "z" to null,
                                "nova" to null,
                                "s" to "<http://example.org/s4>",
                                "o" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p17164" to "<http://www.w3.org/1999/02/22-rdf-syntax-ns#type>",
                                "#o17164" to "<http://www.w3.org/2002/07/owl#DatatypeProperty>",
                                "p" to "<http://example.org/p>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/entailment/bind04.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str1"),
                        POPExpression(dictionary, AOPBuildInCallSTRDT(AOPVariable("str"), AOPIri("http://www.w3.org/2001/XMLSchema#string"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p21745",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p21745" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str1",
                            "s",
                            "#p21745",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/s2>",
                                "#p21745" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strdt01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str1"),
                        POPExpression(dictionary, AOPBuildInCallSTRDT(AOPVariable("str"), AOPIri("http://www.w3.org/2001/XMLSchema#string"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p21878",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p21878" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str1",
                            "s",
                            "#p21878",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/s2>",
                                "#p21878" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strdt01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str1"),
                        POPExpression(dictionary, AOPBuildInCallSTRDT(AOPBuildInCallSTR(AOPVariable("str")), AOPIri("http://www.w3.org/2001/XMLSchema#string"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p22072",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p22072" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str1",
                            "s",
                            "#p22072",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "str1" to "\"bar\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s2>",
                                "#p22072" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strdt02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str1"),
                        POPExpression(dictionary, AOPBuildInCallSTRDT(AOPBuildInCallSTR(AOPVariable("str")), AOPIri("http://www.w3.org/2001/XMLSchema#string"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p22213",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p22213" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str1",
                            "s",
                            "#p22213",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "str1" to "\"bar\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s2>",
                                "#p22213" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strdt02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str1"),
                        POPExpression(dictionary, AOPBuildInCallSTRDT(AOPVariable("o"), AOPIri("http://www.w3.org/2001/XMLSchema#string"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "p" to "<http://example.org/date>",
                                    "o" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "p" to "<http://example.org/date>",
                                    "o" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "p" to "<http://example.org/date>",
                                    "o" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "p" to "<http://example.org/date>",
                                    "o" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n1>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n2>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n3>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n4>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n5>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"bar\"@en"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"BAZ\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"食べ物\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s5>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"100%\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s6>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s7>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str1",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/d1>",
                                "p" to "<http://example.org/date>",
                                "o" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/d2>",
                                "p" to "<http://example.org/date>",
                                "o" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/d3>",
                                "p" to "<http://example.org/date>",
                                "o" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/d4>",
                                "p" to "<http://example.org/date>",
                                "o" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n1>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n2>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n3>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n4>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n5>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "str1" to "\"foo\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"foo\""
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"bar\"@en"
                            ),
                            mutableMapOf(
                                "str1" to "\"BAZ\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"BAZ\""
                            ),
                            mutableMapOf(
                                "str1" to "\"食べ物\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"食べ物\""
                            ),
                            mutableMapOf(
                                "str1" to "\"100%\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s5>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"100%\""
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/s6>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/s7>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strdt03.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("s2"),
                        POPExpression(dictionary, AOPBuildInCallSTRLANG(AOPVariable("str"), AOPSimpleLiteral("\"", "en-US"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p22584",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p22584" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "s2",
                            "s",
                            "#p22584",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "s2" to null,
                                "s" to "<http://example.org/s2>",
                                "#p22584" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strlang01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("s2"),
                        POPExpression(dictionary, AOPBuildInCallSTRLANG(AOPVariable("str"), AOPSimpleLiteral("\"", "en-US"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p22717",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p22717" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "s2",
                            "s",
                            "#p22717",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "s2" to null,
                                "s" to "<http://example.org/s2>",
                                "#p22717" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strlang01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("s2"),
                        POPExpression(dictionary, AOPBuildInCallSTRLANG(AOPBuildInCallSTR(AOPVariable("str")), AOPSimpleLiteral("\"", "en-US"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p22908",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p22908" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "s2",
                            "s",
                            "#p22908",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "s2" to "\"bar\"@en-us",
                                "s" to "<http://example.org/s2>",
                                "#p22908" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strlang02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("s2"),
                        POPExpression(dictionary, AOPBuildInCallSTRLANG(AOPBuildInCallSTR(AOPVariable("str")), AOPSimpleLiteral("\"", "en-US"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p23049",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p23049" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "s2",
                            "s",
                            "#p23049",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "s2" to "\"bar\"@en-us",
                                "s" to "<http://example.org/s2>",
                                "#p23049" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strlang02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str1"),
                        POPExpression(dictionary, AOPBuildInCallSTRLANG(AOPVariable("o"), AOPSimpleLiteral("\"", "en-US"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "p" to "<http://example.org/date>",
                                    "o" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "p" to "<http://example.org/date>",
                                    "o" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "p" to "<http://example.org/date>",
                                    "o" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "p" to "<http://example.org/date>",
                                    "o" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n1>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n2>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n3>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n4>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n5>",
                                    "p" to "<http://example.org/num>",
                                    "o" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"bar\"@en"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"BAZ\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"食べ物\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s5>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"100%\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s6>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s7>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str1",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/d1>",
                                "p" to "<http://example.org/date>",
                                "o" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/d2>",
                                "p" to "<http://example.org/date>",
                                "o" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/d3>",
                                "p" to "<http://example.org/date>",
                                "o" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/d4>",
                                "p" to "<http://example.org/date>",
                                "o" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n1>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n2>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n3>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n4>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/n5>",
                                "p" to "<http://example.org/num>",
                                "o" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "str1" to "\"foo\"@en-us",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"foo\""
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"bar\"@en"
                            ),
                            mutableMapOf(
                                "str1" to "\"BAZ\"@en-us",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"BAZ\""
                            ),
                            mutableMapOf(
                                "str1" to "\"食べ物\"@en-us",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"食べ物\""
                            ),
                            mutableMapOf(
                                "str1" to "\"100%\"@en-us",
                                "s" to "<http://example.org/s5>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"100%\""
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/s6>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str1" to null,
                                "s" to "<http://example.org/s7>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/strlang03.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("ceil"),
                        POPExpression(dictionary, AOPBuildInCallCEIL(AOPVariable("num"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p23719",
                                "num"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/n1>",
                                    "#p23719" to "<http://example.org/num>",
                                    "num" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n2>",
                                    "#p23719" to "<http://example.org/num>",
                                    "num" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n3>",
                                    "#p23719" to "<http://example.org/num>",
                                    "num" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n4>",
                                    "#p23719" to "<http://example.org/num>",
                                    "num" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n5>",
                                    "#p23719" to "<http://example.org/num>",
                                    "num" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "ceil",
                            "s",
                            "#p23719",
                            "num"
                        ), listOf(
                            mutableMapOf(
                                "ceil" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/n1>",
                                "#p23719" to "<http://example.org/num>",
                                "num" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "ceil" to "\"-1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n2>",
                                "#p23719" to "<http://example.org/num>",
                                "num" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "ceil" to "\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n3>",
                                "#p23719" to "<http://example.org/num>",
                                "num" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "ceil" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/n4>",
                                "#p23719" to "<http://example.org/num>",
                                "num" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "ceil" to "\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n5>",
                                "#p23719" to "<http://example.org/num>",
                                "num" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/ceil01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("floor"),
                        POPExpression(dictionary, AOPBuildInCallFLOOR(AOPVariable("num"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p23815",
                                "num"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/n1>",
                                    "#p23815" to "<http://example.org/num>",
                                    "num" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n2>",
                                    "#p23815" to "<http://example.org/num>",
                                    "num" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n3>",
                                    "#p23815" to "<http://example.org/num>",
                                    "num" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n4>",
                                    "#p23815" to "<http://example.org/num>",
                                    "num" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n5>",
                                    "#p23815" to "<http://example.org/num>",
                                    "num" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "floor",
                            "s",
                            "#p23815",
                            "num"
                        ), listOf(
                            mutableMapOf(
                                "floor" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/n1>",
                                "#p23815" to "<http://example.org/num>",
                                "num" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "floor" to "\"-2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n2>",
                                "#p23815" to "<http://example.org/num>",
                                "num" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "floor" to "\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n3>",
                                "#p23815" to "<http://example.org/num>",
                                "num" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "floor" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/n4>",
                                "#p23815" to "<http://example.org/num>",
                                "num" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "floor" to "\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n5>",
                                "#p23815" to "<http://example.org/num>",
                                "num" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/floor01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("round"),
                        POPExpression(dictionary, AOPBuildInCallROUND(AOPVariable("num"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p23911",
                                "num"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/n1>",
                                    "#p23911" to "<http://example.org/num>",
                                    "num" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n2>",
                                    "#p23911" to "<http://example.org/num>",
                                    "num" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n3>",
                                    "#p23911" to "<http://example.org/num>",
                                    "num" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n4>",
                                    "#p23911" to "<http://example.org/num>",
                                    "num" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/n5>",
                                    "#p23911" to "<http://example.org/num>",
                                    "num" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "round",
                            "s",
                            "#p23911",
                            "num"
                        ), listOf(
                            mutableMapOf(
                                "round" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/n1>",
                                "#p23911" to "<http://example.org/num>",
                                "num" to "\"-1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "round" to "\"-2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n2>",
                                "#p23911" to "<http://example.org/num>",
                                "num" to "\"-1.6\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "round" to "\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n3>",
                                "#p23911" to "<http://example.org/num>",
                                "num" to "\"1.1\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            ),
                            mutableMapOf(
                                "round" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/n4>",
                                "#p23911" to "<http://example.org/num>",
                                "num" to "\"-2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "round" to "\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/n5>",
                                "#p23911" to "<http://example.org/num>",
                                "num" to "\"2.5\"^^<http://www.w3.org/2001/XMLSchema#decimal>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/round01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str"),
                        POPExpression(dictionary, AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2"))),
                        POPValues(dictionary, listOf(
                                "#s24013",
                                "#p24013",
                                "str1",
                                "#s24014",
                                "#p24014",
                                "str2"
                            ), listOf(
                                mutableMapOf(
                                    "#s24013" to "<http://example.org/s6>",
                                    "#p24013" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "#s24014" to "<http://example.org/s7>",
                                    "#p24014" to "<http://example.org/str>",
                                    "str2" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str",
                            "#s24013",
                            "#p24013",
                            "str1",
                            "#s24014",
                            "#p24014",
                            "str2"
                        ), listOf(
                            mutableMapOf(
                                "str" to "\"abcDEF\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "#s24013" to "<http://example.org/s6>",
                                "#p24013" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "#s24014" to "<http://example.org/s7>",
                                "#p24014" to "<http://example.org/str>",
                                "str2" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/concat01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str"),
                        POPExpression(dictionary, AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2"))),
                        POPValues(dictionary, listOf(
                                "#s24041",
                                "#p24041",
                                "str1",
                                "#s24042",
                                "#p24042",
                                "str2"
                            ), listOf(
                                mutableMapOf(
                                    "#s24041" to "<http://example.org/s6>",
                                    "#p24041" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "#s24042" to "<http://example.org/s7>",
                                    "#p24042" to "<http://example.org/str>",
                                    "str2" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str",
                            "#s24041",
                            "#p24041",
                            "str1",
                            "#s24042",
                            "#p24042",
                            "str2"
                        ), listOf(
                            mutableMapOf(
                                "str" to "\"abcDEF\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "#s24041" to "<http://example.org/s6>",
                                "#p24041" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "#s24042" to "<http://example.org/s7>",
                                "#p24042" to "<http://example.org/str>",
                                "str2" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/concat01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("str"),
                        POPExpression(dictionary, AOPBuildInCallCONCAT(AOPVariable("str1"), AOPVariable("str2"))),
                        POPValues(dictionary, listOf(
                                "s1",
                                "#p24128",
                                "str1",
                                "s2",
                                "#p24129",
                                "str2"
                            ), listOf(
                                mutableMapOf(
                                    "s1" to "<http://example.org/s1>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"123\"",
                                    "s2" to "<http://example.org/s1>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"123\""
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s2>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"日本語\"@ja",
                                    "s2" to "<http://example.org/s1>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"123\""
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s3>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"english\"@en",
                                    "s2" to "<http://example.org/s1>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"123\""
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s4>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"français\"@fr",
                                    "s2" to "<http://example.org/s1>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"123\""
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s5>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s1>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"123\""
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s6>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s1>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"123\""
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s7>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s2" to "<http://example.org/s1>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"123\""
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s1>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"123\"",
                                    "s2" to "<http://example.org/s2>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"日本語\"@ja"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s2>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"日本語\"@ja",
                                    "s2" to "<http://example.org/s2>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"日本語\"@ja"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s3>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"english\"@en",
                                    "s2" to "<http://example.org/s2>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"日本語\"@ja"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s4>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"français\"@fr",
                                    "s2" to "<http://example.org/s2>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"日本語\"@ja"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s5>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s2>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"日本語\"@ja"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s6>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s2>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"日本語\"@ja"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s7>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s2" to "<http://example.org/s2>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"日本語\"@ja"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s1>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"123\"",
                                    "s2" to "<http://example.org/s3>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"english\"@en"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s2>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"日本語\"@ja",
                                    "s2" to "<http://example.org/s3>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"english\"@en"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s3>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"english\"@en",
                                    "s2" to "<http://example.org/s3>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"english\"@en"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s4>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"français\"@fr",
                                    "s2" to "<http://example.org/s3>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"english\"@en"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s5>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s3>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"english\"@en"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s6>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s3>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"english\"@en"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s7>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s2" to "<http://example.org/s3>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"english\"@en"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s1>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"123\"",
                                    "s2" to "<http://example.org/s4>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"français\"@fr"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s2>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"日本語\"@ja",
                                    "s2" to "<http://example.org/s4>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"français\"@fr"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s3>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"english\"@en",
                                    "s2" to "<http://example.org/s4>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"français\"@fr"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s4>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"français\"@fr",
                                    "s2" to "<http://example.org/s4>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"français\"@fr"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s5>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s4>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"français\"@fr"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s6>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s4>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"français\"@fr"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s7>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s2" to "<http://example.org/s4>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"français\"@fr"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s1>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"123\"",
                                    "s2" to "<http://example.org/s5>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s2>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"日本語\"@ja",
                                    "s2" to "<http://example.org/s5>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s3>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"english\"@en",
                                    "s2" to "<http://example.org/s5>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s4>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"français\"@fr",
                                    "s2" to "<http://example.org/s5>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s5>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s5>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s6>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s5>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s7>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s2" to "<http://example.org/s5>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s1>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"123\"",
                                    "s2" to "<http://example.org/s6>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s2>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"日本語\"@ja",
                                    "s2" to "<http://example.org/s6>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s3>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"english\"@en",
                                    "s2" to "<http://example.org/s6>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s4>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"français\"@fr",
                                    "s2" to "<http://example.org/s6>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s5>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s6>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s6>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s6>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s7>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s2" to "<http://example.org/s6>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s1>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"123\"",
                                    "s2" to "<http://example.org/s7>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s2>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"日本語\"@ja",
                                    "s2" to "<http://example.org/s7>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s3>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"english\"@en",
                                    "s2" to "<http://example.org/s7>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s4>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"français\"@fr",
                                    "s2" to "<http://example.org/s7>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s5>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s7>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s6>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "s2" to "<http://example.org/s7>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "s1" to "<http://example.org/s7>",
                                    "#p24128" to "<http://example.org/str>",
                                    "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s2" to "<http://example.org/s7>",
                                    "#p24129" to "<http://example.org/str>",
                                    "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "str",
                            "s1",
                            "#p24128",
                            "str1",
                            "s2",
                            "#p24129",
                            "str2"
                        ), listOf(
                            mutableMapOf(
                                "str" to "\"123123\"",
                                "s1" to "<http://example.org/s1>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"123\"",
                                "s2" to "<http://example.org/s1>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"123\""
                            ),
                            mutableMapOf(
                                "str" to "\"日本語123\"",
                                "s1" to "<http://example.org/s2>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"日本語\"@ja",
                                "s2" to "<http://example.org/s1>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"123\""
                            ),
                            mutableMapOf(
                                "str" to "\"english123\"",
                                "s1" to "<http://example.org/s3>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"english\"@en",
                                "s2" to "<http://example.org/s1>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"123\""
                            ),
                            mutableMapOf(
                                "str" to "\"français123\"",
                                "s1" to "<http://example.org/s4>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"français\"@fr",
                                "s2" to "<http://example.org/s1>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"123\""
                            ),
                            mutableMapOf(
                                "str" to "\"abc123\"",
                                "s1" to "<http://example.org/s5>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s1>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"123\""
                            ),
                            mutableMapOf(
                                "str" to "\"def123\"",
                                "s1" to "<http://example.org/s6>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s1>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"123\""
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s7>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s2" to "<http://example.org/s1>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"123\""
                            ),
                            mutableMapOf(
                                "str" to "\"123日本語\"",
                                "s1" to "<http://example.org/s1>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"123\"",
                                "s2" to "<http://example.org/s2>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"日本語\"@ja"
                            ),
                            mutableMapOf(
                                "str" to "\"日本語日本語\"@ja",
                                "s1" to "<http://example.org/s2>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"日本語\"@ja",
                                "s2" to "<http://example.org/s2>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"日本語\"@ja"
                            ),
                            mutableMapOf(
                                "str" to "\"english日本語\"",
                                "s1" to "<http://example.org/s3>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"english\"@en",
                                "s2" to "<http://example.org/s2>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"日本語\"@ja"
                            ),
                            mutableMapOf(
                                "str" to "\"français日本語\"",
                                "s1" to "<http://example.org/s4>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"français\"@fr",
                                "s2" to "<http://example.org/s2>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"日本語\"@ja"
                            ),
                            mutableMapOf(
                                "str" to "\"abc日本語\"",
                                "s1" to "<http://example.org/s5>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s2>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"日本語\"@ja"
                            ),
                            mutableMapOf(
                                "str" to "\"def日本語\"",
                                "s1" to "<http://example.org/s6>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s2>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"日本語\"@ja"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s7>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s2" to "<http://example.org/s2>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"日本語\"@ja"
                            ),
                            mutableMapOf(
                                "str" to "\"123english\"",
                                "s1" to "<http://example.org/s1>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"123\"",
                                "s2" to "<http://example.org/s3>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"english\"@en"
                            ),
                            mutableMapOf(
                                "str" to "\"日本語english\"",
                                "s1" to "<http://example.org/s2>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"日本語\"@ja",
                                "s2" to "<http://example.org/s3>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"english\"@en"
                            ),
                            mutableMapOf(
                                "str" to "\"englishenglish\"@en",
                                "s1" to "<http://example.org/s3>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"english\"@en",
                                "s2" to "<http://example.org/s3>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"english\"@en"
                            ),
                            mutableMapOf(
                                "str" to "\"françaisenglish\"",
                                "s1" to "<http://example.org/s4>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"français\"@fr",
                                "s2" to "<http://example.org/s3>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"english\"@en"
                            ),
                            mutableMapOf(
                                "str" to "\"abcenglish\"",
                                "s1" to "<http://example.org/s5>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s3>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"english\"@en"
                            ),
                            mutableMapOf(
                                "str" to "\"defenglish\"",
                                "s1" to "<http://example.org/s6>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s3>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"english\"@en"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s7>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s2" to "<http://example.org/s3>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"english\"@en"
                            ),
                            mutableMapOf(
                                "str" to "\"123français\"",
                                "s1" to "<http://example.org/s1>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"123\"",
                                "s2" to "<http://example.org/s4>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"français\"@fr"
                            ),
                            mutableMapOf(
                                "str" to "\"日本語français\"",
                                "s1" to "<http://example.org/s2>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"日本語\"@ja",
                                "s2" to "<http://example.org/s4>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"français\"@fr"
                            ),
                            mutableMapOf(
                                "str" to "\"englishfrançais\"",
                                "s1" to "<http://example.org/s3>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"english\"@en",
                                "s2" to "<http://example.org/s4>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"français\"@fr"
                            ),
                            mutableMapOf(
                                "str" to "\"françaisfrançais\"@fr",
                                "s1" to "<http://example.org/s4>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"français\"@fr",
                                "s2" to "<http://example.org/s4>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"français\"@fr"
                            ),
                            mutableMapOf(
                                "str" to "\"abcfrançais\"",
                                "s1" to "<http://example.org/s5>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s4>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"français\"@fr"
                            ),
                            mutableMapOf(
                                "str" to "\"deffrançais\"",
                                "s1" to "<http://example.org/s6>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s4>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"français\"@fr"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s7>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s2" to "<http://example.org/s4>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"français\"@fr"
                            ),
                            mutableMapOf(
                                "str" to "\"123abc\"",
                                "s1" to "<http://example.org/s1>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"123\"",
                                "s2" to "<http://example.org/s5>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"日本語abc\"",
                                "s1" to "<http://example.org/s2>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"日本語\"@ja",
                                "s2" to "<http://example.org/s5>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"englishabc\"",
                                "s1" to "<http://example.org/s3>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"english\"@en",
                                "s2" to "<http://example.org/s5>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"françaisabc\"",
                                "s1" to "<http://example.org/s4>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"français\"@fr",
                                "s2" to "<http://example.org/s5>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"abcabc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s1" to "<http://example.org/s5>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s5>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"defabc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s1" to "<http://example.org/s6>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s5>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s7>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s2" to "<http://example.org/s5>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"123def\"",
                                "s1" to "<http://example.org/s1>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"123\"",
                                "s2" to "<http://example.org/s6>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"日本語def\"",
                                "s1" to "<http://example.org/s2>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"日本語\"@ja",
                                "s2" to "<http://example.org/s6>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"englishdef\"",
                                "s1" to "<http://example.org/s3>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"english\"@en",
                                "s2" to "<http://example.org/s6>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"françaisdef\"",
                                "s1" to "<http://example.org/s4>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"français\"@fr",
                                "s2" to "<http://example.org/s6>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"abcdef\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s1" to "<http://example.org/s5>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s6>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to "\"defdef\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s1" to "<http://example.org/s6>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s6>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s7>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s2" to "<http://example.org/s6>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s1>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"123\"",
                                "s2" to "<http://example.org/s7>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s2>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"日本語\"@ja",
                                "s2" to "<http://example.org/s7>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s3>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"english\"@en",
                                "s2" to "<http://example.org/s7>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s4>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"français\"@fr",
                                "s2" to "<http://example.org/s7>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s5>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s7>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s6>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s2" to "<http://example.org/s7>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "str" to null,
                                "s1" to "<http://example.org/s7>",
                                "#p24128" to "<http://example.org/str>",
                                "str1" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s2" to "<http://example.org/s7>",
                                "#p24129" to "<http://example.org/str>",
                                "str2" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/concat02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("len"),
                        POPExpression(dictionary, AOPBuildInCallSTRLEN(AOPVariable("str"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p24974",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "#p24974" to "<http://example.org/str>",
                                    "str" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p24974" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "#p24974" to "<http://example.org/str>",
                                    "str" to "\"BAZ\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "#p24974" to "<http://example.org/str>",
                                    "str" to "\"食べ物\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s5>",
                                    "#p24974" to "<http://example.org/str>",
                                    "str" to "\"100%\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s6>",
                                    "#p24974" to "<http://example.org/str>",
                                    "str" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s7>",
                                    "#p24974" to "<http://example.org/str>",
                                    "str" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "len",
                            "s",
                            "#p24974",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "len" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s1>",
                                "#p24974" to "<http://example.org/str>",
                                "str" to "\"foo\""
                            ),
                            mutableMapOf(
                                "len" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s2>",
                                "#p24974" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            ),
                            mutableMapOf(
                                "len" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s3>",
                                "#p24974" to "<http://example.org/str>",
                                "str" to "\"BAZ\""
                            ),
                            mutableMapOf(
                                "len" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s4>",
                                "#p24974" to "<http://example.org/str>",
                                "str" to "\"食べ物\""
                            ),
                            mutableMapOf(
                                "len" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s5>",
                                "#p24974" to "<http://example.org/str>",
                                "str" to "\"100%\""
                            ),
                            mutableMapOf(
                                "len" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s6>",
                                "#p24974" to "<http://example.org/str>",
                                "str" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "len" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/s7>",
                                "#p24974" to "<http://example.org/str>",
                                "str" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/length01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("ustr"),
                        POPExpression(dictionary, AOPBuildInCallUCASE(AOPVariable("str"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p25083",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "#p25083" to "<http://example.org/str>",
                                    "str" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p25083" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "#p25083" to "<http://example.org/str>",
                                    "str" to "\"BAZ\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "#p25083" to "<http://example.org/str>",
                                    "str" to "\"食べ物\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s5>",
                                    "#p25083" to "<http://example.org/str>",
                                    "str" to "\"100%\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s6>",
                                    "#p25083" to "<http://example.org/str>",
                                    "str" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s7>",
                                    "#p25083" to "<http://example.org/str>",
                                    "str" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "ustr",
                            "s",
                            "#p25083",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "ustr" to "\"FOO\"",
                                "s" to "<http://example.org/s1>",
                                "#p25083" to "<http://example.org/str>",
                                "str" to "\"foo\""
                            ),
                            mutableMapOf(
                                "ustr" to "\"BAR\"@en",
                                "s" to "<http://example.org/s2>",
                                "#p25083" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            ),
                            mutableMapOf(
                                "ustr" to "\"BAZ\"",
                                "s" to "<http://example.org/s3>",
                                "#p25083" to "<http://example.org/str>",
                                "str" to "\"BAZ\""
                            ),
                            mutableMapOf(
                                "ustr" to "\"食べ物\"",
                                "s" to "<http://example.org/s4>",
                                "#p25083" to "<http://example.org/str>",
                                "str" to "\"食べ物\""
                            ),
                            mutableMapOf(
                                "ustr" to "\"100%\"",
                                "s" to "<http://example.org/s5>",
                                "#p25083" to "<http://example.org/str>",
                                "str" to "\"100%\""
                            ),
                            mutableMapOf(
                                "ustr" to "\"ABC\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s6>",
                                "#p25083" to "<http://example.org/str>",
                                "str" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "ustr" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s7>",
                                "#p25083" to "<http://example.org/str>",
                                "str" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/ucase01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("lstr"),
                        POPExpression(dictionary, AOPBuildInCallLCASE(AOPVariable("str"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p25192",
                                "str"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "#p25192" to "<http://example.org/str>",
                                    "str" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "#p25192" to "<http://example.org/str>",
                                    "str" to "\"bar\"@en"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "#p25192" to "<http://example.org/str>",
                                    "str" to "\"BAZ\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "#p25192" to "<http://example.org/str>",
                                    "str" to "\"食べ物\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s5>",
                                    "#p25192" to "<http://example.org/str>",
                                    "str" to "\"100%\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s6>",
                                    "#p25192" to "<http://example.org/str>",
                                    "str" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s7>",
                                    "#p25192" to "<http://example.org/str>",
                                    "str" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "lstr",
                            "s",
                            "#p25192",
                            "str"
                        ), listOf(
                            mutableMapOf(
                                "lstr" to "\"foo\"",
                                "s" to "<http://example.org/s1>",
                                "#p25192" to "<http://example.org/str>",
                                "str" to "\"foo\""
                            ),
                            mutableMapOf(
                                "lstr" to "\"bar\"@en",
                                "s" to "<http://example.org/s2>",
                                "#p25192" to "<http://example.org/str>",
                                "str" to "\"bar\"@en"
                            ),
                            mutableMapOf(
                                "lstr" to "\"baz\"",
                                "s" to "<http://example.org/s3>",
                                "#p25192" to "<http://example.org/str>",
                                "str" to "\"BAZ\""
                            ),
                            mutableMapOf(
                                "lstr" to "\"食べ物\"",
                                "s" to "<http://example.org/s4>",
                                "#p25192" to "<http://example.org/str>",
                                "str" to "\"食べ物\""
                            ),
                            mutableMapOf(
                                "lstr" to "\"100%\"",
                                "s" to "<http://example.org/s5>",
                                "#p25192" to "<http://example.org/str>",
                                "str" to "\"100%\""
                            ),
                            mutableMapOf(
                                "lstr" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s6>",
                                "#p25192" to "<http://example.org/str>",
                                "str" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "lstr" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "s" to "<http://example.org/s7>",
                                "#p25192" to "<http://example.org/str>",
                                "str" to "\"DEF\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/lcase01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("sum"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("y"), AOPVariable("x"))),
                        POPValues(dictionary, listOf(
                                "#p26416",
                                "x",
                                "#p26417",
                                "y",
                                "s"
                            ), listOf(
                                mutableMapOf(
                                    "#p26416" to "<http://example/p>",
                                    "x" to "\"a\"",
                                    "#p26417" to "<http://example/q>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example/x1>"
                                ),
                                mutableMapOf(
                                    "#p26416" to "<http://example/p>",
                                    "x" to "_:b",
                                    "#p26417" to "<http://example/q>",
                                    "y" to "\"1\"",
                                    "s" to "<http://example/x2>"
                                ),
                                mutableMapOf(
                                    "#p26416" to "<http://example/p>",
                                    "x" to "<http://example/a>",
                                    "#p26417" to "<http://example/q>",
                                    "y" to "\"1\"",
                                    "s" to "<http://example/x3>"
                                ),
                                mutableMapOf(
                                    "#p26416" to "<http://example/p>",
                                    "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p26417" to "<http://example/q>",
                                    "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example/x4>"
                                ),
                                mutableMapOf(
                                    "#p26416" to "<http://example/p>",
                                    "x" to "\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                    "#p26417" to "<http://example/q>",
                                    "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example/x5>"
                                ),
                                mutableMapOf(
                                    "#p26416" to "<http://example/p>",
                                    "x" to "\"1\"",
                                    "#p26417" to "<http://example/q>",
                                    "y" to "\"2\"",
                                    "s" to "<http://example/x6>"
                                ),
                                mutableMapOf(
                                    "#p26416" to "<http://example/p>",
                                    "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "#p26417" to "<http://example/q>",
                                    "y" to "\"2\"",
                                    "s" to "<http://example/x7>"
                                ),
                                mutableMapOf(
                                    "#p26416" to "<http://example/p>",
                                    "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "#p26417" to "<http://example/q>",
                                    "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example/x8>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "sum",
                            "#p26416",
                            "x",
                            "#p26417",
                            "y",
                            "s"
                        ), listOf(
                            mutableMapOf(
                                "sum" to null,
                                "#p26416" to "<http://example/p>",
                                "x" to "\"a\"",
                                "#p26417" to "<http://example/q>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example/x1>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26416" to "<http://example/p>",
                                "x" to "_:b",
                                "#p26417" to "<http://example/q>",
                                "y" to "\"1\"",
                                "s" to "<http://example/x2>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26416" to "<http://example/p>",
                                "x" to "<http://example/a>",
                                "#p26417" to "<http://example/q>",
                                "y" to "\"1\"",
                                "s" to "<http://example/x3>"
                            ),
                            mutableMapOf(
                                "sum" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p26416" to "<http://example/p>",
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p26417" to "<http://example/q>",
                                "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example/x4>"
                            ),
                            mutableMapOf(
                                "sum" to "\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "#p26416" to "<http://example/p>",
                                "x" to "\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "#p26417" to "<http://example/q>",
                                "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example/x5>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26416" to "<http://example/p>",
                                "x" to "\"1\"",
                                "#p26417" to "<http://example/q>",
                                "y" to "\"2\"",
                                "s" to "<http://example/x6>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26416" to "<http://example/p>",
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "#p26417" to "<http://example/q>",
                                "y" to "\"2\"",
                                "s" to "<http://example/x7>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26416" to "<http://example/p>",
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "#p26417" to "<http://example/q>",
                                "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example/x8>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/plus-1.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("sum"),
                        POPExpression(dictionary, AOPAddition(AOPBuildInCallSTR(AOPVariable("y")), AOPBuildInCallSTR(AOPVariable("x")))),
                        POPValues(dictionary, listOf(
                                "#p26589",
                                "x",
                                "#p26590",
                                "y",
                                "s"
                            ), listOf(
                                mutableMapOf(
                                    "#p26589" to "<http://example/p>",
                                    "x" to "\"a\"",
                                    "#p26590" to "<http://example/q>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example/x1>"
                                ),
                                mutableMapOf(
                                    "#p26589" to "<http://example/p>",
                                    "x" to "_:b",
                                    "#p26590" to "<http://example/q>",
                                    "y" to "\"1\"",
                                    "s" to "<http://example/x2>"
                                ),
                                mutableMapOf(
                                    "#p26589" to "<http://example/p>",
                                    "x" to "<http://example/a>",
                                    "#p26590" to "<http://example/q>",
                                    "y" to "\"1\"",
                                    "s" to "<http://example/x3>"
                                ),
                                mutableMapOf(
                                    "#p26589" to "<http://example/p>",
                                    "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p26590" to "<http://example/q>",
                                    "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example/x4>"
                                ),
                                mutableMapOf(
                                    "#p26589" to "<http://example/p>",
                                    "x" to "\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                    "#p26590" to "<http://example/q>",
                                    "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example/x5>"
                                ),
                                mutableMapOf(
                                    "#p26589" to "<http://example/p>",
                                    "x" to "\"1\"",
                                    "#p26590" to "<http://example/q>",
                                    "y" to "\"2\"",
                                    "s" to "<http://example/x6>"
                                ),
                                mutableMapOf(
                                    "#p26589" to "<http://example/p>",
                                    "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "#p26590" to "<http://example/q>",
                                    "y" to "\"2\"",
                                    "s" to "<http://example/x7>"
                                ),
                                mutableMapOf(
                                    "#p26589" to "<http://example/p>",
                                    "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                    "#p26590" to "<http://example/q>",
                                    "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "s" to "<http://example/x8>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "sum",
                            "#p26589",
                            "x",
                            "#p26590",
                            "y",
                            "s"
                        ), listOf(
                            mutableMapOf(
                                "sum" to null,
                                "#p26589" to "<http://example/p>",
                                "x" to "\"a\"",
                                "#p26590" to "<http://example/q>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example/x1>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26589" to "<http://example/p>",
                                "x" to "_:b",
                                "#p26590" to "<http://example/q>",
                                "y" to "\"1\"",
                                "s" to "<http://example/x2>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26589" to "<http://example/p>",
                                "x" to "<http://example/a>",
                                "#p26590" to "<http://example/q>",
                                "y" to "\"1\"",
                                "s" to "<http://example/x3>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26589" to "<http://example/p>",
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p26590" to "<http://example/q>",
                                "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example/x4>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26589" to "<http://example/p>",
                                "x" to "\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "#p26590" to "<http://example/q>",
                                "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example/x5>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26589" to "<http://example/p>",
                                "x" to "\"1\"",
                                "#p26590" to "<http://example/q>",
                                "y" to "\"2\"",
                                "s" to "<http://example/x6>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26589" to "<http://example/p>",
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "#p26590" to "<http://example/q>",
                                "y" to "\"2\"",
                                "s" to "<http://example/x7>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p26589" to "<http://example/p>",
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#string>",
                                "#p26590" to "<http://example/q>",
                                "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example/x8>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/plus-2.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallMD5(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s26889",
                                "#p26889",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s26889" to "<http://example.org/s1>",
                                    "#p26889" to "<http://example.org/str>",
                                    "l" to "\"foo\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s26889",
                            "#p26889",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"acbd18db4cc2f85cedef654fccc4a4d8\"",
                                "#s26889" to "<http://example.org/s1>",
                                "#p26889" to "<http://example.org/str>",
                                "l" to "\"foo\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/md5-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallMD5(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s26907",
                                "#p26907",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s26907" to "<http://example.org/s1>",
                                    "#p26907" to "<http://example.org/str>",
                                    "l" to "\"foo\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s26907",
                            "#p26907",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"acbd18db4cc2f85cedef654fccc4a4d8\"",
                                "#s26907" to "<http://example.org/s1>",
                                "#p26907" to "<http://example.org/str>",
                                "l" to "\"foo\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/md5-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallMD5(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s26970",
                                "#p26970",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s26970" to "<http://example.org/s4>",
                                    "#p26970" to "<http://example.org/str>",
                                    "l" to "\"食べ物\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s26970",
                            "#p26970",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"e7ada485d13b1decf628c9211bc3a97b\"",
                                "#s26970" to "<http://example.org/s4>",
                                "#p26970" to "<http://example.org/str>",
                                "l" to "\"食べ物\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/md5-02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallMD5(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s26988",
                                "#p26988",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s26988" to "<http://example.org/s4>",
                                    "#p26988" to "<http://example.org/str>",
                                    "l" to "\"食べ物\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s26988",
                            "#p26988",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"e7ada485d13b1decf628c9211bc3a97b\"",
                                "#s26988" to "<http://example.org/s4>",
                                "#p26988" to "<http://example.org/str>",
                                "l" to "\"食べ物\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/md5-02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallSHA1(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s27051",
                                "#p27051",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s27051" to "<http://example.org/s1>",
                                    "#p27051" to "<http://example.org/str>",
                                    "l" to "\"foo\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s27051",
                            "#p27051",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33\"",
                                "#s27051" to "<http://example.org/s1>",
                                "#p27051" to "<http://example.org/str>",
                                "l" to "\"foo\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/sha1-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallSHA1(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s27069",
                                "#p27069",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s27069" to "<http://example.org/s1>",
                                    "#p27069" to "<http://example.org/str>",
                                    "l" to "\"foo\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s27069",
                            "#p27069",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"0beec7b5ea3f0fdbc95d0dd47f3c5bc275da8a33\"",
                                "#s27069" to "<http://example.org/s1>",
                                "#p27069" to "<http://example.org/str>",
                                "l" to "\"foo\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/sha1-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallSHA1(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s27132",
                                "#p27132",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s27132" to "<http://example.org/s8>",
                                    "#p27132" to "<http://example.org/str>",
                                    "l" to "\"食\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s27132",
                            "#p27132",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"d46696735b6a09ff407bfc1a9407e008840db9c9\"",
                                "#s27132" to "<http://example.org/s8>",
                                "#p27132" to "<http://example.org/str>",
                                "l" to "\"食\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/sha1-02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallSHA1(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s27150",
                                "#p27150",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s27150" to "<http://example.org/s8>",
                                    "#p27150" to "<http://example.org/str>",
                                    "l" to "\"食\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s27150",
                            "#p27150",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"d46696735b6a09ff407bfc1a9407e008840db9c9\"",
                                "#s27150" to "<http://example.org/s8>",
                                "#p27150" to "<http://example.org/str>",
                                "l" to "\"食\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/sha1-02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallSHA256(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s27213",
                                "#p27213",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s27213" to "<http://example.org/s1>",
                                    "#p27213" to "<http://example.org/str>",
                                    "l" to "\"foo\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s27213",
                            "#p27213",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae\"",
                                "#s27213" to "<http://example.org/s1>",
                                "#p27213" to "<http://example.org/str>",
                                "l" to "\"foo\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/sha256-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallSHA256(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s27231",
                                "#p27231",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s27231" to "<http://example.org/s1>",
                                    "#p27231" to "<http://example.org/str>",
                                    "l" to "\"foo\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s27231",
                            "#p27231",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"2c26b46b68ffc68ff99b453c1d30413413422d706483bfa0f98a5e886266e7ae\"",
                                "#s27231" to "<http://example.org/s1>",
                                "#p27231" to "<http://example.org/str>",
                                "l" to "\"foo\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/sha256-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallSHA256(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s27294",
                                "#p27294",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s27294" to "<http://example.org/s8>",
                                    "#p27294" to "<http://example.org/str>",
                                    "l" to "\"食\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s27294",
                            "#p27294",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"0fbe868d1df356ca9df7ebff346da3a56280e059a7ea81186ef885b140d254ee\"",
                                "#s27294" to "<http://example.org/s8>",
                                "#p27294" to "<http://example.org/str>",
                                "l" to "\"食\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/sha256-02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("hash"),
                        POPExpression(dictionary, AOPBuildInCallSHA256(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#s27312",
                                "#p27312",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "#s27312" to "<http://example.org/s8>",
                                    "#p27312" to "<http://example.org/str>",
                                    "l" to "\"食\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "hash",
                            "#s27312",
                            "#p27312",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "hash" to "\"0fbe868d1df356ca9df7ebff346da3a56280e059a7ea81186ef885b140d254ee\"",
                                "#s27312" to "<http://example.org/s8>",
                                "#p27312" to "<http://example.org/str>",
                                "l" to "\"食\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/sha256-02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallMINUTES(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27431",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27431" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27431" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27431" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27431" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27431",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"28\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p27431" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"38\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p27431" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"59\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p27431" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p27431" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/minutes-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallMINUTES(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27473",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27473" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27473" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27473" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27473" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27473",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"28\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p27473" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"38\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p27473" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"59\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p27473" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p27473" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/minutes-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallSECONDS(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27563",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27563" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27563" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27563" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27563" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27563",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"1.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/d1>",
                                "#p27563" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/d2>",
                                "#p27563" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"0.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/d3>",
                                "#p27563" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"3.0\"^^<http://www.w3.org/2001/XMLSchema#decimal>",
                                "s" to "<http://example.org/d4>",
                                "#p27563" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/seconds-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallHOURS(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27648",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27648" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27648" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27648" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27648" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27648",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p27648" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"15\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p27648" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"23\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p27648" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p27648" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/hours-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallHOURS(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27690",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27690" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27690" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27690" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27690" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27690",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"11\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p27690" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"15\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p27690" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"23\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p27690" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p27690" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/hours-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallMONTH(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27780",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27780" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27780" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27780" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27780" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27780",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"6\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p27780" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p27780" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"6\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p27780" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p27780" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/month-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallMONTH(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27822",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27822" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27822" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27822" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27822" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27822",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"6\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p27822" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"12\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p27822" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"6\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p27822" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p27822" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/month-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallYEAR(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27912",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27912" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27912" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27912" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27912" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27912",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"2010\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p27912" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2010\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p27912" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2008\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p27912" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2011\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p27912" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/year-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallYEAR(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p27954",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p27954" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p27954" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p27954" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p27954" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p27954",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"2010\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p27954" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2010\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p27954" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2008\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p27954" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"2011\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p27954" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/year-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallDAY(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p28044",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p28044" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p28044" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p28044" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p28044" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p28044",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"21\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p28044" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"21\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p28044" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"20\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p28044" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p28044" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/day-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallDAY(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p28086",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p28086" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p28086" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p28086" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p28086" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p28086",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"21\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d1>",
                                "#p28086" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"21\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d2>",
                                "#p28086" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"20\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d3>",
                                "#p28086" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "s" to "<http://example.org/d4>",
                                "#p28086" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/day-01.rq */ ,
            /*{
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallTIMEZONE(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p28176",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p28176" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p28176" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p28176" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p28176" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p28176",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"\"PT0S\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>\"",
                                "s" to "<http://example.org/d1>",
                                "#p28176" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"\"-PT8H\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>\"",
                                "s" to "<http://example.org/d2>",
                                "#p28176" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"\"PT0S\"^^<http://www.w3.org/2001/XMLSchema#dayTimeDuration>\"",
                                "s" to "<http://example.org/d3>",
                                "#p28176" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"\"",
                                "s" to "<http://example.org/d4>",
                                "#p28176" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }()*/ /* resources/sparql11-test-suite/functions/timezone-01.rq */
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallTZ(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p28261",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p28261" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p28261" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p28261" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p28261" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p28261",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"Z\"",
                                "s" to "<http://example.org/d1>",
                                "#p28261" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"-08:00\"",
                                "s" to "<http://example.org/d2>",
                                "#p28261" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"Z\"",
                                "s" to "<http://example.org/d3>",
                                "#p28261" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"\"",
                                "s" to "<http://example.org/d4>",
                                "#p28261" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/tz-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("x"),
                        POPExpression(dictionary, AOPBuildInCallTZ(AOPVariable("date"))),
                        POPValues(dictionary, listOf(
                                "s",
                                "#p28303",
                                "date"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/d1>",
                                    "#p28303" to "<http://example.org/date>",
                                    "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d2>",
                                    "#p28303" to "<http://example.org/date>",
                                    "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d3>",
                                    "#p28303" to "<http://example.org/date>",
                                    "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/d4>",
                                    "#p28303" to "<http://example.org/date>",
                                    "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "x",
                            "s",
                            "#p28303",
                            "date"
                        ), listOf(
                            mutableMapOf(
                                "x" to "\"Z\"",
                                "s" to "<http://example.org/d1>",
                                "#p28303" to "<http://example.org/date>",
                                "date" to "\"2010-06-21T11:28:01Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"-08:00\"",
                                "s" to "<http://example.org/d2>",
                                "#p28303" to "<http://example.org/date>",
                                "date" to "\"2010-12-21T15:38:02-08:00\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"Z\"",
                                "s" to "<http://example.org/d3>",
                                "#p28303" to "<http://example.org/date>",
                                "date" to "\"2008-06-20T23:59:00Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            ),
                            mutableMapOf(
                                "x" to "\"\"",
                                "s" to "<http://example.org/d4>",
                                "#p28303" to "<http://example.org/date>",
                                "date" to "\"2011-02-01T01:02:03\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/tz-01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("b2"),
                        POPExpression(dictionary, AOPBuildInCallBNODE1(AOPVariable("s2"))),
                        POPValues(dictionary, listOf(
                                "a",
                                "#p28458",
                                "s1",
                                "b",
                                "#p28459",
                                "s2"
                            ), listOf(
                                mutableMapOf(
                                    "a" to "<http://example.org/s1>",
                                    "#p28458" to "<http://example.org/str>",
                                    "s1" to "\"foo\"",
                                    "b" to "<http://example.org/s1>",
                                    "#p28459" to "<http://example.org/str>",
                                    "s2" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "a" to "<http://example.org/s3>",
                                    "#p28458" to "<http://example.org/str>",
                                    "s1" to "\"BAZ\"",
                                    "b" to "<http://example.org/s1>",
                                    "#p28459" to "<http://example.org/str>",
                                    "s2" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "a" to "<http://example.org/s1>",
                                    "#p28458" to "<http://example.org/str>",
                                    "s1" to "\"foo\"",
                                    "b" to "<http://example.org/s3>",
                                    "#p28459" to "<http://example.org/str>",
                                    "s2" to "\"BAZ\""
                                ),
                                mutableMapOf(
                                    "a" to "<http://example.org/s3>",
                                    "#p28458" to "<http://example.org/str>",
                                    "s1" to "\"BAZ\"",
                                    "b" to "<http://example.org/s3>",
                                    "#p28459" to "<http://example.org/str>",
                                    "s2" to "\"BAZ\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "b2",
                            "a",
                            "#p28458",
                            "s1",
                            "b",
                            "#p28459",
                            "s2"
                        ), listOf(
                            mutableMapOf(
                                "b2" to "_:28413\"foo\"",
                                "a" to "<http://example.org/s1>",
                                "#p28458" to "<http://example.org/str>",
                                "s1" to "\"foo\"",
                                "b" to "<http://example.org/s1>",
                                "#p28459" to "<http://example.org/str>",
                                "s2" to "\"foo\""
                            ),
                            mutableMapOf(
                                "b2" to "_:28413\"foo\"",
                                "a" to "<http://example.org/s3>",
                                "#p28458" to "<http://example.org/str>",
                                "s1" to "\"BAZ\"",
                                "b" to "<http://example.org/s1>",
                                "#p28459" to "<http://example.org/str>",
                                "s2" to "\"foo\""
                            ),
                            mutableMapOf(
                                "b2" to "_:28413\"BAZ\"",
                                "a" to "<http://example.org/s1>",
                                "#p28458" to "<http://example.org/str>",
                                "s1" to "\"foo\"",
                                "b" to "<http://example.org/s3>",
                                "#p28459" to "<http://example.org/str>",
                                "s2" to "\"BAZ\""
                            ),
                            mutableMapOf(
                                "b2" to "_:28413\"BAZ\"",
                                "a" to "<http://example.org/s3>",
                                "#p28458" to "<http://example.org/str>",
                                "s1" to "\"BAZ\"",
                                "b" to "<http://example.org/s3>",
                                "#p28459" to "<http://example.org/str>",
                                "s2" to "\"BAZ\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/bnode01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("b1"),
                        POPExpression(dictionary, AOPBuildInCallBNODE1(AOPVariable("s1"))),
                        POPValues(dictionary, listOf(
                                "b2",
                                "a",
                                "#p28458",
                                "s1",
                                "b",
                                "#p28459",
                                "s2"
                            ), listOf(
                                mutableMapOf(
                                    "b2" to "_:28413\"foo\"",
                                    "a" to "<http://example.org/s1>",
                                    "#p28458" to "<http://example.org/str>",
                                    "s1" to "\"foo\"",
                                    "b" to "<http://example.org/s1>",
                                    "#p28459" to "<http://example.org/str>",
                                    "s2" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "b2" to "_:28413\"foo\"",
                                    "a" to "<http://example.org/s3>",
                                    "#p28458" to "<http://example.org/str>",
                                    "s1" to "\"BAZ\"",
                                    "b" to "<http://example.org/s1>",
                                    "#p28459" to "<http://example.org/str>",
                                    "s2" to "\"foo\""
                                ),
                                mutableMapOf(
                                    "b2" to "_:28413\"BAZ\"",
                                    "a" to "<http://example.org/s1>",
                                    "#p28458" to "<http://example.org/str>",
                                    "s1" to "\"foo\"",
                                    "b" to "<http://example.org/s3>",
                                    "#p28459" to "<http://example.org/str>",
                                    "s2" to "\"BAZ\""
                                ),
                                mutableMapOf(
                                    "b2" to "_:28413\"BAZ\"",
                                    "a" to "<http://example.org/s3>",
                                    "#p28458" to "<http://example.org/str>",
                                    "s1" to "\"BAZ\"",
                                    "b" to "<http://example.org/s3>",
                                    "#p28459" to "<http://example.org/str>",
                                    "s2" to "\"BAZ\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "b1",
                            "b2",
                            "a",
                            "#p28458",
                            "s1",
                            "b",
                            "#p28459",
                            "s2"
                        ), listOf(
                            mutableMapOf(
                                "b1" to "_:28407\"foo\"",
                                "b2" to "_:28413\"foo\"",
                                "a" to "<http://example.org/s1>",
                                "#p28458" to "<http://example.org/str>",
                                "s1" to "\"foo\"",
                                "b" to "<http://example.org/s1>",
                                "#p28459" to "<http://example.org/str>",
                                "s2" to "\"foo\""
                            ),
                            mutableMapOf(
                                "b1" to "_:28407\"BAZ\"",
                                "b2" to "_:28413\"foo\"",
                                "a" to "<http://example.org/s3>",
                                "#p28458" to "<http://example.org/str>",
                                "s1" to "\"BAZ\"",
                                "b" to "<http://example.org/s1>",
                                "#p28459" to "<http://example.org/str>",
                                "s2" to "\"foo\""
                            ),
                            mutableMapOf(
                                "b1" to "_:28407\"foo\"",
                                "b2" to "_:28413\"BAZ\"",
                                "a" to "<http://example.org/s1>",
                                "#p28458" to "<http://example.org/str>",
                                "s1" to "\"foo\"",
                                "b" to "<http://example.org/s3>",
                                "#p28459" to "<http://example.org/str>",
                                "s2" to "\"BAZ\""
                            ),
                            mutableMapOf(
                                "b1" to "_:28407\"BAZ\"",
                                "b2" to "_:28413\"BAZ\"",
                                "a" to "<http://example.org/s3>",
                                "#p28458" to "<http://example.org/str>",
                                "s1" to "\"BAZ\"",
                                "b" to "<http://example.org/s3>",
                                "#p28459" to "<http://example.org/str>",
                                "s2" to "\"BAZ\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/bnode01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("b2"),
                        POPExpression(dictionary, AOPBuildInCallBNODE0()),
                        POPValues(dictionary, listOf(
                            ), listOf(
                                mutableMapOf(
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "b2"
                        ), listOf(
                            mutableMapOf(
                                "b2" to "_:3121931244"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/bnode02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("b1"),
                        POPExpression(dictionary, AOPBuildInCallBNODE0()),
                        POPValues(dictionary, listOf(
                                "b2"
                            ), listOf(
                                mutableMapOf(
                                    "b2" to "_:3121931244"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "b1",
                            "b2"
                        ), listOf(
                            mutableMapOf(
                                "b1" to "_:3121431246",
                                "b2" to "_:3121931244"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/bnode02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("b2"),
                        POPExpression(dictionary, AOPBuildInCallBNODE0()),
                        POPValues(dictionary, listOf(
                            ), listOf(
                                mutableMapOf(
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "b2"
                        ), listOf(
                            mutableMapOf(
                                "b2" to "_:3125031268"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/bnode02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("b1"),
                        POPExpression(dictionary, AOPBuildInCallBNODE0()),
                        POPValues(dictionary, listOf(
                                "b2"
                            ), listOf(
                                mutableMapOf(
                                    "b2" to "_:3125031268"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "b1",
                            "b2"
                        ), listOf(
                            mutableMapOf(
                                "b1" to "_:3125731270",
                                "b2" to "_:3125031268"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/bnode02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("n"),
                        POPExpression(dictionary, AOPDateTime("\"2020-02-24T17:30:04Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>")),
                        POPValues(dictionary, listOf(
                            ), listOf(
                                mutableMapOf(
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "n"
                        ), listOf(
                            mutableMapOf(
                                "n" to "\"2020-02-24T17:30:04Z\"^^<http://www.w3.org/2001/XMLSchema#dateTime>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/now01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("iri"),
                        POPExpression(dictionary, AOPBuildInCallIRI(AOPSimpleLiteral("\"", "iri"), "http://example.org/")),
                        POPValues(dictionary, listOf(
                            ), listOf(
                                mutableMapOf(
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "iri"
                        ), listOf(
                            mutableMapOf(
                                "iri" to "<http://example.org/iri>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/iri01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("uri"),
                        POPExpression(dictionary, AOPBuildInCallURI(AOPSimpleLiteral("\"", "uri"), "http://example.org/")),
                        POPValues(dictionary, listOf(
                                "iri"
                            ), listOf(
                                mutableMapOf(
                                    "iri" to "<http://example.org/iri>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "uri",
                            "iri"
                        ), listOf(
                            mutableMapOf(
                                "uri" to "<http://example.org/uri>",
                                "iri" to "<http://example.org/iri>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/iri01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("integer"),
                        POPExpression(dictionary, AOPBuildInCallIF(AOPEQ(AOPBuildInCallLANG(AOPVariable("o")), AOPSimpleLiteral("\"", "ja")), AOPBoolean(true), AOPBoolean(false))),
                        POPValues(dictionary, listOf(
                                "s",
                                "p",
                                "o"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://example.org/s1>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"123\""
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s2>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"日本語\"@ja"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s3>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"english\"@en"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s4>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"français\"@fr"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s5>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s6>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                                ),
                                mutableMapOf(
                                    "s" to "<http://example.org/s7>",
                                    "p" to "<http://example.org/str>",
                                    "o" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "integer",
                            "s",
                            "p",
                            "o"
                        ), listOf(
                            mutableMapOf(
                                "integer" to "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "s" to "<http://example.org/s1>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"123\""
                            ),
                            mutableMapOf(
                                "integer" to "\"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "s" to "<http://example.org/s2>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"日本語\"@ja"
                            ),
                            mutableMapOf(
                                "integer" to "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "s" to "<http://example.org/s3>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"english\"@en"
                            ),
                            mutableMapOf(
                                "integer" to "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "s" to "<http://example.org/s4>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"français\"@fr"
                            ),
                            mutableMapOf(
                                "integer" to "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "s" to "<http://example.org/s5>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"abc\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "integer" to "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "s" to "<http://example.org/s6>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"def\"^^<http://www.w3.org/2001/XMLSchema#string>"
                            ),
                            mutableMapOf(
                                "integer" to "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "s" to "<http://example.org/s7>",
                                "p" to "<http://example.org/str>",
                                "o" to "\"7\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/if01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("error"),
                        POPExpression(dictionary, AOPBuildInCallIF(AOPDivision(AOPInteger(0), AOPInteger(1)), AOPBoolean(false), AOPBoolean(true))),
                        POPValues(dictionary, listOf(
                            ), listOf(
                                mutableMapOf(
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "error"
                        ), listOf(
                            mutableMapOf(
                                "error" to null
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/functions/if02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("eq"),
                        POPExpression(dictionary, AOPEQ(AOPVariable("y"), AOPVariable("z"))),
                        POPValues(dictionary, listOf(
                                "#p34861",
                                "y",
                                "#p34862",
                                "z",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "#p34861" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p34862" to "<http://www.example.org/schema#q>",
                                    "z" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                ),
                                mutableMapOf(
                                    "#p34861" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p34862" to "<http://www.example.org/schema#q>",
                                    "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "eq",
                            "#p34861",
                            "y",
                            "#p34862",
                            "z",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "eq" to "\"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "#p34861" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p34862" to "<http://www.example.org/schema#q>",
                                "z" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            ),
                            mutableMapOf(
                                "eq" to "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "#p34861" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p34862" to "<http://www.example.org/schema#q>",
                                "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("eq"),
                        POPExpression(dictionary, AOPEQ(AOPVariable("y"), AOPVariable("z"))),
                        POPValues(dictionary, listOf(
                                "#p34900",
                                "y",
                                "#p34901",
                                "z",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "#p34900" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p34901" to "<http://www.example.org/schema#q>",
                                    "z" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                ),
                                mutableMapOf(
                                    "#p34900" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p34901" to "<http://www.example.org/schema#q>",
                                    "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "eq",
                            "#p34900",
                            "y",
                            "#p34901",
                            "z",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "eq" to "\"true\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "#p34900" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p34901" to "<http://www.example.org/schema#q>",
                                "z" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            ),
                            mutableMapOf(
                                "eq" to "\"false\"^^<http://www.w3.org/2001/XMLSchema#boolean>",
                                "#p34900" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p34901" to "<http://www.example.org/schema#q>",
                                "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp01.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("sum"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("z"), AOPVariable("y"))),
                        POPValues(dictionary, listOf(
                                "#p35010",
                                "y",
                                "#p35011",
                                "z",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "#p35010" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35011" to "<http://www.example.org/schema#q>",
                                    "z" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                ),
                                mutableMapOf(
                                    "#p35010" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35011" to "<http://www.example.org/schema#q>",
                                    "z" to "\"foobar\"",
                                    "x" to "<http://www.example.org/instance#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "sum",
                            "#p35010",
                            "y",
                            "#p35011",
                            "z",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "sum" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35010" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35011" to "<http://www.example.org/schema#q>",
                                "z" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p35010" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35011" to "<http://www.example.org/schema#q>",
                                "z" to "\"foobar\"",
                                "x" to "<http://www.example.org/instance#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("sum"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("z"), AOPVariable("y"))),
                        POPValues(dictionary, listOf(
                                "#p35038",
                                "y",
                                "#p35039",
                                "z",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "#p35038" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35039" to "<http://www.example.org/schema#q>",
                                    "z" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                ),
                                mutableMapOf(
                                    "#p35038" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35039" to "<http://www.example.org/schema#q>",
                                    "z" to "\"foobar\"",
                                    "x" to "<http://www.example.org/instance#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "sum",
                            "#p35038",
                            "y",
                            "#p35039",
                            "z",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "sum" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35038" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35039" to "<http://www.example.org/schema#q>",
                                "z" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            ),
                            mutableMapOf(
                                "sum" to null,
                                "#p35038" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35039" to "<http://www.example.org/schema#q>",
                                "z" to "\"foobar\"",
                                "x" to "<http://www.example.org/instance#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp02.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("sum"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("z"), AOPVariable("y"))),
                        POPValues(dictionary, listOf(
                                "#p35150",
                                "y",
                                "#p35151",
                                "z",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "#p35150" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35151" to "<http://www.example.org/schema#q>",
                                    "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "sum",
                            "#p35150",
                            "y",
                            "#p35151",
                            "z",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "sum" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35150" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35151" to "<http://www.example.org/schema#q>",
                                "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp03.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("twice"),
                        POPExpression(dictionary, AOPMultiplication(AOPVariable("sum"), AOPInteger(2))),
                        POPValues(dictionary, listOf(
                                "sum",
                                "#p35150",
                                "y",
                                "#p35151",
                                "z",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "sum" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35150" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35151" to "<http://www.example.org/schema#q>",
                                    "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "twice",
                            "sum",
                            "#p35150",
                            "y",
                            "#p35151",
                            "z",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "twice" to "\"6\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "sum" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35150" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35151" to "<http://www.example.org/schema#q>",
                                "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp03.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("sum"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("z"), AOPVariable("y"))),
                        POPValues(dictionary, listOf(
                                "#p35195",
                                "y",
                                "#p35196",
                                "z",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "#p35195" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35196" to "<http://www.example.org/schema#q>",
                                    "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "sum",
                            "#p35195",
                            "y",
                            "#p35196",
                            "z",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "sum" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35195" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35196" to "<http://www.example.org/schema#q>",
                                "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp03.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("twice"),
                        POPExpression(dictionary, AOPMultiplication(AOPVariable("sum"), AOPInteger(2))),
                        POPValues(dictionary, listOf(
                                "sum",
                                "#p35195",
                                "y",
                                "#p35196",
                                "z",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "sum" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35195" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35196" to "<http://www.example.org/schema#q>",
                                    "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "twice",
                            "sum",
                            "#p35195",
                            "y",
                            "#p35196",
                            "z",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "twice" to "\"6\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "sum" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35195" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35196" to "<http://www.example.org/schema#q>",
                                "z" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp03.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("sum"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("y"), AOPVariable("y"))),
                        POPValues(dictionary, listOf(
                                "x",
                                "#p35308",
                                "y"
                            ), listOf(
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35308" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35308" to "<http://www.example.org/schema#p>",
                                    "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "sum",
                            "x",
                            "#p35308",
                            "y"
                        ), listOf(
                            mutableMapOf(
                                "sum" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35308" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "sum" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35308" to "<http://www.example.org/schema#p>",
                                "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp04.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("sum"),
                        POPExpression(dictionary, AOPAddition(AOPVariable("y"), AOPVariable("y"))),
                        POPValues(dictionary, listOf(
                                "x",
                                "#p35338",
                                "y"
                            ), listOf(
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35338" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35338" to "<http://www.example.org/schema#p>",
                                    "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "sum",
                            "x",
                            "#p35338",
                            "y"
                        ), listOf(
                            mutableMapOf(
                                "sum" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35338" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "sum" to "\"4\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35338" to "<http://www.example.org/schema#p>",
                                "y" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp04.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("dt"),
                        POPExpression(dictionary, AOPBuildInCallDATATYPE(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "x",
                                "#p35424",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35424" to "<http://www.example.org/schema#p>",
                                    "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35424" to "<http://www.example.org/schema#p>",
                                    "l" to "<http://www.example.org/schema#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "dt",
                            "x",
                            "#p35424",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "dt" to "<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35424" to "<http://www.example.org/schema#p>",
                                "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "dt" to null,
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35424" to "<http://www.example.org/schema#p>",
                                "l" to "<http://www.example.org/schema#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp05.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("dt"),
                        POPExpression(dictionary, AOPBuildInCallDATATYPE(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "x",
                                "#p35449",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35449" to "<http://www.example.org/schema#p>",
                                    "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                ),
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35449" to "<http://www.example.org/schema#p>",
                                    "l" to "<http://www.example.org/schema#a>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "dt",
                            "x",
                            "#p35449",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "dt" to "<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35449" to "<http://www.example.org/schema#p>",
                                "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            ),
                            mutableMapOf(
                                "dt" to null,
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35449" to "<http://www.example.org/schema#p>",
                                "l" to "<http://www.example.org/schema#a>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp05.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("m"),
                        POPExpression(dictionary, AOPUndef()),
                        POPValues(dictionary, listOf(
                                "x",
                                "#p35534",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35534" to "<http://www.example.org/schema#p>",
                                    "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "m",
                            "x",
                            "#p35534",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "m" to null,
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35534" to "<http://www.example.org/schema#p>",
                                "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp06.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("dt"),
                        POPExpression(dictionary, AOPBuildInCallDATATYPE(AOPVariable("m"))),
                        POPValues(dictionary, listOf(
                                "m",
                                "x",
                                "#p35534",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "m" to null,
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35534" to "<http://www.example.org/schema#p>",
                                    "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "dt",
                            "m",
                            "x",
                            "#p35534",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "dt" to null,
                                "m" to null,
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35534" to "<http://www.example.org/schema#p>",
                                "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp06.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("m"),
                        POPExpression(dictionary, AOPUndef()),
                        POPValues(dictionary, listOf(
                                "x",
                                "#p35558",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35558" to "<http://www.example.org/schema#p>",
                                    "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "m",
                            "x",
                            "#p35558",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "m" to null,
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35558" to "<http://www.example.org/schema#p>",
                                "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp06.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("dt"),
                        POPExpression(dictionary, AOPBuildInCallDATATYPE(AOPVariable("m"))),
                        POPValues(dictionary, listOf(
                                "m",
                                "x",
                                "#p35558",
                                "l"
                            ), listOf(
                                mutableMapOf(
                                    "m" to null,
                                    "x" to "<http://www.example.org/instance#a>",
                                    "#p35558" to "<http://www.example.org/schema#p>",
                                    "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "dt",
                            "m",
                            "x",
                            "#p35558",
                            "l"
                        ), listOf(
                            mutableMapOf(
                                "dt" to null,
                                "m" to null,
                                "x" to "<http://www.example.org/instance#a>",
                                "#p35558" to "<http://www.example.org/schema#p>",
                                "l" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp06.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("dt"),
                        POPExpression(dictionary, AOPBuildInCallDATATYPE(AOPVariable("l"))),
                        POPValues(dictionary, listOf(
                                "#p35654",
                                "y",
                                "#p35655",
                                "l",
                                "x"
                            ), listOf(
                                mutableMapOf(
                                    "#p35654" to "<http://www.example.org/schema#p>",
                                    "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35655" to "<http://www.example.org/schema#q>",
                                    "l" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "x" to "<http://www.example.org/instance#a>"
                                ),
                                mutableMapOf(
                                    "#p35654" to "<http://www.example.org/schema#p>",
                                    "y" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                    "#p35655" to null,
                                    "l" to null,
                                    "x" to "<http://www.example.org/instance#b>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "dt",
                            "#p35654",
                            "y",
                            "#p35655",
                            "l",
                            "x"
                        ), listOf(
                            mutableMapOf(
                                "dt" to "<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35654" to "<http://www.example.org/schema#p>",
                                "y" to "\"1\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35655" to "<http://www.example.org/schema#q>",
                                "l" to "\"2\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "x" to "<http://www.example.org/instance#a>"
                            ),
                            mutableMapOf(
                                "dt" to null,
                                "#p35654" to "<http://www.example.org/schema#p>",
                                "y" to "\"3\"^^<http://www.w3.org/2001/XMLSchema#integer>",
                                "#p35655" to null,
                                "l" to null,
                                "x" to "<http://www.example.org/instance#b>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/project-expression/projexp07.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("FullName"),
                        POPExpression(dictionary, AOPBuildInCallCONCAT(AOPBuildInCallCONCAT(AOPVariable("F"), AOPSimpleLiteral("\"", " ")), AOPVariable("L"))),
                        POPValues(dictionary, listOf(
                                "#p37205",
                                "F",
                                "#p37206",
                                "L",
                                "P"
                            ), listOf(
                                mutableMapOf(
                                    "#p37205" to "<http://xmlns.com/foaf/0.1/firstName>",
                                    "F" to "\"John\"",
                                    "#p37206" to "<http://xmlns.com/foaf/0.1/lastName>",
                                    "L" to "\"Doe\"",
                                    "P" to "<http://p1>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "FullName",
                            "#p37205",
                            "F",
                            "#p37206",
                            "L",
                            "P"
                        ), listOf(
                            mutableMapOf(
                                "FullName" to "\"John Doe\"",
                                "#p37205" to "<http://xmlns.com/foaf/0.1/firstName>",
                                "F" to "\"John\"",
                                "#p37206" to "<http://xmlns.com/foaf/0.1/lastName>",
                                "L" to "\"Doe\"",
                                "P" to "<http://p1>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/subquery/sq12.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("P"),
                        POPExpression(dictionary, AOPUndef()),
                        POPValues(dictionary, listOf(
                                "s",
                                "FullName"
                            ), listOf(
                                mutableMapOf(
                                    "s" to "<http://p1>",
                                    "FullName" to "\"John Doe\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "P",
                            "s",
                            "FullName"
                        ), listOf(
                            mutableMapOf(
                                "P" to null,
                                "s" to "<http://p1>",
                                "FullName" to "\"John Doe\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/subquery/sq12.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("p"),
                        POPExpression(dictionary, AOPIri("http://xmlns.com/foaf/0.1/name")),
                        POPValues(dictionary, listOf(
                                "P",
                                "s",
                                "FullName"
                            ), listOf(
                                mutableMapOf(
                                    "P" to null,
                                    "s" to "<http://p1>",
                                    "FullName" to "\"John Doe\""
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "p",
                            "P",
                            "s",
                            "FullName"
                        ), listOf(
                            mutableMapOf(
                                "p" to "<http://xmlns.com/foaf/0.1/name>",
                                "P" to null,
                                "s" to "<http://p1>",
                                "FullName" to "\"John Doe\""
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/subquery/sq12.rq */ ,
            {
                val dictionary=ResultSetDictionary()
                MicroTestPN(
                    POPBind(
                        dictionary,
                        AOPVariable("FullName"),
                        POPExpression(dictionary, AOPBuildInCallCONCAT(AOPBuildInCallCONCAT(AOPVariable("F"), AOPSimpleLiteral("\"", " ")), AOPVariable("L"))),
                        POPValues(dictionary, listOf(
                                "#p37260",
                                "F",
                                "#p37261",
                                "L",
                                "P"
                            ), listOf(
                                mutableMapOf(
                                    "#p37260" to "<http://xmlns.com/foaf/0.1/firstName>",
                                    "F" to "\"John\"",
                                    "#p37261" to "<http://xmlns.com/foaf/0.1/lastName>",
                                    "L" to "\"Doe\"",
                                    "P" to "<http://p1>"
                                )
                            )
                        )
                    ),
                    POPValues(dictionary, listOf(
                            "FullName",
                            "#p37260",
                            "F",
                            "#p37261",
                            "L",
                            "P"
                        ), listOf(
                            mutableMapOf(
                                "FullName" to "\"John Doe\"",
                                "#p37260" to "<http://xmlns.com/foaf/0.1/firstName>",
                                "F" to "\"John\"",
                                "#p37261" to "<http://xmlns.com/foaf/0.1/lastName>",
                                "L" to "\"Doe\"",
                                "P" to "<http://p1>"
                            )
                        )
                    )
                )
            }() /* resources/sparql11-test-suite/subquery/sq12.rq */ ,
            {
                MicroTest0(AOPUndef(), AOPUndef())
            }()
    ).mapIndexed { index, data ->
        DynamicTest.dynamicTest("$index") {
            try {
                if (data.input is AOPBase) {
                    val input = data.input as AOPBase
                    val output: AOPConstant
                    if (data is MicroTestA1) {
                        output = input.calculate(data.resultSet, data.resultRow)
                    } else if (data is MicroTestAN) {
                        setAggregationMode(input, true, data.resultRows.count())
                        for (resultRow in data.resultRows)
                            input.calculate(data.resultSet, resultRow)
                        setAggregationMode(input, false, data.resultRows.count())
                        output = input.calculate(data.resultSet, data.resultSet.createResultRow())
                    } else {
                        val resultSet = ResultSet(ResultSetDictionary())
                        output = input.calculate(resultSet, resultSet.createResultRow())
                    }
                    assertTrue(data.expected is AOPConstant)
                    if (!data.expected.equals(output)) {
                        if (data is MicroTestA1)
                            println(data.resultRow)
                        println(output.valueToString())
                        println((data.expected as AOPConstant).valueToString())
                    }
                    assertTrue(data.expected.equals(output))
                } else if (data.input is POPBase) {
                    val input = data.input as POPBase
                    assertTrue(data.expected is POPValues)
                    val output = QueryResultToXML.toXML(input).first()
                    val expected = QueryResultToXML.toXML(data.expected as POPValues).first()
                    if (!expected.myEquals(output)){
                        println(output.toPrettyString())
                        println(expected.toPrettyString())
                    }
                    assertTrue(expected.myEquals(output))
                }
            } catch (e: Throwable) {
                assertTrue(data.expected is Throwable)
            }
        }
    }
}
