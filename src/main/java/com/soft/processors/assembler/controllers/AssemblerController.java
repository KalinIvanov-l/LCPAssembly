package com.soft.processors.assembler.controllers;

import com.soft.processors.assembler.LCPAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssemblerController {

    @PostMapping("/assemble")
    public ResponseEntity<String> assemble(@RequestBody String fileName) {
        try {
            LCPAssembler.assemble(fileName);
            return ResponseEntity.ok("Assembly completed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred during assembly: " + e.getMessage());
        }
    }
}
