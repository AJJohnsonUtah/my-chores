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
public class ChoreFrequencyConverter implements AttributeConverter<ChoreFrequency, Long> {

    @Override
    public Long convertToDatabaseColumn(ChoreFrequency x) {
        if (x.getTimeBetweenRepeats() != null) {
            return -1 * x.getTimeBetweenRepeats();
        } else {
            Long daysLong = 0L;
            if (x.getDaysToRepeat().contains(WeekDay.SUNDAY)) {
                daysLong = daysLong | (1);
            }
            if (x.getDaysToRepeat().contains(WeekDay.MONDAY)) {
                daysLong = daysLong | (1 << 1);
            }
            if (x.getDaysToRepeat().contains(WeekDay.TUESDAY)) {
                daysLong = daysLong | (1 << 2);
            }
            if (x.getDaysToRepeat().contains(WeekDay.WEDNESDAY)) {
                daysLong = daysLong | (1 << 3);
            }
            if (x.getDaysToRepeat().contains(WeekDay.THURSDAY)) {
                daysLong = daysLong | (1 << 4);
            }
            if (x.getDaysToRepeat().contains(WeekDay.FRIDAY)) {
                daysLong = daysLong | (1 << 5);
            }
            if (x.getDaysToRepeat().contains(WeekDay.SATURDAY)) {
                daysLong = daysLong | (1 << 6);
            }
            return daysLong;
        }
    }

    @Override
    public ChoreFrequency convertToEntityAttribute(Long y) {
        ChoreFrequency choreFrequency ;
        if (y < 0) {
            choreFrequency = new ChoreFrequency(y * -1);
        } else {
            Set<WeekDay> daysToRepeat = new HashSet<>();
            if ((y & (1)) > 0) {
                daysToRepeat.add(WeekDay.SUNDAY);
            }
            if ((y & (1 << 1)) > 0) {
                daysToRepeat.add(WeekDay.MONDAY);
            }
            if ((y & (1 << 2)) > 0) {
                daysToRepeat.add(WeekDay.TUESDAY);
            }
            if ((y & (1 << 3)) > 0) {
                daysToRepeat.add(WeekDay.WEDNESDAY);
            }
            if ((y & (1 << 4)) > 0) {
                daysToRepeat.add(WeekDay.THURSDAY);
            }
            if ((y & (1 << 5)) > 0) {
                daysToRepeat.add(WeekDay.FRIDAY);
            }
            if ((y & (1 << 6)) > 0) {
                daysToRepeat.add(WeekDay.SATURDAY);
            }
            choreFrequency = new ChoreFrequency(daysToRepeat);
        }
        return choreFrequency;
    }
}
