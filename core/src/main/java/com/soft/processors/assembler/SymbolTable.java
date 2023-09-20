package com.soft.processors.assembler;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class represents the symbol table used in the LCP (Little Computer Processor) assembler.
 * It provides methods for adding and retrieving symbols and their values.
 * The symbol table is implemented as a HashMap that maps symbol names (Strings) to their values.
 *
 * @author kalin
 */

@Getter
@Setter
@AllArgsConstructor
public class SymbolTable {
  private static final Logger LOGGER = LoggerFactory.getLogger(SymbolTable.class);
  private static final String DELIMITER = ", ";
  private final Map<String, Integer> symbolStore;

  /**
   * Constructs a new SymbolTable object with an empty symbol store.
   */
  public SymbolTable() {
    symbolStore = new HashMap<>();
  }

  /**
   * Adds a new symbol to the symbol table.
   *
   * @param name  the name of the symbol (String)
   * @param value the value of the symbol (Integer)
   */
  public void addSymbol(String name, int value) {
    checkIfAlreadyExist(name);
    symbolStore.put(name, value);
  }

  /**
   * Returns the value of a symbol in the symbol table.
   * If the symbol is not defined in the symbol table, logs a message and returns 0.
   *
   * @param name the name of the symbol to retrieve
   * @return the value of the symbol
   */
  public int getSymbolValue(String name) {
    return symbolStore.getOrDefault(name, 0);
  }

  /**
   * Returns a string representation of the symbol table.
   * The string contains a list of all symbols in the symbol table and their values.
   *
   * @return a string representation of the symbol table
   */
  @Override
  public String toString() {
    return "Symbol Table: { "
            + symbolStore.entrySet().stream()
            .map(entry -> entry.getKey() + "=" + String.format("%1$02X", entry.getValue()) + "h")
            .collect(Collectors.joining(DELIMITER))
            + " }\n";
  }

  /**
   * Check if symbol already exists or not.
   *
   * @param name the name of the symbol
   */
  private void checkIfAlreadyExist(String name) {
    if (symbolStore.containsKey(name)) {
      throw new IllegalArgumentException("Symbol is already defined, cannot have duplication ");
    }
  }
}