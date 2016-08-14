/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.User;
import com.njin.mychores.service.UserService;
import java.util.Locale;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author aj
 */
@RestController
@RequestMapping(value = "/api/user")
@ControllerAdvice
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    MessageSource messageSource;
    
    @Autowired
    HttpSession httpSession;
    
    @ExceptionHandler    
    ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(messageSource.getMessage("non.unique.email", new String[]{"notReally@this.one"}, Locale.getDefault()));
    }
    
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity<User> createUser(@RequestParam("email") String email, @RequestParam("password") String password) {
        User user = new User();
        user.setEmail(email);
        user.setPasswordHash(BCrypt.hashpw(password, BCrypt.gensalt()));
        userService.createUser(user);
        return ResponseEntity.ok(user);
    }
    
    @RequestMapping(value = "/find/{userId}", method= RequestMethod.GET)
    public ResponseEntity<User> findUser(Long userId) {
        User user = userService.findUser(userId);      
        if(user == null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    
    @RequestMapping(value="/login", method=RequestMethod.POST)
    public ResponseEntity<User> login(@RequestParam("email") String email, @RequestParam("password") String password) {
        if(userService.authenticateUser(email, password)) {
            return ResponseEntity.ok((User) httpSession.getAttribute("user"));
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
}
