package com.soft.processors.assembler;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

/**
 * This class represents an assembler for the LCP (Little Computer Processor) architecture.
 * It contains methods for parsing a source file, generating a symbol table, and generating a listing file and a
 * coefficient file.
 * The assembler includes two passes - the first pass generates a symbol table and the second pass generates the
 * actual machine code.
 *
 * @author kalin
 */
@Getter
@Setter
public class LCPAssembler {
    private static final Logger LOGGER = LoggerFactory.getLogger(LCPAssembler.class);
    private static final SymbolTable symbolTable = new SymbolTable();
    private static final ArrayList<Instruction> program = new ArrayList<>();
    private static final Configuration config = new Configuration();
    private static String inputFile = "";

    private LCPAssembler() {
    }

    /**
     * This method contains execution of two passes. First is create a lexer that feed off of input stream.
     * Also create a buffer of tokens pulled from lexer and create symbol table.
     * Second visit tree and generate source.
     *
     * @param sourceFile example file for program
     * @return if parse successes return true
     */
    private static boolean parseSourceFile(String sourceFile) {
        try {
            ANTLRInputStream input = new ANTLRInputStream(new FileInputStream(sourceFile));

            LCPLexer lexer = new LCPLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            LCPParser parser = new LCPParser(tokens);

            LCPParser.pass = 1;
            program.clear();
            ParseTree tree = parser.assemblyProg();
            LCPParserVisitor visitor = new LCPParserVisitor(symbolTable, program, config);
            visitor.visit(tree);
            LOGGER.info("{}", symbolTable);

            LCPParser.pass = 2;
            program.clear();
            visitor.visit(tree);
        } catch (Exception exception) {
            LOGGER.info("ERROR(parseSourceFile): ", exception);
            return false;
        }
        return true;
    }

    /**
     * This method check instruction
     */
    public static void printListing() {
        String listing = "";
        String operandStr;
        String formatString;
        int pc = 0;

        LOGGER.info("; Address : Code\t; Instruction");

        for (Instruction instr : program) {
            String line = "";
            line = line.concat(String.format("%1$02X", pc++));
            line = line.concat("\t\t : ");
            int opcode = instr.getOpcode();
            operandStr = instr.getOperandStr();

            InstructionConfig instructionConfig = config.getInstructionConfig(instr.getOpcodeStr());
            if (instructionConfig == null) {
                LOGGER.info(line, "UNKNOWN INSTRUCTION: ", instr.getOpcodeStr(), "\r\n");
            }
            assert instructionConfig != null;
            int addressingModes = instructionConfig.getAddressingModes();
            if (instr.getMode() == AddressMode.Mode.DEFAULT && (addressingModes != 0)) {
                LOGGER.info(line, "INVALID ADDRESSING MODE FOR: ", instr.getOpcodeStr(), "\r\n");
            }

            if (instr.getMode() == AddressMode.Mode.ABSOLUTE && ((addressingModes & 0x01) == 0)) {
                LOGGER.info(line, "INVALID ABSOLUTE MODE FOR: ", instr.getOpcodeStr(), "\n");
            }

            if (instr.getMode() == AddressMode.Mode.IMMEDIATE && ((addressingModes & 0x02) == 0)) {
                LOGGER.info(line, "INVALID IMMEDIATE MODE FOR: ", instr.getOpcodeStr(), "\n");
                continue;
            }

            int instructionCode;
            instructionCode = opcode << (config.getInstructionFieldsConfig().getOperandFieldLength() +
                    config.getInstructionFieldsConfig().getAddressingModeFieldLength());
            if (instr.getMode() == AddressMode.Mode.IMMEDIATE) {
                operandStr = "#" + operandStr;
                instructionCode += 1 << config.getInstructionFieldsConfig().getOperandFieldLength();
            }
            if (instr.getMode() != AddressMode.Mode.DEFAULT) {
                instructionCode += instr.getOperand();
            }

            formatString = "%1$" + String.format("%02d",
                    (config.getInstructionFieldsConfig().getOpcodeFieldLength() +
                            config.getInstructionFieldsConfig().getAddressingModeFieldLength() +
                            config.getInstructionFieldsConfig().getOperandFieldLength()) / 5 + 1)
                    + "X";
            line = line.concat(String.format(formatString, instructionCode));

            line = line.concat("\t\t; "
                    + (String.format("%1$-5s", instr.getOpcodeStr())) + "\t"
                    + operandStr);

            listing = listing.concat(line + "\r\n");
        }
        LOGGER.info("{}", listing);
    }

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
     * This method read the input file name and parse the source file
     *
     * @param fileName name of input file
     */
    public static void assemble(String fileName) {
        String outputFile;
        PrintStream ps;
        LCPAssembler.inputFile = fileName;

        try {
            config.loadDefaultConfig();
            config.readConfig();

            if (inputFile == null) {
                LOGGER.info("Input file not specified!\r\nUsage: java LCPAssembler <input_file>");
                return;
            }

            outputFile = inputFile;
            if (inputFile.contains(".")) {
                outputFile = inputFile.substring(0, inputFile.lastIndexOf('.'));
            }
            outputFile += ".lst";
            try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile)) {
                ps = new PrintStream(fileOutputStream);
                System.setOut(ps);

                if (!parseSourceFile(inputFile)) {
                    return;
                }
                printListing();
                ps = new PrintStream(fileOutputStream);
            }
            System.setOut(ps);
            printCoefficientFile();
        } catch (Exception exception) {
            LOGGER.info("ERROR(main): ", exception);
        }
    }
}