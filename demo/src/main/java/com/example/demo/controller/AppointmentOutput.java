package com.example.demo.controller;

import com.example.demo.services.DateComparator;
import com.example.demo.services.TimeComparator;

import java.sql.Date;
import java.sql.Time;
import java.util.TreeSet;

public class AppointmentOutput {
    private Date date1;
    private TreeSet<Time> time1;

    public AppointmentOutput(Date date1, TreeSet time1) {
        this.date1 = date1;
        this.time1 = time1;
    }
    public AppointmentOutput(Date date1, Time time) {
        this.date1 = date1;
        if(this.time1 == null) this.time1 = new TreeSet<Time>(new TimeComparator());
        this.time1.add(time);
    }

    public AppointmentOutput() {
        this.time1 = new TreeSet<Time>(new TimeComparator());
    }

    public void addTime1(Time time){
        this.time1.add(time);
    }



    public Date getDate1() {
        return date1;
    }

    public TreeSet getTime1() {
        return time1;
    }

    public void setTime1(TreeSet time1) {
        this.time1 = time1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }
}
