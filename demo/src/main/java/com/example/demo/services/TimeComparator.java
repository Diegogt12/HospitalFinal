package com.example.demo.services;

import java.sql.Time;
import java.util.Comparator;

public class TimeComparator implements Comparator<Time> {

    @Override
    public int compare(Time o1, Time o2) {
        if( o1.after(o2) ) return 1;
        return -1;
    }
}