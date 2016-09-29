/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.converter;

import com.njin.mychores.model.ChoreFrequency;
import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author AJ
 */
@Converter
public class ChoreFrequencyConverter implements AttributeConverter<ChoreFrequency, Long> {

    @Override
    public Long convertToDatabaseColumn(ChoreFrequency x) {
        Long result;
        if (x.getTimeBetweenRepeats() != null) {
            result = -1 * x.getTimeBetweenRepeats();
        } else if(!x.getDaysToRepeat().isEmpty()) {
            Long daysInteger = 0L;
            if (x.getDaysToRepeat().contains(DayOfWeek.SUNDAY)) {
                daysInteger = daysInteger | (1);
            }
            if (x.getDaysToRepeat().contains(DayOfWeek.MONDAY)) {
                daysInteger = daysInteger | (1 << 1);
            }
            if (x.getDaysToRepeat().contains(DayOfWeek.TUESDAY)) {
                daysInteger = daysInteger | (1 << 2);
            }
            if (x.getDaysToRepeat().contains(DayOfWeek.WEDNESDAY)) {
                daysInteger = daysInteger | (1 << 3);
            }
            if (x.getDaysToRepeat().contains(DayOfWeek.THURSDAY)) {
                daysInteger = daysInteger | (1 << 4);
            }
            if (x.getDaysToRepeat().contains(DayOfWeek.FRIDAY)) {
                daysInteger = daysInteger | (1 << 5);
            }
            if (x.getDaysToRepeat().contains(DayOfWeek.SATURDAY)) {
                daysInteger = daysInteger | (1 << 6);
            }
            result = daysInteger;
        } else {
            result = 0L;
        }
        return result;
    }

    @Override
    public ChoreFrequency convertToEntityAttribute(Long valToConvert) {        
        ChoreFrequency choreFrequency;
        if (valToConvert < 0) {
            choreFrequency = new ChoreFrequency(valToConvert * -1);
        } else {
            Set<DayOfWeek> daysToRepeat = new HashSet<>();
            if ((valToConvert & (1)) > 0) {
                daysToRepeat.add(DayOfWeek.SUNDAY);
            }
            if ((valToConvert & (1 << 1)) > 0) {
                daysToRepeat.add(DayOfWeek.MONDAY);
            }
            if ((valToConvert & (1 << 2)) > 0) {
                daysToRepeat.add(DayOfWeek.TUESDAY);
            }
            if ((valToConvert & (1 << 3)) > 0) {
                daysToRepeat.add(DayOfWeek.WEDNESDAY);
            }
            if ((valToConvert & (1 << 4)) > 0) {
                daysToRepeat.add(DayOfWeek.THURSDAY);
            }
            if ((valToConvert & (1 << 5)) > 0) {
                daysToRepeat.add(DayOfWeek.FRIDAY);
            }
            if ((valToConvert & (1 << 6)) > 0) {
                daysToRepeat.add(DayOfWeek.SATURDAY);
            }
            choreFrequency = new ChoreFrequency(daysToRepeat);
        }
        return choreFrequency;
    }
}
