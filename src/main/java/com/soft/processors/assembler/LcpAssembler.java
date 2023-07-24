package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents an assembler for the LCP (Little Computer Processor) architecture.
 * It contains methods for parsing a source file, generating a symbol table,
 * and generating a listing file && a coefficient file.
 * The assembler includes two passes - the first pass generates a symbol table
 * and the second pass generates the actual machine code.
 *
 * @author kalin
 */
@Getter
@Setter
public final class LcpAssembler {
  private static final String ROM_FILE = "ROM.coe";
  private static final Logger LOGGER = LoggerFactory.getLogger(LcpAssembler.class);
  @Getter
  private static final SymbolTable SYMBOL_TABLE = new SymbolTable();
  @Getter
  private static final ArrayList<Instruction> PROGRAM = new ArrayList<>();
  @Getter
  private static final Configuration CONFIG = new Configuration();
  private static String inputFile = "";

  /**
   * Constructor LcpAssembler.
   *
   * @throws InstantiationException if access is not allowed
   */
  private LcpAssembler() throws InstantiationException {
    throw new InstantiationException("This is a utility class and should not be instantiated ");
  }

  /**
   * This method contains execution of two passes.
   * First is create a lexer that feed off of input stream.
   * Also create a buffer of tokens pulled from lexer and create symbol table.
   * Second visit tree and generate source.
   *
   * @param sourceFile example file for program
   * @return if parse successes return true
   */
  public static boolean parseSourceFile(String sourceFile) throws IOException {
    ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(sourceFile));

    LcpLexer lexer = new LcpLexer(input);
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    LcpParser parser = new LcpParser(tokens);

    LcpParser.pass = 1;
    PROGRAM.clear();
    ParseTree tree = parser.assemblyProg();
    LcpParserVisitor visitor = new LcpParserVisitor(SYMBOL_TABLE, PROGRAM, CONFIG);
    visitor.visit(tree);
    LOGGER.info("{}", SYMBOL_TABLE);

    LcpParser.pass = 2;
    PROGRAM.clear();
    visitor.visit(tree);
    return true;
  }

  /**
   * This method check instruction and also print listing based on given instruction.
   */
  public static String printListing() {
    StringBuilder listing = new StringBuilder();
    listing.append("; Address : Code             ;  Instruction\n\n");

    int pc = 0;
    for (Instruction instr : PROGRAM) {
      String line = generateListingLine(instr, pc++);
      listing.append(line).append("\n");
    }

    String listingContent = listing.toString();
    LOGGER.info("{}", listingContent);
    return listingContent;
  }

  private static String generateListingLine(Instruction instr, int programCounter) {
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
            .append("\t\t; ")
            .append(String.format("%1$-5s", instr.getOpcodeStr()))
            .append("\t")
            .append(operandStr);

    return line.toString();
  }

  /**
   * Prints the ROM.coe initialization file for the program's instruction coefficients.
   */
  public static void printCoefficientFile() {
    String vector = "";
    LOGGER.info("ROM.coe initialization file ");
    LOGGER.info("Block memory depth=256, width=12 ");
    LOGGER.info("memory_initialization_radix=16 ");
    LOGGER.info("memory_initialization_vector= ");

    for (Instruction instr : PROGRAM) {
      int opcode = instr.getOpcode() << 1;
      if (instr.getMode() == AddressMode.Mode.IMMEDIATE) {
        opcode += 1;
      }
      vector = vector.concat(String.format("%1$01X", opcode)
              + String.format("%1$02X", instr.getOperand()) + " ");
    }
    LOGGER.info(vector, ";");
  }

  /**
   * This method read the input file name and parse the source file.
   *
   * @param fileName name of input file
   * @return input file
   * @throws IOException if file is not found
   */
  public static AssemblyResult assemble(String fileName) throws IOException {
    LcpAssembler.inputFile = fileName;
    CONFIG.loadDefaultConfig();
    CONFIG.readConfig();

    String outputFile;
    outputFile = ROM_FILE;
    if (inputFile.contains(".")) {
      outputFile = inputFile.substring(0, inputFile.lastIndexOf('.'));
    }
    outputFile += ".lst";

    if (!parseSourceFile(inputFile)) {
      return new AssemblyResult("", inputFile);
    } else if (PROGRAM.isEmpty()) {
      LOGGER.warn("No instructions were generated. Listing cannot be generated.");
      return new AssemblyResult("", outputFile);
    }

    String listingContent = printListing();
    return new AssemblyResult(listingContent, outputFile);
  }
}