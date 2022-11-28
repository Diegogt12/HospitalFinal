package com.example.demo.controller;

import com.example.demo.domain.Appointment;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;

public class AppointmentInput {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotBlank
    @NotNull
    private Date date;
    @NotNull
    private Time time;
    @NotNull ( message = "patient dni id is null")
    @NotBlank( message = "patient dni is blank")
    private String dni;

    public AppointmentInput(){}

    public AppointmentInput(Date date, Time time, String dni) {
        this.date = date;
        this.time = time;
        this.dni = dni;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(java.sql.Date date) {
        this.date = date;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }



    public Appointment toDomain(long appId,  String nurseDni, String doctorDni) {

        Appointment app = new Appointment(appId,this.date,this.time,nurseDni,doctorDni,this.dni);
        return app;

    }

}
