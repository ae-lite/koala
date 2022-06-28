package io.github.aelite.koala.ast

import io.github.aelite.koala.Object
import io.github.aelite.koala.StackFrame
import io.github.aelite.koala.stdlib.lang.String

class AstStringLiteralExpression(val value: String) : AstExpression() {
    override fun evaluate(stackFrame: StackFrame): Object {
        return String.construct(this.value)
    }
}
