/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreUser;
import java.util.Locale;
import javax.activity.InvalidActivityException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class ChoreControllerTest extends BaseTest {
    
    
    public ChoreControllerTest() {
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
        resetCurrentUser();
    }
    
    @Test
    public void createChoreTest() {
        ChoreUser currentUser = createTestUserAndLogin();
        
    }
    
    @Test
    public void createChoreWithoutLoginError() {
        try {
            choreController.createChore(new Chore());
            fail("Without being logged in, creating a chore should fail.");
        } catch (InvalidActivityException ex) {
            assertEquals("A user must be logged in to create a chore", ex.getMessage(), messageSource.getMessage("non.valid.activity", null, Locale.getDefault()));
        }        
    }
}
