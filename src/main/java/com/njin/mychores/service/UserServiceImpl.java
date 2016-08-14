/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.UserDao;
import com.njin.mychores.model.User;
import javax.servlet.http.HttpSession;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
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
    private HttpSession httpSession;
    
    @Override
    public User findUser(Long id) {
        return userDao.findUser(id);
    }
    
    @Override
    public boolean authenticateUser(String email, String password) {
        User user = userDao.findUserForAuthentication(email);
        if(user != null && BCrypt.checkpw(password, user.getPasswordHash())) {
            httpSession.setAttribute("user", userDao.findUser(user.getId()));
            return true;                
        } 

        httpSession.setAttribute("user", null);
        return false;        
    }
    
    @Override
    public void createUser(User user) {
        userDao.createUser(user);
    }
    
}
