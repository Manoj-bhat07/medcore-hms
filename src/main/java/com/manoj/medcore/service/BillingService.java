package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.BillingRequestDTO;
import com.manoj.medcore.dto.BillingResponseDTO;
import com.manoj.medcore.dto.BillingUpdateDTO;
import com.manoj.medcore.exception.BillingNotFoundException;
import com.manoj.medcore.exception.PatientNotFoundException;
import com.manoj.medcore.model.Billing;
import com.manoj.medcore.model.Patient;
import com.manoj.medcore.repository.BillingRepository;
import com.manoj.medcore.repository.PatientRepository;

@Service
public class BillingService {

    private final BillingRepository billingRepository;
    private final PatientRepository patientRepository;

    public BillingService(
            BillingRepository billingRepository,
            PatientRepository patientRepository) {

        this.billingRepository = billingRepository;
        this.patientRepository = patientRepository;
    }

    public BillingResponseDTO createBilling(
            BillingRequestDTO requestDTO) {

        Patient patient = patientRepository.findById(
                requestDTO.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient not found with id: "
                                + requestDTO.getPatientId()));

        Billing billing = new Billing();
        billing.setAmount(requestDTO.getAmount());
        billing.setPaymentStatus(requestDTO.getPaymentStatus());
        billing.setBillingDate(requestDTO.getBillingDate());
        billing.setDescription(requestDTO.getDescription());
        billing.setPatient(patient);

        Billing savedBilling = billingRepository.save(billing);

        return mapToResponse(savedBilling);
    }

    public List<BillingResponseDTO> getAllBillings() {

        List<Billing> billings = billingRepository.findAll();
        List<BillingResponseDTO> responseList = new ArrayList<>();

        for (Billing billing : billings) {
            responseList.add(mapToResponse(billing));
        }

        return responseList;
    }

    public BillingResponseDTO getBillingById(Long id) {

        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new BillingNotFoundException(
                        "Billing not found with id: " + id));

        return mapToResponse(billing);
    }

    public BillingResponseDTO updateBilling(
            Long id,
            BillingUpdateDTO updateDTO) {

        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new BillingNotFoundException(
                        "Billing not found with id: " + id));

        Patient patient = patientRepository.findById(
                updateDTO.getPatientId())
                .orElseThrow(() -> new PatientNotFoundException(
                        "Patient not found with id: "
                                + updateDTO.getPatientId()));

        billing.setAmount(updateDTO.getAmount());
        billing.setPaymentStatus(updateDTO.getPaymentStatus());
        billing.setBillingDate(updateDTO.getBillingDate());
        billing.setDescription(updateDTO.getDescription());
        billing.setPatient(patient);

        Billing updatedBilling = billingRepository.save(billing);

        return mapToResponse(updatedBilling);
    }

    public void deleteBilling(Long id) {

        Billing billing = billingRepository.findById(id)
                .orElseThrow(() -> new BillingNotFoundException(
                        "Billing not found with id: " + id));

        billingRepository.delete(billing);
    }

    private BillingResponseDTO mapToResponse(Billing billing) {

        BillingResponseDTO responseDTO = new BillingResponseDTO();

        responseDTO.setId(billing.getId());
        responseDTO.setAmount(billing.getAmount());
        responseDTO.setPaymentStatus(billing.getPaymentStatus());
        responseDTO.setBillingDate(billing.getBillingDate());
        responseDTO.setDescription(billing.getDescription());

        if (billing.getPatient() != null) {
            responseDTO.setPatientId(billing.getPatient().getId());
        }

        return responseDTO;
    }
}
