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
 * The assembler includes two passes - the first pass generates a symbol table,
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
   * This method contains execution of two passes.
   * First is create a lexer that feeds off of input stream.
   * Also create a buffer of tokens pulled from lexer and create symbol table.
   * Second visit tree and generate a source.
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
   * This method read the input file name and parse the source file.
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