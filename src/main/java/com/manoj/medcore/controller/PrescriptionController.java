package com.manoj.medcore.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.medcore.dto.PrescriptionRequestDTO;
import com.manoj.medcore.dto.PrescriptionResponseDTO;
import com.manoj.medcore.dto.PrescriptionUpdateDTO;
import com.manoj.medcore.service.PrescriptionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/prescriptions")
public class PrescriptionController {

    private final PrescriptionService prescriptionService;

    public PrescriptionController(
            PrescriptionService prescriptionService) {

        this.prescriptionService = prescriptionService;
    }

    @PostMapping
    public ResponseEntity<PrescriptionResponseDTO> createPrescription(
            @Valid @RequestBody PrescriptionRequestDTO requestDTO) {

        PrescriptionResponseDTO responseDTO =
                prescriptionService.createPrescription(requestDTO);

        return ResponseEntity
                .status(201)
                .body(responseDTO);
    }

    @GetMapping
    public List<PrescriptionResponseDTO> getPrescriptions() {

        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDTO> getPrescriptionById(
            @PathVariable Long id) {

        PrescriptionResponseDTO responseDTO =
                prescriptionService.getPrescriptionById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PrescriptionResponseDTO> updatePrescription(
            @PathVariable Long id,
            @Valid @RequestBody PrescriptionUpdateDTO updateDTO) {

        PrescriptionResponseDTO responseDTO =
                prescriptionService.updatePrescription(id, updateDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePrescription(
            @PathVariable Long id) {

        prescriptionService.deletePrescription(id);

        return ResponseEntity.ok(
                "Prescription with ID " + id + " deleted");
    }
}