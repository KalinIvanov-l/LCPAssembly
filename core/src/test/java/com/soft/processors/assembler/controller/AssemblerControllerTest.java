package com.soft.processors.assembler.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AssemblerControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldLoadFileSuccessfully() throws Exception {
    var mockFile = new MockMultipartFile(
            "file",
            "filename.txt",
            MediaType.TEXT_PLAIN_VALUE,
            "Mock file content".getBytes()
    );

    mockMvc.perform(multipart("/assembler/loadFile")
                    .file(mockFile))
            .andExpect(status().isOk())
            .andExpect(content().string("Mock file content"));
  }

  @Test
  void shouldLoadFileCorrectly() throws Exception {
    var fileContent = "some assembly code";
    var file = new MockMultipartFile("file", "test.asm", "text/plain", fileContent.getBytes());

    mockMvc.perform(MockMvcRequestBuilders.multipart("/assembler/loadFile")
                    .file(file))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string(fileContent));
  }
}