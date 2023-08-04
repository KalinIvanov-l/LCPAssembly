package com.soft.processors.assembler;

import com.google.gson.JsonSyntaxException;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

class LcpAssemblerTest {
  @Test
  void checkWithEmptyStringFile() {
    assertThrows(IllegalArgumentException.class, () -> LcpAssembler.assemble(""));
  }

  @Test
  void assembleWithValidProgramFile() throws IOException {
    String fileName = "src/main/resources/gcd_a.asm";
    AssemblyResult result;

    try {
      result = LcpAssembler.assemble(fileName);
      assertTrue(result.getOutputFile().endsWith(".lst"));
    } catch (JsonSyntaxException e) {
      fail("JSON syntax error: " + e.getMessage());
    }
  }

  @Test
  void assembleWithWrongFileName() {
    String fileName = "invalid.tss";
    assertThrows(IllegalArgumentException.class, () -> LcpAssembler.assemble(fileName));
  }
}
