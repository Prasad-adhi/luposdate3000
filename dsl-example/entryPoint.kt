package lupos.codegen

fun codeTypes(type: String): CodeType {
    return registeredTypes[type]!!
}

fun codeFile(name: String, pkg: String, init: CodeFile.() -> Unit): CodeFile {
    val file = CodeFile(name, pkg)
    file.init()
    return file
}

fun codeSegment(name: String, init: CodeSegment.() -> Unit): CodeSegment {
    val seg = CodeSegment(null, name)
    seg.init()
    return seg
}

fun codeVar(name: String, type: CodeType): CodeVariableDefinition {
    val v = CodeVariableDefinition(CodeName(name))
    v.type_ = type
    return v
}

fun codeVal(name: String, init: CodeExpressionBuilder.() -> ACodeExpression): CodeConstantDefinition {
    val v = CodeConstantDefinition(CodeName(name), CodeExpressionBuilder().init())
    return v
}
