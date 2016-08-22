/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author aj
 */
@Controller
@RequestMapping
public class SiteController {
    
    /**
     * Any page visits that are not mapped to an appropriate call land here.
     * @return 
     */
    @RequestMapping(method=RequestMethod.GET)
    public String sendToHomePage() {
        return "/resources/index.html";
    }
}