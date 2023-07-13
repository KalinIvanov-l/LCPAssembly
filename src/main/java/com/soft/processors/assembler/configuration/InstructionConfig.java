package com.soft.processors.assembler.configuration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * This class represents the configuration of an instruction in an assembler.
 *
 * @author kalin
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
public class InstructionConfig {
  public String mnemocode;
  public Integer opcode;
  public Integer addressingModes;

  /**
   * Constructor InstructionConfig.
   *
   * @param mnemocode       the mnemonic code for the instruction
   * @param opcode          the opcode for the instruction
   * @param addressingModes the number of addressing modes supported by the instruction
   */
  public InstructionConfig(String mnemocode, Integer opcode, Integer addressingModes) {
    super();
    this.mnemocode = mnemocode;
    this.opcode = opcode;
    this.addressingModes = addressingModes;
  }
}
