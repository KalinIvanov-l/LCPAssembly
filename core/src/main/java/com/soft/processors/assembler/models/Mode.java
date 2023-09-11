package com.soft.processors.assembler.models;

/**
 * This enum implements the ModeOption interface.
 * It provides three modes: DEFAULT, ABSOLUTE, and IMMEDIATE.
 * Instructions can use these modes to determine their behavior.
 *
 * @see ModeOption
 * @author kalin
 */
public enum Mode implements ModeOption {
  DEFAULT,
  ABSOLUTE,
  IMMEDIATE
}
