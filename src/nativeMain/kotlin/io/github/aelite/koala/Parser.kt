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

        return clazzCtxToAst(clazzCtx)
    }

    private fun clazzCtxToAst(clazzCtx: KoalaParser.ClazzContext): Class {
        val clazz = Class(clazzCtx.name!!.text!!)
        clazzCtx.nativeMethods.forEach { nativeMethodCtx -> clazz.registerMethod(nativeMethodCtxToAst(nativeMethodCtx, clazz)) }
        clazzCtx.methods.forEach { methodCtx -> clazz.registerMethod(methodCtxToAst(methodCtx, clazz)) }
        return clazz
    }

    private fun nativeMethodCtxToAst(nativeMethodCtx: KoalaParser.NativeMethodContext, clazz: Class): NativeMethod {
        return NativeMethod(
            nativeMethodCtx.name!!.text!!,
            nativeMethodCtx.parameters.map(this::formalParameterCtxToAst),
            if (nativeMethodCtx.returnType != null) UnresolvedType(nativeMethodCtx.returnType!!.text!!) else clazz
        ) { _, _ -> throw Exception("the implementation of the native method was not yet registered") }
    }

    private fun methodCtxToAst(methodCtx: KoalaParser.MethodContext, clazz: Class): AstMethod {
        return AstMethod(
            methodCtx.name!!.text!!,
            methodCtx.parameters.map(this::formalParameterCtxToAst),
            if (methodCtx.returnType != null) UnresolvedType(methodCtx.returnType!!.text!!) else clazz,
            methodCtx.statements.map { statementCtx -> AstStatementCreator().visit(statementCtx)!! }
        )
    }

    private fun formalParameterCtxToAst(formalParameterCtx: KoalaParser.FormalParameterContext): FormalParameter {
        return FormalParameter(
            formalParameterCtx.name!!.text!!,
            UnresolvedType(formalParameterCtx.type!!.text!!)
        )
    }
}
