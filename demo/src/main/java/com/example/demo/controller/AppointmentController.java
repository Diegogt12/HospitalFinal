package com.example.demo.controller;


import com.example.demo.exceptions.*;
import com.example.demo.services.AppointmentServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.TreeSet;

@RestController
@Tag( name = "Appointments")
public class AppointmentController {

    @Autowired
    AppointmentServices appointmentServices;

    @PostMapping("/appointment/{professionalId}")
    @Operation( summary =  "Post appointment ", responses = {
        @ApiResponse(description = "Appointment created", responseCode ="202",
        content =  @Content (mediaType = "application/json")),
        @ApiResponse(description = "Appointment error", responseCode = "409", content = @Content)
    })

    public ResponseEntity<String> createAppointment(@PathVariable String professionalId, @RequestBody AppointmentInput appointmentInput) {
        try {
           appointmentServices.createAppointment(professionalId, appointmentInput);
           return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (ExceptionPatientDoesNotExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionAppointmentAlreadyExist e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionPatientAlreadyHasAnAppointmentThisTime e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionTimeNotValidForAppointment e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionCantMakeAppointmentForThatTime e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionCantMakeAppointmentForThatDate e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionProfesionalDoesNotExist e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionCantMakeAppointmentOnWeekend e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionScheduleNotValidForProfesional e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionCantMakeAppointmentForSameDni e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/appointments/{profesionalId}/free")
    @Operation( summary =  "Check free schedule of professional ", responses = {
            @ApiResponse(description = "Get free schedule success", responseCode ="202",
                    content =  @Content (mediaType = "application/json")),
            @ApiResponse(description = "Appointment error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<ArrayList<AppointmentOutput>> checkFreeSchedule (@PathVariable String profesionalId){
        try{
            ArrayList<AppointmentOutput> freeSchedule = appointmentServices.checkFreeSchedule(profesionalId);
            return ResponseEntity.ok(freeSchedule);
        } catch (ExceptionProfesionalDoesNotExist e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


    @GetMapping("/appointments/{profesionalId}")
    @Operation( summary =  "Get appointments from professional ", responses = {
            @ApiResponse(description = "Get appointments from professional ", responseCode ="202",
                    content =  @Content (mediaType = "application/json")),
            @ApiResponse(description = "Appointment error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<TreeSet<AppointmentOutput>> getAppointments (@PathVariable String profesionalId){
        try{
            TreeSet<AppointmentOutput> freeSchedule = appointmentServices.appointmentsInOrden(profesionalId);
            return ResponseEntity.ok(freeSchedule);
        } catch (ExceptionProfesionalDoesNotExist e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }


}
