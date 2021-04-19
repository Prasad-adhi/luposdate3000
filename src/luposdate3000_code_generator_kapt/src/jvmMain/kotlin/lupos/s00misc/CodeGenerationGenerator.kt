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
package lupos.s00misc

import lupos.modulename.MyPrintWriter
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.RoundEnvironment
import javax.annotation.processing.SupportedOptions
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement

@SupportedOptions("kapt.kotlin.generated")
public class CodeGenerationGenerator : AbstractProcessor() {
    init {
        println("CodeGenerationGenerator init")
    }

    override fun getSupportedSourceVersion(): SourceVersion = SourceVersion.latest()
    override fun getSupportedAnnotationTypes(): MutableSet<String> = mutableSetOf(CodeGenerationAnnotation::class.java.name)

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment): Boolean {
        val kaptKotlinGeneratedDir = processingEnv.options["kapt.kotlin.generated"] ?: return false
        roundEnv.getElementsAnnotatedWith(CodeGenerationAnnotation::class.java)
            .forEach { element ->
                try {
                    // input ->
                    val className = element.enclosingElement.simpleName.toString()
                    val packageName = processingEnv.elementUtils.getPackageOf(element).toString()
                    val variableName = element.simpleName.toString()
                    val variableValue = (element as VariableElement).constantValue.toString()
                    // output->
                    val folderName = "$kaptKotlinGeneratedDir/$packageName"
                    val fileName = "$folderName/${className}___$variableName.kt"
                    val buf = MyPrintWriter(true)
                    generateSourceCode(className, packageName, variableName, variableValue, folderName, fileName)
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
        return true
    }
}
