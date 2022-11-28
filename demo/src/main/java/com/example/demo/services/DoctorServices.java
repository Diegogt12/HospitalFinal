package com.example.demo.services;

import com.example.demo.controller.DoctorInput;
import com.example.demo.controller.DoctorOutput;
import com.example.demo.controller.ScheduleInput;
import com.example.demo.domain.Appointment;
import com.example.demo.domain.Doctor;
import com.example.demo.domain.Nurse;
import com.example.demo.exceptions.*;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.NurseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalTime;
import java.util.ArrayList;

@Service
public class DoctorServices {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    NurseRepository nurseRepository;
    @Autowired
    AppointmentRepository appointmentRepository;

    public void addDoctor(DoctorInput doctorInput) throws ExceptionDoctorAlreadyExists, ExceptionNurseAlreadyExists, ExceptionTimeNotValidForAppointment {
        Doctor c = doctorInput.toDomain();
        if (doctorRepository.existsById(c.getDni())) throw new ExceptionDoctorAlreadyExists();
        if (nurseRepository.existsById(c.getDni()))throw new ExceptionNurseAlreadyExists();
        if(doctorRepository.existsByDoctorId(c.getDoctorId())) throw new ExceptionDoctorAlreadyExists();
        LocalTime apTime = c.getHourStart().toLocalTime();
        LocalTime apTime2 = c.getHourFinish().toLocalTime();
        if( (apTime.getMinute() != 00 ) || (apTime.getSecond() != 00)) throw new ExceptionTimeNotValidForAppointment();
        if( (apTime2.getMinute() != 00 ) || (apTime2.getSecond() != 00)) throw new ExceptionTimeNotValidForAppointment();
        doctorRepository.save(c);
    }


    public void modifyDoctorSchedule(String doctorId, ScheduleInput scheduleInput) throws ExceptionDoctorDoesNotExists, ExceptionTimeNotValidForAppointment {
        if (!doctorRepository.existsById(doctorId))throw new ExceptionDoctorDoesNotExists();
        //check if the appointment is made in an hour o´clock
        LocalTime apTime = scheduleInput.getHourStart().toLocalTime();
        LocalTime apTime2 = scheduleInput.getHourFinish().toLocalTime();
        if( (apTime.getMinute() != 00 ) || (apTime.getSecond() != 00)) throw new ExceptionTimeNotValidForAppointment();
        if( (apTime2.getMinute() != 00 ) || (apTime2.getSecond() != 00)) throw new ExceptionTimeNotValidForAppointment();
        Doctor d = doctorRepository.findById(doctorId).orElse(null);
        d.setHourStart(scheduleInput.getHourStart());
        d.setHourFinish(scheduleInput.getHourFinish());
        doctorRepository.save(d);
    }

    public DoctorOutput checkOccupiedDoctor() throws ExceptionDoctorDoesNotExists {
        ArrayList<Date> mondayAndFriday = Appointment.checkNextMondayAndFridayToDate();
        Date nextMonday = mondayAndFriday.get(0);
        Date nextFriday = mondayAndFriday.get(1);
        String dniDoctor = appointmentRepository.findByDate1BetweenWhereCountIsMaxDoctor(nextMonday, nextFriday);
        if(dniDoctor== null) throw new ExceptionDoctorDoesNotExists();
        if(!doctorRepository.existsById(dniDoctor)) throw new ExceptionDoctorDoesNotExists();
        Doctor d = doctorRepository.findById(dniDoctor).orElse(null);
        DoctorOutput dOut = d.toOutput();
        return dOut;
    }
}
