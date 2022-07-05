package io.github.aelite.koala

import io.github.aelite.koala.ast.*
import io.github.aelite.koala.parser.antlr.generated.KoalaBaseVisitor
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

        return AstClassCreator().clazzCtxToAst(clazzCtx)
    }

    inner class AstClassCreator {
        fun clazzCtxToAst(clazzCtx: KoalaParser.ClazzContext): Class {
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
                methodCtx.statements.map { statementCtx -> astStatementCreator.visit(statementCtx)!! }
            )
        }

        private fun formalParameterCtxToAst(formalParameterCtx: KoalaParser.FormalParameterContext): FormalParameter {
            return FormalParameter(
                formalParameterCtx.name!!.text!!,
                UnresolvedType(formalParameterCtx.type!!.text!!)
            )
        }
    }

    private val astStatementCreator = object : KoalaBaseVisitor<AstStatement>(){
        override fun visitStringLiteralExpression(ctx: KoalaParser.StringLiteralExpressionContext): AstStatement {
            return AstStringLiteralExpression(ctx.value!!.text!!)
        }

        override fun visitVariableExpression(ctx: KoalaParser.VariableExpressionContext): AstStatement {
            return AstVariableExpression(ctx.name!!.text!!)
        }

        override fun visitMethodCallExpression(ctx: KoalaParser.MethodCallExpressionContext): AstStatement {
            val obj = this.visit(ctx.obj!!)
            val parameters = ctx.parameters.mapNotNull(this::visit)
            return AstMethodCallExpression(obj as AstExpression, ctx.name!!.text!!, parameters as List<AstExpression>)
        }
    }
}
