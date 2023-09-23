package com.soft.processors.assembler.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.soft.processors.assembler.exceptions.ConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
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
  Path configFilePath = Path.of("core/config.json");
  private InstructionFieldsConfig instructionFieldsConfig;
  private HashMap<String, InstructionConfig> instructionConfigMap;

  /**
   * Returns the instruction configuration for the specified mnemonic.
   *
   * @param key the mnemonic to look up
   * @return the instruction configuration, or null if the mnemonic is not found
   * @throws ConfigurationException if provide input is empty
   */
  public InstructionConfig getInstructionConfig(String key) throws ConfigurationException {
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
   *
   * @throws ConfigurationException if provide input is empty
   */
  private void validateInstructionConfigMap() throws ConfigurationException {
    if (instructionConfigMap.isEmpty()) {
      throw new ConfigurationException("Instruction configuration map cannot be empty.");
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
    String json = String.join("\n", Files.readAllLines(configFilePath));
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
    JsonArray instructionArray = configObj.getAsJsonArray("instructionConfig");

    List<JsonObject> instructionList = new ArrayList<>();
    instructionArray.forEach(element -> instructionList.add(element.getAsJsonObject()));

    return instructionList.stream()
            .collect(Collectors.toMap(
                    obj -> obj.getAsJsonPrimitive("mnemocode").getAsString(),
                    obj -> new InstructionConfig(
                            obj.getAsJsonPrimitive("mnemocode").getAsString(),
                            obj.getAsJsonPrimitive("opcode").getAsInt(),
                            obj.getAsJsonPrimitive("addressingModes").getAsInt()
                    ),
                    (existing, replacement) -> replacement,
                    HashMap::new
            ));
  }
}
