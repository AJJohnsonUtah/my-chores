/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.Chore;
import java.util.List;
import javax.activity.InvalidActivityException;
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
public class ChoreController {
    
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Chore createChore(@RequestBody Chore chore) throws InvalidActivityException {
        return null;
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Chore updateChore(@RequestBody Chore chore) throws InvalidActivityException {
        return null;
    }
    
    @RequestMapping(value = "/chore-group-user/{choreGroupUserId}", method = RequestMethod.GET)
    public List<Chore> getActiveChoresForChoreGroupUser(@PathVariable Long choreGroupUserId) throws InvalidActivityException {
        return null;
    }
    
    @RequestMapping(value = "/chore-user/{choreUserId}", method = RequestMethod.GET)
    public List<Chore> getActiveChoresForChoreUser(@PathVariable Long choreUserId) throws InvalidActivityException {
        return null;
    }
    
    @RequestMapping(value = "/current-user", method = RequestMethod.GET)
    public List<Chore> getActiveChoresForCurrentUser() throws InvalidActivityException {
        return null;
    }
    
    @RequestMapping(value = "/chore-group/{choreGroupId", method = RequestMethod.GET)
    public List<Chore> getActiveChoresForChoreGroup(@PathVariable Long choreGroupId) throws InvalidActivityException {
        return null;
    }
    
}
