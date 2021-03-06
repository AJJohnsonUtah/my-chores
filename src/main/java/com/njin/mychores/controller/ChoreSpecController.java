/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.service.ChoreService;
import com.njin.mychores.service.ChoreSpecService;
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
@RequestMapping(value = "/api/chore-spec")
public class ChoreSpecController extends BaseController {

    @Autowired
    ChoreSpecService choreSpecService;
    
    @Autowired
    ChoreService choreService;

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE) 
    public ChoreSpec createChoreSpec(@RequestBody ChoreSpec choreSpec) throws InvalidActivityException, IllegalAccessException {
        checkRequiredAuthentication();
        return choreSpecService.createChoreSpec(choreSpec);
    }
    
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ChoreSpec updateChoreSpec(@RequestBody ChoreSpec choreSpec) throws InvalidActivityException, IllegalAccessException {
        checkRequiredAuthentication();       
        return choreSpecService.updateChoreSpec(choreSpec);
    }
    
    @RequestMapping(value = "/find/{choreSpecId}", method = RequestMethod.GET)
    public ChoreSpec findChoreSpec(@PathVariable Long choreSpecId) throws InvalidActivityException, IllegalAccessException {
        checkRequiredAuthentication();
        return choreSpecService.findChoreSpec(choreSpecId);
    }
    
    @RequestMapping(value = "/{choreSpecId}/chores", method = RequestMethod.GET)
    public List<Chore> findChoresOfChoreSpec(@PathVariable Long choreSpecId) throws InvalidActivityException, IllegalAccessException {
        checkRequiredAuthentication();
        return choreService.findChoresWithChoreSpecId(choreSpecId);
    }        

}
