package io.github.aelite.koala

import okio.BufferedSource

class Parser {
    fun parse(sourceFile: BufferedSource): Class {
        return Class("Main")
            .registerMethod(NativeMethod("main") { _, _ ->
                println("hello world!")
                Class("placeholder").construct()
            })
    }
}
