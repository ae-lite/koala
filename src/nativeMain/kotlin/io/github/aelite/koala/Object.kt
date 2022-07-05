package io.github.aelite.koala

open class Object(private val clazz: InstantiableClass) {
    fun invoke(name: String, vararg parameters: Object): Object {
        return this.clazz.invoke(name, this, *parameters)
    }
}
