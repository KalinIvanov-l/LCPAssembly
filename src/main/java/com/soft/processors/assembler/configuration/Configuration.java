package com.soft.processors.assembler.configuration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

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
    private static final String CFG_FILE = "config.json";
    private InstructionFieldsConfig instructionFieldsConfig;
    private HashMap<String, InstructionConfig> instructionConfigMap;

    public InstructionConfig getInstructionConfig(String key) {
        if (instructionConfigMap.containsKey(key)) {
            return instructionConfigMap.get(key);
        }
        return null;
    }

    public InstructionFieldsConfig getInstructionFieldsConfig() {
        return instructionFieldsConfig;
    }

    /**
     * Read configuration from file [config.json]
     */
    public void readConfig() {
        instructionFieldsConfig = new InstructionFieldsConfig(3, 1, 8);
        instructionConfigMap = new HashMap<>();
        try {
            StringBuilder jsonBuilder;
            try (BufferedReader reader = new BufferedReader(new FileReader(
                    CFG_FILE))) {
                jsonBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    jsonBuilder.append(line);
                }
            }

            String json = jsonBuilder.toString();
            JsonObject configObj = JsonParser.parseString(json)
                    .getAsJsonObject();

            // Parse instruction fields config
            JsonObject fieldsObj = configObj
                    .getAsJsonObject("instructionFieldsConfig");
            instructionFieldsConfig = new InstructionFieldsConfig(0, 0, 0);
            instructionFieldsConfig.opcodeFieldLength = fieldsObj
                    .getAsJsonPrimitive("opcodeFieldLength").getAsInt();
            instructionFieldsConfig.addressingModeFieldLength = fieldsObj
                    .getAsJsonPrimitive("addressingModeFieldLength").getAsInt();
            instructionFieldsConfig.operandFieldLength = fieldsObj
                    .getAsJsonPrimitive("operandFieldLength").getAsInt();

            // Parse instruction config map
            instructionConfigMap = new HashMap<>();
            JsonArray instructionArray = configObj
                    .getAsJsonArray("instructionConfig");
            for (int i = 0; i < instructionArray.size(); i++) {
                JsonObject instructionObj = instructionArray.get(i)
                        .getAsJsonObject();
                InstructionConfig instructionConfig = new InstructionConfig("",
                        0, 0);
                instructionConfig.mnemocode = instructionObj
                        .getAsJsonPrimitive("mnemocode").getAsString();
                instructionConfig.opcode = instructionObj.getAsJsonPrimitive(
                        "opcode").getAsInt();
                instructionConfig.addressingModes = instructionObj
                        .getAsJsonPrimitive("addressingModes").getAsInt();
                instructionConfigMap.put(instructionConfig.mnemocode,
                        instructionConfig);
            }
            LOGGER.info("Configuration read: OK");
        } catch (IOException e) {
            LOGGER.error("Error reading configuration file: ");
        }
    }

    /**
     * Initializes the instructionFieldsConfig and instructionConfigMap with default values
     */
    public void loadDefaultConfig() {
        instructionFieldsConfig = new InstructionFieldsConfig(3, 1, 8);
        instructionConfigMap = new HashMap<>();
    }
}
