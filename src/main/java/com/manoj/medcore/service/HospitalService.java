package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.HospitalRequestDTO;
import com.manoj.medcore.dto.HospitalResponseDTO;
import com.manoj.medcore.dto.HospitalUpdateDTO;
import com.manoj.medcore.exception.HospitalNotFoundException;
import com.manoj.medcore.model.Hospital;
import com.manoj.medcore.repository.HospitalRepository;

@Service
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public HospitalService(HospitalRepository hospitalRepository) {
        this.hospitalRepository = hospitalRepository;
    }

    public HospitalResponseDTO createHospital(HospitalRequestDTO requestDTO) {
        Hospital hospital = new Hospital();
        hospital.setName(requestDTO.getName());
        hospital.setCity(requestDTO.getCity());

        Hospital savedHospital = hospitalRepository.save(hospital);
        return mapToResponse(savedHospital);
    }

    public List<HospitalResponseDTO> getAllHospitals() {
        List<Hospital> hospitals = hospitalRepository.findAll();
        List<HospitalResponseDTO> responseList = new ArrayList<>();

        for (Hospital hospital : hospitals) {
            responseList.add(mapToResponse(hospital));
        }

        return responseList;
    }

    public HospitalResponseDTO getHospitalById(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
               .orElseThrow(() ->
        new HospitalNotFoundException("Hospital not found with id: " + id));
        return mapToResponse(hospital);
    }

    public HospitalResponseDTO updateHospital(Long id, HospitalUpdateDTO updateDTO) {
        Hospital hospital = hospitalRepository.findById(id)
                .orElseThrow(() ->
        new HospitalNotFoundException("Hospital not found with id: " + id));

        if (updateDTO.getName() != null) {
            hospital.setName(updateDTO.getName());
        }

        if (updateDTO.getCity() != null) {
            hospital.setCity(updateDTO.getCity());
        }

        Hospital updatedHospital = hospitalRepository.save(hospital);
        return mapToResponse(updatedHospital);
    }

    public void deleteHospital(Long id) {
        Hospital hospital = hospitalRepository.findById(id)
              .orElseThrow(() ->
        new HospitalNotFoundException("Hospital not found with id: " + id));

        hospitalRepository.delete(hospital);
    }

    private HospitalResponseDTO mapToResponse(Hospital hospital) {
        HospitalResponseDTO responseDTO = new HospitalResponseDTO();
        responseDTO.setId(hospital.getId());
        responseDTO.setName(hospital.getName());
        responseDTO.setCity(hospital.getCity());
        return responseDTO;
    }
}