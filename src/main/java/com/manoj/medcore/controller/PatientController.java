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

import com.manoj.medcore.dto.PatientRequestDTO;
import com.manoj.medcore.dto.PatientResponseDTO;
import com.manoj.medcore.dto.PatientUpdateDTO;
import com.manoj.medcore.service.PatientService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<PatientResponseDTO> createPatient(
            @Valid @RequestBody PatientRequestDTO requestDTO) {

        PatientResponseDTO responseDTO =
                patientService.createPatient(requestDTO);

        return ResponseEntity
                .status(201)
                .body(responseDTO);
    }

    // GET ALL
    @GetMapping
    public List<PatientResponseDTO> getPatients() {

        return patientService.getAllPatients();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> getPatientById(
            @PathVariable Long id) {

        PatientResponseDTO responseDTO =
                patientService.getPatientById(id);

        return ResponseEntity.ok(responseDTO);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<PatientResponseDTO> updatePatient(
            @PathVariable Long id,
            @Valid @RequestBody PatientUpdateDTO updateDTO) {

        PatientResponseDTO responseDTO =
                patientService.updatePatient(id, updateDTO);

        return ResponseEntity.ok(responseDTO);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePatient(
            @PathVariable Long id) {

        patientService.deletePatient(id);

        return ResponseEntity.ok(
                "Patient with ID " + id + " deleted");
    }
}