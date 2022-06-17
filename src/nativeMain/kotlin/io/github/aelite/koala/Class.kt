package io.github.aelite.koala

class Class(val name: String) {
    private val methods = HashMap<String, AbstractMethod>()

    fun construct(vararg args: Object): Object {
        val instance = Object(this)
        try { this.invoke("constructor", instance, *args) } catch (_: Exception) { }
        return instance
    }

    fun registerMethod(method: AbstractMethod): Class {
        this.methods[method.name] = method
        return this
    }

    fun invoke(name: String, instance: Object, vararg args: Object): Object {
        val method = this.methods[name]
        if (method != null) return method.invoke(instance, *args)
        throw Exception("method $name not found")
    }
}
