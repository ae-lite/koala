package io.github.aelite.koala

class StackFrame {
    private val variables = HashMap<String, Object>()

    fun add(name: String, value: Object) {
        this.variables[name] = value
    }

    fun set(name: String, value: Object) {
        this.variables[name] = value
    }

    fun get(name: String): Object {
        return this.variables[name]!!
    }

    fun push() {
        TODO("scopes are not yet implemented")
    }

    fun pop() {
        TODO("scopes are not yet implemented")
    }
}
