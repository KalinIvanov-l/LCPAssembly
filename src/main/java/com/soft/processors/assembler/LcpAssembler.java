package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import java.io.FileInputStream;
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
public class LcpAssembler {
  private static final String ROM_FILE = "ROM.coe";
  private static final Logger LOGGER = LoggerFactory.getLogger(LcpAssembler.class);
  @Getter
  private static final SymbolTable symbolTable = new SymbolTable();
  @Getter
  private static final ArrayList<Instruction> program = new ArrayList<>();
  @Getter
  private static final Configuration config = new Configuration();
  private static String inputFile = "";

  private LcpAssembler() {
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
  public static boolean parseSourceFile(String sourceFile) {
    try {
      ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(sourceFile));

      LcpLexer lexer = new LcpLexer(input);
      CommonTokenStream tokens = new CommonTokenStream(lexer);
      LcpParser parser = new LcpParser(tokens);

      LcpParser.pass = 1;
      program.clear();
      ParseTree tree = parser.assemblyProg();
      LcpParserVisitor visitor = new LcpParserVisitor(symbolTable, program, config);
      visitor.visit(tree);
      LOGGER.info("{}", symbolTable);

      LcpParser.pass = 2;
      program.clear();
      visitor.visit(tree);
    } catch (Exception exception) {
      LOGGER.error("ERROR(parseSourceFile): ", exception);
      return false;
    }
    return true;
  }

  /**
   * This method check instruction.
   */
  public static String printListing() {
    String listing = "";
    String operandStr;
    int pc = 0;

    listing = listing.concat("; Address : Code             ;  Instruction\n\n");

    for (Instruction instr : program) {
      String line = "";
      line = line.concat(String.format("%1$02X", pc++));
      line = line.concat("\t\t : ");
      int opcode = instr.getOpcode();
      operandStr = instr.getOperandStr();

      InstructionConfig instructionConfig = config.getInstructionConfig(instr.getOpcodeStr());
      if (instructionConfig == null) {
        line = line.concat(String.format("UNKNOWN INSTRUCTION: %s", instr.getOpcodeStr()));
        listing = listing.concat(line + "\r\n");
        continue;
      }

      int instructionCode;
      instructionCode = opcode << (config.getInstructionFieldsConfig().getOperandFieldLength()
              + config.getInstructionFieldsConfig().getAddressingModeFieldLength());
      if (instr.getMode() == AddressMode.Mode.IMMEDIATE) {
        operandStr = "#" + operandStr;
        instructionCode += 1 << config.getInstructionFieldsConfig().getOperandFieldLength();
      }
      if (instr.getMode() != AddressMode.Mode.DEFAULT) {
        instructionCode += instr.getOperand();
      }

      line = line.concat(String.format("%1$03X", instructionCode));
      line = line.concat("\t\t; "
              + (String.format("%1$-5s", instr.getOpcodeStr())) + "\t"
              + operandStr);

      listing = listing.concat(line + "\r\n");
    }
    LOGGER.info("{}", listing);
    return listing;
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

    for (Instruction instr : program) {
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
   */
  public static AssemblyResult assemble(String fileName) {
    String outputFile = "";
    LcpAssembler.inputFile = fileName;

    try {
      config.loadDefaultConfig();
      config.readConfig();

      if (inputFile == null) {
        LOGGER.info("Input file not specified!\r\nUsage: java LCPAssembler <input_file>");
        return new AssemblyResult("", outputFile);
      }

      outputFile = ROM_FILE;
      if (inputFile.contains(".")) {
        outputFile = inputFile.substring(0, inputFile.lastIndexOf('.'));
      }
      outputFile += ".lst";

      if (!parseSourceFile(inputFile)) {
        return new AssemblyResult("", outputFile);
      }
      printCoefficientFile();

    } catch (Exception exception) {
      LOGGER.error("ERROR(main): ", exception);
      return new AssemblyResult("", outputFile);
    }

    String listingContent = printListing();
    return new AssemblyResult(listingContent, outputFile);
  }
}