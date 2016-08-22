/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.service.SessionService;
import com.njin.mychores.service.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aj
 */
@RestController
@RequestMapping(value = "/api/user")
@ControllerAdvice
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;
    
    @Autowired
    SessionService sessionService;
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<ChoreUser> createUser(@RequestBody ChoreUser user) {        
        user.setPasswordHash(BCrypt.hashpw(user.getPasswordHash(), BCrypt.gensalt()));
        userService.createUser(user);    
        return ResponseEntity.ok(user);
    }
    
    @RequestMapping(value = "/find/{userId}", method= RequestMethod.GET)
    public ResponseEntity<ChoreUser> findUser(Long userId) {
        ChoreUser user = userService.findUser(userId);      
        if(user == null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<ChoreUser> login(@RequestBody ChoreUser user) {
        if(userService.authenticateUser(user)) {
            return ResponseEntity.ok((ChoreUser) sessionService.getCurrentUser());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
    
    @RequestMapping(value="/current", method=RequestMethod.GET)
    public ResponseEntity<ChoreUser> getCurrentUser() {
        return ResponseEntity.ok(sessionService.getCurrentUser());
    }
}
