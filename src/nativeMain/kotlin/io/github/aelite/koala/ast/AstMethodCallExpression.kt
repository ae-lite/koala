package io.github.aelite.koala.ast

import io.github.aelite.koala.Object
import io.github.aelite.koala.StackFrame

class AstMethodCallExpression(val obj: AstExpression, val name: String, val parameters: List<AstExpression>) : AstExpression() {
    override fun evaluate(stackFrame: StackFrame): Object {
        val obj = this.obj.evaluate(stackFrame)
        val parameters = this.parameters.map {parameter -> parameter.evaluate(stackFrame)}.toTypedArray()
        return obj.invoke(name, *parameters)
    }
}
