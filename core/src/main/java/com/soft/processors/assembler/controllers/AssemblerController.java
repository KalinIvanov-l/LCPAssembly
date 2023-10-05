package com.soft.processors.assembler.controllers;

import com.soft.processors.assembler.LcpAssembler;
import com.soft.processors.assembler.exceptions.InvalidFileException;
import com.soft.processors.assembler.models.AssemblyResult;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
   * Loads file which contains example program and return its context.
   *
   * @param file The uploaded file.
   * @return the loaded file content
   * @throws IOException          If an I/O error occurs while loading the file.
   * @throws InvalidFileException if the file is empty
   */
  @PostMapping("/loadFile")
  public ResponseEntity<String> loadFile(@RequestParam("file") MultipartFile file)
          throws IOException, InvalidFileException {
    checkNotEmpty(file);
    String fileContent = new String(file.getBytes(), StandardCharsets.UTF_8);
    return ResponseEntity.ok(fileContent);
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
   * Check if the provided file is empty.
   *
   * @param file the loaded file content
   * @throws InvalidFileException if the file is empty
   */
  private void checkNotEmpty(MultipartFile file) throws InvalidFileException {
    if (file.isEmpty()) {
      throw new InvalidFileException("The Provided file is empty. Not file uploaded ");
    }

  }

  /**
   * Creates a ResponseEntity containing a successful response based on the provided AssemblyResult.
   *
   * @param assemblyResult containing listing, console output, and logs.
   * @return A ResponseEntity with a success status and a map containing assembled data.
   */
  private ResponseEntity<Map<String, Object>> createSuccessfulResponse(
          AssemblyResult assemblyResult) {

    Map<String, Object> response = Stream.of(
                    new AbstractMap.SimpleEntry<>("listing", assemblyResult.getListing()),
                    new AbstractMap.SimpleEntry<>("consoleOutput", assemblyResult.getOutputFile()),
                    new AbstractMap.SimpleEntry<>("logs", assemblyResult.getLogs())
            )
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    return ResponseEntity.ok(response);
  }
}