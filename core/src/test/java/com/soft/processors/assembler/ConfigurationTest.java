package com.soft.processors.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonParser;
import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.exceptions.ConfigurationException;
import com.soft.processors.assembler.configuration.InstructionConfig;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ConfigurationTest {
  private Configuration configuration;

  @BeforeEach
  void init() {
    configuration = new Configuration();
  }

  @Test
  void shouldAssertDefaultInstructionConfig() {
    configuration.loadDefaultConfig();

    var instructionFieldsConfig =
            configuration.getInstructionFieldsConfig();
    assertEquals(3, instructionFieldsConfig.opcodeFieldLength);
    assertEquals(1, instructionFieldsConfig.addressingModeFieldLength);
    assertEquals(8, instructionFieldsConfig.operandFieldLength);

    Map<String, InstructionConfig> instructionConfigMap =
            configuration.getInstructionConfigMap();
    assertNotNull(instructionConfigMap);
    assertEquals(0, instructionConfigMap.size());
  }

  @Test
  void shouldThrowExceptionWithInvalidConfigurationFile() {
    var invalidPath = Path.of("invalid_config.json");
    assertThrows(FileNotFoundException.class, () -> configuration.readConfig(invalidPath));
  }

  @Test
  void shouldThrowExceptionWhenInstructionIsEmpty() {
    Map<String, InstructionConfig> instructionConfigMap = new HashMap<>();
    configuration.setInstructionConfigMap(instructionConfigMap);

    assertThrows(ConfigurationException.class, () -> configuration.getInstructionConfig(""));
  }

  @Test
  void shouldReadInstructionFieldsConfig() {
    var jsonString = "{ \"instructionFieldsConfig\": { \"opcodeFieldLength\": 3,"
            + " \"addressingModeFieldLength\": 1, \"operandFieldLength\": 8 } }";
    var configObj = JsonParser.parseString(jsonString).getAsJsonObject();
    var instructionFieldsConfig = configuration.readInstructionFieldsConfig(configObj);

    assertEquals(3, instructionFieldsConfig.getOpcodeFieldLength());
    assertEquals(1, instructionFieldsConfig.getAddressingModeFieldLength());
    assertEquals(8, instructionFieldsConfig.getOperandFieldLength());
  }

  @Test
  void shouldReadInstructionConfigMap() {
    var jsonString = "{ \"instructionConfig\": [ { \"mnemocode\": \"ADD\", \"opcode\": 1, \"addressingModes\": 2 } ] }";
    var configObj = JsonParser.parseString(jsonString).getAsJsonObject();
    Map<String, InstructionConfig> instructionConfigMap = configuration.readInstructionConfigMap(configObj);

    assertEquals(1, instructionConfigMap.size());
    assertTrue(instructionConfigMap.containsKey("ADD"));

    var instructionConfig = instructionConfigMap.get("ADD");
    assertEquals("ADD", instructionConfig.getMnemocode());
    assertEquals(1, instructionConfig.getOpcode());
    assertEquals(2, instructionConfig.getAddressingModes());
  }

  @Test
  void shouldThrowExceptionWhenReadingConfigWithMalformedJson() {
    var jsonString = "{ \"instructionFieldsConfig\": { \"opcodeFieldLength\": \"invalid\","
            + " \"addressingModeFieldLength\": 1, \"operandFieldLength\": 8 } }";
    var configObj = JsonParser.parseString(jsonString).getAsJsonObject();
    assertThrows(NumberFormatException.class, () -> configuration.readInstructionFieldsConfig(configObj));
  }
}