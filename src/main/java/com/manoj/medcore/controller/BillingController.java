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
import org.springframework.web.bind.annotation.RestController;

import com.manoj.medcore.dto.BillingRequestDTO;
import com.manoj.medcore.dto.BillingResponseDTO;
import com.manoj.medcore.dto.BillingUpdateDTO;
import com.manoj.medcore.service.BillingService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/billings")
public class BillingController {

    private final BillingService billingService;

    public BillingController(BillingService billingService) {
        this.billingService = billingService;
    }

    @PostMapping
    public ResponseEntity<BillingResponseDTO> createBilling(
            @Valid @RequestBody BillingRequestDTO requestDTO) {

        BillingResponseDTO responseDTO =
                billingService.createBilling(requestDTO);

        return ResponseEntity
                .status(201)
                .body(responseDTO);
    }

    @GetMapping
    public List<BillingResponseDTO> getBillings() {

        return billingService.getAllBillings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BillingResponseDTO> getBillingById(
            @PathVariable Long id) {

        BillingResponseDTO responseDTO =
                billingService.getBillingById(id);

        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BillingResponseDTO> updateBilling(
            @PathVariable Long id,
            @Valid @RequestBody BillingUpdateDTO updateDTO) {

        BillingResponseDTO responseDTO =
                billingService.updateBilling(id, updateDTO);

        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBilling(
            @PathVariable Long id) {

        billingService.deleteBilling(id);

        return ResponseEntity.ok(
                "Billing with ID " + id + " deleted");
    }
}
