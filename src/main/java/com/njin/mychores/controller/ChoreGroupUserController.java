/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreGroupUserStatus;
import com.njin.mychores.service.ChoreGroupUserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
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
@RequestMapping(value = "/api/chore-group-user")
public class ChoreGroupUserController extends BaseController {
    @Autowired
    ChoreGroupUserService choreGroupUserService;
    
    @RequestMapping(value = "/invite", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void inviteChoreUserToChoreGroup(@RequestBody ChoreGroupUser choreGroupUser) throws IllegalAccessException {
        checkRequiredAuthentication();        
        choreGroupUserService.inviteUserToChoreGroup(choreGroupUser);
    }
    
    @RequestMapping(value = "/accept", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void acceptChoreGroupInvitation(@RequestBody ChoreGroupUser choreGroupUser) throws IllegalAccessException {
        checkRequiredAuthentication();
        choreGroupUserService.acceptChoreGroupInvitation(choreGroupUser);
    }
    
    @RequestMapping(value = "/decline", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void declineChoreGroupInvitation(@RequestBody ChoreGroupUser choreGroupUser) throws IllegalAccessException {
        checkRequiredAuthentication();
        choreGroupUserService.declineChoreGroupInvitation(choreGroupUser);
    }
    
    @RequestMapping(value = "/find-all-sent", method = RequestMethod.GET)
    public List<ChoreGroupUser> findAllPendingSentInvitations() throws IllegalAccessException {
        checkRequiredAuthentication();
        return choreGroupUserService.findAllForInviterWithStatus(sessionService.getCurrentUser(), ChoreGroupUserStatus.PENDING);
    }
    
    @RequestMapping(value = "/find-all-received", method = RequestMethod.GET)
    public List<ChoreGroupUser> findAllPendingReceivedInvitations() throws IllegalAccessException {
        checkRequiredAuthentication();
        return choreGroupUserService.findAllForUserWithStatus(sessionService.getCurrentUser(), ChoreGroupUserStatus.PENDING);
    }
}