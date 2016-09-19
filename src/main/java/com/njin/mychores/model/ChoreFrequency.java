/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoField;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author AJ
 */
public class ChoreFrequency implements Serializable {

    private Integer timeBetweenRepeats;
    private Set<DayOfWeek> daysToRepeat;

    private static Integer MILLIS_IN_DAY = 86400000 - 1;

    public ChoreFrequency(Integer timeBetweenRepeats) {
        daysToRepeat = null;
        this.timeBetweenRepeats = timeBetweenRepeats;
    }

    public ChoreFrequency(Set<DayOfWeek> daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }

    public Integer getTimeBetweenRepeats() {
        return timeBetweenRepeats;
    }

    public void setTimeBetweenRepeats(Integer timeBetweenRepeats) {
        this.timeBetweenRepeats = timeBetweenRepeats;
    }

    public Set<DayOfWeek> getDaysToRepeat() {
        return daysToRepeat;
    }

    public void setDaysToRepeat(Set<DayOfWeek> daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }

    public LocalDateTime getTimeOfNextInstance(LocalDateTime previousInstanceFinished) {
        LocalDateTime effectiveDate = previousInstanceFinished;

        if (getDaysToRepeat() != null && !getDaysToRepeat().isEmpty()) {
            for (int i = 0; i < 7; i++) {
                effectiveDate.plusDays(1);
                if (getDaysToRepeat().contains(effectiveDate.getDayOfWeek())) {
                    break;
                }
            }
            effectiveDate = effectiveDate.withHour(5);
            effectiveDate = effectiveDate.withMinute(0);
        } else if (getTimeBetweenRepeats() != null) {

            effectiveDate = effectiveDate.plus(getTimeBetweenRepeats(), ChronoField.MILLI_OF_DAY.getBaseUnit());

            if (getTimeBetweenRepeats() >= MILLIS_IN_DAY) {
                effectiveDate = effectiveDate.withHour(5);
                effectiveDate = effectiveDate.withMinute(0);
            }
        }

        return effectiveDate;
    }

}
