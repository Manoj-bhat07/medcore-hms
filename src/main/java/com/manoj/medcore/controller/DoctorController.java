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

import com.manoj.medcore.dto.DoctorRequestDTO;
import com.manoj.medcore.dto.DoctorResponseDTO;
import com.manoj.medcore.dto.DoctorUpdateDTO;
import com.manoj.medcore.service.DoctorService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    // CREATE
    @PostMapping
    public ResponseEntity<DoctorResponseDTO> createDoctor(
            @Valid @RequestBody DoctorRequestDTO requestDTO) {

        DoctorResponseDTO responseDTO =
                doctorService.createDoctor(requestDTO);

        return ResponseEntity
                .status(201)
                .body(responseDTO);
    }

    // GET ALL
    @GetMapping
    public List<DoctorResponseDTO> getDoctors() {

        return doctorService.getAllDoctors();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> getDoctorById(
            @PathVariable Long id) {

        DoctorResponseDTO responseDTO =
                doctorService.getDoctorById(id);

        return ResponseEntity.ok(responseDTO);
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> updateDoctor(
            @PathVariable Long id,
            @Valid @RequestBody DoctorUpdateDTO updateDTO) {

        DoctorResponseDTO responseDTO =
                doctorService.updateDoctor(id, updateDTO);

        return ResponseEntity.ok(responseDTO);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDoctor(
            @PathVariable Long id) {

        doctorService.deleteDoctor(id);

        return ResponseEntity.ok(
                "Doctor with ID " + id + " deleted");
    }
}