package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import java.util.ArrayList;
import java.util.List;

import com.soft.processors.assembler.models.AddressMode;
import com.soft.processors.assembler.models.Instruction;
import lombok.Getter;
import lombok.Setter;

/**
 * The type LCP parser visitor.
 */
@Getter
@Setter
public class LcpParserVisitor extends LcpBaseVisitor<Instruction> {
  private final SymbolTable symbolTable;
  private final ArrayList<Instruction> program;
  private Instruction instr = new Instruction();
  private final Configuration config;
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
    this.program = (ArrayList<Instruction>) program;
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
    String ident;
    String constant;

    if (LcpParser.pass == 1 && (ctx.IDENT() != null)) {
      ident = ctx.IDENT().getText().trim();
      int value;
      if (ctx.CONST() != null) {
        constant = ctx.CONST().getText().trim().toUpperCase();
        if (constant.endsWith("H")) {
          value = Integer.parseInt(constant.substring(0, constant.length() - 1), 16);
        } else {
          value = Integer.parseInt(constant);
        }
        symbolTable.addSymbol(ident, value);
      }

    }
    return null;
  }

  @Override
  public Instruction visitLabeledInstr(LcpParser.LabeledInstrContext ctx) {
    String label;

    if (LcpParser.pass == 1) {
      if (ctx.LABEL() != null) {
        label = ctx.LABEL().getText().trim();
      } else {
        return null;
      }
      symbolTable.addSymbol(label, programCounter);
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
  public Instruction visitInstrOnly(LcpParser.InstrOnlyContext ctx) {
    String mnemocode = ctx.INSTR().getText().trim();
    InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);
    instr.setMode(AddressMode.Mode.DEFAULT);
    if (instructionConfig != null) {
      instr.setOpcode(instructionConfig.getOpcode());
      instr.setOpcodeStr(instructionConfig.getMnemocode());
    } else {
      instr.setOpcode(0);
      instr.setOpcodeStr(mnemocode);
    }
    return super.visitInstrOnly(ctx);
  }

  @Override
  public Instruction visitInstrOper(LcpParser.InstrOperContext ctx) {
    return processInstruction(ctx.INSTR().getText().trim());
  }

  @Override
  public Instruction visitInstrExpr(LcpParser.InstrExprContext ctx) {
    return processInstruction(ctx.INSTR().getText().trim());
  }

  private Instruction processInstruction(String mnemocode) {
    InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);
    if (instructionConfig != null) {
      instr.setOpcode(instructionConfig.getOpcode());
      instr.setOpcodeStr(instructionConfig.getMnemocode());
    } else {
      instr.setOpcode(0);
      instr.setOpcodeStr(mnemocode);
    }
    return instr;
  }

  @Override
  public Instruction visitInstrLabel(LcpParser.InstrLabelContext ctx) {
    if (LcpParser.pass == 2) {
      String mnemocode = ctx.INSTR().getText().trim();
      InstructionConfig instructionConfig = config.getInstructionConfig(mnemocode);
      instr.setMode(AddressMode.Mode.ABSOLUTE);
      if (instructionConfig != null) {
        instr.setOpcode(instructionConfig.getOpcode());
        instr.setOpcodeStr(instructionConfig.getMnemocode());
        instr.setOperand(symbolTable.getSymbolValue(ctx.LABEL().getText().trim()));
        instr.setOperandStr(ctx.LABEL().getText().trim());
      } else {
        instr.setOpcode(0);
        instr.setOpcodeStr(mnemocode);
      }
    }
    return super.visitInstrLabel(ctx);
  }

  @Override
  public Instruction visitOperExpr(LcpParser.OperExprContext ctx) {
    instr.setMode(AddressMode.Mode.ABSOLUTE);
    return super.visitOperExpr(ctx);
  }

  @Override
  public Instruction visitOperImmExpr(LcpParser.OperImmExprContext ctx) {
    instr.setMode(AddressMode.Mode.IMMEDIATE);
    return super.visitOperImmExpr(ctx);
  }

  @Override
  public Instruction visitExprIdent(LcpParser.ExprIdentContext ctx) {
    if (LcpParser.pass == 2) {
      instr.setOperand(symbolTable.getSymbolValue(ctx.IDENT().getText().trim()));
      instr.setOperandStr(ctx.IDENT().getText().trim());
    }
    return super.visitExprIdent(ctx);
  }

  @Override
  public Instruction visitExprConstant(LcpParser.ExprConstantContext ctx) {
    String constant = ctx.CONST().getText().trim().toUpperCase();
    int value;
    if (constant.endsWith("H")) {
      value = Integer.parseInt(constant.substring(0, constant.length() - 1), 16);
    } else {
      value = Integer.parseInt(constant);
    }
    instr.setOperand(value);
    instr.setOperandStr(String.format("%1$02Xh", value));
    return super.visitExprConstant(ctx);
  }
}
