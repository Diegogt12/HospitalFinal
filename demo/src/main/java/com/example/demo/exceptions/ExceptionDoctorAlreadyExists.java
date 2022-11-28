package com.example.demo.exceptions;

public class ExceptionDoctorAlreadyExists extends Exception{
    public ExceptionDoctorAlreadyExists() {
        super("Doctor already exist");
    }
}
