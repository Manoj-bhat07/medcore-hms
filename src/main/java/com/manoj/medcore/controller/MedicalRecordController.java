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

import com.manoj.medcore.dto.MedicalRecordRequestDTO;
import com.manoj.medcore.dto.MedicalRecordResponseDTO;
import com.manoj.medcore.dto.MedicalRecordUpdateDTO;
import com.manoj.medcore.service.MedicalRecordService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/medical-records")
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    public MedicalRecordController(
            MedicalRecordService medicalRecordService) {

        this.medicalRecordService = medicalRecordService;
    }

    @PostMapping
    public ResponseEntity<MedicalRecordResponseDTO> createMedicalRecord(
            @Valid @RequestBody MedicalRecordRequestDTO requestDTO) {

        MedicalRecordResponseDTO responseDTO =
                medicalRecordService.createMedicalRecord(requestDTO);

        return ResponseEntity
                .status(201)
                .body(responseDTO);
    }

    @GetMapping
    public List<MedicalRecordResponseDTO> getMedicalRecords() {

        return medicalRecordService.getAllMedicalRecords();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDTO> getMedicalRecordById(
            @PathVariable Long id) {

        MedicalRecordResponseDTO responseDTO =
                medicalRecordService.getMedicalRecordById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MedicalRecordResponseDTO> updateMedicalRecord(
            @PathVariable Long id,
            @Valid @RequestBody MedicalRecordUpdateDTO updateDTO) {

        MedicalRecordResponseDTO responseDTO =
                medicalRecordService.updateMedicalRecord(id, updateDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMedicalRecord(
            @PathVariable Long id) {

        medicalRecordService.deleteMedicalRecord(id);

        return ResponseEntity.ok(
                "Medical record with ID " + id + " deleted");
    }
}