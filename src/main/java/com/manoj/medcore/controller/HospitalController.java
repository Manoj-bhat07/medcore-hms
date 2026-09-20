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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.manoj.medcore.dto.HospitalRequestDTO;
import com.manoj.medcore.dto.HospitalResponseDTO;
import com.manoj.medcore.dto.HospitalUpdateDTO;
import com.manoj.medcore.service.HospitalService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {

    private final HospitalService hospitalService;

    public HospitalController(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

   @PostMapping
public ResponseEntity<HospitalResponseDTO> createHospital(
        @Valid @RequestBody HospitalRequestDTO hospitalDTO) {

        HospitalResponseDTO responseDTO =
                hospitalService.createHospital(hospitalDTO);

        return ResponseEntity.status(201).body(responseDTO);
    }

    @GetMapping
    public List<HospitalResponseDTO> getHospitals() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/search")
    public String searchHospital(@RequestParam String city) {
        return "Searching hospitals in " + city;
    }

    @PutMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> updateHospital(
            @PathVariable Long id,
            @RequestBody HospitalUpdateDTO updatedHospitalDTO) {

        HospitalResponseDTO responseDTO =
                hospitalService.updateHospital(id, updatedHospitalDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HospitalResponseDTO> getHospitalById(
            @PathVariable Long id) {

        HospitalResponseDTO responseDTO =
                hospitalService.getHospitalById(id);

        return ResponseEntity.ok(responseDTO);
    }

  @DeleteMapping("/{id}")
public ResponseEntity<String> deleteHospital(@PathVariable Long id) {

    hospitalService.deleteHospital(id);

    return ResponseEntity.ok("Hospital with ID " + id + " deleted");
}
}