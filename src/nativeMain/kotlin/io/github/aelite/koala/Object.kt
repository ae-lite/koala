package io.github.aelite.koala

open class Object(private val clazz: InstantiableClass) {
    fun invoke(name: String, vararg args: Object): Object {
        return this.clazz.invoke(name, this, *args)
    }
}
