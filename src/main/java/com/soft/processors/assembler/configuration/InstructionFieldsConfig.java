package com.soft.processors.assembler.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents the configuration of the fields in an instruction in an assembler
 *
 * @author kalin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructionFieldsConfig {
    public int opcodeFieldLength;
    public int addressingModeFieldLength;
    public int operandFieldLength;
}