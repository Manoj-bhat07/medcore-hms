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
import com.manoj.medcore.model.Hospital;
import com.manoj.medcore.service.HospitalService;

@RestController
@RequestMapping("/hospitals")
public class HospitalController {
private final HospitalService hospitalService;

public HospitalController(HospitalService hospitalService) {
    this.hospitalService = hospitalService;
}


  @PostMapping
public ResponseEntity<HospitalResponseDTO> createHospital(
        @RequestBody HospitalRequestDTO hospitalDTO) {

  Hospital createdHospital =
        hospitalService.createHospital(hospitalDTO);

    HospitalResponseDTO responseDTO = new HospitalResponseDTO();

    responseDTO.setId(createdHospital.getId());
    responseDTO.setName(createdHospital.getName());
    responseDTO.setCity(createdHospital.getCity());

    return ResponseEntity.status(201).body(responseDTO);
}

   @GetMapping
public List<HospitalResponseDTO> getHospitals() {
    return hospitalService.getHospitalResponses();
}
    @GetMapping("/search")
    public String searchHospital(@RequestParam String city) {
    return "Searching hospitals in " + city;
    }
    
        @PutMapping("/{id}")
public ResponseEntity<HospitalResponseDTO> updateHospital(
        @PathVariable Long id,
        @RequestBody HospitalUpdateDTO updatedHospitalDTO) {

    Hospital hospital = hospitalService.updateHospital(id, updatedHospitalDTO);

    if (hospital == null) {
        return ResponseEntity.notFound().build();
    }

    HospitalResponseDTO responseDTO = new HospitalResponseDTO();

    responseDTO.setId(hospital.getId());
    responseDTO.setName(hospital.getName());
    responseDTO.setCity(hospital.getCity());

    return ResponseEntity.ok(responseDTO);
}

        @GetMapping("/{id}")
public ResponseEntity<HospitalResponseDTO> getHospitalById(
        @PathVariable Long id) {

    Hospital hospital = hospitalService.getHospitalById(id);

    if (hospital == null) {
        return ResponseEntity.notFound().build();
    }

    HospitalResponseDTO responseDTO = new HospitalResponseDTO();

    responseDTO.setId(hospital.getId());
    responseDTO.setName(hospital.getName());
    responseDTO.setCity(hospital.getCity());

    return ResponseEntity.ok(responseDTO);
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
