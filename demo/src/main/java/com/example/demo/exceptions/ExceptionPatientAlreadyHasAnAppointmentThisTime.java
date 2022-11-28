package com.example.demo.exceptions;

public class ExceptionPatientAlreadyHasAnAppointmentThisTime extends Exception{
    public ExceptionPatientAlreadyHasAnAppointmentThisTime() { super("Patient already has an appointment this time");
    }
}
