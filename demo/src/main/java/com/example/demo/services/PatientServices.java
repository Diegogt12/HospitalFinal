package com.example.demo.services;

import com.example.demo.controller.AppointmentOutput;
import com.example.demo.controller.PatientInput;
import com.example.demo.domain.Appointment;
import com.example.demo.domain.Patient;
import com.example.demo.exceptions.ExceptionPatientAlreadyExists;
import com.example.demo.exceptions.ExceptionPatientDoesNotExists;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeSet;

import static java.util.Calendar.DATE;

@Service
public class PatientServices {

    @Autowired
    PatientRepository patientRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    public void addPatient(PatientInput pacientInput) throws ExceptionPatientAlreadyExists {
        Patient p = pacientInput.toDomain();
        if (patientRepository.existsById(p.getDni())) throw new ExceptionPatientAlreadyExists();
        patientRepository.save(p);
    }

    public AppointmentOutput getAppointmentsByDate(String patientDni, String date) throws ExceptionPatientDoesNotExists {
        if(!patientRepository.existsById(patientDni)) throw new ExceptionPatientDoesNotExists();
        Calendar calendar= Calendar.getInstance();
        LocalDate localDate1 = LocalDate.parse(date);
        Date date1 = java.sql.Date.valueOf(localDate1);
        calendar.setTime(date1);
        calendar.add(DATE,1);

        java.util.Date date2 = calendar.getTime();
        Date dateSql = new java.sql.Date(date2.getTime());


        ArrayList<Appointment> appointments = appointmentRepository.findAllByDate1AndPatientDni(dateSql,patientDni);

        TreeSet<Time> times = new TreeSet<>(new TimeComparator());
        for(Appointment a : appointments ){
            times.add(a.getTime1());
        }
        AppointmentOutput appointmentOutput = new AppointmentOutput();
        if(appointments.isEmpty()) appointmentOutput.setDate1(dateSql);
        else appointmentOutput = new AppointmentOutput(appointments.get(0).getDate1(), times);

        return appointmentOutput;

    }
}
