package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.HospitalRequestDTO;
import com.manoj.medcore.dto.HospitalResponseDTO;
import com.manoj.medcore.dto.HospitalUpdateDTO;
import com.manoj.medcore.model.Hospital;


@Service
public class HospitalService {

    private final List<Hospital> hospitals = new ArrayList<>();
     private Long nextId = 1L;


    public Hospital createHospital(HospitalRequestDTO hospitalDTO) {

    Hospital hospital = new Hospital();

    hospital.setName(hospitalDTO.getName());
    hospital.setCity(hospitalDTO.getCity());

    hospital.setId(nextId++);

    hospitals.add(hospital);

    return hospital;
    }
    public List<HospitalResponseDTO> getHospitalResponses() {

    List<HospitalResponseDTO> responseList = new ArrayList<>();

    for (Hospital hospital : hospitals) {

        HospitalResponseDTO responseDTO = new HospitalResponseDTO();

        responseDTO.setId(hospital.getId());
        responseDTO.setName(hospital.getName());
        responseDTO.setCity(hospital.getCity());

        responseList.add(responseDTO);
    }

    return responseList;
}

    public List<Hospital> getHospitals() {
        return hospitals;
    }


    public Hospital getHospitalById(Long id) {

    for (Hospital hospital : hospitals) {

        if (hospital.getId().equals(id)) {
            return hospital;
        }
    }

    return null;
}



    public Hospital updateHospital(Long id, HospitalUpdateDTO updatedHospitalDTO) {
    for (Hospital hospital : hospitals) {
        if (hospital.getId().equals(id)) {
            hospital.setName(updatedHospitalDTO.getName());
            hospital.setCity(updatedHospitalDTO.getCity());
            return hospital;
        }
    }
    return null;
}

public boolean deleteHospital(Long id) {

    Iterator<Hospital> iterator = hospitals.iterator();

    while (iterator.hasNext()) {

        Hospital hospital = iterator.next();

        if (hospital.getId().equals(id)) {
            iterator.remove();
            return true;
        }
    }

    return false;
}
}