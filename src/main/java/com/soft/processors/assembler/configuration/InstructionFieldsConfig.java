package com.soft.processors.assembler.configuration;

import lombok.Getter;
import lombok.Setter;

/**
 * This class represents the configuration of the fields in an instruction in an assembler
 *
 * @author kalin
 */
@Getter
@Setter
public class InstructionFieldsConfig {
    public int opcodeFieldLength;
    public int addressingModeFieldLength;
    public int operandFieldLength;

    public InstructionFieldsConfig(int opcodeFieldLength,
                                   int addressingModeFieldLength, int operandFieldLength) {
        this.opcodeFieldLength = opcodeFieldLength;
        this.addressingModeFieldLength = addressingModeFieldLength;
        this.operandFieldLength = operandFieldLength;
    }
}