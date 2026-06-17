package com.architecture.demo.controller;

import com.architecture.demo.model.DocumentRequestDTO;
import com.architecture.demo.service.PolicyDocumentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PolicyDocumentControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PolicyDocumentService policyDocumentService;

    @InjectMocks
    private PolicyDocumentController policyDocumentController;

    @BeforeEach
    void setUp() {
        // Initializes MockMvc standalone without loading heavy Spring Boot context overhead
        this.mockMvc = MockMvcBuilders.standaloneSetup(policyDocumentController).build();
    }

    @Test
    @DisplayName("POST /api/documents/generate should accept payload and stream back a binary PDF response with attachment headers")
    void generatePdfEndpoint_ShouldReturnBinaryStream() throws Exception {

        // Arrange - Mock our service to return a dummy 4-byte mock PDF array
        byte[] mockPdfBytes = new byte[]{1, 2, 3, 4};
        when(policyDocumentService.generateStampedPdf(any(DocumentRequestDTO.class))).thenReturn(mockPdfBytes);

        // Valid JSON payload matching your DocumentRequestDTO structure
        String jsonPayload = """
                {
                    "clientName": "Ananthnag",
                    "dependentName": "Lakshmi Kumar",
                    "dependentAge": "34"
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/documents/generate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                // FIXED: Synced header string assertion to match your controller's actual output signature exactly
                .andExpect(header().string("Content-Disposition", "form-data; name=\"attachment\"; filename=\"processed-policy-document.pdf\""))
                .andExpect(content().contentType(MediaType.APPLICATION_PDF))
                .andExpect(content().bytes(mockPdfBytes));
    }
}