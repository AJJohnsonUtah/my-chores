/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.service.UserService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.PathVariable;
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ChoreUser createUser(@RequestBody ChoreUser user) {
        if (userService.findUser(user.getEmail()) == null) {
            user.setPasswordHash(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10)));
            userService.createUser(user);
            return user;
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("non.unique.email", new String[]{user.getEmail()}, Locale.getDefault()));
        }
    }

    @RequestMapping(value = "/find/{userId}", method = RequestMethod.GET)
    public ChoreUser findUser(@PathVariable Long userId) {
        ChoreUser user = userService.findUser(userId);
        if (user == null) {
            return user;
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("non.known.email", null, Locale.getDefault()));
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ChoreUser login(@RequestBody ChoreUser user) {
        if (userService.findUser(user.getEmail()) == null) {
            throw new IllegalArgumentException(messageSource.getMessage("non.known.email", null, Locale.getDefault()));
        }
        if (userService.authenticateUser(user)) {
            return sessionService.getCurrentUser();
        }
        throw new IllegalArgumentException(messageSource.getMessage("non.correct.password", null, Locale.getDefault()));
    }

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public ChoreUser getCurrentUser() {
        return sessionService.getCurrentUser();
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public void logout() {
        sessionService.setCurrentUser(null);
    }
}
