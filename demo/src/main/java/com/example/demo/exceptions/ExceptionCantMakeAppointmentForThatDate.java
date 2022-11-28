package com.example.demo.exceptions;

public class ExceptionCantMakeAppointmentForThatDate extends Exception{
    public ExceptionCantMakeAppointmentForThatDate() { super("Cant make an appointment for that date, make sure is between next monday and friday");
    }
}
