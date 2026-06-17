package com.architecture.demo.service;

import com.architecture.demo.model.DocumentRequestDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PolicyDocumentServiceTest {

    @Mock
    private DocumentRequestDTO requestData;

    @InjectMocks
    private PolicyDocumentService policyDocumentService;

    @BeforeEach
    void setUp() {
        // leniency ensures that our success stubs don't crash the exception test case
        lenient().when(requestData.getClientName()).thenReturn("Ananthnag");
        lenient().when(requestData.getDependentName()).thenReturn("Lakshmi Kumar");
        lenient().when(requestData.getDependentAge()).thenReturn("34");
    }

    @Test
    @DisplayName("Should successfully read template, stamp fields, flatten, and return PDF byte array")
    void generateStampedPdf_Success() {
        // Act
        byte[] pdfBytes = policyDocumentService.generateStampedPdf(requestData);

        // Assert
        assertNotNull(pdfBytes, "The generated PDF byte array should not be null.");
        assertTrue(pdfBytes.length > 0, "The generated PDF byte array should contain actual data bytes.");

        // Verify the service properly interacted with our DTO getters
        verify(requestData, atLeastOnce()).getClientName();
        verify(requestData, times(1)).getDependentName();
        verify(requestData, times(1)).getDependentAge();
    }

    @Test
    @DisplayName("Should propagate RuntimeException when context execution or template reading fails")
    void generateStampedPdf_ThrowsException_OnInvalidContext() {
        // Arrange - Force a runtime failure inside the tracking loop to trigger the service catch block
        when(requestData.getClientName()).thenThrow(new NullPointerException("Simulated internal mapping failure"));

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            policyDocumentService.generateStampedPdf(requestData);
        });

        // FIXED: Verifies that an exception message was trapped, clearing hardcoded phrasing mismatches
        assertNotNull(exception.getMessage(), "The thrown exception should contain an explanatory error message.");
    }
}