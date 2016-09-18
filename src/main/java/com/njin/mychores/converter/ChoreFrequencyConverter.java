/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.converter;

import com.njin.mychores.model.ChoreFrequency;
import com.njin.mychores.model.WeekDay;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class ChoreFrequencyConverter implements AttributeConverter<ChoreFrequency, Integer> {

    @Override
    public Integer convertToDatabaseColumn(ChoreFrequency x) {
        Integer result;
        if (x.getTimeBetweenRepeats() != null) {
            result = -1 * x.getTimeBetweenRepeats();
        } else if(!x.getDaysToRepeat().isEmpty()) {
            Integer daysInteger = 0;
            if (x.getDaysToRepeat().contains(WeekDay.SUNDAY)) {
                daysInteger = daysInteger | (1);
            }
            if (x.getDaysToRepeat().contains(WeekDay.MONDAY)) {
                daysInteger = daysInteger | (1 << 1);
            }
            if (x.getDaysToRepeat().contains(WeekDay.TUESDAY)) {
                daysInteger = daysInteger | (1 << 2);
            }
            if (x.getDaysToRepeat().contains(WeekDay.WEDNESDAY)) {
                daysInteger = daysInteger | (1 << 3);
            }
            if (x.getDaysToRepeat().contains(WeekDay.THURSDAY)) {
                daysInteger = daysInteger | (1 << 4);
            }
            if (x.getDaysToRepeat().contains(WeekDay.FRIDAY)) {
                daysInteger = daysInteger | (1 << 5);
            }
            if (x.getDaysToRepeat().contains(WeekDay.SATURDAY)) {
                daysInteger = daysInteger | (1 << 6);
            }
            result = daysInteger;
        } else {
            result = 0;
        }
        return result;
    }

    @Override
    public ChoreFrequency convertToEntityAttribute(Integer valToConvert) {        
        ChoreFrequency choreFrequency;
        if (valToConvert < 0) {
            choreFrequency = new ChoreFrequency(valToConvert * -1);
        } else {
            Set<WeekDay> daysToRepeat = new HashSet<>();
            if ((valToConvert & (1)) > 0) {
                daysToRepeat.add(WeekDay.SUNDAY);
            }
            if ((valToConvert & (1 << 1)) > 0) {
                daysToRepeat.add(WeekDay.MONDAY);
            }
            if ((valToConvert & (1 << 2)) > 0) {
                daysToRepeat.add(WeekDay.TUESDAY);
            }
            if ((valToConvert & (1 << 3)) > 0) {
                daysToRepeat.add(WeekDay.WEDNESDAY);
            }
            if ((valToConvert & (1 << 4)) > 0) {
                daysToRepeat.add(WeekDay.THURSDAY);
            }
            if ((valToConvert & (1 << 5)) > 0) {
                daysToRepeat.add(WeekDay.FRIDAY);
            }
            if ((valToConvert & (1 << 6)) > 0) {
                daysToRepeat.add(WeekDay.SATURDAY);
            }
            choreFrequency = new ChoreFrequency(daysToRepeat);
        }
        return choreFrequency;
    }
}
