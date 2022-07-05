package io.github.aelite.koala.ast

import io.github.aelite.koala.Object
import io.github.aelite.koala.StackFrame

abstract class AstExpression : AstStatement {
    abstract fun evaluate(stackFrame: StackFrame): Object

    override fun execute(stackFrame: StackFrame) {
        this.evaluate(stackFrame)
    }
}
