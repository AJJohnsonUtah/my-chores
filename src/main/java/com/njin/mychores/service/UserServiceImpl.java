/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.UserDao;
import com.njin.mychores.model.ChoreUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author aj
 */
@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;
    
    @Autowired
    SessionService sessionService;
    
    @Override
    public ChoreUser findUser(Long id) {
        return userDao.findUser(id);
    }
    
    @Override
    public ChoreUser findUser(String email) {
        return userDao.findUser(email);
    }
    
    @Override
    public boolean authenticateUser(ChoreUser userToAuthenticate) {        
        ChoreUser user = userDao.findUser(userToAuthenticate.getEmail());
        if(user != null && BCrypt.checkpw(userToAuthenticate.getPassword(), user.getPasswordHash())) {
            sessionService.setCurrentUser(userDao.findUser(user.getId()));
            return true;
        } 
        sessionService.setCurrentUser(null);
        return false;        
    }
    
    @Override
    public void createUser(ChoreUser user) {
        userDao.createUser(user);
    }
    
}