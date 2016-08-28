/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.service.SessionService;
import com.njin.mychores.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.bcrypt.BCrypt;
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
@ControllerAdvice
@RequestMapping(value = "/api/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;
    
    @Autowired
    SessionService sessionService;
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ChoreUser createUser(@RequestBody ChoreUser user) {        
        if(userService.findUser(user.getEmail()) == null) {
            user.setPasswordHash(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
            userService.createUser(user);    
            return user;
        } else {
            throw new IllegalArgumentException("This email already has an account associated with it.");
        }
    }
    
    @RequestMapping(value = "/find/{userId}", method= RequestMethod.GET)
    public ChoreUser findUser(Long userId) {
        ChoreUser user = userService.findUser(userId);      
        if(user == null) {
            return user;
        } else {
            throw new IllegalArgumentException("User not found.");
        }
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ChoreUser login(@RequestBody ChoreUser user) {
        if(userService.findUser(user.getEmail()) != null && userService.authenticateUser(user)) {
            return sessionService.getCurrentUser();
        }        
        throw new SecurityException("Invalid username/password.");        
    }
    
    @RequestMapping(value="/current", method=RequestMethod.GET)
    public ChoreUser getCurrentUser() {
        return sessionService.getCurrentUser();
    }
    
    @RequestMapping(value="/logout", method=RequestMethod.GET)
    public void logout() {
        sessionService.setCurrentUser(null);
    }
}

