package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.HospitalPageResponseDTO;
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

    public List<HospitalResponseDTO> searchHospitalsByCity(String city) {
        List<Hospital> hospitals = hospitalRepository.findByCityIgnoreCase(city);
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

    public HospitalPageResponseDTO getHospitalsPage(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page number cannot be negative");
        }

        if (size <= 0) {
            throw new IllegalArgumentException("Page size must be greater than zero");
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Hospital> hospitalPage = hospitalRepository.findAll(pageable);

        List<HospitalResponseDTO> content = new ArrayList<>();
        for (Hospital hospital : hospitalPage.getContent()) {
            content.add(mapToResponse(hospital));
        }

        HospitalPageResponseDTO responseDTO = new HospitalPageResponseDTO();
        responseDTO.setContent(content);
        responseDTO.setPage(hospitalPage.getNumber());
        responseDTO.setSize(hospitalPage.getSize());
        responseDTO.setTotalElements(hospitalPage.getTotalElements());
        responseDTO.setTotalPages(hospitalPage.getTotalPages());

        return responseDTO;
    }

    private HospitalResponseDTO mapToResponse(Hospital hospital) {
        HospitalResponseDTO responseDTO = new HospitalResponseDTO();
        responseDTO.setId(hospital.getId());
        responseDTO.setName(hospital.getName());
        responseDTO.setCity(hospital.getCity());
        return responseDTO;
    }
}