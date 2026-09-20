package com.manoj.medcore.dto;
import jakarta.validation.constraints.NotBlank;

public class HospitalRequestDTO {

    @NotBlank(message = "Hospital name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}