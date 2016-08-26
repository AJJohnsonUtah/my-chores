/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.ChoreUser;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AJ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@Transactional
public class UserControllerTest {
    
    @Autowired
    UserController userController;
    
    public UserControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @Rollback
    @After
    public void tearDown() {
    }
    
    @Test
    public void testCreateUserAndAuthenticate() {
        ChoreUser user = new ChoreUser();
        String email = "test@test.com";
        String password = "fakearoni??22";
        user.setEmail(email);
        user.setPassword(password);
        
        ChoreUser createdUser = userController.createUser(user);
        
        assertEquals(createdUser.getEmail(), email);
        assertNotNull(createdUser.getId());

        user = new ChoreUser();
        user.setEmail(email);
        user.setPassword(password);
        ChoreUser loggedInUser = userController.login(user);
        assertEquals(createdUser.getEmail(), email);
        assertNotNull(createdUser.getId());
        
        assertEquals(loggedInUser, userController.getCurrentUser());
    }
}
