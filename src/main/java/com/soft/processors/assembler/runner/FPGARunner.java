package com.soft.processors.assembler.runner;

import com.soft.processors.assembler.LCPAssembler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author kalin
 */
@SpringBootApplication
public class FPGARunner {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(FPGARunner.class);
        LCPAssembler.assemble("src/main/resources/test.txt");
    }
}
