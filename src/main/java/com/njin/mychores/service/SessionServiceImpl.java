/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.model.ChoreUser;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class SessionServiceImpl implements SessionService {
    @Autowired 
    private HttpSession httpSession;
    
    public void setCurrentUser(ChoreUser user) {
        httpSession.setAttribute("user", user);
    }
    
    public ChoreUser getCurrentUser() {
        return (ChoreUser) httpSession.getAttribute("user");
    }
}
