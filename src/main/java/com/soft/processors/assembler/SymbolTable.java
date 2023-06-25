package com.soft.processors.assembler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents the symbol table used in the LCP (Little Computer Processor) assembler.
 * It provides methods for adding and retrieving symbols and their values.
 * The symbol table is implemented as a HashMap that maps symbol names (Strings) to their values (Integers).
 *
 * @author kalin
 */

@Getter
@Setter
@AllArgsConstructor
public class SymbolTable {
    private static final Logger LOGGER = LoggerFactory.getLogger(SymbolTable.class);
    private final HashMap<String, Integer> symbolStore;

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
    public void addSymbol(String name, Integer value) {
        if (symbolStore.containsKey(name)) {
            LOGGER.debug(name, "Symbol is already defined ");
        } else {
            symbolStore.put(name, value);
        }
    }

    /**
     * Returns the value of a symbol in the symbol table.
     * If the symbol is not defined in the symbol table, logs a message and returns 0.
     *
     * @param name the name of the symbol to retrieve (String)
     * @return the value of the symbol (Integer)
     */
    public Integer getSymbolValue(String name) {
        if (symbolStore.containsKey(name)) {
            return symbolStore.get(name);
        } else {
            LOGGER.info(name, " Symbol is not defined ");
            return 0;
        }
    }

    /**
     * Returns a string representation of the symbol table.
     * The string contains a list of all symbols in the symbol table and their values.
     *
     * @return a string representation of the symbol table (String)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n Symbol Table: { ");
        for (Map.Entry<String, Integer> entry : symbolStore.entrySet()) {
            sb.append(entry.getKey()).append("=").append(String.format("%1$02X", entry.getValue())).append("h, ");
        }
        sb.append("}\n");
        return sb.toString();
    }
}