package com.soft.processors.assembler.exceptions;

/**
 * The InvalidFileException defines an Exception throws when a provided file is empty.
 *
 * @author kalin
 */
public class InvalidFileException extends Exception {

  /**
   * Constructor InvalidFileException.
   *
   * @param message the description message for exception
   */
  public InvalidFileException(String message) {
    super(message);
  }
}
