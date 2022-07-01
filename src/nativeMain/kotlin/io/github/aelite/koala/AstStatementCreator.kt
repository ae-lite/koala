package io.github.aelite.koala

import io.github.aelite.koala.ast.*
import io.github.aelite.koala.parser.antlr.generated.KoalaBaseVisitor
import io.github.aelite.koala.parser.antlr.generated.KoalaParser

class AstStatementCreator : KoalaBaseVisitor<AstStatement>() {
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
