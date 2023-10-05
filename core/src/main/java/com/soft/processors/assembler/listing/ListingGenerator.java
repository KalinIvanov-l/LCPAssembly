package com.soft.processors.assembler.listing;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.models.Instruction;
import com.soft.processors.assembler.models.Mode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
  private static final List<Instruction> PROGRAM = new ArrayList<>();

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
          Configuration config, Instruction instr, int programCounter) {
    return IntStream.rangeClosed(1, 4)
            .mapToObj(partNumber -> generatePart(config, instr, programCounter, partNumber))
            .collect(Collectors.joining(" : ", "", ""));
  }

  private static String generatePart(
          Configuration config, Instruction instr, int programCounter, int partNumber) {
    return switch (partNumber) {
      case 1 -> String.format("%1$02X\t\t", programCounter);
      case 2 -> String.format("%1$03X\t\t\t", calculateOpcode(instr, config));
      case 3 -> String.format("%1$-5s\t\t", instr.getOpcodeStr());
      case 4 -> formatOperand(instr);
      default -> "";
    };
  }

  /**
   * Calculates the opcode value for the given instruction based on its configuration.
   *
   * @param instr  The instruction for which to calculate the opcode.
   * @param config The configuration containing the instruction field lengths.
   * @return The calculated opcode value.
   */
  private static int calculateOpcode(Instruction instr, Configuration config) {
    var opcode = instr.getOpcode()
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
    return instr.getMode() == Mode.IMMEDIATE ? "#" + instr.getOperandStr() : instr.getOperandStr();
  }
}
