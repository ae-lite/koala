package io.github.aelite.koala.ast

import io.github.aelite.koala.Object
import io.github.aelite.koala.StackFrame

class AstVariableExpression(val name: String) : AstExpression() {
    override fun evaluate(stackFrame: StackFrame): Object {
        return stackFrame.get(name)
    }
}
