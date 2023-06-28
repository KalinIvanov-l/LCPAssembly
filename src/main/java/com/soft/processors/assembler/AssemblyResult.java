package com.soft.processors.assembler;

public class AssemblyResult {
    private final String listing;
    private final String outputFile;
    private String logs;

    public AssemblyResult(String listing, String outputFile) {
        this.listing = listing;
        this.outputFile = outputFile;
    }

    public AssemblyResult(String listing, String outputFile, String logs) {
        this.listing = listing;
        this.outputFile = outputFile;
        this.logs = logs;
    }

    public String getListing() {
        return listing;
    }

    public String getOutputFile() {
        return outputFile;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }
}