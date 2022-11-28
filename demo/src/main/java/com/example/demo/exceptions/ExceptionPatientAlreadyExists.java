package com.example.demo.exceptions;

public class ExceptionPatientAlreadyExists extends Exception {
    public ExceptionPatientAlreadyExists() {
        super(  "Patient already exists" );
    }
}