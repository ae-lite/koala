package io.github.aelite.koala.ast

import io.github.aelite.koala.*

class AstMethod(name: String, parameters: List<FormalParameter>, returnType: Interface, val statements: List<AstStatement>) : Method(name, parameters, returnType) {
    override fun onInvoke(self: Object, parameters: Map<String, Object>): Object {
        val stackFrame = StackFrame()
        stackFrame.add("this", self)
        parameters.forEach { parameter -> stackFrame.add(parameter.key, parameter.value) }
        this.statements.forEach { statement -> statement.execute(stackFrame) }
        // TODO check for return statement (maybe by catching some "return"-exception)
        return self
    }
}
