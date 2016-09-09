/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.service.ChoreGroupService;
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
@RequestMapping(value = "/api/chore-group")
public class ChoreGroupController extends BaseController {
    
    @Autowired
    ChoreGroupService choreGroupService;
        
    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChoreGroup createChoreGroup(@RequestBody ChoreGroup choreGroup) throws IllegalAccessException {
        checkRequiredAuthentication();
        choreGroupService.createChoreGroup(choreGroup);
        return choreGroupService.findChoreGroup(choreGroup.getId());
    }
        
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChoreGroup updateChoreGroup(@RequestBody ChoreGroup choreGroup) throws IllegalAccessException {
        checkRequiredAuthentication();
        return choreGroupService.updateChoreGroup(choreGroup);
    }        
    
    @RequestMapping(value = "/active-members", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ChoreGroupUser> activeMembersOfChoreGroup(@RequestBody ChoreGroup choreGroup) throws IllegalAccessException {
        checkRequiredAuthentication();
        return choreGroupService.findAllActiveMembersForChoreGroup(choreGroup);
    }   
    
    @RequestMapping(value = "/members", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public List<ChoreGroupUser> membersOfChoreGroup(@RequestBody ChoreGroup choreGroup) throws IllegalAccessException {
        checkRequiredAuthentication();
        return choreGroupService.findAllMembersForChoreGroup(choreGroup);
    }
    
}
