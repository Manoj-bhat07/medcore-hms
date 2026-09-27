package com.manoj.medcore.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.manoj.medcore.model.Patient;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    List<Patient> findByNameContainingIgnoreCase(String name);
}