package com.soft.processors.assembler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * This AssemblyResult class provides listing from given instruction.
 *
 * @author kalin
 */
@Getter
@Setter
@AllArgsConstructor
public class AssemblyResult {
  private final String listing;
  private final String outputFile;
  private String logs;

  public AssemblyResult(String listing, String outputFile) {
    this.listing = listing;
    this.outputFile = outputFile;
  }
}