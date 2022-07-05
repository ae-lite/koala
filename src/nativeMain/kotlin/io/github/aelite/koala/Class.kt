package io.github.aelite.koala

class Class(name: String) : InstantiableClass(name) {
    fun construct(vararg parameters: Object): Object {
        val instance = Object(this)
        try {
            return super.invoke("constructor", instance, *parameters)
        } catch (_: Exception) { }
        return instance
    }
}
