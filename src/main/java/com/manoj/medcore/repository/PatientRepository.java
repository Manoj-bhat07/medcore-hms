package com.manoj.medcore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manoj.medcore.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
}