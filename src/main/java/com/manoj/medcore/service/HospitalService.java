package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.model.Hospital;

@Service
public class HospitalService {

    private List<Hospital> hospitals = new ArrayList<>();
     private Long nextId = 1L;
    
    public Hospital createHospital(Hospital hospital) {

        hospital.setId(nextId++);

        hospitals.add(hospital);

        return hospital;
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

public Hospital updateHospital(Long id, Hospital updatedHospital) {

    for (Hospital hospital : hospitals) {

        if (hospital.getId().equals(id)) {

            hospital.setName(updatedHospital.getName());
            hospital.setCity(updatedHospital.getCity());

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