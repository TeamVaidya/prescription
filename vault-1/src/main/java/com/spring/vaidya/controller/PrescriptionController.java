package com.spring.vaidya.controller;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spring.vaidya.entity.ErrorResponse;
import com.spring.vaidya.entity.Prescription;
import com.spring.vaidya.exception.FileStorageException;
import com.spring.vaidya.exception.PrescriptionNotFoundException;
import com.spring.vaidya.service.PrescriptionService;
import io.swagger.v3.oas.annotations.*;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Prescription Controller", description = "APIs for managing prescriptions")
@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "http://localhost:5173")
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Prescription creation and management API", description = "APIs for creating and managing prescriptions"))
public class PrescriptionController {

    private static final Logger logger = LoggerFactory.getLogger(PrescriptionController.class);

    @Autowired
    private PrescriptionService prescriptionService;

    @Operation(summary = "Create a new prescription", description = "Saves a new prescription in the system")
    @PostMapping("/post")
    public ResponseEntity<?> createPrescription(@RequestBody Prescription prescription) {
        logger.info("Creating prescription: {}", prescription);
        try {
            Prescription savedPrescription = prescriptionService.createPrescription(prescription);
            logger.info("Prescription created successfully: {}", savedPrescription);
            return new ResponseEntity<>(savedPrescription, HttpStatus.CREATED);
        } catch (FileNotFoundException e) {
            logger.error("File not found: {}", e.getMessage());
            throw new FileStorageException("Prescription file not found: " + e.getMessage());
        } catch (Exception e) {
            logger.error("Error creating prescription: {}", e.getMessage());
            return buildErrorResponse("An error occurred while creating the prescription.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Update an existing prescription", description = "Updates the details of an existing prescription")
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        logger.info("Updating prescription with ID: {}", id);
        try {
            Prescription updatedPrescription = prescriptionService.updatePrescription(id, prescription);
            logger.info("Prescription updated successfully: {}", updatedPrescription);
            return ResponseEntity.ok(updatedPrescription);
        } catch (PrescriptionNotFoundException e) {
            logger.warn("Prescription not found: {}", e.getMessage());
            return buildErrorResponse(e.getMessage(), e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error updating prescription: {}", e.getMessage());
            return buildErrorResponse("Error updating prescription.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get prescription by ID", description = "Fetches a prescription by its unique ID")
    @GetMapping("/{id}")
    public ResponseEntity<?> getPrescriptionById(@PathVariable Long id) {
        logger.info("Fetching prescription with ID: {}", id);
        try {
            Prescription prescription = prescriptionService.getPrescriptionById(id);
            logger.info("Prescription found: {}", prescription);
            return ResponseEntity.ok(prescription);
        } catch (PrescriptionNotFoundException e) {
            logger.warn("Prescription not found: {}", e.getMessage());
            return buildErrorResponse(e.getMessage(), e, HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Get prescriptions by user ID and date", description = "Fetches all prescriptions for a specific user on a given date")
    @GetMapping("/user/{userId}/date/{date}")
    public ResponseEntity<?> getPrescriptionsByUserIdAndDate(@PathVariable Long userId, @PathVariable String date) {
        logger.info("Fetching prescriptions for user ID: {} on date: {}", userId, date);
        try {
            LocalDate parsedDate = LocalDate.parse(date);
            List<Prescription> prescriptions = prescriptionService.getPrescriptionsByUserIdAndDate(userId, parsedDate);

            if (prescriptions.isEmpty()) {
                logger.warn("No prescriptions found for user ID: {} on date: {}", userId, date);
                return buildErrorResponse("No prescriptions found for the given user and date.", null, HttpStatus.NO_CONTENT);
            }
            logger.info("Prescriptions retrieved successfully.");
            return ResponseEntity.ok(prescriptions);
        } catch (Exception e) {
            logger.error("Invalid date format or request: {}", e.getMessage());
            return buildErrorResponse("Invalid date format or request.", e, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete prescription by ID", description = "Removes a prescription from the system")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrescription(@PathVariable Long id) {
        logger.info("Deleting prescription with ID: {}", id);
        try {
            prescriptionService.deletePrescription(id);
            logger.info("Prescription deleted successfully.");
            return ResponseEntity.ok("Prescription deleted successfully.");
        } catch (PrescriptionNotFoundException e) {
            logger.warn("Prescription not found: {}", e.getMessage());
            return buildErrorResponse(e.getMessage(), e, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            logger.error("Error deleting prescription: {}", e.getMessage());
            return buildErrorResponse("Error deleting prescription.", e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Get all prescriptions", description = "Retrieves all available prescriptions")
    @GetMapping
    public ResponseEntity<?> getAllPrescriptions() {
        logger.info("Fetching all prescriptions");
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        if (prescriptions.isEmpty()) {
            logger.warn("No prescriptions found.");
            return buildErrorResponse("No prescriptions found.", null, HttpStatus.NO_CONTENT);
        }
        logger.info("Prescriptions retrieved successfully.");
        return ResponseEntity.ok(prescriptions);
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, Exception ex, HttpStatus status) {
        logger.error("Error response: {} - {}", status, message);
        return new ResponseEntity<>(
                new ErrorResponse(LocalDateTime.now(), status.value(), status.getReasonPhrase(), message + (ex != null ? " " + ex.getMessage() : "")),
                status);
    }
}
