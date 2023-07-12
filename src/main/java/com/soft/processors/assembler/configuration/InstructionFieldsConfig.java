package com.soft.processors.assembler.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents the configuration of the fields in an instruction in an assembler.
 *
 * @author kalin
 */
@Getter
@Setter
@NoArgsConstructor
public class InstructionFieldsConfig {
  public Integer opcodeFieldLength;
  public Integer addressingModeFieldLength;
  public Integer operandFieldLength;

  /**
   * Constructor InstructionFieldsConfig.
   *
   * @param opcodeFieldLength         the length of the opcode field
   * @param addressingModeFieldLength the length of the addressing mode field
   * @param operandFieldLength        the length of the operand field
   */
  public InstructionFieldsConfig(Integer opcodeFieldLength,
                                 Integer addressingModeFieldLength, Integer operandFieldLength) {
    super();
    this.opcodeFieldLength = opcodeFieldLength;
    this.addressingModeFieldLength = addressingModeFieldLength;
    this.operandFieldLength = operandFieldLength;
  }

  @Override
  public String toString() {
    return String.format(
            "%d, %d, %d", opcodeFieldLength, addressingModeFieldLength, operandFieldLength);
  }
}