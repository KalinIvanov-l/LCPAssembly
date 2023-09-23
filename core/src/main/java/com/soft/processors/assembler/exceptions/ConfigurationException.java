package com.soft.processors.assembler.exceptions;

/**
 * The ConfigurationException defines an Exception throws when a configuration file is wrong.
 *
 * @author kalin
 */
public class ConfigurationException extends Exception {

  /**
   * Constructor ConfigurationException.
   *
   * @param message the description message for exception
   */
  public ConfigurationException(String message) {
    super(message);
  }
}
