package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.models.Instruction;
import com.soft.processors.assembler.models.Mode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class LcpParserVisitorTest {
  private LcpParserVisitor visitor;

  @BeforeEach
  void init() {
    List<Instruction> program = new ArrayList<>();
    var symbolTable = new SymbolTable();
    var config = new Configuration();
    visitor = new LcpParserVisitor(symbolTable, program, config);
  }

  @Test
  void shouldVisitOperatorImmExpr() {
    LcpParser.OperImmExprContext context = Mockito.mock(LcpParser.OperImmExprContext.class);

    visitor.visitOperImmExpr(context);

    assertEquals(Mode.IMMEDIATE, visitor.getInstruction().getMode());
  }

  @Test
  void shouldVisitExpressionConstant() {
    LcpParser.ExprConstantContext context = Mockito.mock(LcpParser.ExprConstantContext.class);
    var terminalNode = Mockito.mock(TerminalNode.class);

    when(context.CONST()).thenReturn(terminalNode);
    when(terminalNode.getText()).thenReturn("10");

    visitor.visitExprConstant(context);

    assertEquals(10, visitor.getInstruction().getOperand());
  }
}
