/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.model.HttpErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 *
 * @author AJ
 */
public abstract class BaseController {        
    @ExceptionHandler    
    ResponseEntity<HttpErrorBody> handleException(Exception e) {        
        HttpErrorBody response = new HttpErrorBody(HttpStatus.BAD_REQUEST, e.getMessage());
        return ResponseEntity.status(response.getHttpStatus())
                .body(response);
    }
}
