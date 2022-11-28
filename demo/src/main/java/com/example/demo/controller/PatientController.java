package com.example.demo.controller;

import com.example.demo.domain.Appointment;
import com.example.demo.exceptions.ExceptionPatientAlreadyExists;
import com.example.demo.exceptions.ExceptionPatientDoesNotExists;
import com.example.demo.services.PatientServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.TreeSet;

@RestController
@Tag( name = "Patients")
public class PatientController {

    @Autowired
    PatientServices patientServices;

    @PostMapping("/patients")
    @Operation( summary = " Add patient", responses = {
            @ApiResponse(description = "Post patient success", responseCode ="202",
                    content =  @Content(mediaType = "application/json")),
            @ApiResponse(description = "Patient error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<String> addPatient(@Valid @RequestBody PatientInput pacientInput) {
        try {
            patientServices.addPatient(pacientInput);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ExceptionPatientAlreadyExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/patient/{patientId}/appointments/{date}")
    @Operation( summary =  " Get patient appointment by date", responses = {
            @ApiResponse(description = "Get patient appointments by date success", responseCode ="202",
                    content =  @Content (mediaType = "application/json")),
            @ApiResponse(description = "Patient error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<AppointmentOutput> getAppointmentsByDate (@PathVariable String patientId ,@PathVariable String date){
        try{
            AppointmentOutput appointmentOutput = patientServices.getAppointmentsByDate(patientId, date);
            return ResponseEntity.ok(appointmentOutput);
        }catch(ExceptionPatientDoesNotExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
