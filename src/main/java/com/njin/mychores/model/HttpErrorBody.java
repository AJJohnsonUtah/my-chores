/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.model;

import java.io.Serializable;
import org.springframework.http.HttpStatus;

/**
 *
 * @author AJ
 */
public class HttpErrorBody implements Serializable{
    private HttpStatus httpStatus;
    private String status;
    private String message;

    public HttpErrorBody(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.status = "Error";
    }   
    
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
}
