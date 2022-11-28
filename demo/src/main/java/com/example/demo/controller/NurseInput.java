package com.example.demo.controller;


import com.example.demo.domain.Nurse;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Time;

public class NurseInput {
    @NotNull( message = "nurse dni is null")
    @NotBlank( message = "nurse dni is blank")
    private String dni;
    @NotNull( message = "nurse name is null")
    @NotBlank( message = "nurse name is blank")
    private String name;
    @NotNull( message = "nurse name is null")
    @NotBlank( message = "nurse name is blank")
    private String nurseId;
    @NotNull
    private Time hourStart;
    @NotNull
    private Time hourFinish;


    public NurseInput(){}

    public NurseInput(String dni, String name, String nurseId, Time hourStart, Time hourFinish) {
        this.dni = dni;
        this.name = name;
        this.nurseId = nurseId;
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

    public String getNurseId() {
        return nurseId;
    }

    public void setNurseId(String nurseId) {
        this.nurseId = nurseId;
    }

    public Nurse toDomain() {
    Nurse n = new Nurse(this.dni,this.name,this.nurseId,this.hourStart,this.hourFinish);
        return n;
    }
}
