package lupos.s11p2p

import lupos.s00misc.kotlinStacktrace
import lupos.s00misc.parseFromXml
import lupos.s00misc.Trace
import lupos.s00misc.XMLElement
import lupos.s02buildSyntaxTree.LexerCharIterator
import lupos.s02buildSyntaxTree.LookAheadTokenIterator
import lupos.s02buildSyntaxTree.rdf.Dictionary
import lupos.s02buildSyntaxTree.rdf.ID_Triple
import lupos.s02buildSyntaxTree.sparql1_1.SPARQLParser
import lupos.s02buildSyntaxTree.sparql1_1.TokenIteratorSPARQLParser
import lupos.s02buildSyntaxTree.turtle.TurtleParserWithDictionary
import lupos.s02buildSyntaxTree.turtle.TurtleScanner
import lupos.s03buildOperatorGraph.OperatorGraphVisitor
import lupos.s05logicalOptimisation.LogicalOptimizer
import lupos.s06resultRepresentation.ResultRow
import lupos.s06resultRepresentation.ResultSet
import lupos.s06resultRepresentation.Variable
import lupos.s07physicalOperators.*
import lupos.s07physicalOperators.POPBaseNullableIterator
import lupos.s08tripleStore.TripleStore
import lupos.s09physicalOptimisation.PhysicalOptimizer
import lupos.s10outputResult.QueryResultToXML
import lupos.s11p2p.*
import lupos.s13endpoint.*
import lupos.s13endpoint.Endpoint


typealias P2P = P2PLocalDummy
