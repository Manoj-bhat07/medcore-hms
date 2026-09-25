package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.PrescriptionRequestDTO;
import com.manoj.medcore.dto.PrescriptionResponseDTO;
import com.manoj.medcore.dto.PrescriptionUpdateDTO;
import com.manoj.medcore.exception.PatientNotFoundException;
import com.manoj.medcore.exception.PrescriptionNotFoundException;
import com.manoj.medcore.model.Patient;
import com.manoj.medcore.model.Prescription;
import com.manoj.medcore.repository.PatientRepository;
import com.manoj.medcore.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final PatientRepository patientRepository;

    public PrescriptionService(
            PrescriptionRepository prescriptionRepository,
            PatientRepository patientRepository) {

        this.prescriptionRepository = prescriptionRepository;
        this.patientRepository = patientRepository;
    }

    public PrescriptionResponseDTO createPrescription(
            PrescriptionRequestDTO requestDTO) {

        Patient patient =
                patientRepository.findById(requestDTO.getPatientId())
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + requestDTO.getPatientId()));

        Prescription prescription = new Prescription();

        prescription.setMedicine(requestDTO.getMedicine());
        prescription.setDosage(requestDTO.getDosage());
        prescription.setFrequency(requestDTO.getFrequency());
        prescription.setInstructions(requestDTO.getInstructions());
        prescription.setPrescriptionDate(
                requestDTO.getPrescriptionDate());
        prescription.setPatient(patient);

        Prescription savedPrescription =
                prescriptionRepository.save(prescription);

        return mapToResponse(savedPrescription);
    }

    public List<PrescriptionResponseDTO> getAllPrescriptions() {

        List<Prescription> prescriptions =
                prescriptionRepository.findAll();

        List<PrescriptionResponseDTO> responseList =
                new ArrayList<>();

        for (Prescription prescription : prescriptions) {
            responseList.add(mapToResponse(prescription));
        }

        return responseList;
    }

    public PrescriptionResponseDTO getPrescriptionById(Long id) {

        Prescription prescription =
                prescriptionRepository.findById(id)
                        .orElseThrow(() ->
                                new PrescriptionNotFoundException(
                                        "Prescription not found with id: "
                                                + id));

        return mapToResponse(prescription);
    }

    public PrescriptionResponseDTO updatePrescription(
            Long id,
            PrescriptionUpdateDTO updateDTO) {

        Prescription prescription =
                prescriptionRepository.findById(id)
                        .orElseThrow(() ->
                                new PrescriptionNotFoundException(
                                        "Prescription not found with id: "
                                                + id));

        Patient patient =
                patientRepository.findById(updateDTO.getPatientId())
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + updateDTO.getPatientId()));

        prescription.setMedicine(updateDTO.getMedicine());
        prescription.setDosage(updateDTO.getDosage());
        prescription.setFrequency(updateDTO.getFrequency());
        prescription.setInstructions(updateDTO.getInstructions());
        prescription.setPrescriptionDate(
                updateDTO.getPrescriptionDate());
        prescription.setPatient(patient);

        Prescription updatedPrescription =
                prescriptionRepository.save(prescription);

        return mapToResponse(updatedPrescription);
    }

    public void deletePrescription(Long id) {

        Prescription prescription =
                prescriptionRepository.findById(id)
                        .orElseThrow(() ->
                                new PrescriptionNotFoundException(
                                        "Prescription not found with id: "
                                                + id));

        prescriptionRepository.delete(prescription);
    }

    private PrescriptionResponseDTO mapToResponse(
            Prescription prescription) {

        PrescriptionResponseDTO responseDTO =
                new PrescriptionResponseDTO();

        responseDTO.setId(prescription.getId());
        responseDTO.setMedicine(prescription.getMedicine());
        responseDTO.setDosage(prescription.getDosage());
        responseDTO.setFrequency(prescription.getFrequency());
        responseDTO.setInstructions(
                prescription.getInstructions());
        responseDTO.setPrescriptionDate(
                prescription.getPrescriptionDate());
        responseDTO.setPatientId(
                prescription.getPatient().getId());

        return responseDTO;
    }
}