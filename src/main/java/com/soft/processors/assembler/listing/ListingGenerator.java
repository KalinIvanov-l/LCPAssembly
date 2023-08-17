package com.soft.processors.assembler.listing;

import com.soft.processors.assembler.AddressMode;
import com.soft.processors.assembler.Instruction;
import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import java.util.List;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class generates a listing content for a program's instructions
 * based on a provided configuration. It produces a formatted representation of the program's
 * addresses, machine code, and instructions.
 * The generated listing is helpful for debugging and analysis purposes.
 *
 * @author kalin
 */
public class ListingGenerator {
  private static final Logger LOGGER = LoggerFactory.getLogger(ListingGenerator.class);
  @Getter
  private static final Configuration CONFIG = new Configuration();

  /**
   * Constructor ListingGenerator.
   *
   * @throws InstantiationException if access is not allowed
   */
  private ListingGenerator() throws InstantiationException {
    throw new InstantiationException("This is a utility class and should not be instantiated ");
  }

  /**
   * This method check instruction and also print listing based on given instruction.
   *
   * @param program The list of instructions in the program.
   * @return The generated listing content as a string.
   */
  public static String generateListing(List<Instruction> program, Configuration config) {
    StringBuilder listing = new StringBuilder();
    listing.append("; Address : Machine Code                   ;  Instruction\n\n");

    int counter = 0;
    for (Instruction instr : program) {
      String line = generateListingLine(instr, counter++, config);
      listing.append(line).append("\n");
    }

    String listingContent = listing.toString();
    LOGGER.info("{}", listingContent);
    return listingContent;
  }

  /**
   * Generates a single line of listing content for the provided instruction and program counter.
   *
   * @param instr          The instruction for which to generate the listing line.
   * @param programCounter The program counter value for the instruction.
   * @return The generated listing line as a string.
   */
  private static String generateListingLine(
          Instruction instr, int programCounter, Configuration config) {
    StringBuilder line = new StringBuilder();
    line.append(String.format("%1$02X", programCounter)).append("\t\t : ");

    InstructionConfig instructionConfig = CONFIG.getInstructionConfig(instr.getOpcodeStr());
    if (instructionConfig == null) {
      line.append("UNKNOWN INSTRUCTION: ").append(instr.getOpcodeStr());
      return line.toString();
    }

    int opcode = instr.getOpcode() << (CONFIG.getInstructionFieldsConfig().getOperandFieldLength()
            + CONFIG.getInstructionFieldsConfig().getAddressingModeFieldLength());
    String operandStr = instr.getOperandStr();

    if (instr.getMode() == AddressMode.Mode.IMMEDIATE) {
      operandStr = "#" + operandStr;
      opcode += 1 << CONFIG.getInstructionFieldsConfig().getOperandFieldLength();
    } else if (instr.getMode() != AddressMode.Mode.DEFAULT) {
      opcode += instr.getOperand();
    }

    line.append(String.format("%1$03X", opcode))
            .append("\t\t\t\t\t\t\t\t\t\t\t\t; ")
            .append(String.format("%1$-5s", instr.getOpcodeStr()))
            .append("\t\t\t")
            .append(operandStr);

    return line.toString();
  }
}
