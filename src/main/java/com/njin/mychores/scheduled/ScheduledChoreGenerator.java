/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.scheduled;

import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.service.ChoreService;
import com.njin.mychores.service.ChoreSpecService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class ScheduledChoreGenerator {
 
    @Autowired
    ChoreService choreService;
    
    @Autowired
    ChoreSpecService choreSpecService;
    
    @Scheduled(fixedRate = 100000L)
    public void createAllScheduledChores() throws IllegalAccessException {
        System.out.println("creating those chores!");
        List<ChoreSpec> scheduledChoreSpecs = choreSpecService.findChoreSpecsWithPastNextInstanceDates();
        for(ChoreSpec scheduledChoreSpec : scheduledChoreSpecs) {
            choreService.createChoreFromChoreSpec(scheduledChoreSpec);
        }
    }
    
}
