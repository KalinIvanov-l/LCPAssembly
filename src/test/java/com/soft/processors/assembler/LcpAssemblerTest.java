package com.soft.processors.assembler;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

class LcpAssemblerTest {
  @Test
  void checkWithEmptyStringFile() {
    assertThrows(IllegalArgumentException.class, () -> LcpAssembler.assemble(""));
  }

  @Test
  void assembleWithWrongFileName() {
    String fileName = "invalid.tss";
    assertThrows(IllegalArgumentException.class, () -> LcpAssembler.assemble(fileName));
  }
}
