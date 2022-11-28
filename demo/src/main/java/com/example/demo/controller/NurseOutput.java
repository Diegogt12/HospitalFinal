package com.example.demo.controller;

import com.example.demo.domain.Nurse;

public class NurseOutput {
    private String nurseNumber;
    private String name;


    public NurseOutput(){

    }
    public NurseOutput(String nurseNumber, String name) {
        this.nurseNumber = nurseNumber;
        this.name = name;
    }

    public String getNurseId() {
        return nurseNumber;
    }

    public void setNurseId(String nurseId) {
        this.nurseNumber = nurseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
