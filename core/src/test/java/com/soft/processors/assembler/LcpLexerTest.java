package com.soft.processors.assembler;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LcpLexerTest {

  @Test
  void shouldAssertDirectiveTokens() {
    var input = "EQU DATA ORG MACRO ( ) ENDMACRO , : #";
    List<String> expectedTokens = List.of("EQU", "DATA", "ORG", "MACRO", "(", ")", "ENDMACRO", ",", ":", "#", "<EOF>");
    assertEquals(expectedTokens, getTokens(input));
  }

  @Test
  void shouldAssertInstructionTokens() {
    var input = "MOV ADD SUB MUL";
    List<String> expectedTokens = List.of("MOV", "ADD", "SUB", "MUL", "<EOF>");
    assertEquals(expectedTokens, getTokens(input));
  }

  @Test
  void shouldAssertLabelTokens() {
    var input = "label1: label2: label3:";
    List<String> expectedTokens = List.of("label1", ":", "label2", ":", "label3", ":", "<EOF>");
    assertEquals(expectedTokens, getTokens(input));
  }

  private List<String> getTokens(String input) {
    var lexer = new LcpLexer(new ANTLRInputStream(input));
    var tokens = new CommonTokenStream(lexer);
    tokens.fill();
    return tokens.getTokens().stream().map(Token::getText).collect(Collectors.toList());
  }
}
