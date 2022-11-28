package com.example.demo.exceptions;

public class ExceptionAppointmentAlreadyExist extends Exception {
    public ExceptionAppointmentAlreadyExist() {
        super("Appointment already exist for that profesional at that time");
    }
}