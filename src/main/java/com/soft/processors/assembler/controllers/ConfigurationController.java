package com.soft.processors.assembler.controllers;

import com.soft.processors.assembler.configuration.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kalin
 */
@RestController
@RequestMapping("config")
public class ConfigurationController {
    private final Configuration configuration;

    @Autowired
    public ConfigurationController(Configuration configuration) {
        this.configuration = configuration;
    }

    @GetMapping
    public ResponseEntity<Configuration> getConfig() {
        return ResponseEntity.ok(configuration);
    }

    @GetMapping("/read")
    public ResponseEntity<String> readConfig() {
        configuration.readConfig();
        return ResponseEntity.ok("Configuration read successfully");
    }

    @GetMapping("/loadFile")
    public ResponseEntity<String> loadDefaultConfig() {
        configuration.loadDefaultConfig();
        return ResponseEntity.ok("Default configuration loaded");
    }
}
