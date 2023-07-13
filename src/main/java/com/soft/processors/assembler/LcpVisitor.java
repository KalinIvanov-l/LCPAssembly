package com.soft.processors.assembler;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * Generated from grammar/LCP.g4 by ANTLR 4.5.3
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link LcpParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for operations with no return type.
 */
public interface LcpVisitor<T> extends ParseTreeVisitor<T> {
  /**
   * Visit a parse tree produced by {@link LcpParser#assemblyProg}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitAssemblyProg(LcpParser.AssemblyProgContext ctx);

  /**
   * Visit a parse tree produced by {@link LcpParser#line}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitLine(LcpParser.LineContext ctx);

  /**
   * Visit a parse tree produced by {@link LcpParser#directive}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitDirective(LcpParser.DirectiveContext ctx);

  /**
   * Visit a parse tree produced by the {@code labeledInstr}
   * labeled alternative in {@link LcpParser#labeledInstruction}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitLabeledInstr(LcpParser.LabeledInstrContext ctx);

  /**
   * Visit a parse tree produced by the {@code instr}
   * labeled alternative in {@link LcpParser#labeledInstruction}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitInstr(LcpParser.InstrContext ctx);

  /**
   * Visit a parse tree produced by the {@code instrOnly}
   * labeled alternative in {@link LcpParser#instruction}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitInstrOnly(LcpParser.InstrOnlyContext ctx);

  /**
   * Visit a parse tree produced by the {@code instrOper}
   * labeled alternative in {@link LcpParser#instruction}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitInstrOper(LcpParser.InstrOperContext ctx);

  /**
   * Visit a parse tree produced by the {@code instrExpr}
   * labeled alternative in {@link LcpParser#instruction}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitInstrExpr(LcpParser.InstrExprContext ctx);

  /**
   * Visit a parse tree produced by the {@code instrLabel}
   * labeled alternative in {@link LcpParser#instruction}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitInstrLabel(LcpParser.InstrLabelContext ctx);

  /**
   * Visit a parse tree produced by the {@code operExpr}
   * labeled alternative in {@link LcpParser#oper}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitOperExpr(LcpParser.OperExprContext ctx);

  /**
   * Visit a parse tree produced by the {@code operImmExpr}
   * labeled alternative in {@link LcpParser#oper}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitOperImmExpr(LcpParser.OperImmExprContext ctx);

  /**
   * Visit a parse tree produced by the {@code exprIdent}
   * labeled alternative in {@link LcpParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitExprIdent(LcpParser.ExprIdentContext ctx);

  /**
   * Visit a parse tree produced by the {@code exprConstant}
   * labeled alternative in {@link LcpParser#expr}.
   *
   * @param ctx the parse tree
   * @return the visitor result
   */
  T visitExprConstant(LcpParser.ExprConstantContext ctx);
}