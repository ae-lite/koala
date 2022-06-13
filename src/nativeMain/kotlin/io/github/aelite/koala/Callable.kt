package io.github.aelite.koala

fun interface Callable {
    fun invoke(self: Object, vararg args: Object): Object
}
