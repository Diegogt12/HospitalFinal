package com.example.demo.controller;

import com.example.demo.domain.Patient;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PatientInput {
    @NotNull
    @NotBlank
    private String dni;
    @NotNull
    @NotBlank
    private String name;
    @NotNull
    @NotBlank
    private String address;

    public PatientInput(){}

    public PatientInput(String dni, String name, String address) {
        this.dni = dni;
        this.name = name;
        this.address = address;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Patient toDomain() {
    Patient p = new Patient(this.dni,this.name,this.address);
    return p;
    }
}
