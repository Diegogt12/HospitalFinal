package com.example.demo.repository;

import com.example.demo.domain.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {
    @Query(value = "SELECT nurse_dni FROM appointments WHERE (date1 BETWEEN :date1 AND :date2 ) GROUP BY nurse_dni ORDER BY COUNT(nurse_dni)desc limit 0,1;", nativeQuery = true)
    String findByDate1BetweenWhereCountIsMax(@Param( "date1" ) Date date1  , @Param( "date2" )Date date2);

    @Query(value = "SELECT doctor_dni FROM appointments WHERE (date1 BETWEEN :date1 AND :date2 ) GROUP BY doctor_dni ORDER BY COUNT(doctor_dni)desc limit 0,1;", nativeQuery = true)
    String findByDate1BetweenWhereCountIsMaxDoctor(@Param( "date1" ) Date date1  , @Param( "date2" )Date date2);

    Appointment findByPatientDniAndDate1AndTime1(String patientDni, Date date1, Time time1);

    boolean existsByNurseDniAndDate1AndTime1(String nurseDni, Date date1, Time time1);


    ArrayList<Appointment> findAllByNurseDniAndDate1Between(String nurseId, Date nextMonday, Date nextFriday);


    ArrayList<Appointment> findAllByDoctorDniAndDate1Between(String doctorDni, Date nextMonday, Date nextFriday);

    boolean existsByDoctorDniAndDate1AndTime1(String doctorDni, Date date1, Time time1);


    ArrayList<Appointment> findAllByDate1AndPatientDni(Date dateSql, String patientDni);
}
