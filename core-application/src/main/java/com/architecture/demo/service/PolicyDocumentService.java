package com.architecture.demo.service;

import com.architecture.demo.model.DocumentRequestDTO;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

@Service
public class PolicyDocumentService {

    private static final Logger logger = LoggerFactory.getLogger(PolicyDocumentService.class);

    public byte[] generateStampedPdf(DocumentRequestDTO requestData) {
        logger.info("Starting generation engine for client: {}", requestData.getClientName());

        try (InputStream templateStream = getClass().getResourceAsStream("/templates/test-form.pdf")) {

            if (templateStream == null) {
                throw new IllegalStateException("Template resource 'test-form.pdf' could not be found on classpath.");
            }

            try (PDDocument document = Loader.loadPDF(templateStream.readAllBytes())) {
                PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

                if (acroForm != null) {
                    // Mapping matching your discovered form fields
                    setFieldIfExists(acroForm, "Name", requestData.getClientName());
                    setFieldIfExists(acroForm, "Name of Dependent", requestData.getDependentName());
                    setFieldIfExists(acroForm, "Age\t of Dependent", requestData.getDependentAge());

                    logger.debug("Flattening forms fields to secure layout.");
                    acroForm.flatten();
                }

                try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                    document.save(baos);
                    logger.info("PDF document successfully compiled into memory payload.");
                    return baos.toByteArray();
                }
            }
        } catch (Exception e) {
            logger.error("Failed to generate stamped PDF document matching request profile", e);
            throw new RuntimeException("Error processing document template engine generation context", e);
        }
    }

    private void setFieldIfExists(PDAcroForm acroForm, String fieldName, String value) {
        try {
            PDField field = acroForm.getField(fieldName);
            if (field != null && value != null) {
                field.setValue(value);
            } else {
                logger.warn("Skipping field mapping context: Matchcode metadata label '{}' not found in template.", fieldName);
            }
        } catch (Exception e) {
            logger.error("Failed to write data block into field target key: {}", fieldName, e);
        }
    }
}