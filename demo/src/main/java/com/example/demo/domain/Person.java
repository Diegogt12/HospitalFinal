package com.example.demo.domain;

import javax.persistence.Id;

public class Person {
    protected String name;
    @Id
    protected String dni;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }
}
