package com.soft.processors.assembler;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * This class initializes the Spring Boot application context and runs the application.
 *
 * @author kalin
 */
@SpringBootApplication
public class AssemblerApplication {
  public static void main(String[] args) throws IOException {
    SpringApplication.run(AssemblerApplication.class, args);
    LcpAssembler.assemble("src/main/resources/test.txt");
  }
}