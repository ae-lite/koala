package io.github.aelite.koala

abstract class Method(val name: String, val parameters: List<FormalParameter>, val returnType: Class) {
    protected abstract fun onInvoke(self: Object, parameters: Map<String, Object>): Object

    fun invoke(self: Object, vararg parameters: Object): Object {
        val namedParameters = HashMap<String, Object>()
        for ((index, parameter) in parameters.withIndex()) {
            namedParameters[this.parameters[index].name] = parameter
        }
        return this.onInvoke(self, namedParameters)
    }
}
