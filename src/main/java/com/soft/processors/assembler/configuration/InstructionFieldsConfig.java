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
public class InstructionFieldsConfig {
    public Integer opcodeFieldLength;
    public Integer addressingModeFieldLength;
    public Integer operandFieldLength;

    public InstructionFieldsConfig(Integer opcodeFieldLength,
                                   Integer addressingModeFieldLength, Integer operandFieldLength) {
        super();
        this.opcodeFieldLength = opcodeFieldLength;
        this.addressingModeFieldLength = addressingModeFieldLength;
        this.operandFieldLength = operandFieldLength;
    }

    @Override
    public String toString() {
        return String.format("%d, %d, %d", opcodeFieldLength, addressingModeFieldLength, operandFieldLength);
    }
    public Integer getAddressingModeFieldLength() {
        return addressingModeFieldLength;
    }
    public Integer getOperandFieldLength() {
        return operandFieldLength;
    }
}