package io.github.aelite.koala.ast

import io.github.aelite.koala.*

class AstMethod(name: String, parameters: List<FormalParameter>, returnType: Class) : Method(name, parameters, returnType) {
    override fun onInvoke(self: Object, parameters: Map<String, Object>): Object {
        val stackFrame = StackFrame()
        // TODO: copy parameters into stackFrame
        TODO("Not yet implemented")
    }
}
