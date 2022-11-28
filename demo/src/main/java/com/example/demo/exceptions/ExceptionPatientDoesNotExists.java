package com.example.demo.exceptions;

public class ExceptionPatientDoesNotExists extends Exception {
    public ExceptionPatientDoesNotExists() {
        super(  "Patient does not exists" );
    }
}