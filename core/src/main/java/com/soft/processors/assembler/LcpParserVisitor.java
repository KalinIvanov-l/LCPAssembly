package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import com.soft.processors.assembler.exceptions.ConfigurationException;
import com.soft.processors.assembler.models.Instruction;
import com.soft.processors.assembler.models.Mode;
import java.util.List;
import java.util.Optional;
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
  private Instruction instruction = new Instruction();
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
    Optional.ofNullable(ctx)
            .filter(c -> LcpParser.pass != 1)
            .filter(c -> c.IDENT() != null && c.CONST() != null)
            .ifPresent(c -> {
              var ident = c.IDENT().getText().trim();
              int value;
              try {
                value = parseConstant(c.CONST().getText().trim());
              } catch (ConfigurationException e) {
                throw new RuntimeException(e);
              }
              symbolTable.addSymbol(ident, value);
            });
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
    program.add(new Instruction(instruction.getOpcode(),
            instruction.getOperand(),
            instruction.getMode(),
            instruction.getOpcodeStr(),
            instruction.getOperandStr()));
    programCounter++;
    return instruction;
  }

  @Override
  public Instruction visitInstrOnly(LcpParser.InstrOnlyContext ctx) throws ConfigurationException {
    var mnemocode = ctx.INSTR().getText().trim();
    InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);
    instruction.setMode(Mode.DEFAULT);
    instruction.setOpcode(instructionConfig != null ? instructionConfig.getOpcode() : 0);
    instruction.setOpcodeStr(instructionConfig != null
            ? instructionConfig.getMnemocode() : mnemocode);
    return super.visitInstrOnly(ctx);
  }

  @Override
  public Instruction visitInstrOper(LcpParser.InstrOperContext ctx) throws ConfigurationException {
    initInstrOperandContext(ctx);
    return super.visitInstrOper(ctx);
  }

  @Override
  public Instruction visitInstrExpr(LcpParser.InstrExprContext ctx) throws ConfigurationException {
    initExpOperandContext(ctx);
    return super.visitInstrExpr(ctx);
  }

  @Override
  public Instruction visitInstrLabel(LcpParser.InstrLabelContext ctx)
          throws ConfigurationException {
    if (LcpParser.pass == 2) {
      var mnemocode = ctx.INSTR().getText().trim();
      InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);

      instruction.setMode(Mode.ABSOLUTE);
      instruction.setOpcode(instructionConfig != null ? instructionConfig.getOpcode() : 0);
      instruction.setOpcodeStr(instructionConfig != null
              ? instructionConfig.getMnemocode() : mnemocode);

      var label = ctx.LABEL().getText().trim();
      instruction.setOperand(symbolTable.getSymbolValue(label));
      instruction.setOperandStr(label);
    }
    return super.visitInstrLabel(ctx);
  }

  @Override
  public Instruction visitOperExpr(LcpParser.OperExprContext ctx) {
    instruction.setMode(Mode.ABSOLUTE);
    return super.visitOperExpr(ctx);
  }

  @Override
  public Instruction visitOperImmExpr(LcpParser.OperImmExprContext ctx) {
    instruction.setMode(Mode.IMMEDIATE);
    return super.visitOperImmExpr(ctx);
  }

  @Override
  public Instruction visitExprIdent(LcpParser.ExprIdentContext ctx) {
    if (LcpParser.pass == 2) {
      var ident = ctx.IDENT().getText().trim();
      instruction.setOperand(symbolTable.getSymbolValue(ident));
      instruction.setOperandStr(ident);
    }
    return super.visitExprIdent(ctx);
  }

  @Override
  public Instruction visitExprConstant(LcpParser.ExprConstantContext ctx) {
    var constant = ctx.CONST().getText().trim().toUpperCase();
    var value = constant.endsWith("H")
            ? Integer.parseInt(constant.substring(0, constant.length() - 1), 16) :
              Integer.parseInt(constant);

    instruction.setOperand(value);
    instruction.setOperandStr(String.format("%1$02Xh", value));
    return super.visitExprConstant(ctx);
  }

  private void setInstructionOpcode(Instruction instruction, String mnemocode)
          throws ConfigurationException {
    InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);
    instruction.setOpcode(instructionConfig != null ? instructionConfig.getOpcode() : 0);
    instruction.setOpcodeStr(instructionConfig != null
            ? instructionConfig.getMnemocode() : mnemocode);
  }

  private void initInstrOperandContext(LcpParser.InstrOperContext ctx)
          throws ConfigurationException {
    var mnemocode = ctx.INSTR().getText().trim();
    setInstructionOpcode(instruction, mnemocode);
  }

  private void initExpOperandContext(LcpParser.InstrExprContext ctx) throws ConfigurationException {
    var mnemocode = ctx.INSTR().getText().trim();
    setInstructionOpcode(instruction, mnemocode);
  }

  private int parseConstant(String constant) throws ConfigurationException {
    return Optional.of(constant.trim().toUpperCase())
            .map(c -> c.endsWith("H") ? Integer.parseInt(c.substring(0, c.length() - 1), 16)
                    : Integer.parseInt(c)).orElseThrow(() ->
                    new ConfigurationException("Invalid constant format: " + constant));
  }
}
