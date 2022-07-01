package io.github.aelite.koala

import io.github.aelite.koala.ast.AstMethod
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

        val parseCtx = parser.parse()
        val clazzCtx = parseCtx.findClazz()!!

        val clazz = Class(clazzCtx.name!!.text!!)

        clazzCtx.nativeMethods
            .map { nativeMethodCtx -> NativeMethod(
                nativeMethodCtx.name!!.text!!,
                nativeMethodCtx.parameters.map { formalParameterCtx -> FormalParameter(
                    formalParameterCtx.name!!.text!!,
                    UnresolvedType(formalParameterCtx.type!!.text!!)
                ) },
                if (nativeMethodCtx.returnType != null) UnresolvedType(nativeMethodCtx.returnType!!.text!!) else clazz
            ) { _, _ -> throw Exception("the implementation of the native method was not yet registered") } }
            .forEach(clazz::registerMethod)

        val astStatementCreator = AstStatementCreator()
        clazzCtx.methods
            .map {methodCtx -> AstMethod(
                methodCtx.name!!.text!!,
                methodCtx.parameters.map { formalParameterCtx -> FormalParameter(
                    formalParameterCtx.name!!.text!!,
                    UnresolvedType(formalParameterCtx.type!!.text!!)
                ) },
                if (methodCtx.returnType != null) UnresolvedType(methodCtx.returnType!!.text!!) else clazz,
                methodCtx.statements.map { statementCtx -> astStatementCreator.visit(statementCtx)!! }
            )}
            .forEach(clazz::registerMethod)

        return clazz
    }
}
