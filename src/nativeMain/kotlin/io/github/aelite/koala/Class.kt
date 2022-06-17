package io.github.aelite.koala

class Class(name: String) : InstantiableClass(name) {
    fun construct(vararg args: Object): Object {
        val instance = Object(this)
        try {
            return super.invoke("constructor", instance, *args)
        } catch (_: Exception) { }
        return instance
    }
}
