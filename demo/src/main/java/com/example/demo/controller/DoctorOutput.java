package com.example.demo.controller;

public class DoctorOutput {
    private String doctorNumber;
    private String name;

    public DoctorOutput(String doctorNumber, String name) {
        this.doctorNumber = doctorNumber;
        this.name = name;
    }

    public DoctorOutput(){}

    public String getDoctorId() {
        return doctorNumber;
    }

    public void setDoctorId(String doctorId) {
        this.doctorNumber = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
