package io.github.aelite.koala

import okio.BufferedSource

class Parser {
    fun parse(sourceFile: BufferedSource): Class {
        return Class("Main")
            .registerMethod(NativeMethod("main") { self, _ ->
                println("hello world!")
                self
            })
    }
}
