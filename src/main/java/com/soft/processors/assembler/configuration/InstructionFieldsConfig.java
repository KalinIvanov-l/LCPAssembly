package com.soft.processors.assembler.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents the configuration of the fields in an instruction in an assembler.
 *
 * @author kalin
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InstructionFieldsConfig {
  public int opcodeFieldLength;
  public int addressingModeFieldLength;
  public int operandFieldLength;

  /**
   * Constructor InstructionFieldsConfig.
   *
   * @param opcodeFieldLength         the length of the opcode field
   * @param addressingModeFieldLength the length of the addressing mode field
   * @param operandFieldLength        the length of the operand field
   */
  public InstructionFieldsConfig(int opcodeFieldLength,
                                 int addressingModeFieldLength, int operandFieldLength) {

    this.opcodeFieldLength = opcodeFieldLength;
    this.addressingModeFieldLength = addressingModeFieldLength;
    this.operandFieldLength = operandFieldLength;
  }
}