package com.example.demo.controller;

import com.example.demo.domain.Doctor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Time;

public class DoctorInput {
    @NotNull( message = "doctor dni is null")
    @NotBlank( message = "doctor dni is blank")
    private String dni;
    @NotNull( message = "doctor name is null")
    @NotBlank( message = "doctor name is blank")
    private String name;
    @NotNull( message = "doctor id is null")
    @NotBlank( message = "doctor id is blank")
    private String doctorId;
    @PositiveOrZero
    private int experience;

    private Time hourStart;

    private Time hourFinish;


    public DoctorInput(){}

    public DoctorInput(String dni, String name, String doctorId, int experience, Time hourStart, Time hourFinish) {
        this.dni = dni;
        this.name = name;
        this.doctorId = doctorId;
        this.experience = experience;
        this.hourStart = hourStart;
        this.hourFinish = hourFinish;
    }

    public Time getHourStart() {
        return hourStart;
    }

    public void setHourStart(Time hourStart) {
        this.hourStart = hourStart;
    }

    public Time getHourFinish() {
        return hourFinish;
    }

    public void setHourFinish(Time hourFinish) {
        this.hourFinish = hourFinish;
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

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public Doctor toDomain() {
        Doctor c = new Doctor(this.dni,this.name,this.doctorId,this.experience,this.hourStart,this.hourFinish);
        return c;
    }
}
