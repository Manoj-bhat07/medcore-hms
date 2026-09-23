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

import com.manoj.medcore.dto.AppointmentRequestDTO;
import com.manoj.medcore.dto.AppointmentResponseDTO;
import com.manoj.medcore.dto.AppointmentUpdateDTO;
import com.manoj.medcore.service.AppointmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(
            AppointmentService appointmentService) {

        this.appointmentService = appointmentService;
    }

    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> createAppointment(
            @Valid @RequestBody AppointmentRequestDTO requestDTO) {

        AppointmentResponseDTO responseDTO =
                appointmentService.createAppointment(requestDTO);

        return ResponseEntity
                .status(201)
                .body(responseDTO);
    }

    @GetMapping
    public List<AppointmentResponseDTO> getAppointments() {

        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> getAppointmentById(
            @PathVariable Long id) {

        AppointmentResponseDTO responseDTO =
                appointmentService.getAppointmentById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppointmentResponseDTO> updateAppointment(
            @PathVariable Long id,
            @Valid @RequestBody AppointmentUpdateDTO updateDTO) {

        AppointmentResponseDTO responseDTO =
                appointmentService.updateAppointment(id, updateDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAppointment(
            @PathVariable Long id) {

        appointmentService.deleteAppointment(id);

        return ResponseEntity.ok(
                "Appointment with ID " + id + " deleted");
    }
}