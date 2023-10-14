package com.soft.processors.assembler;

import com.soft.processors.assembler.exceptions.ConfigurationException;
import org.antlr.v4.runtime.tree.AbstractParseTreeVisitor;

/**
 * Generated from grammar/LCP.g4 by ANTLR 4.5.3
 * This class provides an empty implementation of {@link LcpVisitor},
 * which can be extended to create a visitor which only needs to handle a subset
 * of the available methods.
 *
 * @param <T> The return type of the visit operation.
 */
public class LcpBaseVisitor<T> extends AbstractParseTreeVisitor<T> implements LcpVisitor<T> {
  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitAssemblyProg(LcpParser.AssemblyProgContext ctx) {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitLine(LcpParser.LineContext ctx) {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitDirective(LcpParser.DirectiveContext ctx) {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitLabeledInstr(LcpParser.LabeledInstrContext ctx) {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitInstr(LcpParser.InstrContext ctx) {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitInstrOnly(LcpParser.InstrOnlyContext ctx) throws ConfigurationException {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitInstrOper(LcpParser.InstrOperContext ctx) throws ConfigurationException {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitInstrExpr(LcpParser.InstrExprContext ctx) throws ConfigurationException {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitInstrLabel(LcpParser.InstrLabelContext ctx) throws ConfigurationException {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitOperExpr(LcpParser.OperExprContext ctx) {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitOperImmExpr(LcpParser.OperImmExprContext ctx) {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitExprIdent(LcpParser.ExprIdentContext ctx) {
    return visitChildren(ctx);
  }

  /**
   * {@inheritDoc}
   *
   * <p>The default implementation returns the result of calling
   * {@link #visitChildren} on {@code ctx}.</p>
   */
  @Override
  public T visitExprConstant(LcpParser.ExprConstantContext ctx) {
    return visitChildren(ctx);
  }
//
//  @Override
//  public T visitNewDirective(LcpParser.NewDirectiveContext ctx) {
//    return visitChildren(ctx);
//  }
//
//  /**
//   * Visits a 'macroDefinition' node in the parse tree.
//   *
//   * @param ctx the 'macroDefinition' context.
//   * @return the result of visiting this node.
//   */
//  @Override
//  public T visitMacroDefinition(LcpParser.MacroDefinitionContext ctx) {
//    return visitChildren(ctx);
//  }
//
//  /**
//   * Visits a 'paramList' node in the parse tree.
//   *
//   * @param ctx the 'paramList' context.
//   * @return the result of visiting this node.
//   */
//  @Override
//  public T visitParamList(LcpParser.ParamListContext ctx) {
//    return visitChildren(ctx);
//  }
}