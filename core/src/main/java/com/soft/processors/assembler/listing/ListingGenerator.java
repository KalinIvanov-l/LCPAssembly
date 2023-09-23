package com.soft.processors.assembler.listing;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import com.soft.processors.assembler.exceptions.ConfigurationException;
import com.soft.processors.assembler.models.Instruction;
import com.soft.processors.assembler.models.Mode;
import java.util.ArrayList;
import lombok.Getter;

/**
 * This class generates a listing content for a program's instructions
 * based on a provided configuration. It produces a formatted representation of the program's
 * addresses, machine code, and instructions.
 * The generated listing is helpful for debugging and analysis purposes.
 *
 * @author kalin
 */
public final class ListingGenerator {
  @Getter
  private static final Configuration CONFIG = new Configuration();
  @Getter
  private static final ArrayList<Instruction> PROGRAM = new ArrayList<>();

  /**
   * Constructor ListingGenerator.
   *
   * @throws InstantiationException if access is not allowed
   */
  private ListingGenerator() throws InstantiationException {
    throw new InstantiationException("This is a utility class and should not be instantiated ");
  }

  /**
   * Generates a single line of listing content for the provided instruction and program counter.
   *
   * @param config          The configuration containing instruction and field information.
   * @param instr           The instruction for which to generate the listing line.
   * @param programCounter  The program counter value for the instruction.
   * @return The generated listing line as a string.
   */
  public static String generateListingLine(
          Configuration config, Instruction instr, int programCounter)
          throws ConfigurationException {
    StringBuilder line = new StringBuilder();
    line.append(String.format("%1$02X", programCounter)).append("\t\t : ");

    InstructionConfig instructionConfig = config.getInstructionConfig(instr.getOpcodeStr());
    if (instructionConfig == null) {
      return line.append("UNKNOWN INSTRUCTION: ").append(instr.getOpcodeStr()).toString();
    }

    int opcode = calculateOpcode(instr, config);
    String operandStr = formatOperand(instr);

    line.append(String.format("%1$03X", opcode))
            .append("\t\t\t : ")
            .append(String.format("%1$-5s", instr.getOpcodeStr()))
            .append("\t : ")
            .append(operandStr);

    return line.toString();
  }

  /**
   * Calculates the opcode value for the given instruction based on its configuration.
   *
   * @param instr  The instruction for which to calculate the opcode.
   * @param config The configuration containing the instruction field lengths.
   * @return The calculated opcode value.
   */
  private static int calculateOpcode(Instruction instr, Configuration config) {
    int opcode = instr.getOpcode()
            << (config.getInstructionFieldsConfig().getOperandFieldLength()
            + config.getInstructionFieldsConfig().getAddressingModeFieldLength());

    opcode += (instr.getMode() == Mode.IMMEDIATE)
            ? (1 << config.getInstructionFieldsConfig().getOperandFieldLength()) : 0;
    opcode += (instr.getMode() != Mode.DEFAULT) ? instr.getOperand() : 0;

    return opcode;
  }

  /**
   * Formats the operand string for the given instruction, adding a '#' symbol
   * if the instruction's mode is IMMEDIATE.
   *
   * @param instr The instruction for which to format the operand.
   * @return The formatted operand string.
   */
  private static String formatOperand(Instruction instr) {
    String operandStr = instr.getOperandStr();

    if (instr.getMode() == Mode.IMMEDIATE) {
      operandStr = "#" + operandStr;
    }
    return operandStr;
  }
}
