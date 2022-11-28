package com.example.demo.exceptions;

public class ExceptionCantMakeAppointmentOnWeekend extends Exception{
    public ExceptionCantMakeAppointmentOnWeekend() { super("Cant make an appointment on weekends");
    }
}
