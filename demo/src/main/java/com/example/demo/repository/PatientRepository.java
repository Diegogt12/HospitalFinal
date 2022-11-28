package com.example.demo.repository;

import com.example.demo.domain.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Time;

@Repository
public interface PatientRepository extends JpaRepository<Patient, String> {

}
