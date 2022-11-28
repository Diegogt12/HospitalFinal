package com.example.demo.controller;

import com.example.demo.domain.Doctor;
import com.example.demo.domain.Nurse;
import com.example.demo.exceptions.*;

import com.example.demo.services.DoctorServices;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag( name = "Doctors")
public class DoctorController {

    @Autowired
    DoctorServices doctorServices;

    @PostMapping("/doctors")
    @Operation( summary =  "Add doctor ", responses = {
            @ApiResponse(description = "Doctor created", responseCode ="202",
                    content =  @Content(mediaType = "application/json")),
            @ApiResponse(description = "Doctor error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<String> addDoctors(@Valid @RequestBody DoctorInput doctorInput) {
        try {
            doctorServices.addDoctor(doctorInput);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (ExceptionDoctorAlreadyExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionNurseAlreadyExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionTimeNotValidForAppointment e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/doctor/{doctorId}/schedule")
    @Operation( summary =  "Modify doctor schedule ", responses = {
            @ApiResponse(description = "Doctor schedule modified", responseCode ="202",
                    content =  @Content (mediaType = "application/json")),
            @ApiResponse(description = "Doctor error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<String> modifyDoctorSchedule (@PathVariable String doctorId, @Valid @RequestBody ScheduleInput scheduleInput){
        try{
            doctorServices.modifyDoctorSchedule(doctorId,scheduleInput);
            return ResponseEntity.status(HttpStatus.ACCEPTED).build();
        }catch (ExceptionDoctorDoesNotExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (ExceptionTimeNotValidForAppointment e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


    @GetMapping("/doctor/occupied")
    @Operation( summary =  "Check most occupied doctor ", responses = {
            @ApiResponse(description = "Get most occupied doctor success", responseCode ="202",
                    content =  @Content (mediaType = "application/json")),
            @ApiResponse(description = "Doctor error", responseCode = "409", content = @Content)
    })
    public ResponseEntity<DoctorOutput> checkOccupiedDoctor (){
        try{
            DoctorOutput occupiedDoctor = doctorServices.checkOccupiedDoctor();
            return ResponseEntity.ok(occupiedDoctor);
        }catch(ExceptionDoctorDoesNotExists e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }
}
