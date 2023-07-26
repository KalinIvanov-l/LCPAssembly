//package com.soft.processors.assembler;
//
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class LcpAssemblerTest {
//  private static final String TEST_FILE = "src/test/resources/test.txt";
//
//  @Test
//  void checkParseSourceFileWithExistFile() throws IOException {
//    boolean result = LcpAssembler.parseSourceFile(TEST_FILE);
//
//    assertTrue(result);
//    assertEquals(7, LcpAssembler.getSYMBOL_TABLE().getSymbolStore().size());
//    assertEquals(22, LcpAssembler.getPROGRAM().size());
//  }
//
//  @Test
//  void checkParseSourceFileWithNotExistFile() {
//    assertThrows(IOException.class, () -> LcpAssembler.parseSourceFile("src/test/resources/invalid_program.txt"));
//  }
//
//  @Test
//  void checkAssembleOutput() throws IOException {
//    AssemblyResult result = LcpAssembler.assemble(TEST_FILE);
//
//    assertNotNull(result);
//    assertNotNull(result.getListing());
//    assertNotNull(result.getOutputFile());
//  }
//}
