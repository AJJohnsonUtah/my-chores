/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.util.Set;

/**
 *
 * @author AJ
 */
public class ChoreFrequency implements Serializable {

    private Long timeBetweenRepeats;
    private Set<DayOfWeek> daysToRepeat;

    private static Long MILLIS_IN_DAY = 86400000L - 1L;

    public ChoreFrequency(Long timeBetweenRepeats) {
        daysToRepeat = null;
        this.timeBetweenRepeats = timeBetweenRepeats;
    }

    public ChoreFrequency(Set<DayOfWeek> daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }
    
    public ChoreFrequency() {        
    };

    public Long getTimeBetweenRepeats() {
        return timeBetweenRepeats;
    }

    public void setTimeBetweenRepeats(Long timeBetweenRepeats) {
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

        if ((getDaysToRepeat() == null || getDaysToRepeat().isEmpty()) && getTimeBetweenRepeats() == null) {
            return null;
        }
        
        if (getDaysToRepeat() != null && !getDaysToRepeat().isEmpty()) {
            for (int i = 0; i < 7; i++) {
                effectiveDate = effectiveDate.plusDays(1);
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
