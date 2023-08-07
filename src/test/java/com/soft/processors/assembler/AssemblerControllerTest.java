package com.soft.processors.assembler;

import com.soft.processors.assembler.controllers.AssemblerController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AssemblerControllerTest {
  @InjectMocks
  private AssemblerController assemblerController;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void checkSaveFileSuccess() {
    String fileData = "some data";

    ResponseEntity<Map<String, String>> response = assemblerController.saveFile(fileData);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertTrue(response.getBody().containsKey("message"));
    assertEquals("File saved successfully.", response.getBody().get("message"));
  }
}
