/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.service.ChoreGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author AJ
 */
@RestController
@RequestMapping(value = "/api/user")
@ControllerAdvice
public class ChoreGroupController {
    
    @Autowired
    ChoreGroupService choreGroupService;
    
    
    @RequestMapping(value = "/create")
    public ResponseEntity<ChoreGroup> createChoreGroup(ChoreGroup choreGroup) {
        choreGroupService.createChoreGroup(choreGroup);
        return ResponseEntity.ok(choreGroupService.findChoreGroup(choreGroup.getId()));
    }
    
}
