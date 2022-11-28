package com.example.demo.services;

import com.example.demo.controller.AppointmentOutput;

import java.sql.Time;
import java.util.Comparator;

public class DateComparator implements Comparator<AppointmentOutput> {

        @Override
        public int compare(AppointmentOutput o1, AppointmentOutput o2) {
            if( o1.getDate1().after(o2.getDate1()) ) return 1;
            return -1;
        }


}

