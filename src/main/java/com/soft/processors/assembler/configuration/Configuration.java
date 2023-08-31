package com.soft.processors.assembler.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Configuration data and functions.
 * Note: The configuration is kept in a file named "config.json"
 *
 * @author kalin
 */
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Configuration {
  private static final Logger LOGGER = LoggerFactory.getLogger(Configuration.class);
  Path configFilePath = Path.of("config.json");
  private InstructionFieldsConfig instructionFieldsConfig;
  private HashMap<String, InstructionConfig> instructionConfigMap;

  /**
   * Returns the instruction configuration for the specified mnemonic.
   *
   * @param key the mnemonic to look up
   * @return the instruction configuration, or null if the mnemonic is not found
   */
  public InstructionConfig getInstructionConfig(String key) {
    validateInstructionConfigMap();
    return instructionConfigMap.getOrDefault(key, null);
  }

  /**
   * Read configuration from file [config.json]
   */
  public void readConfig(Path path) throws IOException {
    validateFilePath(path);
    JsonObject configObj = readConfigFile();
    instructionFieldsConfig = readInstructionFieldsConfig(configObj);
    instructionConfigMap = readInstructionConfigMap(configObj);
    LOGGER.info("Configuration read: OK");
  }

  /**
   * Initializes the instructionFieldsConfig and instructionConfigMap with default values.
   */
  public void loadDefaultConfig() {
    instructionFieldsConfig = new InstructionFieldsConfig(3, 1, 8);
    instructionConfigMap = new HashMap<>();
  }

  /**
   * Provides simple check for instruction.
   */
  private void validateInstructionConfigMap() {
    if (instructionConfigMap.isEmpty()) {
      throw new IllegalArgumentException("Instruction configuration map cannot be empty.");
    }
  }

  /**
   * Validate if the provided path exists.
   *
   * @param path the path to be validated
   * @throws FileNotFoundException if provided file is invalid
   */
  private void validateFilePath(Path path) throws FileNotFoundException {
    if (!Files.exists(path)) {
      throw new FileNotFoundException("Provided file doesn't exist ");
    }
  }

  /**
   * Reads the content of the configuration file and parses it into a JsonObject.
   *
   * @return The JsonObject containing the configuration data.
   * @throws IOException if an I/O error occurs while reading the configuration file.
   */
  private JsonObject readConfigFile() throws IOException {
    return parseJsonFile(configFilePath);
  }

  /**
   * Provides implementation for read given configuration file.
   *
   * @param configFilePath the given file
   * @return The JsonObject containing the configuration data.
   * @throws IOException if an I/O error occurs while reading the configuration file.
   */
  private JsonObject parseJsonFile(Path configFilePath) throws IOException {
    StringBuilder jsonBuilder = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new FileReader(configFilePath.toFile()))) {
      String line;
      while ((line = reader.readLine()) != null) {
        jsonBuilder.append(line);
      }
    }

    String json = jsonBuilder.toString();
    return JsonParser.parseString(json).getAsJsonObject();
  }

  /**
   * Reads the instruction fields configuration from the given JsonObject and creates an
   * InstructionFieldsConfig object with the extracted data.
   *
   * @param configObj The JsonObject containing the instruction fields configuration.
   * @return An InstructionFieldsConfig object with the configuration data.
   */
  private InstructionFieldsConfig readInstructionFieldsConfig(JsonObject configObj) {
    JsonObject fieldsObj = configObj.getAsJsonObject("instructionFieldsConfig");
    int opcodeFieldLength = fieldsObj.getAsJsonPrimitive("opcodeFieldLength").getAsInt();
    int addressingModeFieldLength = fieldsObj.getAsJsonPrimitive(
            "addressingModeFieldLength").getAsInt();
    int operandFieldLength = fieldsObj.getAsJsonPrimitive("operandFieldLength").getAsInt();

    return new InstructionFieldsConfig(
            opcodeFieldLength, addressingModeFieldLength, operandFieldLength);
  }

  /**
   * Reads the instruction configuration map from the given JsonObject and creates a HashMap
   * containing InstructionConfig objects with the extracted data.
   *
   * @param configObj The JsonObject containing the instruction configuration map.
   * @return A HashMap containing InstructionConfig objects with mnemonic
   *         as keys and the corresponding configuration data as values.
   */
  private HashMap<String, InstructionConfig> readInstructionConfigMap(JsonObject configObj) {
    HashMap<String, InstructionConfig> configHashMap = new HashMap<>();
    JsonArray instructionArray = configObj.getAsJsonArray("instructionConfig");

    for (int i = 0; i < instructionArray.size(); i++) {
      JsonObject instructionObj = instructionArray.get(i).getAsJsonObject();
      String mnemocode = instructionObj.getAsJsonPrimitive("mnemocode").getAsString();
      int opcode = instructionObj.getAsJsonPrimitive("opcode").getAsInt();
      int addressingModes = instructionObj.getAsJsonPrimitive("addressingModes").getAsInt();
      InstructionConfig instructionConfig = new InstructionConfig(
              mnemocode, opcode, addressingModes);
      configHashMap.put(mnemocode, instructionConfig);
    }
    return configHashMap;
  }
}
