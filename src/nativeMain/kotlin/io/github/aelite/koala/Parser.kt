package io.github.aelite.koala

import io.github.aelite.koala.parser.antlr.generated.KoalaLexer
import io.github.aelite.koala.parser.antlr.generated.KoalaParser
import org.antlr.v4.kotlinruntime.CharStreams
import org.antlr.v4.kotlinruntime.CommonTokenStream
import org.antlr.v4.kotlinruntime.ConsoleErrorListener

class Parser {
    fun parse(source: String): Interface {
        val lexer = KoalaLexer(CharStreams.fromString(source, "hello.koala"))
        val parser = KoalaParser(CommonTokenStream(lexer))
        parser.addErrorListener(ConsoleErrorListener.INSTANCE)
        val tree = parser.greet()

        return Class("Main")
            .registerMethod(NativeMethod("main") { self, _ ->
                println("hello world!")
                self
            })
    }
}
