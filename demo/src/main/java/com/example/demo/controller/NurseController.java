package com.example.demo.controller;

import com.example.demo.domain.Nurse;
import com.example.demo.exceptions.*;
import com.example.demo.services.NurseServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.TreeSet;

@RestController
@Tag( name = "Nurses")
public class NurseController {

    @Autowired
    NurseServices nurseServices;


    @PostMapping("/nurses")
    @Operation( summary =  "Add nurse ", responses = {
            @ApiResponse(description = "Nurse created", responseCode ="202",
                    content =  @Content(mediaType = "application/json")),
            @ApiResponse(description = "Nurse error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<String> addNurse(@Valid @RequestBody NurseInput nurseInput) {
        try {
            nurseServices.addNurse(nurseInput);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ExceptionNurseAlreadyExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionDoctorAlreadyExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/nurse/{nurseId}/schedule")
    @Operation( summary =  "Modify nurse Schedule ", responses = {
            @ApiResponse(description = "Modify nurse schedule success", responseCode ="202",
                    content =  @Content (mediaType = "application/json")),
            @ApiResponse(description = "Nurse error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<String> modifyNurseSchedule(@PathVariable String nurseId, @Valid @RequestBody ScheduleInput scheduleInput) {
        try {
            nurseServices.modifyNurseSchedule(nurseId, scheduleInput);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        } catch (ExceptionNurseDoesNotExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionTimeNotValidForAppointment e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }





    @GetMapping("/nurse/occupied")
    @Operation( summary =  " Check most occupied nurse", responses = {
            @ApiResponse(description = "Get most occupied success", responseCode ="202",
                    content =  @Content (mediaType = "application/json")),
            @ApiResponse(description = "Nurse error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<NurseOutput> checkOccupiedNurse (){
        try{
            NurseOutput occupiedNurse = nurseServices.checkOccupiedNurse();
            return ResponseEntity.ok(occupiedNurse);
        }catch(ExceptionNurseDoesNotExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

}