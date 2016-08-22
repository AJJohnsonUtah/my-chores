/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.service.SessionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AJ
 */
@ContextConfiguration(classes={JpaConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@Transactional
public class BaseTest {
    
    @Autowired
    UserController userController;
    
    public void createTestUserAndLogin() {
        ChoreUser user = new ChoreUser();
        user.setEmail("test@test.com");
        user.setPassword("fakearoni??22");
        
        userController.createUser(user);
        
        user = new ChoreUser();
        user.setEmail("test@test.com");
        user.setPassword("fakearoni??22");
        
        userController.login(user);
    }
}
