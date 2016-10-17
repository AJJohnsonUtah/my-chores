/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreStatus;
import com.njin.mychores.service.ChoreService;
import java.util.List;
import javax.activity.InvalidActivityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AJ
 */
@RestController
@ControllerAdvice
@RequestMapping(value = "/api/chore")
public class ChoreController extends BaseController {

    @Autowired
    ChoreService choreService;
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Chore createChore(@RequestBody Chore chore) throws InvalidActivityException, IllegalAccessException {
        checkRequiredAuthentication();
        return choreService.createChore(chore);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Chore updateChore(@RequestBody Chore chore) throws InvalidActivityException, IllegalAccessException {
        checkRequiredAuthentication();
        return choreService.updateChore(chore);
    }
    
    @RequestMapping(value = "/chore-group-user/{choreGroupUserId}", method = RequestMethod.GET)
    public List<Chore> getActiveChoresForChoreGroupUser(@PathVariable Long choreGroupUserId) throws InvalidActivityException, IllegalAccessException {
        checkRequiredAuthentication();
        return choreService.findChoresWithChoreGroupUserIdAndStatus(choreGroupUserId, ChoreStatus.TODO);
    }   
    
    @RequestMapping(value = "/current-user", method = RequestMethod.GET)
    public List<Chore> getActiveChoresForCurrentUser() throws IllegalAccessException, InvalidActivityException {
        return choreService.findChoresWithChoreUserAndStatus(sessionService.getCurrentUser().getId(), ChoreStatus.TODO);
    }
    
    @RequestMapping(value = "/chore-group/{choreGroupId", method = RequestMethod.GET)
    public List<Chore> getActiveChoresForChoreGroup(@PathVariable Long choreGroupId) throws IllegalAccessException, InvalidActivityException {
        return choreService.findChoresWithChoreGroupIdAndStatus(choreGroupId, ChoreStatus.TODO);
    }
    
    @RequestMapping(value = "/current-user/completed", method = RequestMethod.GET)
    public List<Chore> getCompletedChoresForCurrentUser() throws IllegalAccessException, InvalidActivityException {
        return choreService.findChoresWithChoreUserAndStatus(sessionService.getCurrentUser().getId(), ChoreStatus.COMPLETED);
    }
    
}
