/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.model.ChoreStatus;
import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.service.ChoreService;
import java.time.LocalDateTime;
import java.util.Locale;
import javax.activity.InvalidActivityException;
import static junit.framework.TestCase.assertTrue;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertNull;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    ChoreService choreService;
    
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
    public void createChoreWithoutLoginError() throws IllegalAccessException, InvalidActivityException {
        try {
            choreController.createChore(new Chore());
            fail("Without being logged in, creating a chore should fail.");
        } catch (IllegalAccessException ex) {
            assertEquals("A user must be logged in to create a chore", ex.getMessage(), messageSource.getMessage("login.required", null, Locale.getDefault()));
        }
    }
    
    @Test
    public void updateChoreTest() throws InvalidActivityException, IllegalAccessException {
        ChoreUser currentUser = createTestUserAndLogin();
        ChoreGroupUser owner = createTestChoreGroup();
        ChoreSpec choreSpec = createTestChoreSpecWithPreferredUser(owner);
        
        Chore createdChore = choreSpecController.findChoresOfChoreSpec(choreSpec.getChoreSpecId()).get(0);
        LocalDateTime created = createdChore.getCreated();
        LocalDateTime firstUpdated = createdChore.getUpdated();
        
        assertEquals("The created chore should begin with status TODO.", ChoreStatus.TODO, createdChore.getStatus());
        assertEquals("The created chore should have the same created and updated date", created, firstUpdated);
        
        createdChore.setStatus(ChoreStatus.COMPLETED);
        choreController.updateChore(createdChore);
        
        Chore updatedChore = choreSpecController.findChoresOfChoreSpec(choreSpec.getChoreSpecId()).get(0);
        LocalDateTime secondUpdated = updatedChore.getUpdated();
        
        assertEquals("The updated chore should have status COMPLETED.", ChoreStatus.COMPLETED, createdChore.getStatus());
        assertTrue("The updated chore's updated time should be later than the first updated time.", secondUpdated.isAfter(firstUpdated));
    }

    @Test
    public void createAndCompleteChoreTest() throws InvalidActivityException, IllegalAccessException {
        ChoreUser currentUser = createTestUserAndLogin();
        ChoreGroupUser owner = createTestChoreGroup();
        ChoreSpec choreSpec = createTestChoreSpecWithPreferredUser(owner);
        
        Chore createdChore = choreSpecController.findChoresOfChoreSpec(choreSpec.getChoreSpecId()).get(0);
        
        assertNull("The next instance date should be null, since a chore exists.", choreSpec.getNextInstanceDate());
        
        createdChore.setStatus(ChoreStatus.COMPLETED);
        choreController.updateChore(createdChore);
        
        Chore updatedChore = choreSpecController.findChoresOfChoreSpec(choreSpec.getChoreSpecId()).get(0);

        choreSpec = choreSpecController.findChoreSpec(choreSpec.getChoreSpecId());
        
        assertNotNull("The next instance date should be non-null, since a chore was completed.", choreSpec.getNextInstanceDate());
        
        choreSpec.setNextInstanceDate(LocalDateTime.now());
        choreSpecController.updateChoreSpec(choreSpec);
        assertEquals("There should be one more active chore again.", 1, choreSpecController.findChoresOfChoreSpec(choreSpec.getChoreSpecId()).size());
        choreService.createChoreFromChoreSpec(choreSpec);
        assertEquals("There should be one more active chore again.", 2, choreSpecController.findChoresOfChoreSpec(choreSpec.getChoreSpecId()).size());
    }
    
}
