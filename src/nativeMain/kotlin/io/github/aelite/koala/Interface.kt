package io.github.aelite.koala

open class Interface(val name: String) {
    private val methods = HashMap<String, Method>()

    fun registerMethod(method: Method): Interface {
        this.methods[method.name] = method
        return this
    }

    fun invoke(name: String, instance: Object, vararg args: Object): Object {
        val method = this.methods[name]
        if (method != null) return method.invoke(instance, *args)
        throw Exception("method $name not found")
    }
}
