package com.soft.processors.assembler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.exceptions.ConfigurationException;
import com.soft.processors.assembler.configuration.InstructionConfig;
import com.soft.processors.assembler.configuration.InstructionFieldsConfig;

import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.HashMap;

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

    InstructionFieldsConfig instructionFieldsConfig =
            configuration.getInstructionFieldsConfig();
    assertEquals(3, instructionFieldsConfig.opcodeFieldLength);
    assertEquals(1, instructionFieldsConfig.addressingModeFieldLength);
    assertEquals(8, instructionFieldsConfig.operandFieldLength);

    HashMap<String, InstructionConfig> instructionConfigMap =
            configuration.getInstructionConfigMap();
    assertNotNull(instructionConfigMap);
    assertEquals(0, instructionConfigMap.size());
  }

  @Test
  void shouldThrowExceptionWithInvalidConfigurationFile() {
    Path invalidPath = Path.of("invalid_config.json");
    assertThrows(FileNotFoundException.class, () -> configuration.readConfig(invalidPath));
  }

  @Test
  void shouldThrowExceptionWhenInstructionIsEmpty() {
    HashMap<String, InstructionConfig> instructionConfigMap = new HashMap<>();
    configuration.setInstructionConfigMap(instructionConfigMap);

    assertThrows(ConfigurationException.class, () -> configuration.getInstructionConfig(""));
  }
}