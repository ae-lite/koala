package io.github.aelite.koala

open class Class {
    val name: String
    private val base: Class?
    private val methods = HashMap<String, AbstractMethod>()

    constructor(name: String): this(name, null)

    constructor(name: String, base: Class?) {
        this.name = name
        this.base = base
    }

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
        else if (this.base != null) return this.base.invoke(name, instance, *args)
        throw Exception("method $name not found")
    }
}
