/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.model.HttpErrorBody;
import com.njin.mychores.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author AJ
 */
public abstract class BaseController {
    
    @Autowired
    SessionService sessionService;
    
    @ExceptionHandler    
    ResponseEntity<HttpErrorBody> handleException(Exception e) {        
        HttpErrorBody response;
        if(e instanceof IllegalAccessException) {
            response = new HttpErrorBody(HttpStatus.UNAUTHORIZED, e.getMessage());
        } else {
            response = new HttpErrorBody(HttpStatus.BAD_REQUEST, e.getMessage());
        }
        return ResponseEntity.status(response.getHttpStatus())
                .body(response);
    }
    
    protected void checkRequiredAuthentication() throws IllegalAccessException {
        ChoreUser currentUser = sessionService.getCurrentUser();
        if(currentUser == null) {
            throw new IllegalAccessException("User must be logged in to perform this action.");
        } 
    }

}
