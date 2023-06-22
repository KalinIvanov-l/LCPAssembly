package com.soft.processors.assembler.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * This class represents the configuration of an instruction in an assembler
 *
 * @author kalin
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstructionConfig {
    public String mnemocode;
    public int opcode;
    public int addressingModes;
}
