package com.architecture.demo.model;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentRequestDTO {

    @NotBlank(message = "Client Name is mandatory")
    private String clientName;

    @NotBlank(message = "Dependent Name is mandatory")
    private String dependentName;
    private String dependentAge;
}