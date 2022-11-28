package com.example.demo.exceptions;

public class ExceptionTimeNotValidForAppointment extends Exception{
    public ExceptionTimeNotValidForAppointment() { super("Cant make an appointment for this time");}
}
