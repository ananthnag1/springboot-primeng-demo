package com.architecture.demo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DocumentRequestDTO {

    @NotBlank(message = "Client name is required")
    private String clientName;

    private String dependentName;
    private String dependentAge;
}