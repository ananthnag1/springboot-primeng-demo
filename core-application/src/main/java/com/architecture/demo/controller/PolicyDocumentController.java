package com.architecture.demo.controller;

import com.architecture.demo.model.DocumentRequestDTO;
import com.architecture.demo.service.PolicyDocumentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class PolicyDocumentController {

    private final PolicyDocumentService documentService;

    public PolicyDocumentController(PolicyDocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateDocument(@Valid @RequestBody DocumentRequestDTO requestDto) {

        byte[] pdfContents = documentService.generateStampedPdf(requestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "processed-policy-document.pdf");
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(pdfContents.length)
                .body(pdfContents);
    }
}