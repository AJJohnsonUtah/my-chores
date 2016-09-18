package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.model.ChoreUser;
import java.util.Date;
import java.util.List;
import javax.activity.InvalidActivityException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author AJ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@Transactional
public class ChoreSpecControllerTest extends BaseTest {
    
    public ChoreSpecControllerTest() {
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
    public void createChoreSpecTest() throws InvalidActivityException, IllegalAccessException {
        ChoreUser currentUser = createTestUserAndLogin();
        ChoreGroupUser owner = createTestChoreGroup();
        
        ChoreSpec choreSpecToCreate = new ChoreSpec();
        choreSpecToCreate.setChoreGroup(owner.getChoreGroup());
        choreSpecToCreate.setName(TestConstants.testChoreSpecName);
        choreSpecToCreate.setPreferredDoer(owner);
        choreSpecToCreate.setFrequency(TestConstants.frequencyMondays);
        choreSpecToCreate.setNextInstanceDate(TestConstants.currentTime);
        
        assertNull("The choreSpec should not have an id before creation.", choreSpecToCreate.getChoreSpecId());
        
        ChoreSpec createdChoreSpec = choreSpecController.createChoreSpec(choreSpecToCreate);
        
        assertEquals("The properties of the created chore spec should be the same as specified.", choreSpecToCreate.getChoreGroup(), createdChoreSpec.getChoreGroup());
        assertEquals("The properties of the created chore spec should be the same as specified.", choreSpecToCreate.getName(), createdChoreSpec.getName());
        assertEquals("The properties of the created chore spec should be the same as specified.", choreSpecToCreate.getPreferredDoer(), createdChoreSpec.getPreferredDoer());
        assertEquals("The properties of the created chore spec should be the same as specified.", choreSpecToCreate.getFrequency(), createdChoreSpec.getFrequency());
        assertNull("The next instance date of the created chore spec should now be null.", createdChoreSpec.getNextInstanceDate());       
        
        List<ChoreSpec> groupsChoreSpecs = choreGroupController.getChoreGroupChoreSpecs(owner.getChoreGroup().getId());
        assertEquals("The chore group's list of chore specs should only have this created chore spec.", 1, groupsChoreSpecs.size());
        
        assertEquals("The only element in the chore specs list should be the newly created spec.", createdChoreSpec, groupsChoreSpecs.get(0));
    }
    
    @Test
    public void createChoreSpecPopulatesLists() throws InvalidActivityException, IllegalAccessException {
        ChoreUser currentUser = createTestUserAndLogin();
        ChoreGroupUser owner = createTestChoreGroup();        
        ChoreSpec choreSpec = createTestChoreSpecWithPreferredUser(owner);
        
        List<Chore> choresFound = choreController.getActiveChoresForCurrentUser();
        assertEquals("Current user should have 1 chore.", 1, choresFound.size());
        
        choresFound = choreController.getActiveChoresForCurrentUser();
        assertEquals("Preferred doer user should have 1 chore.", 1, choresFound.size());
        
        choresFound = choreController.getActiveChoresForChoreGroupUser(owner.getId());
        assertEquals("Preferred doer should have 1 chore.", 1, choresFound.size());
        
        choresFound = choreController.getActiveChoresForChoreGroup(owner.getChoreGroup().getId());
        assertEquals("Preferred doer should have 1 chore.", 1, choresFound.size());
        
        choresFound = choreSpecController.findChoresOfChoreSpec(choreSpec.getChoreSpecId());
        assertEquals("Preferred doer should have 1 chore.", 1, choresFound.size());
    }
    
    @Test 
    public void createChoreSpecInFuture() throws InvalidActivityException, IllegalAccessException {        
        ChoreUser currentUser = createTestUserAndLogin();
        ChoreGroupUser owner = createTestChoreGroup();        
        Date futureDate = new Date();
        futureDate.setTime(futureDate.getTime() + 10000000);
        ChoreSpec choreSpec = createTestChoreSpecWithPreferredUserAndDate(owner, futureDate);
                
        assertNotNull("A chore spec should be created, even if the date is in the future.", choreSpec);
        
        List<Chore> choresFound = choreController.getActiveChoresForCurrentUser();
        assertEquals("Current user should have no chores.", 0, choresFound.size());
        
        choresFound = choreController.getActiveChoresForCurrentUser();
        assertEquals("Preferred doer user should have no chores.", 0, choresFound.size());
        
        choresFound = choreController.getActiveChoresForChoreGroupUser(owner.getId());
        assertEquals("Preferred doer should have no chores.", 0, choresFound.size());
        
        choresFound = choreController.getActiveChoresForChoreGroup(owner.getChoreGroup().getId());
        assertEquals("Chore group should have no chores.", 0, choresFound.size());
        
        choresFound = choreSpecController.findChoresOfChoreSpec(choreSpec.getChoreSpecId());
        assertEquals("Chore spec should have no chores.", 0, choresFound.size());
    }        
    
    @Test
    public void updateChoreSpecTest() throws InvalidActivityException, IllegalAccessException {
        ChoreUser currentUser = createTestUserAndLogin();
        ChoreGroupUser owner = createTestChoreGroup();        
        ChoreSpec choreSpec = createTestChoreSpecWithPreferredUser(owner);
        
        choreSpec.setPreferredDoer(null);
        ChoreSpec updatedChoreSpec = choreSpecController.updateChoreSpec(choreSpec);
        
        assertNull("The updated chore spec should have a null preferred doer.", updatedChoreSpec.getPreferredDoer());
        assertEquals("The updated and pre-updated chore specs should have the same ids.", choreSpec.getChoreSpecId(), updatedChoreSpec.getChoreSpecId());
    }
    
}
