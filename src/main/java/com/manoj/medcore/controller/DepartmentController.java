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

import com.manoj.medcore.dto.DepartmentRequestDTO;
import com.manoj.medcore.dto.DepartmentResponseDTO;
import com.manoj.medcore.dto.DepartmentUpdateDTO;
import com.manoj.medcore.service.DepartmentService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(
            DepartmentService departmentService) {

        this.departmentService = departmentService;
    }

    @PostMapping
    public ResponseEntity<DepartmentResponseDTO> createDepartment(
            @Valid @RequestBody DepartmentRequestDTO requestDTO) {

        DepartmentResponseDTO responseDTO =
                departmentService.createDepartment(requestDTO);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping
    public List<DepartmentResponseDTO> getDepartments() {

        return departmentService.getAllDepartments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DepartmentResponseDTO> getDepartmentById(
            @PathVariable Long id) {

        DepartmentResponseDTO responseDTO =
                departmentService.getDepartmentById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
public ResponseEntity<DepartmentResponseDTO> updateDepartment(
        @PathVariable Long id,
        @Valid @RequestBody DepartmentUpdateDTO updateDTO) {

    DepartmentResponseDTO responseDTO =
            departmentService.updateDepartment(id, updateDTO);

    return ResponseEntity.ok(responseDTO);
}

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDepartment(
            @PathVariable Long id) {

        departmentService.deleteDepartment(id);

        return ResponseEntity.ok(
                "Department with ID " + id + " deleted");
    }
}