package lupos.s00misc

import lupos.s00misc.Coverage

/* explicitly storing the classname has the advantage, that the classname is accessible in native code too, and not just in java via reflection */
abstract class Luposdate3000Exception(val classname: String, msg: String) : Exception(msg)

//not implemented exceptions --->>>
abstract class NotImplementedException(classname: String, msg: String) : Luposdate3000Exception(classname, msg)
class HistogramNotImplementedException(classname: String) : NotImplementedException("HistogramNotImplementedException", "Histograms not implemented in '$classname'.")
class FileIONotImplementedException() : NotImplementedException("FileIONotImplementedException", "File IO not implemented.")
class ServiceNotImplementedException() : NotImplementedException("ServiceNotImplementedException", "Service is currently not implemented.")
class TripleStoreModifyOperationsNotImplementedException() : NotImplementedException("TripleStoreModifyOperationsNotImplementedException", "Triple store has not implemented Insert and Delete.")
class IteratorBundleColumnModeNotImplementedException() : NotImplementedException("IteratorBundleColumnModeNotImplementedException", "IteratorBundle is unable to convert to column Mode.")
class IteratorBundleRowModeNotImplementedException() : NotImplementedException("IteratorBundleRowModeNotImplementedException", "IteratorBundle is unable to convert to row Mode.")
class SparqlFeatureNotImplementedException(name: String) : NotImplementedException("SparqlFeatureNotImplementedException", "Sparql feature '$name' not implemented.")
class EvaluateNotImplementedException(classname: String) : NotImplementedException("EvaluateNotImplementedException", "Evaluate not implemented in '$classname'.")
class ToSparqlNotImplementedException(classname: String) : NotImplementedException("ToSparqlNotImplementedException", "toSparql not implemented in '$classname'.")

//incompatible implementation exceptions --->>>
class FunktionWontWorkWithThisImplementationException() : Luposdate3000Exception("FunktionWontWorkWithThisImplementationException", "Funktion should not work with this implementation of the interface.")
class DictionaryCanNotInferTypeFromValueException() : Luposdate3000Exception("DictionaryCanNotInferTypeFromValueException", "Dictionary can not infer the type of given Value.")

//syntax exceptions --->>>
abstract class SyntaxException(classname: String, msg: String) : Luposdate3000Exception(classname, msg)
class RecoursiveVariableDefinitionSyntaxException(name: String) : SyntaxException("RecoursiveVariableDefinitionSyntaxException", "Recoursive variable definition not allowed '$name'.")
class ProjectionDoubleDefinitionOfVariableSyntaxException(name: String) : SyntaxException("DoubleDefinitionOfVariableSyntaxException", "Projection must not contain same variable as bind and selection '${name}'.")
class AggregateNotAllowedSyntaxException : SyntaxException("AggregateNotAllowedSyntaxException", "Aggregates are not allowed here.")
class VariableNotDefinedSyntaxException(classname: String, name: String) : SyntaxException("VariableNotDefinedSyntaxException", "Variable '$name' unknown within '$classname'.")
class GroupByClauseNotUsedException() : SyntaxException("GroupByClauseNotUsedException", "expression within group-by-clauses must be bound to a variable.")
class GroupByColumnMissing(name: String) : SyntaxException("GroupByColumnMissing", "Group By requires the column '$name', which does not exist within this GroupBy-Clause.")
class GroupByDuplicateColumnException() : SyntaxException("GroupByDuplicateColumnException", "no duplicate columns allowed in group-by.")
class XMLNotParseableException() : SyntaxException("XMLNotParseableException", "Xml is not parseable.")

//evaluation exceptions --->>>
abstract class EvaluationException(classname: String, msg: String) : Luposdate3000Exception(classname, msg)
class IncompatibleTypesDuringCompareException() : EvaluationException("IncompatibleTypesDuringCompareException", "The provided types are incompatible.")
class CanNotCastBNodeToDoubleException() : EvaluationException("CanNotCastBNodeToDoubleException", "Can not cast BNode to Double.")
class CanNotCastBNodeToDecimalException() : EvaluationException("CanNotCastBNodeToDecimalException", "Can not cast BNode to Decimal.")
class CanNotCastBNodeToIntException() : EvaluationException("CanNotCastBNodeToIntException", "Can not cast BNode to Int.")
class CanNotCastBNodeToBooleanException() : EvaluationException("CanNotCastBNodeToBooleanException", "Can not cast BNode to Boolean.")
class CanNotCastBooleanToDoubleException() : EvaluationException("CanNotCastBooleanToDoubleException", "Can not cast Boolean to Double.")
class CanNotCastBooleanToDecimalException() : EvaluationException("CanNotCastBooleanToDecimalException", "Can not cast Boolean to Decimal.")
class CanNotCastBooleanToIntException() : EvaluationException("CanNotCastBooleanToIntException", "Can not cast Boolean to Int.")
class CanNotCastUndefToDoubleException() : EvaluationException("CanNotCastUndefToDoubleException", "Can not cast Undef to Double.")
class CanNotCastUndefToDecimalException() : EvaluationException("CanNotCastUndefToDecimalException", "Can not cast Undef to Decimal.")
class CanNotCastUndefToIntException() : EvaluationException("CanNotCastUndefToIntException", "Can not cast Undef to Int.")
class CanNotCastUndefToBooleanException() : EvaluationException("CanNotCastUndefToBooleanException", "Can not cast Undef to Boolean.")
class CanNotCastErrorToDoubleException() : EvaluationException("CanNotCastErrorToDoubleException", "Can not cast Error to Double.")
class CanNotCastErrorToDecimalException() : EvaluationException("CanNotCastErrorToDecimalException", "Can not cast Error to Decimal.")
class CanNotCastErrorToIntException() : EvaluationException("CanNotCastErrorToIntException", "Can not cast Error to Int.")
class CanNotCastErrorToBooleanException() : EvaluationException("CanNotCastErrorToBooleanException", "Can not cast Error to Boolean.")
class CanNotCastIriToDoubleException() : EvaluationException("CanNotCastIriToDoubleException", "Can not cast Iri to Double.")
class CanNotCastIriToDecimalException() : EvaluationException("CanNotCastIriToDecimalException", "Can not cast Iri to Decimal.")
class CanNotCastIriToIntException() : EvaluationException("CanNotCastIriToIntException", "Can not cast Iri to Int.")
class CanNotCastIriToBooleanException() : EvaluationException("CanNotCastIriToBooleanException", "Can not cast Iri to Boolean.")
class CanNotCastDateTimeToDoubleException() : EvaluationException("CanNotCastDateTimeToDoubleException", "Can not cast DateTime to Double.")
class CanNotCastDateTimeToDecimalException() : EvaluationException("CanNotCastDateTimeToDecimalException", "Can not cast DateTime to Decimal.")
class CanNotCastDateTimeToIntException() : EvaluationException("CanNotCastDateTimeToIntException", "Can not cast DateTime to Int.")
class CanNotCastDateTimeToBooleanException() : EvaluationException("CanNotCastDateTimeToBooleanException", "Can not cast DateTime to Boolean.")
class CanNotCastLiteralToDoubleException() : EvaluationException("CanNotCastLiteralToDoubleException", "Can not cast Literal to Double.")
class CanNotCastLiteralToDecimalException() : EvaluationException("CanNotCastLiteralToDecimalException", "Can not cast Literal to Decimal.")
class CanNotCastLiteralToIntException() : EvaluationException("CanNotCastLiteralToIntException", "Can not cast Literal to Int.")
class UnknownOperatorTypeInXMLException(type: String) : EvaluationException("UnknownOperatorTypeInXMLException", "Unknown type '$type' during parsing xml file.")
class UnknownDataFile(filename: String) : EvaluationException("UnknownDataFile", "Unknown filetype '$filename' during parsing to xml.")
class EnpointRecievedInvalidPath(path: String) : EvaluationException("EnpointRecievedInvalidPath", "There was a not recognized request with path '$path'.")
class ResourceNotFoundException(resourceName: String) : EvaluationException("ResourceNotFoundException", "File '$resourceName' not found.")
class GraphNameAlreadyExistsDuringCreateException(name: String) : EvaluationException("GraphNameAlreadyExistsDuringCreateException", "The graph '$name' already exists before creation.")
class GraphNameNotExistsDuringDeleteException(name: String) : EvaluationException("GraphNameNotExistsDuringDeleteException", "The graph '$name' did not exist before deletion.")
class GraphNameNotFoundException(name: String) : EvaluationException("GraphNameNotFoundException", "The graph '$name' does not exist.")
class CommuncationUnexpectedHeaderException(header: String) : EvaluationException("CommuncationUnexpectedHeaderException", "Unexpected Message with header '$header' received.")
class UnreachableException : EvaluationException("UnreachableException", "This should be unreachable.")
class CommunicationConnectionClosedException : EvaluationException("CommunicationConnectionClosedException", "Communication channel unexpectedly closed.")
class EmptyResultException : EvaluationException("EmptyResultException", "")

//known bugs --->>>
class BugException(classname: String, bugname: String) : Luposdate3000Exception("BugException", "Class '$classname' has bug '$bugname'.")
//used to indicate, that intentionally every exception is caught here
typealias DontCareWhichException = Throwable

class JenaBugException(bugname: String) : Luposdate3000Exception("JenaBugException", "Jena has bug: '$bugname'")
