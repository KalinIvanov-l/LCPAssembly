package com.soft.processors.assembler;

import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LcpAssemblerTest {
  @Test
  void checkWithEmptyStringFile() {
    assertThrows(IllegalArgumentException.class, () -> LcpAssembler.assemble(""));
  }

  @Test
  void assembleWithValidProgramFile() throws IOException {
    String fileName = "src/main/resources/gcd_a.asm";
    AssemblyResult result = LcpAssembler.assemble(fileName);

    assertTrue(result.getOutputFile().endsWith(".lst"));
  }

  @Test
  void assembleWithWrongFileName() {
    String fileName = "invalid.tss";
    assertThrows(IllegalArgumentException.class, () -> LcpAssembler.assemble(fileName));
  }
}
