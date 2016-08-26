/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.UserDao;
import com.njin.mychores.model.ChoreUser;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class SessionServiceImpl implements SessionService {
    @Autowired 
    private HttpSession httpSession;
    
    @Autowired
    private UserDao choreUserDao;
    
    @Override
    public void setCurrentUser(ChoreUser user) {
        httpSession.setAttribute("userId", user.getId());
    }
    
    @Override
    public ChoreUser getCurrentUser() {
        if(httpSession.getAttribute("userId") == null) {
            return null;
        } else {
            return choreUserDao.findUser((Long)httpSession.getAttribute("userId"));
        }
    }
}
