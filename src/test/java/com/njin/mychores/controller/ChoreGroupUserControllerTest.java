/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreGroupUserStatus;
import com.njin.mychores.model.ChoreUser;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.BeforeClass;
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
**/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@Transactional
public class ChoreGroupUserControllerTest extends BaseTest {
    
    @Autowired
    ChoreGroupUserController choreGroupUserController;
    
    public ChoreGroupUserControllerTest() {
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
    public void inviteToChoreGroupAndAccept() throws IllegalAccessException {
        ChoreUser userToInvite = createUserWithEmail("User@ToInvite.com");        
        ChoreUser userWhoInvited = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
        
        assertEquals("No invitations should exist yet.", choreGroupUserController.findAllPendingSentInvitations().size(), 0);
        
        ChoreGroupUser invitation = new ChoreGroupUser();
        invitation.setChoreGroup(choreGroup);
        invitation.setChoreUser(userToInvite);
        choreGroupUserController.inviteChoreUserToChoreGroup(invitation);
        
        List<ChoreGroupUser> sentInvitations = choreGroupUserController.findAllPendingSentInvitations();
        assertEquals("1 invitations should exist .", 1, sentInvitations.size());
        invitation = sentInvitations.get(0);
        
        assertEquals("The inviter should be the current user.", userWhoInvited.getEmail(), invitation.getInvitedBy().getEmail());
        assertEquals("The recipient should be the other user.", userToInvite.getEmail(), invitation.getChoreUser().getEmail());
        assertEquals("The status should be pending.", invitation.getStatus(), ChoreGroupUserStatus.PENDING);                
        
        /** SWITCH TO OTHER USER TO TEST RECIPIENT FUNCTIONS **/
        userController.logout();
        userController.login(userToInvite);
        
        List<ChoreGroupUser> receivedInvitations = choreGroupUserController.findAllPendingReceivedInvitations();
        assertEquals("1 invitations should exist .", 1, receivedInvitations.size());
        invitation = receivedInvitations.get(0);
        
        assertEquals("The inviter should be the current user.", userWhoInvited.getEmail(), invitation.getInvitedBy().getEmail());
        assertEquals("The recipient should be the other user.", userToInvite.getEmail(), invitation.getChoreUser().getEmail());
        assertEquals("The status should be pending.", invitation.getStatus(), ChoreGroupUserStatus.PENDING);  
        
        List<ChoreGroup> activeChoreGroups = choreGroupController.readAllChoreGroups();
        assertEquals("The recipient should not have any active chore groups yet", 0, activeChoreGroups.size());
        
        choreGroupUserController.acceptChoreGroupInvitation(invitation);
        
        receivedInvitations = choreGroupUserController.findAllPendingReceivedInvitations();
        assertEquals("0 invitations should exist .", 0, receivedInvitations.size());
        
        activeChoreGroups = choreGroupController.readAllChoreGroups();
        assertEquals("The recipient should not have any active chore groups yet", 1, activeChoreGroups.size());
        
        ChoreGroup acceptedChoreGroup = activeChoreGroups.get(0);
        assertEquals("The accepted chore group should be the same as the first created.", choreGroup.getChoreGroupName(), acceptedChoreGroup.getChoreGroupName());
        
        List<ChoreGroupUser> membersOfGroup = acceptedChoreGroup.getChoreGroupUsers();
        assertEquals("There should be 2 members in this group now.", 2, membersOfGroup.size());
    }
    
    @Test
    public void acceptInviteToChoreGroup() throws IllegalAccessException {
        ChoreUser userToInvite = createUserWithEmail("User@ToInvite.com");        
        ChoreUser currentUser = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
        
        assertEquals("No invitations should exist yet.", choreGroupUserController.findAllPendingSentInvitations().size(), 0);
        
        ChoreGroupUser invitation = new ChoreGroupUser();
        invitation.setChoreGroup(choreGroup);
        invitation.setChoreUser(userToInvite);
        choreGroupUserController.inviteChoreUserToChoreGroup(invitation);
        
        List<ChoreGroupUser> sentInvitations = choreGroupUserController.findAllPendingSentInvitations();
        assertEquals("1 invitations should exist .", 1, sentInvitations.size());
        invitation = sentInvitations.get(0);
        
        assertEquals("The inviter should be the current user.", currentUser.getEmail(), invitation.getInvitedBy().getEmail());
        assertEquals("The recipient should be the other user.", userToInvite.getEmail(), invitation.getChoreUser().getEmail());
        assertEquals("The status should be pending.", invitation.getStatus(), ChoreGroupUserStatus.PENDING);                
    }
}