package com.manoj.medcore.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.manoj.medcore.dto.DepartmentRequestDTO;
import com.manoj.medcore.dto.DepartmentResponseDTO;
import com.manoj.medcore.dto.DepartmentUpdateDTO;
import com.manoj.medcore.exception.DepartmentNotFoundException;
import com.manoj.medcore.exception.HospitalNotFoundException;
import com.manoj.medcore.model.Department;
import com.manoj.medcore.model.Hospital;
import com.manoj.medcore.repository.DepartmentRepository;
import com.manoj.medcore.repository.HospitalRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final HospitalRepository hospitalRepository;

    public DepartmentService(
            DepartmentRepository departmentRepository,
            HospitalRepository hospitalRepository) {

        this.departmentRepository = departmentRepository;
        this.hospitalRepository = hospitalRepository;
    }

    // CREATE
    public DepartmentResponseDTO createDepartment(
            DepartmentRequestDTO requestDTO) {

        Hospital hospital =
                hospitalRepository.findById(requestDTO.getHospitalId())
                        .orElseThrow(() ->
                                new HospitalNotFoundException(
                                        "Hospital not found with id: "
                                                + requestDTO.getHospitalId()));

        Department department = new Department();

        department.setName(requestDTO.getName());
        department.setHospital(hospital);

        Department savedDepartment =
                departmentRepository.save(department);

        return mapToResponse(savedDepartment);
    }

    // GET ALL
    public List<DepartmentResponseDTO> getAllDepartments() {

        List<Department> departments =
                departmentRepository.findAll();

        List<DepartmentResponseDTO> responseList =
                new ArrayList<>();

        for (Department department : departments) {
            responseList.add(mapToResponse(department));
        }

        return responseList;
    }

    // GET BY ID
    public DepartmentResponseDTO getDepartmentById(Long id) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new DepartmentNotFoundException(
                                        "Department not found with id: "
                                                + id));

        return mapToResponse(department);
    }

    // UPDATE
    public DepartmentResponseDTO updateDepartment(
            Long id,
            DepartmentUpdateDTO updateDTO) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new DepartmentNotFoundException(
                                        "Department not found with id: "
                                                + id));

        Hospital hospital =
                hospitalRepository.findById(updateDTO.getHospitalId())
                        .orElseThrow(() ->
                                new HospitalNotFoundException(
                                        "Hospital not found with id: "
                                                + updateDTO.getHospitalId()));

        department.setName(updateDTO.getName());
        department.setHospital(hospital);

        Department updatedDepartment =
                departmentRepository.save(department);

        return mapToResponse(updatedDepartment);
    }

    // DELETE
    public void deleteDepartment(Long id) {

        Department department =
                departmentRepository.findById(id)
                        .orElseThrow(() ->
                                new DepartmentNotFoundException(
                                        "Department not found with id: "
                                                + id));

        departmentRepository.delete(department);
    }

    // DTO MAPPING
    private DepartmentResponseDTO mapToResponse(
            Department department) {

        DepartmentResponseDTO responseDTO =
                new DepartmentResponseDTO();

        responseDTO.setId(department.getId());
        responseDTO.setName(department.getName());
        responseDTO.setHospitalId(
                department.getHospital().getId());

        return responseDTO;
    }
}