package com.soft.processors.assembler.listing;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionFieldsConfig;
import com.soft.processors.assembler.models.Instruction;
import com.soft.processors.assembler.models.Mode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListingGeneratorTest {
  private final Configuration configuration = new Configuration();
  private final Instruction instruction = new Instruction();

  @BeforeEach
  void init() {
    configuration.setInstructionFieldsConfig(new InstructionFieldsConfig(3, 1, 8));
    instruction.setOpcode(2);
    instruction.setOpcodeStr("ADD");
    instruction.setMode(Mode.IMMEDIATE);
    instruction.setOperand(5);
    instruction.setOperandStr("5");
  }

  @Test
  void shouldGenerateListingLineTest() {
    var expected = "0A\t\t : 505\t\t\t : ADD  \t\t : #5";
    var actual = ListingGenerator.generateListingLine(configuration, instruction, 10);
    assertEquals(expected, actual);
  }
}
