package com.manoj.medcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manoj.medcore.model.Billing;

@Repository
public interface BillingRepository extends JpaRepository<Billing, Long> {
}
