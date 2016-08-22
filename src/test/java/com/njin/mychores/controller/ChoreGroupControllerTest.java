/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.ChoreGroup;
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
public class ChoreGroupControllerTest extends BaseTest {
    
    @Autowired
    ChoreGroupController choreGroupController;
    
    public ChoreGroupControllerTest() {
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
    public void testCreateChoreGroup() {
        createTestUserAndLogin();
        assertNotNull("A user must be logged in to create a group.", userController.getCurrentUser());
        
        ChoreGroup choreGroup = new ChoreGroup();        
        String choreGroupName = "Test Chore Group";        
        choreGroup.setChoreGroupName(choreGroupName);
        
        assertNull(choreGroup.getId());
        ChoreGroup createdChoreGroup = choreGroupController.createChoreGroup(choreGroup).getBody();
        
        assertEquals(createdChoreGroup.getChoreGroupName(), choreGroupName);
        assertNotNull(createdChoreGroup.getId());                
        
        assertNotNull("Chore group user set should not be null.", createdChoreGroup.getChoreGroupUsers());
        assertEquals("A chore group user should have been created.", createdChoreGroup.getChoreGroupUsers().size(), 1);
        assertEquals("The creator of the chore group should be a chore group user.", 
                createdChoreGroup.getChoreGroupUsers().get(0).getChoreUser(),
                userController.getCurrentUser().getBody());
    }
}
