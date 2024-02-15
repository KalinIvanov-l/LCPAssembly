package com.soft.processors.assembler.configuration;

import lombok.Data;

/**
 * This class represents the configuration of an instruction in an assembler.
 *
 * @author kalin
 */
@Data
public class InstructionConfig {
  public String mnemocode;
  public int opcode;
  public int addressingModes;

  /**
   * Constructor InstructionConfig.
   *
   * @param mnemocode       the mnemonic code for the instruction
   * @param opcode          the opcode for the instruction
   * @param addressingModes the number of addressing modes supported by the instruction
   */
  public InstructionConfig(String mnemocode, int opcode, int addressingModes) {
    this.mnemocode = mnemocode;
    this.opcode = opcode;
    this.addressingModes = addressingModes;
  }
}
