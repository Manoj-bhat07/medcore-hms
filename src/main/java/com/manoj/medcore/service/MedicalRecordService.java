package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.MedicalRecordRequestDTO;
import com.manoj.medcore.dto.MedicalRecordResponseDTO;
import com.manoj.medcore.dto.MedicalRecordUpdateDTO;
import com.manoj.medcore.exception.MedicalRecordNotFoundException;
import com.manoj.medcore.exception.PatientNotFoundException;
import com.manoj.medcore.model.MedicalRecord;
import com.manoj.medcore.model.Patient;
import com.manoj.medcore.repository.MedicalRecordRepository;
import com.manoj.medcore.repository.PatientRepository;

@Service
public class MedicalRecordService {

    private final MedicalRecordRepository medicalRecordRepository;
    private final PatientRepository patientRepository;

    public MedicalRecordService(
            MedicalRecordRepository medicalRecordRepository,
            PatientRepository patientRepository) {

        this.medicalRecordRepository = medicalRecordRepository;
        this.patientRepository = patientRepository;
    }

    public MedicalRecordResponseDTO createMedicalRecord(
            MedicalRecordRequestDTO requestDTO) {

        Patient patient =
                patientRepository.findById(requestDTO.getPatientId())
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + requestDTO.getPatientId()));

        MedicalRecord medicalRecord = new MedicalRecord();

        medicalRecord.setDiagnosis(requestDTO.getDiagnosis());
        medicalRecord.setSymptoms(requestDTO.getSymptoms());
        medicalRecord.setNotes(requestDTO.getNotes());
        medicalRecord.setRecordDate(requestDTO.getRecordDate());
        medicalRecord.setPatient(patient);

        MedicalRecord savedRecord =
                medicalRecordRepository.save(medicalRecord);

        return mapToResponse(savedRecord);
    }

    public List<MedicalRecordResponseDTO> getAllMedicalRecords() {

        List<MedicalRecord> records =
                medicalRecordRepository.findAll();

        List<MedicalRecordResponseDTO> responseList =
                new ArrayList<>();

        for (MedicalRecord record : records) {
            responseList.add(mapToResponse(record));
        }

        return responseList;
    }

    public MedicalRecordResponseDTO getMedicalRecordById(Long id) {

        MedicalRecord record =
                medicalRecordRepository.findById(id)
                        .orElseThrow(() ->
                                new MedicalRecordNotFoundException(
                                        "Medical record not found with id: "
                                                + id));

        return mapToResponse(record);
    }

    public MedicalRecordResponseDTO updateMedicalRecord(
            Long id,
            MedicalRecordUpdateDTO updateDTO) {

        MedicalRecord record =
                medicalRecordRepository.findById(id)
                        .orElseThrow(() ->
                                new MedicalRecordNotFoundException(
                                        "Medical record not found with id: "
                                                + id));

        Patient patient =
                patientRepository.findById(updateDTO.getPatientId())
                        .orElseThrow(() ->
                                new PatientNotFoundException(
                                        "Patient not found with id: "
                                                + updateDTO.getPatientId()));

        record.setDiagnosis(updateDTO.getDiagnosis());
        record.setSymptoms(updateDTO.getSymptoms());
        record.setNotes(updateDTO.getNotes());
        record.setRecordDate(updateDTO.getRecordDate());
        record.setPatient(patient);

        MedicalRecord updatedRecord =
                medicalRecordRepository.save(record);

        return mapToResponse(updatedRecord);
    }

    public void deleteMedicalRecord(Long id) {

        MedicalRecord record =
                medicalRecordRepository.findById(id)
                        .orElseThrow(() ->
                                new MedicalRecordNotFoundException(
                                        "Medical record not found with id: "
                                                + id));

        medicalRecordRepository.delete(record);
    }

    private MedicalRecordResponseDTO mapToResponse(
            MedicalRecord record) {

        MedicalRecordResponseDTO responseDTO =
                new MedicalRecordResponseDTO();

        responseDTO.setId(record.getId());
        responseDTO.setDiagnosis(record.getDiagnosis());
        responseDTO.setSymptoms(record.getSymptoms());
        responseDTO.setNotes(record.getNotes());
        responseDTO.setRecordDate(record.getRecordDate());
        responseDTO.setPatientId(
                record.getPatient().getId());

        return responseDTO;
    }
}