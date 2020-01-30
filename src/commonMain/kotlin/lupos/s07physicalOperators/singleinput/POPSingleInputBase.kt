package lupos.s07physicalOperators.singleinput

import lupos.s07physicalOperators.POPBase
import lupos.s07physicalOperators.singleinput.modifiers.POPDistinct
import lupos.s07physicalOperators.singleinput.POPBind
import lupos.s07physicalOperators.singleinput.POPBindUndefined
import lupos.s07physicalOperators.singleinput.POPFilter
import lupos.s07physicalOperators.singleinput.POPFilterExact
import lupos.s07physicalOperators.singleinput.POPGroup
import lupos.s07physicalOperators.singleinput.POPMakeBooleanResult
import lupos.s07physicalOperators.singleinput.POPProjection
import lupos.s07physicalOperators.singleinput.POPRename


abstract class POPSingleInputBase(val child: POPBase) : POPBase() {
}
