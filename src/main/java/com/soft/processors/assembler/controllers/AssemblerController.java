package com.soft.processors.assembler.controllers;

import com.soft.processors.assembler.AssemblyResult;
import com.soft.processors.assembler.LcpAssembler;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AssemblerController is a Spring Boot REST controller responsible for handling requests related to
 * assembling, saving, and loading files.
 *
 * @author kalin
 */
@RestController
@RequestMapping("/assembler")
public class AssemblerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(AssemblerController.class);
  private static final String TEST_FILE_PATH = "src/main/resources/test.txt";
  private static final String MESSAGE_ERROR = "error";

  public AssemblerController() {
    LOGGER.info("AssemblerController initialized");
  }

  /**
   * Assembles the specified file content and returns the assembly result.
   *
   * @param fileContent the content of the file.
   * @return assembly result
   */
  @PostMapping("/assemble")
  public ResponseEntity<Map<String, Object>> assemble(@RequestBody String fileContent) {
    LOGGER.info("Received file content: {}", fileContent);
    try {
      Path tempFile = createTempFileFromContent(fileContent);

      LOGGER.info("Assembling the file: {}", tempFile);

      AssemblyResult assemblyResult = LcpAssembler.assemble(tempFile.toString());
      Files.delete(tempFile);

      LOGGER.info("Output file generated: {}", assemblyResult.getOutputFile());

      Map<String, Object> response = new HashMap<>();
      response.put("listing", assemblyResult.getListing());
      response.put("consoleOutput", assemblyResult.getOutputFile());
      response.put("logs", assemblyResult.getLogs());
      return ResponseEntity.ok(response);
    } catch (IOException e) {
      LOGGER.error("Error processing the file: {}", e.getMessage(), e);
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put(MESSAGE_ERROR, "Error processing the file");
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    } catch (Exception e) {
      LOGGER.error("Error occurred during assembly: {}", e.getMessage(), e);
      Map<String, Object> errorResponse = new HashMap<>();
      errorResponse.put(MESSAGE_ERROR, "Error occurred during assembly: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
  }

  /**
   * Save the specified file.
   *
   * @param fileData the data of the file to save.
   * @return message
   */
  @PostMapping("/saveFile")
  public ResponseEntity<Map<String, String>> saveFile(@RequestBody String fileData) {
    try {
      try (FileOutputStream fos = new FileOutputStream(TEST_FILE_PATH)) {
        fos.write(fileData.getBytes());
      }
      Map<String, String> response = new HashMap<>();
      response.put("message", "File saved successfully.");
      return ResponseEntity.ok(response);
    } catch (IOException e) {
      Map<String, String> errorResponse = new HashMap<>();
      errorResponse.put(MESSAGE_ERROR, "Error occurred while saving the file: " + e.getMessage());
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
  }

  /**
   * Loads file which contains example program and return its context.
   *
   * @return the loaded file
   */
  @GetMapping("/loadFile")
  public ResponseEntity<String> loadFile() {
    LOGGER.info("Current working directory: {}", System.getProperty("user.dir"));

    try {
      Path path = Paths.get(TEST_FILE_PATH);
      LOGGER.info("Attempting to load file from path: {}", path);

      if (!Files.exists(path)) {
        LOGGER.info("File does not exist, creating a new file at path: {}", path);
        Files.createFile(path);
      }

      String fileContent = Files.readString(path, StandardCharsets.UTF_8);
      LOGGER.info("File loaded successfully");
      return ResponseEntity.ok(fileContent);
    } catch (IOException e) {
      LOGGER.error("Error occurred while loading the file: {}", e.getMessage(), e);
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
              "Error occurred while loading the file: " + e.getMessage());
    }
  }

  private Path createTempFileFromContent(String fileContent) throws IOException {
    Path tempFile = Files.createTempFile("assembler-", ".tmp");
    Files.writeString(tempFile, fileContent);
    return tempFile;
  }
}