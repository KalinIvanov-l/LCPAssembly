package com.soft.processors.assembler;

import com.google.gson.JsonSyntaxException;
import com.soft.processors.assembler.configuration.Configuration;
import com.soft.processors.assembler.configuration.InstructionConfig;
import com.soft.processors.assembler.configuration.InstructionFieldsConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Configuration.class)
class ConfigurationTest {
  private Configuration configuration;

  @BeforeEach
  void setUp() {
    configuration = new Configuration();
  }

  @Test
  void readConfig() throws IOException {
    configuration.readConfig();

    InstructionFieldsConfig instructionFieldsConfig =
            configuration.getInstructionFieldsConfig();
    assertEquals(3, instructionFieldsConfig.opcodeFieldLength);
    assertEquals(1, instructionFieldsConfig.addressingModeFieldLength);
    assertEquals(8, instructionFieldsConfig.operandFieldLength);

    HashMap<String, InstructionConfig> instructionConfigMap =
            configuration.getInstructionConfigMap();
    assertNotNull(instructionConfigMap);
    assertEquals(7, instructionConfigMap.size());
  }

  @Test
  void loadDefaultConfig() {
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
  void readConfigurationWithInvalidFile() {
    Configuration.setCfgFILE("invalid_config.json");
    assertThrows(FileNotFoundException.class, () -> configuration.readConfig());
  }

  @Test
  void readConfigurationWithInvalidJson() throws IOException {
    File tempFile = File.createTempFile("temp_config", ".json");
    FileWriter writer = new FileWriter(tempFile);
    writer.write("{invalid_json}");
    writer.close();

    Configuration.setCfgFILE(tempFile.getPath());

    assertThrows(JsonSyntaxException.class, configuration::readConfig);
  }

  @Test
  void validateInstructionConfigMap() {
    HashMap<String, InstructionConfig> instructionConfigMap = new HashMap<>();
    configuration.setInstructionConfigMap(instructionConfigMap);

    assertThrows(IllegalArgumentException.class, () -> configuration.getInstructionConfig(""));
  }
}