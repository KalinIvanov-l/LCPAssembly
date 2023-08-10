package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import java.io.File;
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
    listing.append("; Address : Machine Code                   ;  Instruction\n\n");

    int counter = 0;
    for (Instruction instr : PROGRAM) {
      String line = generateListingLine(instr, counter++);
      listing.append(line).append("\n");
    }

    String listingContent = listing.toString();
    LOGGER.info("{}", listingContent);
    return listingContent;
  }

  /**
   * This method read the input file name and parse the source file.
   *
   * @param fileName name of input file
   * @return input file
   * @throws IOException if an I/O error occurs while reading the configuration file.
   */
  public static AssemblyResult assemble(String fileName) throws IOException {
    checkFileName(fileName);
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

  /**
   * Validates provided file name.
   *
   * @param fileName the given string file
   * @throws IllegalArgumentException if provided file is not readable or file is null
   */
  private static void checkFileName(String fileName) throws IllegalArgumentException {
    if (fileName.isEmpty()) {
      throw new IllegalArgumentException("Provided string file is empty ");
    }

    File file = new File(fileName);
    if (!file.exists() || !file.isFile() || !file.canRead()) {
      throw new IllegalArgumentException("File does not exist or is not readable: " + fileName);
    }
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
        .append("\t\t\t\t\t\t\t\t\t\t\t\t; ")
        .append(String.format("%1$-5s", instr.getOpcodeStr()))
        .append("\t\t\t")
        .append(operandStr);

    return line.toString();
  }
}