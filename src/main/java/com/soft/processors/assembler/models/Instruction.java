package com.soft.processors.assembler.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents an instruction in the LCP (Little Computer Processor) architecture.
 * It contains fields for opcode, operand, addressing mode, opcode string, and operand string.
 * The addressing mode is stored as an enum of type Mode,
 * which can have the values DEFAULT, ABSOLUTE, or IMMEDIATE.
 *
 * @author kalin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Instruction {
  private int opcode;
  private int operand;
  private Mode mode;
  private String opcodeStr;
  private String operandStr;
}
