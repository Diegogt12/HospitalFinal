package com.example.demo.domain;

import com.example.demo.controller.AppointmentOutput;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import static java.util.Calendar.DATE;
import static java.util.Calendar.YEAR;

@Entity( name ="appointments")
public class Appointment {
    @Id
    private long appointmentId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull
    private Date date1;
    @NotNull
    private Time time1;
    private String nurseDni;
    private String doctorDni;

    @NotNull ( message = "patient dni is null")
    @NotBlank( message = "patient dni is blank")
    private String patientDni;


    public Appointment(){
    }

    public Appointment(long appointmentId, Date date1, Time time1, String nurseDni, String doctorDni, String patientDni) {
        this.appointmentId = appointmentId;
        this.date1 = date1;
        this.time1 = time1;
        this.nurseDni = nurseDni;
        this.doctorDni = doctorDni;
        this.patientDni = patientDni;
    }

    public long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Time getTime1() {
        return time1;
    }

    public void setTime1(Time time1) {
        this.time1 = time1;
    }

    public String getNurseDni() {
        return nurseDni;
    }

    public void setNurseDni(String nurseDni) {
        this.nurseDni = nurseDni;
    }

    public String getDoctorDni() {
        return doctorDni;
    }

    public void setDoctorDni(String doctorDni) {
        this.doctorDni = doctorDni;
    }

    public String getPatientDni() {
        return patientDni;
    }

    public void setPatientDni(String patientDni) {
        this.patientDni = patientDni;
    }

    public AppointmentOutput toOutput() {
        AppointmentOutput scheduleOutput = new AppointmentOutput(this.date1,this.time1);
        return scheduleOutput;
    }

    public static int[] checkNextMondayAndFriday() {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar.add(DATE,1);
        calendar2.add(DATE,1);
        int i = calendar.get(Calendar.DAY_OF_WEEK) ;

        int e = 0;
        int v = 0;

        switch (i) {
            case 2://Monday
                e = 7;
                v = e + 5;
                calendar.add(DATE, e);
                calendar2.add(DATE, v);
                break;

            case 3://Tuesday
                e = 6;
                v = e + 5;
                calendar.add(DATE, e);
                calendar2.add(DATE, v);

                break;

            case 4://Wenesday
                e = 5;
                v = e + 5;
                calendar.add(DATE, e);
                calendar2.add(DATE, v);
                break;

            case 5:// Tuesday
                e = 4;
                v = e + 5;
                calendar.add(DATE, e);
                calendar2.add(DATE, v);
                break;
            case 6://Friday
                e = 3;
                v = e + 5;
                calendar.add(DATE, e);
                calendar2.add(DATE, v);
                break;


        }


        int[] dates = new int[6];
        dates[0] = calendar.get(Calendar.YEAR);
        dates[1] = calendar.get(Calendar.MONTH) +1;
        if(dates[1] == 13) dates[1] = 0;
        dates[2] = calendar.get(DATE);
        dates[3] = calendar2.get(Calendar.YEAR);
        dates[4] = calendar2.get(Calendar.MONTH)+1;
        if (dates[4] == 13) dates[4] =0;
        dates[5] = calendar2.get(DATE);

        calendar.add(DATE, -e);
        calendar2.add(DATE, -v);


        return dates;
    }


    public static ArrayList<Date> checkNextMondayAndFridayToDate() {

        int[] dates = checkNextMondayAndFriday();
        String nextMonday = "";
        String nextFriday = "";
        for (int i = 0; i < 3; i++) {
            nextMonday += Integer.toString(dates[i]);
            if (i != 2) nextMonday += "-";
        }
        for (int i = 3; i < 6; i++) {
            nextFriday += Integer.toString(dates[i]);
            if (i != 5) nextFriday += "-";
        }


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date parsedMonday = null;
        java.util.Date parsedFriday = null;
        try {
            parsedMonday = format.parse(nextMonday);
            parsedFriday = format.parse(nextFriday);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        java.sql.Date dateMonday = new java.sql.Date(parsedMonday.getTime());
        java.sql.Date dateFriday = new java.sql.Date(parsedFriday.getTime());

        ArrayList<java.sql.Date> MondayAndFriday = new ArrayList<>();
        MondayAndFriday.add(dateMonday);
        MondayAndFriday.add(dateFriday);
        return MondayAndFriday;
    }
}
