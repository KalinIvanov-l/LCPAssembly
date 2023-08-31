package com.soft.processors.assembler.controllers;

import com.soft.processors.assembler.LcpAssembler;
import com.soft.processors.assembler.models.AssemblyResult;
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

  /**
   * Assembles the specified file content and returns the assembly result.
   *
   * @param fileContent the content of the file.
   * @return assembly result
   */
  @PostMapping("/assemble")
  public ResponseEntity<Map<String, Object>> assemble(@RequestBody String fileContent)
          throws IOException {
    LOGGER.info("Received file content: {}", fileContent);
    Path tempFile = createTempFileFromContent(fileContent);
    LOGGER.info("Assembling the file: {}", tempFile);
    AssemblyResult assemblyResult = LcpAssembler.assemble(Path.of(tempFile.toString()));
    Files.delete(tempFile);
    LOGGER.info("Output file generated: {}", assemblyResult.getOutputFile());
    return createSuccessfulResponse(assemblyResult);
  }

  /**
   * Save the specified file.
   *
   * @param fileData the data of the file to save.
   * @return message
   */
  @PostMapping("/saveFile")
  public ResponseEntity<Map<String, String>> saveFile(@RequestBody String fileData)
          throws IOException {
    writeToFile(fileData, new FileOutputStream(TEST_FILE_PATH));
    return createResponseMessage();
  }

  /**
   * Loads file which contains example program and return its context.
   *
   * @return the loaded file
   * @throws IOException If an I/O error occurs while loading the file.
   */
  @GetMapping("/loadFile")
  public ResponseEntity<String> loadFile() throws IOException {
    return ResponseEntity.ok(loadFileContent());
  }

  /**
   * Creates a temporary file with the specified content and returns its path.
   *
   * @param fileContent The content to be written to the temporary file.
   * @return The path to the newly created temporary file.
   * @throws IOException If an I/O error occurs while creating or writing to the temporary file.
   */
  public Path createTempFileFromContent(String fileContent) throws IOException {
    Path tempFile = Files.createTempFile("assembler-", ".tmp");
    Files.writeString(tempFile, fileContent);
    return tempFile;
  }

  /**
   * Writes the provided data to a FileOutputStream, ensuring proper resource management.
   *
   * @param data The data to be written to the file.
   * @param fos The FileOutputStream to write the data to.
   *          It will be automatically closed(try with resources) after writing.
   * @throws IOException If an I/O error occurs while writing to the file.
   */
  public void writeToFile(String data, FileOutputStream fos) throws IOException {
    fos.write(data.getBytes());
  }

  /**
   * Loads the content of a given file, creating the file if it does not exist.
   *
   * @return The content of the file as a string.
   * @throws IOException If an I/O error occurs while loading or creating the file.
   */
  public String loadFileContent() throws IOException {
    Path path = Paths.get(TEST_FILE_PATH);
    LOGGER.info("Attempting to load file from a path: {}", path);
    if (!Files.exists(path)) {
      LOGGER.info("File does not exist, creating a new file at a path: {}", path);
      Files.createFile(path);
    }
    LOGGER.info("File loaded successfully");
    return Files.readString(path, StandardCharsets.UTF_8);
  }

  /**
   * Creates a ResponseEntity containing a successful response based on the provided AssemblyResult.
   *
   * @param assemblyResult containing listing, console output, and logs.
   * @return A ResponseEntity with a success status and a map containing assembled data.
   */
  private ResponseEntity<Map<String, Object>> createSuccessfulResponse(
      AssemblyResult assemblyResult) {
    Map<String, Object> response = new HashMap<>();
    response.put("listing", assemblyResult.getListing());
    response.put("consoleOutput", assemblyResult.getOutputFile());
    response.put("logs", assemblyResult.getLogs());
    return ResponseEntity.ok(response);
  }

  /**
   * Creates a ResponseEntity containing a response message indicating a successful operation.
   *
   * @return A ResponseEntity with a success status and a map containing a success message.
   */
  private ResponseEntity<Map<String, String>> createResponseMessage() {
    Map<String, String> response = new HashMap<>();
    response.put("message", "File saved successfully.");
    return ResponseEntity.ok(response);
  }
}