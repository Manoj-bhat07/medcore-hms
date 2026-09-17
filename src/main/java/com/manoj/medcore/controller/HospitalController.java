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

import com.manoj.medcore.model.Hospital;
import com.manoj.medcore.service.HospitalService;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {
private final HospitalService hospitalService;

public HospitalController(HospitalService hospitalService) {
    this.hospitalService = hospitalService;
}
    private Hospital hospital;
    @PostMapping
public Hospital createHospital(@RequestBody Hospital hospital) {
    return hospitalService.createHospital(hospital);
}

    @GetMapping
public List<Hospital> getHospitals() {
    return hospitalService.getHospitals();
}
    @GetMapping("/search")
    public String searchHospital(@RequestParam String city) {
    return "Searching hospitals in " + city;
    }
    
@PutMapping("/{id}")
public ResponseEntity<Hospital> updateHospital(
        @PathVariable Long id,
        @RequestBody Hospital updatedHospital) {

    Hospital hospital = hospitalService.updateHospital(id, updatedHospital);

    if (hospital == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(hospital);
}

    @GetMapping("/{id}")
public ResponseEntity<Hospital> getHospitalById(@PathVariable Long id) {

    Hospital hospital = hospitalService.getHospitalById(id);

    if (hospital == null) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok(hospital);
}


@DeleteMapping("/{id}")
public ResponseEntity<String> deleteHospital(@PathVariable Long id) {

    boolean deleted = hospitalService.deleteHospital(id);

    if (!deleted) {
        return ResponseEntity.notFound().build();
    }

    return ResponseEntity.ok("Hospital with ID " + id + " deleted");
}
}
