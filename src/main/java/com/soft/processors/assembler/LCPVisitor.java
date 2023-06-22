package com.soft.processors.assembler;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * Generated from grammar/LCP.g4 by ANTLR 4.5.3
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LCPParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return type.
 */
public interface LCPVisitor<T> extends ParseTreeVisitor<T> {
    /**
     * Visit a parse tree produced by {@link LCPParser#assemblyProg}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitAssemblyProg(LCPParser.AssemblyProgContext ctx);

    /**
     * Visit a parse tree produced by {@link LCPParser#line}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLine(LCPParser.LineContext ctx);

    /**
     * Visit a parse tree produced by {@link LCPParser#directive}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitDirective(LCPParser.DirectiveContext ctx);

    /**
     * Visit a parse tree produced by the {@code labeledInstr}
     * labeled alternative in {@link LCPParser#labeledInstruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitLabeledInstr(LCPParser.LabeledInstrContext ctx);

    /**
     * Visit a parse tree produced by the {@code instr}
     * labeled alternative in {@link LCPParser#labeledInstruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstr(LCPParser.InstrContext ctx);

    /**
     * Visit a parse tree produced by the {@code instrOnly}
     * labeled alternative in {@link LCPParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstrOnly(LCPParser.InstrOnlyContext ctx);

    /**
     * Visit a parse tree produced by the {@code instrOper}
     * labeled alternative in {@link LCPParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstrOper(LCPParser.InstrOperContext ctx);

    /**
     * Visit a parse tree produced by the {@code instrExpr}
     * labeled alternative in {@link LCPParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstrExpr(LCPParser.InstrExprContext ctx);

    /**
     * Visit a parse tree produced by the {@code instrLabel}
     * labeled alternative in {@link LCPParser#instruction}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitInstrLabel(LCPParser.InstrLabelContext ctx);

    /**
     * Visit a parse tree produced by the {@code operExpr}
     * labeled alternative in {@link LCPParser#oper}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOperExpr(LCPParser.OperExprContext ctx);

    /**
     * Visit a parse tree produced by the {@code operImmExpr}
     * labeled alternative in {@link LCPParser#oper}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitOperImmExpr(LCPParser.OperImmExprContext ctx);

    /**
     * Visit a parse tree produced by the {@code exprIdent}
     * labeled alternative in {@link LCPParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExprIdent(LCPParser.ExprIdentContext ctx);

    /**
     * Visit a parse tree produced by the {@code exprConstant}
     * labeled alternative in {@link LCPParser#expr}.
     *
     * @param ctx the parse tree
     * @return the visitor result
     */
    T visitExprConstant(LCPParser.ExprConstantContext ctx);
}