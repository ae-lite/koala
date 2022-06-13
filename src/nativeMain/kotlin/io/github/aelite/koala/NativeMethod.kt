package io.github.aelite.koala

class NativeMethod(name: String, private val callback: Callable) : AbstractMethod(name) {
    override fun invoke(self: Object, vararg args: Object): Object {
        return this.callback.invoke(self, *args)
    }
}
