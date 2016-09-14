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
    private Integer timeBetweenRepeats;
    private Set<WeekDay> daysToRepeat;        

    public ChoreFrequency(Integer timeBetweenRepeats) {
        daysToRepeat = null;
        this.timeBetweenRepeats = timeBetweenRepeats;
    }
    
    public ChoreFrequency(Set<WeekDay> daysToRepeat) {
        this.daysToRepeat = daysToRepeat;
    }
    
    public Integer getTimeBetweenRepeats() {
        return timeBetweenRepeats;
    }

    public void setTimeBetweenRepeats(Integer timeBetweenRepeats) {
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
