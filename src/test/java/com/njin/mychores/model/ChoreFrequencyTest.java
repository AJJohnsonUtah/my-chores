/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import com.njin.mychores.config.JpaConfiguration;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.EnumSet;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AJ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@Transactional
public class ChoreFrequencyTest {
    
    private static final Long MILLIS_IN_DAY = 24 * 60 * 60 * 1000L; 
    
    @Test
    public void dailyFrequencyTest() {
        ChoreFrequency dailyFrequency = new ChoreFrequency(MILLIS_IN_DAY);
        
        assertEquals("Chore frequency should have values from constructor input", MILLIS_IN_DAY, dailyFrequency.getTimeBetweenRepeats());
        assertNull("The set of days to repeat should be null when time between repeats is provided", dailyFrequency.getDaysToRepeat());
        
        LocalDateTime currentDate = LocalDateTime.now();
        
        LocalDateTime timeOfNextInstance = dailyFrequency.getTimeOfNextInstance(currentDate);
        
        assertNotEquals("The time of the next instance should be changed", currentDate, timeOfNextInstance);
        assertTrue("The next instance time should be after this one.", timeOfNextInstance.isAfter(currentDate));
        
        LocalDateTime expectedTimeOfNextInstance = currentDate.plusDays(1);
        expectedTimeOfNextInstance = expectedTimeOfNextInstance.withHour(5);
        expectedTimeOfNextInstance = expectedTimeOfNextInstance.withMinute(0);
        
        assertEquals("The date of the next instance should be as expected", expectedTimeOfNextInstance.getDayOfMonth(), timeOfNextInstance.getDayOfMonth());
        assertEquals("The time of the next instance should be as expected", expectedTimeOfNextInstance.getHour(), timeOfNextInstance.getHour());
        assertEquals("The time of the next instance should be as expected", expectedTimeOfNextInstance.getMinute(), timeOfNextInstance.getMinute());        
    }
    
    @Test
    public void daysOfWeekFrequencyTest() {
        ChoreFrequency dailyFrequency = new ChoreFrequency(EnumSet.of(DayOfWeek.MONDAY));
        
        assertNull("The time between repeats should be null when set of weekdays is provided", dailyFrequency.getTimeBetweenRepeats());
        assertEquals("Chore frequency should have values from constructor input", 1, dailyFrequency.getDaysToRepeat().size());
        assertTrue("Chore frequency should have values from constructor input", dailyFrequency.getDaysToRepeat().contains(DayOfWeek.MONDAY));
        
        LocalDateTime currentDate = LocalDateTime.now();                
        
        LocalDateTime timeOfNextInstance = dailyFrequency.getTimeOfNextInstance(currentDate);        
        
        assertNotEquals("The time of the next instance should be changed", currentDate, timeOfNextInstance);
        assertTrue("The next instance time should be after this one.", timeOfNextInstance.isAfter(currentDate));                
        assertTrue("Is within 1 week from now", timeOfNextInstance.isBefore(currentDate.plusDays(7)));
        
        assertEquals("The date of the next instance should be as expected", DayOfWeek.MONDAY, timeOfNextInstance.getDayOfWeek());
        assertEquals("The time of the next instance should be as expected", 5, timeOfNextInstance.getHour());
        assertEquals("The time of the next instance should be as expected", 0, timeOfNextInstance.getMinute());                
    }
}
