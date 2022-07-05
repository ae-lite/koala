package io.github.aelite.koala.ast

import io.github.aelite.koala.StackFrame

interface AstStatement {
    fun execute(stackFrame: StackFrame)
}
