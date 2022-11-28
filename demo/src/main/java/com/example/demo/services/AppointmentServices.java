package com.example.demo.services;

import com.example.demo.controller.AppointmentInput;
import com.example.demo.controller.AppointmentOutput;
import com.example.demo.domain.Appointment;
import com.example.demo.domain.Doctor;
import com.example.demo.domain.Nurse;
import com.example.demo.exceptions.*;
import com.example.demo.repository.AppointmentRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.NurseRepository;
import com.example.demo.repository.PatientRepository;
import com.sun.source.tree.Tree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TreeSet;

import static java.util.Calendar.DATE;

@Service
public class AppointmentServices {
    Calendar calendar = Calendar.getInstance();
@Autowired
    NurseRepository nurseRepository;

@Autowired
    PatientRepository patientRepository;

@Autowired
    AppointmentRepository appointmentRepository;
@Autowired
    DoctorRepository doctorRepository;


    public ArrayList<AppointmentOutput> checkFreeSchedule(String profesionalId) throws  ExceptionProfesionalDoesNotExist {
        ArrayList<Date> mondayAndFriday = Appointment.checkNextMondayAndFridayToDate();
        Date nextMonday = mondayAndFriday.get(0);
        Date nextFriday = mondayAndFriday.get(1);

        ArrayList<AppointmentOutput> appointmentOutputProfesional = new ArrayList<>();

        ArrayList<AppointmentOutput> freeSchedule = new ArrayList<>();

        if(nurseRepository.existsById(profesionalId)) {
            Nurse n = nurseRepository.findById(profesionalId).orElse(null);
            ArrayList<Appointment> appointmentsNurse = appointmentRepository.findAllByNurseDniAndDate1Between(profesionalId, nextMonday, nextFriday);
            //appointment from nurse
            appointmentOutputProfesional = appointmentsToOutput(appointmentsNurse);
            //appointments from nurse in orden
            //all hours from profesional schedule
            freeSchedule = allFreeScheduleFromNextWeek(nextMonday, n.getHourStart(), n.getHourFinish());
            //final schedule free

        }else if(doctorRepository.existsById(profesionalId)){
            Doctor d = doctorRepository.findById(profesionalId).orElse(null);
            ArrayList<Appointment> appointmentsDoctor = appointmentRepository.findAllByDoctorDniAndDate1Between(profesionalId, nextMonday, nextFriday);
            //appointment from nurse
            appointmentOutputProfesional = appointmentsToOutput(appointmentsDoctor);
            //all hours from profesional schedule
             freeSchedule = allFreeScheduleFromNextWeek(nextMonday, d.getHourStart(), d.getHourFinish());

        }else throw new ExceptionProfesionalDoesNotExist();

        //appointments from profesional in orden
        TreeSet<AppointmentOutput> appointmentOutputsInOrdenProfesional = getAppointmentOutputsInOrden(appointmentOutputProfesional);
        //outputs to array list to work with it
        ArrayList<AppointmentOutput> appOutOrdenProfesional = new ArrayList<>(appointmentOutputsInOrdenProfesional);

        ArrayList<AppointmentOutput> freeScheduleFinal = freeScheduleFinal = checkFinalSchedule(appOutOrdenProfesional, freeSchedule);
        return freeScheduleFinal;
    }



    public void createAppointment(String profesionalDni, AppointmentInput appointmentInput) throws ExceptionPatientAlreadyHasAnAppointmentThisTime, ExceptionCantMakeAppointmentOnWeekend, ExceptionTimeNotValidForAppointment, ExceptionCantMakeAppointmentForThatDate, ExceptionPatientDoesNotExists, ExceptionAppointmentAlreadyExist, ExceptionCantMakeAppointmentForThatTime, ExceptionProfesionalDoesNotExist, ExceptionScheduleNotValidForProfesional, ExceptionCantMakeAppointmentForSameDni {
        //check if patient already has appointment
        if(profesionalDni.equals(appointmentInput.getDni())) throw new ExceptionCantMakeAppointmentForSameDni();
        if (!patientRepository.existsById(appointmentInput.getDni())) throw new ExceptionPatientDoesNotExists();
        if(appointmentRepository.findByPatientDniAndDate1AndTime1(appointmentInput.getDni(),appointmentInput.getDate(),appointmentInput.getTime()) != null)  throw new ExceptionPatientAlreadyHasAnAppointmentThisTime();

        //check if the appointemt is made during monday to firday
        if (((calendar.get(Calendar.DAY_OF_WEEK) - 1)) == 6 || ((calendar.get(Calendar.DAY_OF_WEEK) - 1) == 7) ||((calendar.get(Calendar.DAY_OF_WEEK) - 1)) == 0 )
            throw new ExceptionCantMakeAppointmentOnWeekend();
        //check if the appointment is made in an hour o´clock
        LocalTime apTime = appointmentInput.getTime().toLocalTime();
        if( (apTime.getMinute() != 00 ) || (apTime.getSecond() != 00)) throw new ExceptionTimeNotValidForAppointment();
        if( apTime.getHour() > 24)throw new ExceptionTimeNotValidForAppointment();

        //check if the appointent is made for the next week only
        ArrayList<java.sql.Date> mondayAndFriday = Appointment.checkNextMondayAndFridayToDate();
        java.sql.Date nextMonday = mondayAndFriday.get(0);
        java.sql.Date nextFriday = mondayAndFriday.get(1);


        if((appointmentInput.getDate().after(nextFriday)) || (appointmentInput.getDate().before(nextMonday))) throw new ExceptionCantMakeAppointmentForThatDate();

        //check if its a doctor or nurse and save appointment
        if( nurseRepository.existsById(profesionalDni)){
            createAppointmentNurse(appointmentInput,profesionalDni,apTime);
        } else if( doctorRepository.existsById(profesionalDni)){
            createAppointmentDoctor(appointmentInput,profesionalDni,apTime);
        }else throw new ExceptionProfesionalDoesNotExist();
    }



    public TreeSet<AppointmentOutput> appointmentsInOrden(String profesionalDni) throws ExceptionProfesionalDoesNotExist {
        ArrayList<Date> mondayAndFriday = Appointment.checkNextMondayAndFridayToDate();
        Date nextMonday = mondayAndFriday.get(0);
        Date nextFriday = mondayAndFriday.get(1);
        ArrayList<Appointment> appointments = new ArrayList<>();
        ArrayList<AppointmentOutput> appointmentOutputArrayList =new ArrayList<>();
        TreeSet<AppointmentOutput> appointmentOutputsInOrden = new TreeSet<>();


        if(nurseRepository.existsById(profesionalDni)){
            appointments = appointmentRepository.findAllByNurseDniAndDate1Between(profesionalDni,nextMonday,nextFriday);

        }else if(doctorRepository.existsById(profesionalDni)){
            appointments = appointmentRepository.findAllByDoctorDniAndDate1Between(profesionalDni,nextMonday,nextFriday);

        } else throw new ExceptionProfesionalDoesNotExist();

        appointmentOutputArrayList = appointmentsToOutput(appointments);
        appointmentOutputsInOrden = getAppointmentOutputsInOrden(appointmentOutputArrayList);


        return appointmentOutputsInOrden;
    }



    private void createAppointmentDoctor(AppointmentInput appointmentInput, String doctorDni, LocalTime apTime) throws ExceptionAppointmentAlreadyExist, ExceptionCantMakeAppointmentForThatTime, ExceptionScheduleNotValidForProfesional {

        //create appointment
        long appId = appointmentRepository.count() + 1;
        Appointment ap = appointmentInput.toDomain(appId, null, doctorDni);


        //check if profesional has an appointment
        if (appointmentRepository.existsByDoctorDniAndDate1AndTime1(ap.getDoctorDni(),ap.getDate1(),ap.getTime1()) )
            throw new ExceptionAppointmentAlreadyExist();

        //check if the  appointment is made between profesional scheule
        Doctor d = doctorRepository.findById(doctorDni).orElse(null);
        LocalTime doctorTimeStart = d.getHourStart().toLocalTime();
        LocalTime doctorTimeFinish = d.getHourFinish().toLocalTime();
        if((doctorTimeStart.isAfter(apTime)) || (doctorTimeFinish.isBefore(apTime)) || (doctorTimeFinish.compareTo(apTime)==0)) throw new ExceptionScheduleNotValidForProfesional();

        appointmentRepository.save(ap);

    }


    public void createAppointmentNurse(AppointmentInput appointmentInput ,String nurseDni, LocalTime apTime) throws ExceptionAppointmentAlreadyExist, ExceptionCantMakeAppointmentForThatTime, ExceptionScheduleNotValidForProfesional {

        //create appointment
        long appId = appointmentRepository.count() + 1;
        Appointment ap = appointmentInput.toDomain(appId, nurseDni, null);


        //check if profesional has an appointment
        if (appointmentRepository.existsByNurseDniAndDate1AndTime1(ap.getNurseDni(),ap.getDate1(),ap.getTime1()) )
            throw new ExceptionAppointmentAlreadyExist();

        //check if the  appointment is made between profesional scheule
        Nurse n = nurseRepository.findById(nurseDni).orElse(null);
        LocalTime nurseTimeStart = n.getHourStart().toLocalTime();
        LocalTime nurseTimeFinish = n.getHourFinish().toLocalTime();
        if((nurseTimeStart.isAfter(apTime)) || (nurseTimeFinish.isBefore(apTime)) || (nurseTimeFinish.compareTo(apTime)==0)) throw new ExceptionScheduleNotValidForProfesional();

        appointmentRepository.save(ap);
    }

    private ArrayList<AppointmentOutput> appointmentsToOutput(ArrayList<Appointment> appointments) {
        ArrayList<AppointmentOutput> appointmentOutputArrayList = new ArrayList<>();

        for(Appointment a: appointments) {
            boolean isCreated =false;
            for (int i = 0; i < appointmentOutputArrayList.size(); i++) {
                if (a.getDate1().compareTo(appointmentOutputArrayList.get(i).getDate1()) == 0) {
                    appointmentOutputArrayList.get(i).addTime1(a.getTime1());

                    isCreated = true;
                    break;
                }

            }

            if (isCreated== false) {
                AppointmentOutput apO = new AppointmentOutput(a.getDate1(), a.getTime1());
                appointmentOutputArrayList.add(apO);

            }
        }
        return appointmentOutputArrayList;
    }

    private TreeSet<AppointmentOutput> getAppointmentOutputsInOrden(ArrayList<AppointmentOutput> appointmentOutputArrayList) {

        TreeSet<AppointmentOutput> appointmentOutputsInOrden =new TreeSet<AppointmentOutput>(new DateComparator());
        for(AppointmentOutput a: appointmentOutputArrayList){

            AppointmentOutput i = new AppointmentOutput(a.getDate1(), a.getTime1());
            appointmentOutputsInOrden.add(i);
        }

        return  appointmentOutputsInOrden;
    }





    private ArrayList<AppointmentOutput> allFreeScheduleFromNextWeek( Date nextMonday,  Time hourStart, Time hourFinish) {

        ArrayList<AppointmentOutput> freeSchedule = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();


        for (int i = 0; i < 5;i++){

            calendar.setTime(nextMonday);
            calendar.add(DATE,i);
            java.util.Date date2 = calendar.getTime();
            Date dateSql = new Date(date2.getTime());

            LocalTime jHourStart = hourStart.toLocalTime();
            LocalTime jHourFinish = hourFinish.toLocalTime();
            TreeSet<Time> times = new TreeSet<>(new TimeComparator());
            LocalTime j = jHourStart;
            while(j.isBefore(jHourFinish)){
                Time freeHour = Time.valueOf(j);
                times.add(freeHour);
                j = j.plusHours(1);
            }


            AppointmentOutput appointmentOutput = new AppointmentOutput (dateSql , times);
            freeSchedule.add(appointmentOutput);
        }



        return freeSchedule;
    }


    private ArrayList<AppointmentOutput> checkFinalSchedule(ArrayList<AppointmentOutput> appOutOrdenProfesional, ArrayList<AppointmentOutput> freeSchedule) {
        //cojo 1 cita
        for( AppointmentOutput apOut : appOutOrdenProfesional){
            //compruebo que esa cita esta en freeSchedule
            for(int i = 0 ; i< freeSchedule.size();i++){


                if(apOut.getDate1().toString().equals( freeSchedule.get(i).getDate1().toString()) ){
                    ArrayList<Time> timesArrayOut = new ArrayList<>(apOut.getTime1());

                    ArrayList<Time> timesFreeSchedule = new ArrayList<>(freeSchedule.get(i).getTime1());

                    //recorro las horas del dia de las citas
                    for(int j = 0 ; j < timesArrayOut.size(); j++){
                        //recorro las horas del turno
                        for(int k = 0 ; k < timesFreeSchedule.size(); k++){
                            //si la hora de la cita esta en la hora del turno, lo elimino
                            if (timesArrayOut.get(j).compareTo(timesFreeSchedule.get(k))== 0){
                                timesFreeSchedule.remove(k);
                                break;
                            }
                        }
                    }

                    TreeSet<Time> freeScheduleTreeSet = new TreeSet<>(timesFreeSchedule);
                    freeSchedule.get(i).setTime1(freeScheduleTreeSet);
                }
            }
        }
        return freeSchedule;
    }
}
