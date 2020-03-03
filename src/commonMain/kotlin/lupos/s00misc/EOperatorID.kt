package lupos.s00misc

enum class EOperatorID {
    AOPAdditionID,
    AOPAggregationSUMID,
    AOPAggregationMINID,
    AOPAggregationMAXID,
    AOPAggregationAVGID,
    AOPAggregationSAMPLEID,
    AOPAggregationCOUNTID,
    AOPAndID,
    AOPBaseID,
    AOPBinaryOperationFixedNameID,
    AOPBnodeID,
    AOPBooleanID,
    AOPBuildInCallABSID,
    AOPBuildInCallBNODE0ID,
    AOPBuildInCallBNODE1ID,
    AOPBuildInCallBOUNDID,
    AOPBuildInCallCEILID,
    AOPBuildInCallCONCATID,
    AOPBuildInCallCONTAINSID,
    AOPBuildInCallDATATYPEID,
    AOPBuildInCallDAYID,
    AOPBuildInCallFLOORID,
    AOPBuildInCallHOURSID,
    AOPBuildInCallIFID,
    AOPBuildInCallIRIID,
    AOPBuildInCallIsIriID,
    AOPBuildInCallIsLITERALID,
    AOPBuildInCallIsNUMERICID,
    AOPBuildInCallLANGID,
    AOPBuildInCallLANGMATCHESID,
    AOPBuildInCallLCASEID,
    AOPBuildInCallMD5ID,
    AOPBuildInCallMINUTESID,
    AOPBuildInCallMONTHID,
    AOPBuildInCallROUNDID,
    AOPBuildInCallSECONDSID,
    AOPBuildInCallSHA1ID,
    AOPBuildInCallSHA256ID,
    AOPBuildInCallSTRDTID,
    AOPBuildInCallSTRENDSID,
    AOPBuildInCallSTRID,
    AOPBuildInCallSTRLANGID,
    AOPBuildInCallSTRLENID,
    AOPBuildInCallSTRSTARTSID,
    AOPBuildInCallSTRUUIDID,
    AOPBuildInCallTIMEZONEID,
    AOPBuildInCallTZID,
    AOPBuildInCallUCASEID,
    AOPBuildInCallURIID,
    AOPBuildInCallUUIDID,
    AOPBuildInCallYEARID,
    AOPConstantStringID,
    AOPDateTimeID,
    AOPDecimalID,
    AOPDivisionID,
    AOPDoubleID,
    AOPEQID,
    AOPGEQID,
    AOPGTID,
    AOPInID,
    AOPIntegerID,
    AOPIriID,
    AOPLanguageTaggedLiteralID,
    AOPLEQID,
    AOPLTID,
    AOPMultiplicationID,
    AOPNEQID,
    AOPNotID,
    AOPNotInID,
    AOPOrID,
    AOPSetID,
    AOPSimpleLiteralID,
    AOPSubtractionID,
    AOPTypedLiteralID,
    AOPUndefID,
    AOPValueID,
    AOPVariableID,
    LOPBaseID,
    LOPBindID,
    LOPDistinctID,
    LOPFilterID,
    LOPGraphOperationID,
    LOPGroupID,
    LOPJoinID,
    LOPLimitID,
    LOPMakeBooleanResultID,
    LOPModifyDataID,
    LOPModifyID,
    LOPNOOPID,
    LOPOffsetID,
    LOPOptionalID,
    LOPPrefixID,
    LOPProjectionID,
    LOPReducedID,
    LOPRenameID,
    LOPServiceIRIID,
    LOPServiceVARID,
    LOPSortID,
    LOPSubGroupID,
    LOPTripleID,
    LOPUnionID,
    LOPValuesID,
    OPNothingID,
    POPBaseID,
    POPBindID,
    POPDistinctID,
    POPEmptyRowID,
    POPFilterID,
    POPGraphOperationID,
    POPGroupID,
    POPImportFromNetworkPackageID,
    POPImportFromXmlID,
    POPJoinHashMapID,
    POPJoinNestedLoopID,
    POPLimitID,
    POPMakeBooleanResultID,
    POPModifyDataID,
    POPModifyID,
    POPOffsetID,
    POPProjectionID,
    POPRenameID,
    POPServiceIRIID,
    POPSortID,
    POPTemporaryStoreID,
    POPTripleStoreIteratorBaseID,
    POPUnionID,
    POPValuesID,
    TripleInsertIteratorID,
    TripleStoreIteratorGlobalID,
    TripleStoreIteratorLocalFilterID,
    TripleStoreIteratorLocalID
}

val EOperatorIDLOP = arrayOf(
        EOperatorID.LOPBindID,
        EOperatorID.LOPDistinctID,
        EOperatorID.LOPFilterID,
        EOperatorID.LOPGraphOperationID,
        EOperatorID.LOPGroupID,
        EOperatorID.LOPJoinID,
        EOperatorID.LOPLimitID,
        EOperatorID.LOPMakeBooleanResultID,
        EOperatorID.LOPModifyDataID,
        EOperatorID.LOPModifyID,
        EOperatorID.LOPNOOPID,
        EOperatorID.LOPOffsetID,
        EOperatorID.LOPOptionalID,
        EOperatorID.LOPPrefixID,
        EOperatorID.LOPProjectionID,
        EOperatorID.LOPReducedID,
        EOperatorID.LOPRenameID,
        EOperatorID.LOPServiceIRIID,
        EOperatorID.LOPServiceVARID,
        EOperatorID.LOPSortID,
        EOperatorID.LOPSubGroupID,
        EOperatorID.LOPTripleID,
        EOperatorID.LOPUnionID,
        EOperatorID.LOPValuesID
)
val EOperatorIDPOP = arrayOf(
        EOperatorID.POPBindID,
        EOperatorID.POPDistinctID,
        EOperatorID.POPEmptyRowID,
        EOperatorID.POPFilterID,
        EOperatorID.POPGraphOperationID,
        EOperatorID.POPGroupID,
        EOperatorID.POPImportFromNetworkPackageID,
        EOperatorID.POPImportFromXmlID,
        EOperatorID.POPJoinHashMapID,
        EOperatorID.POPJoinNestedLoopID,
        EOperatorID.POPLimitID,
        EOperatorID.POPMakeBooleanResultID,
        EOperatorID.POPModifyDataID,
        EOperatorID.POPModifyID,
        EOperatorID.POPOffsetID,
        EOperatorID.POPProjectionID,
        EOperatorID.POPRenameID,
        EOperatorID.POPServiceIRIID,
        EOperatorID.POPSortID,
        EOperatorID.POPTemporaryStoreID,
        EOperatorID.POPUnionID,
        EOperatorID.POPValuesID,
        EOperatorID.TripleInsertIteratorID,
        EOperatorID.TripleStoreIteratorGlobalID,
        EOperatorID.TripleStoreIteratorLocalFilterID,
        EOperatorID.TripleStoreIteratorLocalID)
val EOperatorIDAOP = arrayOf(EOperatorID.AOPAdditionID,
        EOperatorID.AOPAggregationMINID,
        EOperatorID.AOPAggregationMAXID,
        EOperatorID.AOPAggregationSUMID,
        EOperatorID.AOPAggregationCOUNTID,
        EOperatorID.AOPAggregationAVGID,
        EOperatorID.AOPAggregationSAMPLEID,
        EOperatorID.AOPAndID,
        EOperatorID.AOPBnodeID,
        EOperatorID.AOPBooleanID,
        EOperatorID.AOPBuildInCallABSID,
        EOperatorID.AOPBuildInCallBNODE0ID,
        EOperatorID.AOPBuildInCallBNODE1ID,
        EOperatorID.AOPBuildInCallBOUNDID,
        EOperatorID.AOPBuildInCallCEILID,
        EOperatorID.AOPBuildInCallCONCATID,
        EOperatorID.AOPBuildInCallCONTAINSID,
        EOperatorID.AOPBuildInCallDATATYPEID,
        EOperatorID.AOPBuildInCallDAYID,
        EOperatorID.AOPBuildInCallFLOORID,
        EOperatorID.AOPBuildInCallHOURSID,
        EOperatorID.AOPBuildInCallIFID,
        EOperatorID.AOPBuildInCallIRIID,
        EOperatorID.AOPBuildInCallIsIriID,
        EOperatorID.AOPBuildInCallIsLITERALID,
        EOperatorID.AOPBuildInCallIsNUMERICID,
        EOperatorID.AOPBuildInCallLANGID,
        EOperatorID.AOPBuildInCallLANGMATCHESID,
        EOperatorID.AOPBuildInCallLCASEID,
        EOperatorID.AOPBuildInCallMD5ID,
        EOperatorID.AOPBuildInCallMINUTESID,
        EOperatorID.AOPBuildInCallMONTHID,
        EOperatorID.AOPBuildInCallROUNDID,
        EOperatorID.AOPBuildInCallSECONDSID,
        EOperatorID.AOPBuildInCallSHA1ID,
        EOperatorID.AOPBuildInCallSHA256ID,
        EOperatorID.AOPBuildInCallSTRDTID,
        EOperatorID.AOPBuildInCallSTRENDSID,
        EOperatorID.AOPBuildInCallSTRID,
        EOperatorID.AOPBuildInCallSTRLANGID,
        EOperatorID.AOPBuildInCallSTRLENID,
        EOperatorID.AOPBuildInCallSTRSTARTSID,
        EOperatorID.AOPBuildInCallSTRUUIDID,
        EOperatorID.AOPBuildInCallTIMEZONEID,
        EOperatorID.AOPBuildInCallTZID,
        EOperatorID.AOPBuildInCallUCASEID,
        EOperatorID.AOPBuildInCallURIID,
        EOperatorID.AOPBuildInCallUUIDID,
        EOperatorID.AOPBuildInCallYEARID,
        EOperatorID.AOPDateTimeID,
        EOperatorID.AOPDecimalID,
        EOperatorID.AOPDivisionID,
        EOperatorID.AOPDoubleID,
        EOperatorID.AOPEQID,
        EOperatorID.AOPGEQID,
        EOperatorID.AOPGTID,
        EOperatorID.AOPInID,
        EOperatorID.AOPIntegerID,
        EOperatorID.AOPIriID,
        EOperatorID.AOPLanguageTaggedLiteralID,
        EOperatorID.AOPLEQID,
        EOperatorID.AOPLTID,
        EOperatorID.AOPMultiplicationID,
        EOperatorID.AOPNEQID,
        EOperatorID.AOPNotID,
        EOperatorID.AOPNotInID,
        EOperatorID.AOPOrID,
        EOperatorID.AOPSetID,
        EOperatorID.AOPSimpleLiteralID,
        EOperatorID.AOPSubtractionID,
        EOperatorID.AOPTypedLiteralID,
        EOperatorID.AOPUndefID,
        EOperatorID.AOPValueID,
        EOperatorID.AOPVariableID)
