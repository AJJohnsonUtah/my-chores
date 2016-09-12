/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 *
 * @author AJ
 */
public class ChoreFrequency implements Serializable {
    private Long timeBetweenRepeats;
    private Set<WeekDay> daysToRepeat;        

    public ChoreFrequency(Long timeBetweenRepeats) {
        daysToRepeat = null;
        this.timeBetweenRepeats = timeBetweenRepeats;
    }
    
    public ChoreFrequency(Set<WeekDay> daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }
    
    public Long getTimeBetweenRepeats() {
        return timeBetweenRepeats;
    }

    public void setTimeBetweenRepeats(Long timeBetweenRepeats) {
        this.timeBetweenRepeats = timeBetweenRepeats;
    }

    public Set<WeekDay> getDaysToRepeat() {
        return daysToRepeat;
    }

    public void setDaysToRepeat(Set<WeekDay> daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }        
    
    public Date getTimeOfNextInstance(Date previousInstance) {
        return null;
    }
    
}
