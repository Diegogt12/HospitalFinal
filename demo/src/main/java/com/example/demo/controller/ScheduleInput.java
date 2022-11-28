package com.example.demo.controller;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Time;

public class ScheduleInput {
    @NotNull
    private Time hourStart;
    @NotNull

    private Time hourFinish;

    public ScheduleInput(){}
    public ScheduleInput(Time hourStart, Time hourFinish) {
        this.hourStart = hourStart;
        this.hourFinish = hourFinish;
    }

    public Time getHourStart() {
        return hourStart;
    }

    public void setHourStart(Time hourStart) {
        this.hourStart = hourStart;
    }

    public Time getHourFinish() {
        return hourFinish;
    }

    public void setHourFinish(Time hourFinish) {
        this.hourFinish = hourFinish;
    }
}
