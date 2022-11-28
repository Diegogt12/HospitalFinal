package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity (name = "patients")
public class Patient  {
    @Id
    private String patientDni;
    @NotNull ( message = "pacient name is null")
    @NotBlank ( message = "pacient name is blank")
    private String name;
    @NotNull ( message = "pacient address is null")
    @NotBlank ( message = "pacient address is blank")
    private String address;

    public Patient(){}

    public Patient(String dni, String name, String address) {
        this.patientDni = dni;
        this.name = name;
        this.address = address;
    }

    public String getDni() {
        return patientDni;
    }

    public void setDni(String dni) {
        this.patientDni = dni;
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
}
