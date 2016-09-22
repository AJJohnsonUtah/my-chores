/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.ChoreFrequency;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.EnumSet;

/**
 *
 * @author AJ
 */
public class TestConstants {
    public static String testEmail1 = "test@test.com";
    public static String testEmail2 = "test2@test2.com";
    public static String testPassword1 = "testABC1234?";
    public static String testPassword2 = "TESTabc1234?";
    public static String testChoreGroupName = "Test Chore Group";
    public static String testChoreSpecName = "Test Chore Spec";
    public static ChoreFrequency frequencyDaily = new ChoreFrequency(84000000);
    public static ChoreFrequency frequencyMondays = new ChoreFrequency(EnumSet.of(DayOfWeek.MONDAY));
    public static LocalDateTime currentTime = LocalDateTime.now();
    public static LocalDateTime tomorrow = LocalDateTime.now().plusDays(1L);
}
