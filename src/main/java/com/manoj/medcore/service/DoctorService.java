package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.DoctorRequestDTO;
import com.manoj.medcore.dto.DoctorResponseDTO;
import com.manoj.medcore.dto.DoctorUpdateDTO;
import com.manoj.medcore.exception.DepartmentNotFoundException;
import com.manoj.medcore.exception.DoctorNotFoundException;
import com.manoj.medcore.model.Department;
import com.manoj.medcore.model.Doctor;
import com.manoj.medcore.repository.DepartmentRepository;
import com.manoj.medcore.repository.DoctorRepository;

@Service
public class DoctorService {

    private final  DoctorRepository doctorRepository;
    private final DepartmentRepository departmentRepository;

    public DoctorService(
             DoctorRepository doctorRepository,
            DepartmentRepository departmentRepository) {

        this.doctorRepository = doctorRepository;
        this.departmentRepository = departmentRepository;
    }

    // CREATE
    public DoctorResponseDTO createDoctor(
            DoctorRequestDTO requestDTO) {

        Department department =
                departmentRepository.findById(requestDTO.getDepartmentId())
                        .orElseThrow(() ->
                                new DepartmentNotFoundException(
                                        "Department not found with id: "
                                                + requestDTO.getDepartmentId()));

        Doctor doctor = new Doctor();

        doctor.setName(requestDTO.getName());
        doctor.setSpecialization(requestDTO.getSpecialization());
        doctor.setDepartment(department);

        Doctor savedDoctor =
                doctorRepository.save(doctor);

        return mapToResponse(savedDoctor);
    }

    // GET ALL
    public List<DoctorResponseDTO> getAllDoctors() {

        List<Doctor> doctors =
                doctorRepository.findAll();

        List<DoctorResponseDTO> responseList =
                new ArrayList<>();

        for (Doctor doctor : doctors) {
            responseList.add(mapToResponse(doctor));
        }

        return responseList;
    }

    // GET BY ID
    public DoctorResponseDTO getDoctorById(Long id) {

        Doctor doctor =
                doctorRepository.findById(id)
                        .orElseThrow(() ->
                                new DoctorNotFoundException(
                                        "Doctor not found with id: "
                                                + id));

        return mapToResponse(doctor);
    }

    // UPDATE
    public DoctorResponseDTO updateDoctor(
            Long id,
            DoctorUpdateDTO updateDTO) {

        Doctor doctor =
                doctorRepository.findById(id)
                        .orElseThrow(() ->
                                new DoctorNotFoundException(
                                        "Doctor not found with id: "
                                                + id));

        Department department =
                departmentRepository
                        .findById(updateDTO.getDepartmentId())
                        .orElseThrow(() ->
                                new DepartmentNotFoundException(
                                        "Department not found with id: "
                                                + updateDTO.getDepartmentId()));

        doctor.setName(updateDTO.getName());
        doctor.setSpecialization(updateDTO.getSpecialization());
        doctor.setDepartment(department);

        Doctor updatedDoctor =
                doctorRepository.save(doctor);

        return mapToResponse(updatedDoctor);
    }

    // DELETE
    public void deleteDoctor(Long id) {

        Doctor doctor =
                doctorRepository.findById(id)
                        .orElseThrow(() ->
                                new DoctorNotFoundException(
                                        "Doctor not found with id: "
                                                + id));

        doctorRepository.delete(doctor);
    }

    // DTO MAPPING
    private DoctorResponseDTO mapToResponse(
            Doctor doctor) {

        DoctorResponseDTO responseDTO =
                new DoctorResponseDTO();

        responseDTO.setId(doctor.getId());
        responseDTO.setName(doctor.getName());
        responseDTO.setSpecialization(
                doctor.getSpecialization());
        responseDTO.setDepartmentId(
                doctor.getDepartment().getId());

        return responseDTO;
    }
}