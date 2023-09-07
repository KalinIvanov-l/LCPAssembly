package com.soft.processors.assembler.listing;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.ConfigurationException;
import com.soft.processors.assembler.configuration.InstructionConfig;
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
public class ListingGenerator {
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
      line.append("UNKNOWN INSTRUCTION: ").append(instr.getOpcodeStr());
      return line.toString();
    }

    int opcode =
            instr.getOpcode()
                    << (config.getInstructionFieldsConfig().getOperandFieldLength()
                    + config.getInstructionFieldsConfig().getAddressingModeFieldLength());
    String operandStr = instr.getOperandStr();

    if (instr.getMode() == Mode.IMMEDIATE) {
      operandStr = "#" + operandStr;
      opcode += 1 << config.getInstructionFieldsConfig().getOperandFieldLength();
    } else if (instr.getMode() != Mode.DEFAULT) {
      opcode += instr.getOperand();
    }

    line.append(String.format("%1$03X", opcode))
            .append("\t\t\t : ")
            .append(String.format("%1$-5s", instr.getOpcodeStr()))
            .append("\t : ")
            .append(operandStr);

    return line.toString();
  }
}
