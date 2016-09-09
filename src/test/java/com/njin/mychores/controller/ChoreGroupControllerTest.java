/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreUser;
import java.util.List;
import java.util.Locale;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
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
public class ChoreGroupControllerTest extends BaseTest {
    
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
        resetCurrentUser();
    }
    
    @Test
    public void testCreateChoreGroup() {
        createTestUserAndLogin();
        assertNotNull("A user must be logged in to create a group.", userController.getCurrentUser());
        
        ChoreGroup choreGroup = new ChoreGroup();        
        String choreGroupName = "Test Chore Group";        
        choreGroup.setChoreGroupName(choreGroupName);
        
        assertNull(choreGroup.getId());
        ChoreGroupUser createdOwner = new ChoreGroupUser();
        try {
            createdOwner = choreGroupController.createChoreGroup(choreGroup);
        } catch (IllegalAccessException ex) {
            fail("User is logged in and should have been able to create a chore group.");
        }
        ChoreGroup createdChoreGroup = createdOwner.getChoreGroup();
        
        assertEquals(createdChoreGroup.getChoreGroupName(), choreGroupName);
        assertNotNull(createdChoreGroup.getId());                
        
        assertNotNull("Chore group user set should not be null.", createdChoreGroup.getChoreGroupUsers());
        assertEquals("A chore group user should have been created.", createdChoreGroup.getChoreGroupUsers().size(), 1);
        assertEquals("The creator of the chore group should be a chore group user.", 
                createdChoreGroup.getChoreGroupUsers().get(0).getChoreUser(),
                userController.getCurrentUser());
    }
    
    @Test
    public void testFindAllChoreGroups() {
        createTestUserAndLogin();

        ChoreGroup choreGroup1 = new ChoreGroup();        
        String choreGroupName1 = "Test Chore Group";        
        choreGroup1.setChoreGroupName(choreGroupName1);
        ChoreGroup choreGroup2 = new ChoreGroup();
        String choreGroupName2 = "Test 2 Chore Group";
        choreGroup2.setChoreGroupName(choreGroupName2);
        
        ChoreGroupUser createdOwner1 = new ChoreGroupUser();
        ChoreGroupUser createdOwner2 = new ChoreGroupUser();
        
        try {
            createdOwner1 = choreGroupController.createChoreGroup(choreGroup1);
            createdOwner2 = choreGroupController.createChoreGroup(choreGroup2);
        } catch (IllegalAccessException ex) {
            fail("User is logged in and should be create a chore group.");
        }
        
        ChoreGroup createdChoreGroup1 = createdOwner1.getChoreGroup();
        ChoreGroup createdChoreGroup2 = createdOwner2.getChoreGroup();
        
        assertEquals(createdChoreGroup1.getChoreGroupName(), choreGroup1.getChoreGroupName());
        assertEquals(createdChoreGroup2.getChoreGroupName(), choreGroup2.getChoreGroupName());
        
        assertEquals(createdChoreGroup1.getChoreGroupUsers().get(0).getChoreUser(), userController.getCurrentUser());
        assertEquals(createdChoreGroup2.getChoreGroupUsers().get(0).getChoreUser(), userController.getCurrentUser());
        
        try {
            List<ChoreGroupUser> activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroup1);
            assertNotNull("Created chore groups list should not be null.", activeMembers);
            assertEquals(activeMembers.size(), 1);
            
            activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroup2);
            assertNotNull("Created chore groups list should not be null.", activeMembers);
            assertEquals(activeMembers.size(), 1);
        } catch (IllegalAccessException ex) {
            fail("User is logged in and should be able to read all chore groups.");
        }
    }        
    
    @Test
    public void createChoreGroupWithoutLogin() throws IllegalAccessException {
        ChoreGroup choreGroup = new ChoreGroup();        
        String choreGroupName = "Test Chore Group";        
        choreGroup.setChoreGroupName(choreGroupName);
        try {
            choreGroupController.createChoreGroup(choreGroup);
            fail("Should not be able to create a chore group when not logged in.");
        } catch (IllegalAccessException ex) {
            assertEquals("Exception message should be 'login required' message", ex.getMessage(), messageSource.getMessage("login.required", null, Locale.getDefault()));
        }
    }    

    @Test
    public void findAllUsersOfChoreGroup() throws IllegalAccessException {
        ChoreUser userToInvite = createUserWithEmail("user@toInvite.com");
        ChoreUser userWhoInvited = createTestUserAndLogin();
        ChoreGroupUser choreGroupUser = createTestChoreGroup();
        
        List<ChoreGroupUser> activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroupUser.getChoreGroup());
        assertEquals("There should only be 1 active member upon creation of chore group.", 1, activeMembers.size());
        
        inviteUserToChoreGroup(userToInvite, choreGroupUser.getChoreGroup());
        
        userController.logout();
        userController.login(userToInvite);
        
        try {
            choreGroupController.activeMembersOfChoreGroup(choreGroupUser.getChoreGroup());
            fail("User should be unable to access this data before accepting invitation.");
        } catch (IllegalAccessException ex) {
            assertEquals("Error message should be 'not own data'", ex.getMessage(), messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        
        acceptAllInvitations();
        
        activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroupUser.getChoreGroup());
        assertEquals("There should be 2 active members of chore group upon accepting invitation.", 2, activeMembers.size());
        
        userController.logout();
        userController.login(userWhoInvited);        
        
        activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroupUser.getChoreGroup());
        assertEquals("There should be 2 active members of chore group upon accepting invitation.", 2, activeMembers.size());        
    }
    
    @Test
    public void updateChoreGroupName() throws IllegalAccessException {
        ChoreUser currentUser = createTestUserAndLogin();
        ChoreGroupUser testChoreGroupUser = createTestChoreGroup();
        String originalName = testChoreGroupUser.getChoreGroup().getChoreGroupName();
        testChoreGroupUser.getChoreGroup().setChoreGroupName(originalName + " Updated");
        ChoreGroup updatedChoreGroup = choreGroupController.updateChoreGroup(testChoreGroupUser.getChoreGroup());
        
        assertEquals("Chore group name should change on update.", originalName + " Updated", updatedChoreGroup.getChoreGroupName());        
    }
        
}
