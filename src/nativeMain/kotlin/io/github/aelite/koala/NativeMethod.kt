package io.github.aelite.koala

class NativeMethod(name: String, parameters: List<FormalParameter>, returnType: Class, private val callback: Callable) : Method(name, parameters, returnType) {
    fun interface Callable {
        fun onInvoke(self: Object, parameters: Map<String, Object>): Object
    }

    override fun onInvoke(self: Object, parameters: Map<String, Object>): Object {
        return this.callback.onInvoke(self, parameters)
    }
}
