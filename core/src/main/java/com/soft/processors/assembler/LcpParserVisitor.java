package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import com.soft.processors.assembler.exceptions.ConfigurationException;
import com.soft.processors.assembler.models.Instruction;
import com.soft.processors.assembler.models.Mode;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * The type LCP parser visitor.
 */
@Getter
@Setter
public class LcpParserVisitor extends LcpBaseVisitor<Instruction> {
  private final SymbolTable symbolTable;
  private final List<Instruction> program;
  private final Configuration config;
  private Instruction instr = new Instruction();
  private int programCounter = 0;

  /**
   * Instantiates a new Lcp parser visitor.
   *
   * @param symbolTable the symbol table
   * @param program     the program
   * @param config      the config
   */
  public LcpParserVisitor(
          SymbolTable symbolTable, List<Instruction> program, Configuration config) {

    this.symbolTable = symbolTable;
    this.program = program;
    this.config = config;
  }

  @Override
  public Instruction visitAssemblyProg(LcpParser.AssemblyProgContext ctx) {
    programCounter = 0;
    program.clear();
    return super.visitAssemblyProg(ctx);
  }

  @Override
  public Instruction visitDirective(LcpParser.DirectiveContext ctx) {
    if (LcpParser.pass == 1 && ctx.IDENT() != null && ctx.CONST() != null) {
      var ident = ctx.IDENT().getText().trim();
      var constant = ctx.CONST().getText().trim().toUpperCase();
      var value = constant.endsWith("H")
              ? Integer.parseInt(constant.substring(0, constant.length() - 1), 16)
              : Integer.parseInt(constant);
      symbolTable.addSymbol(ident, value);
    }
    return null;
  }

  @Override
  public Instruction visitLabeledInstr(LcpParser.LabeledInstrContext ctx) {
    if (LcpParser.pass == 1 && ctx.LABEL() != null) {
      var label = ctx.LABEL().getText().trim();
      if (!label.isEmpty()) {
        symbolTable.addSymbol(label, programCounter);
      }
    }
    return super.visitLabeledInstr(ctx);
  }

  @Override
  public Instruction visitInstr(LcpParser.InstrContext ctx) {
    visitChildren(ctx);
    program.add(new Instruction(instr.getOpcode(),
            instr.getOperand(),
            instr.getMode(),
            instr.getOpcodeStr(),
            instr.getOperandStr()));
    programCounter++;
    return instr;
  }

  @Override
  public Instruction visitInstrOnly(LcpParser.InstrOnlyContext ctx) throws ConfigurationException {
    var mnemocode = ctx.INSTR().getText().trim();
    InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);
    instr.setMode(Mode.DEFAULT);
    instr.setOpcode(instructionConfig != null ? instructionConfig.getOpcode() : 0);
    instr.setOpcodeStr(instructionConfig != null ? instructionConfig.getMnemocode() : mnemocode);
    return super.visitInstrOnly(ctx);
  }

  @Override
  public Instruction visitInstrOper(LcpParser.InstrOperContext ctx) throws ConfigurationException {
    return processInstruction(ctx.INSTR().getText().trim());
  }

  @Override
  public Instruction visitInstrExpr(LcpParser.InstrExprContext ctx) throws ConfigurationException {
    return processInstruction(ctx.INSTR().getText().trim());
  }

  private Instruction processInstruction(String mnemocode) throws ConfigurationException {
    InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);
    instr.setOpcode(instructionConfig != null ? instructionConfig.getOpcode() : 0);
    instr.setOpcodeStr(instructionConfig != null ? instructionConfig.getMnemocode() : mnemocode);
    return instr;
  }

  @Override
  public Instruction visitInstrLabel(LcpParser.InstrLabelContext ctx)
          throws ConfigurationException {
    if (LcpParser.pass == 2) {
      String mnemocode = ctx.INSTR().getText().trim();
      InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);

      instr.setMode(Mode.ABSOLUTE);
      instr.setOpcode(instructionConfig != null ? instructionConfig.getOpcode() : 0);
      instr.setOpcodeStr(instructionConfig != null ? instructionConfig.getMnemocode() : mnemocode);

      var label = ctx.LABEL().getText().trim();
      instr.setOperand(symbolTable.getSymbolValue(label));
      instr.setOperandStr(label);
    }
    return super.visitInstrLabel(ctx);
  }

  @Override
  public Instruction visitOperExpr(LcpParser.OperExprContext ctx) {
    instr.setMode(Mode.ABSOLUTE);
    return super.visitOperExpr(ctx);
  }

  @Override
  public Instruction visitOperImmExpr(LcpParser.OperImmExprContext ctx) {
    instr.setMode(Mode.IMMEDIATE);
    return super.visitOperImmExpr(ctx);
  }

  @Override
  public Instruction visitExprIdent(LcpParser.ExprIdentContext ctx) {
    if (LcpParser.pass == 2) {
      var ident = ctx.IDENT().getText().trim();
      instr.setOperand(symbolTable.getSymbolValue(ident));
      instr.setOperandStr(ident);
    }
    return super.visitExprIdent(ctx);
  }

  @Override
  public Instruction visitExprConstant(LcpParser.ExprConstantContext ctx) {
    var constant = ctx.CONST().getText().trim().toUpperCase();
    var value = constant.endsWith("H")
            ? Integer.parseInt(constant.substring(0, constant.length() - 1), 16) :
              Integer.parseInt(constant);

    instr.setOperand(value);
    instr.setOperandStr(String.format("%1$02Xh", value));
    return super.visitExprConstant(ctx);
  }
}
