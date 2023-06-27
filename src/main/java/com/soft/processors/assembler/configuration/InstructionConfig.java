package com.soft.processors.assembler.configuration;

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
public class InstructionConfig {
    public String mnemocode;
    public Integer opcode;
    public Integer addressingModes;

    public InstructionConfig(String mnemocode, Integer opcode, Integer addressingModes) {
        super();
        this.mnemocode = mnemocode;
        this.opcode = opcode;
        this.addressingModes = addressingModes;
    }

    @Override
    public String toString() {
        return String.format("%s, %d, %d", mnemocode, opcode, addressingModes);
    }
    public String getMnemocode() {
        return mnemocode;
    }
    public int getOpcode() {
        return opcode;
    }
}
