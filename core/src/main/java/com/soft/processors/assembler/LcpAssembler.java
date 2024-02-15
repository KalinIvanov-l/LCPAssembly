package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.listing.ListingGenerator;
import com.soft.processors.assembler.models.AssemblyResult;
import com.soft.processors.assembler.models.Instruction;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.Data;
import lombok.Getter;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents an assembler for the LCP (Little Computer Processor) architecture.
 * Provides functionality to parse a source file, generate a symbol table,
 * and create listing and coefficient files.
 * The assembler executes in two passes:
 * the first pass generates a symbol table, and the second pass generates machine code.
 *
 * @author kalin
 */
@Data
public final class LcpAssembler {
  private static final String ROM_FILE = "ROM.coe";
  private static final Logger LOGGER = LoggerFactory.getLogger(LcpAssembler.class);
  @Getter
  private static final SymbolTable SYMBOL_TABLE = new SymbolTable();
  @Getter
  private static final List<Instruction> PROGRAM = new ArrayList<>();
  @Getter
  private static final Configuration CONFIG = new Configuration();
  private static final String DELIMITER = "\n";
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
   * Executes two passes of the assembly process: the first pass to generate a symbol table,
   * followed by a second pass to generate machine code.
   * A lexer is created to process the input stream, which feeds a stream of tokens into the parser.
   * The symbol table is created and subsequently used to generate the source code.
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
   * Generates a listing based on the instructions provided.
   * The listing includes address, machine code, instruction, and label columns.
   *
   * @return The generated listing content as a string.
   */
  public static String generateListing() {
    var header = "\nAddress:      Machine Code:         Instruction:         Labels:\n\n";

    var listingContent = IntStream.range(0, PROGRAM.size())
            .mapToObj(counter ->
                    ListingGenerator.generateListingLine(CONFIG, PROGRAM.get(counter), counter))
            .collect(Collectors.joining(DELIMITER));

    var listing = header + listingContent;
    LOGGER.info("{}", listing);
    return listing;
  }

  /**
   * Reads the input file name and parse the source file.
   *
   * @param fileName name of input file
   * @return input file
   * @throws IOException if an I/O error occurs while reading the configuration file.
   */
  public static AssemblyResult assemble(Path fileName) throws IOException {
    validateFileName(fileName);
    LcpAssembler.inputFile = String.valueOf(fileName);
    CONFIG.loadDefaultConfig();
    CONFIG.readConfig(Path.of(fileName.toUri()));

    var outputFile = inputFile.contains(".")
            ? inputFile.substring(0, inputFile.lastIndexOf('.')) + ".lst"
            : ROM_FILE;

    if (parseSourceFile(inputFile) == inputFile.isEmpty()) {
      LOGGER.warn("No instructions were generated. Listing cannot be generated.");
      return new AssemblyResult("", outputFile);
    }

    var listingContent = generateListing();
    return new AssemblyResult(listingContent, outputFile);
  }

  /**
   * Validates provided file name.
   *
   * @param fileName the given string file
   * @throws IllegalArgumentException if provided file is not readable or file is null
   */
  private static void validateFileName(Path fileName) throws IllegalArgumentException {
    if (!Files.exists(fileName) || !Files.isRegularFile(fileName)) {
      throw new IllegalArgumentException("File does not exist or is not readable: " + fileName);
    }
  }
}