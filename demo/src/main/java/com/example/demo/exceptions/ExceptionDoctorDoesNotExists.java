package com.example.demo.exceptions;

public class ExceptionDoctorDoesNotExists extends Exception {
    public ExceptionDoctorDoesNotExists() {
        super("Doctor does not exists");
    }
}
