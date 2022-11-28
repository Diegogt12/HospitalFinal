package com.example.demo.exceptions;

public class ExceptionCantMakeAppointmentForSameDni extends Exception{
    public ExceptionCantMakeAppointmentForSameDni() { super ( "Patients DNI is the same as professional DNI");    }
}
