package com.example.demo.domain;

import com.example.demo.controller.NurseOutput;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Time;
import java.util.ArrayList;

@Entity (name = "nurses")
public class Nurse {

    @Id
    private String nurseDni;
    @NotNull( message = "nurse name is null")
    @NotBlank( message = "nurse name is blank")
    private String name;
    @NotNull( message = "nurse id is null")
    @NotBlank( message = "nurse id is blank")
    private String nurseId;

    private Time hourStart;

    private Time hourFinish;

    public Nurse(String dni, String name, String nurseId, Time hourStart, Time hourFinish) {
        this.nurseDni = dni;
        this.name = name;
        this.nurseId = nurseId;
        this.hourStart = hourStart;
        this.hourFinish = hourFinish;
    }

    public Nurse(){}
    public Nurse(String dni, String name, String nurseId) {
        this.nurseDni = dni;
        this.name = name;
        this.nurseId = nurseId;
    }

    public String getDni() {
        return nurseDni;
    }

    public void setDni(String dni) {
        this.nurseDni = dni;
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

    public NurseOutput toOutput() {
        NurseOutput nO = new NurseOutput(this.nurseId,this.name);
        return nO;
    }
}
