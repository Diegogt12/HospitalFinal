package com.example.demo.domain;

import com.example.demo.controller.DoctorOutput;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Time;

@Entity(name = "doctors")
public class Doctor {

   @Id
    private String doctorDni;
    @NotNull( message = "doctor name is null")
    @NotBlank( message = "doctor name is blank")
    private String name;
    @NotNull( message = "doctor id is null")
    @NotBlank( message = "doctor id is blank")
    private String doctorId;
    @PositiveOrZero
    private int experience;
@NotNull
    private Time hourStart;

    private Time hourFinish;

    public Doctor(){}

    public Doctor(String dni, String name, String doctorId, int experience, Time hourStart, Time hourFinish) {
        this.doctorDni = dni;
        this.name = name;
        this.doctorId = doctorId;
        this.experience = experience;
        this.hourStart = hourStart;
        this.hourFinish = hourFinish;
    }

    public Doctor(String dni, String name, String doctorId, int experience) {
        this.doctorDni = dni;
        this.name = name;
        this.doctorId = doctorId;
        this.experience = experience;
    }

    public String getDni() {
        return doctorDni;
    }

    public void setDni(String dni) {
        this.doctorDni = dni;
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

    public DoctorOutput toOutput() {
    DoctorOutput dOut = new DoctorOutput(this.doctorId,this.name);
    return dOut;

    }
}
