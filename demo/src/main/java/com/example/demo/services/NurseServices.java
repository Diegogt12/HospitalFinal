package com.example.demo.services;

import com.example.demo.controller.*;
import com.example.demo.domain.Appointment;

import com.example.demo.domain.Nurse;
import com.example.demo.exceptions.*;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.NurseRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.sql.Date;

import java.time.LocalTime;

import java.util.ArrayList;

@Service
public class NurseServices {

    @Autowired
    NurseRepository nurseRepository;

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    PatientRepository patientRepository;

    public void addNurse(NurseInput nurseInput) throws ExceptionNurseAlreadyExists, ExceptionDoctorAlreadyExists {
        Nurse n = nurseInput.toDomain();
        if (nurseRepository.existsById(n.getDni())) throw new ExceptionNurseAlreadyExists();
        if (nurseRepository.existsByNurseId(n.getNurseId())) throw new ExceptionNurseAlreadyExists();
        if (doctorRepository.existsById(n.getDni())) throw new ExceptionDoctorAlreadyExists();

        nurseRepository.save(n);
    }

    public void modifyNurseSchedule(String nurseId, ScheduleInput scheduleInput) throws ExceptionNurseDoesNotExists, ExceptionTimeNotValidForAppointment {
        if (!nurseRepository.existsById(nurseId)) throw new ExceptionNurseDoesNotExists();

        //check if the appointment is made in an hour o´clock
        LocalTime apTime = scheduleInput.getHourStart().toLocalTime();
        LocalTime apTime2 = scheduleInput.getHourFinish().toLocalTime();
        if( (apTime.getMinute() != 00 ) || (apTime.getSecond() != 00)) throw new ExceptionTimeNotValidForAppointment();
        if( (apTime2.getMinute() != 00 ) || (apTime2.getSecond() != 00)) throw new ExceptionTimeNotValidForAppointment();
        Nurse n = nurseRepository.findById(nurseId).orElse(null);
        n.setHourStart(scheduleInput.getHourStart());
        n.setHourFinish(scheduleInput.getHourFinish());

        nurseRepository.save(n);

    }

    public NurseOutput checkOccupiedNurse() throws ExceptionNurseDoesNotExists {
        ArrayList<Date> mondayAndFriday = Appointment.checkNextMondayAndFridayToDate();
        Date nextMonday = mondayAndFriday.get(0);
        Date nextFriday = mondayAndFriday.get(1);
        String dniNurse = appointmentRepository.findByDate1BetweenWhereCountIsMax(nextMonday, nextFriday);
        if(dniNurse == null) throw new ExceptionNurseDoesNotExists();
        if(!nurseRepository.existsById(dniNurse)) throw new ExceptionNurseDoesNotExists();
        Nurse n = nurseRepository.findById(dniNurse).orElse(null);
        NurseOutput nuOut= n.toOutput();
        return nuOut;
    }

}