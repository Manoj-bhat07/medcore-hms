package com.manoj.medcore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DepartmentUpdateDTO {

    @NotBlank(message = "Department name is required")
    private String name;

    @NotNull(message = "Hospital ID is required")
    private Long hospitalId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }
}