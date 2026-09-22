package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.PatientRequestDTO;
import com.manoj.medcore.dto.PatientResponseDTO;
import com.manoj.medcore.dto.PatientUpdateDTO;
import com.manoj.medcore.exception.PatientNotFoundException;
import com.manoj.medcore.model.Patient;
import com.manoj.medcore.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    // CREATE
    public PatientResponseDTO createPatient(
            PatientRequestDTO requestDTO) {

        Patient patient = new Patient();

        patient.setName(requestDTO.getName());
        patient.setAge(requestDTO.getAge());
        patient.setGender(requestDTO.getGender());
        patient.setPhone(requestDTO.getPhone());
        patient.setEmail(requestDTO.getEmail());

        Patient savedPatient =
                patientRepository.save(patient);

        return mapToResponse(savedPatient);
    }

    // GET ALL
    public List<PatientResponseDTO> getAllPatients() {

        List<Patient> patients =
                patientRepository.findAll();

        List<PatientResponseDTO> responseList =
                new ArrayList<>();

        for (Patient patient : patients) {
            responseList.add(mapToResponse(patient));
        }

        return responseList;
    }

    // GET BY ID
    public PatientResponseDTO getPatientById(Long id) {

        Patient patient =
                patientRepository.findById(id)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + id));

        return mapToResponse(patient);
    }

    // UPDATE
    public PatientResponseDTO updatePatient(
            Long id,
            PatientUpdateDTO updateDTO) {

        Patient patient =
                patientRepository.findById(id)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + id));

        patient.setName(updateDTO.getName());
        patient.setAge(updateDTO.getAge());
        patient.setGender(updateDTO.getGender());
        patient.setPhone(updateDTO.getPhone());
        patient.setEmail(updateDTO.getEmail());

        Patient updatedPatient =
                patientRepository.save(patient);

        return mapToResponse(updatedPatient);
    }

    // DELETE
    public void deletePatient(Long id) {

        Patient patient =
                patientRepository.findById(id)
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + id));

        patientRepository.delete(patient);
    }

    // DTO MAPPING
    private PatientResponseDTO mapToResponse(
            Patient patient) {

        PatientResponseDTO responseDTO =
                new PatientResponseDTO();

        responseDTO.setId(patient.getId());
        responseDTO.setName(patient.getName());
        responseDTO.setAge(patient.getAge());
        responseDTO.setGender(patient.getGender());
        responseDTO.setPhone(patient.getPhone());
        responseDTO.setEmail(patient.getEmail());

        return responseDTO;
    }
}