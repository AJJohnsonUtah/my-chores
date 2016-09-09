/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreGroupUserRole;
import com.njin.mychores.model.ChoreGroupUserStatus;
import com.njin.mychores.model.ChoreUser;
import java.util.List;
import java.util.Locale;
import javax.activity.InvalidActivityException;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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
**/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={JpaConfiguration.class}, loader=AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@Transactional
public class ChoreGroupUserControllerTest extends BaseTest {    
    
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
        
        List<ChoreGroupUser> activeChoreGroups = choreGroupUserController.findChoreGroupUsersForCurrentUser();
        assertEquals("The recipient should have 1 chore group.", 1, activeChoreGroups.size());
        assertEquals("The recipient should not have any active chore groups yet", ChoreGroupUserStatus.PENDING, activeChoreGroups.get(0).getStatus());
        
        
        choreGroupUserController.acceptChoreGroupInvitation(invitation);
        
        receivedInvitations = choreGroupUserController.findAllPendingReceivedInvitations();
        assertEquals("0 invitations should exist .", 0, receivedInvitations.size());
        
        activeChoreGroups = choreGroupUserController.findChoreGroupUsersForCurrentUser();
        assertEquals("The recipient should have 1 chore group.", 1, activeChoreGroups.size());
        assertEquals("The recipient should not have any active chore groups yet", ChoreGroupUserStatus.ACCEPTED, activeChoreGroups.get(0).getStatus());
        
        ChoreGroupUser acceptedChoreGroup = activeChoreGroups.get(0);
        assertEquals("The accepted chore group should be the same as the first created.", choreGroup.getChoreGroupName(), acceptedChoreGroup.getChoreGroup().getChoreGroupName());
        
        List<ChoreGroupUser> membersOfGroup = acceptedChoreGroup.getChoreGroup().getChoreGroupUsers();
        assertEquals("There should be 2 members in this group now.", 2, membersOfGroup.size());
    }
    
    @Test
    public void findAllChoreGroupsWithoutLogin() throws IllegalAccessException {
        try {
        choreGroupUserController.findChoreGroupUsersForCurrentUser();
            fail("Should not be able to read all chore groups when not logged in.");
        } catch (IllegalAccessException ex) {
            assertEquals("Exception message should be 'login required' message", ex.getMessage(), messageSource.getMessage("login.required", null, Locale.getDefault()));
        }
    }   
    
    @Test
    public void declineInviteToChoreGroup() throws IllegalAccessException {
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
        
        userController.logout();
        userController.login(userToInvite);
        
        List<ChoreGroupUser> receivedInvitations = choreGroupUserController.findAllPendingReceivedInvitations();
        assertEquals("1 invitations should exist .", 1, receivedInvitations.size());
        invitation = receivedInvitations.get(0);
        
        assertEquals("The inviter should be the current user.", userWhoInvited.getEmail(), invitation.getInvitedBy().getEmail());
        assertEquals("The recipient should be the other user.", userToInvite.getEmail(), invitation.getChoreUser().getEmail());
        assertEquals("The status should be pending.", invitation.getStatus(), ChoreGroupUserStatus.PENDING);  
        
        List<ChoreGroupUser> activeChoreGroups = choreGroupUserController.findChoreGroupUsersForCurrentUser();
        assertEquals("The recipient should have 1 chore group.", 1, activeChoreGroups.size());
        assertEquals("The recipient should not have any active chore groups yet", ChoreGroupUserStatus.PENDING, activeChoreGroups.get(0).getStatus());

        choreGroupUserController.declineChoreGroupInvitation(invitation);
        
        receivedInvitations = choreGroupUserController.findAllPendingReceivedInvitations();
        assertEquals("0 invitations should exist .", 0, receivedInvitations.size());
        
        activeChoreGroups = choreGroupUserController.findChoreGroupUsersForCurrentUser();
        assertEquals("The recipient should have 1 chore group.", 1, activeChoreGroups.size());
        assertEquals("The recipient should not have any active chore groups yet", ChoreGroupUserStatus.DECLINED, activeChoreGroups.get(0).getStatus());

    }
    
    @Test
    public void inviteToChoreGroupWhenNotLoggedIn() {
        try {
            choreGroupUserController.inviteChoreUserToChoreGroup(new ChoreGroupUser());
            fail("Should not be able to send an invite when not logged in.");
        } catch (IllegalAccessException ex) {
            assertEquals(ex.getMessage(), messageSource.getMessage("login.required", null, Locale.getDefault()));
        }
    }
    
    @Test
    public void reinviteToChoreGroupAfterDecline() throws IllegalAccessException  {        
        ChoreUser userToInvite = createUserWithEmail("User@ToInvite.com");        
        ChoreUser userWhoInvited = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
               
        ChoreGroupUser invitation = new ChoreGroupUser();
        invitation.setChoreGroup(choreGroup);
        invitation.setChoreUser(userToInvite);
        
        choreGroupUserController.inviteChoreUserToChoreGroup(invitation);
        userController.logout();
        
        userController.login(userToInvite);
        
        List<ChoreGroupUser> receivedInvitations = choreGroupUserController.findAllPendingReceivedInvitations();
        invitation = receivedInvitations.get(0);        
        choreGroupUserController.declineChoreGroupInvitation(invitation);       
        
        userController.logout();
        userController.login(userWhoInvited);
        
        List<ChoreGroupUser> sentInvitations = choreGroupUserController.findAllPendingSentInvitations();
        assertEquals("There should be no pending sent invitations after being declined.", 0, sentInvitations.size());

        invitation = new ChoreGroupUser();
        invitation.setChoreGroup(choreGroup);
        invitation.setChoreUser(userToInvite);

        choreGroupUserController.inviteChoreUserToChoreGroup(invitation);

        sentInvitations = choreGroupUserController.findAllPendingSentInvitations();
        assertEquals("There should be one pending sent invitation after being resent.", 1, sentInvitations.size());
        
        userController.logout();
        
        userController.login(userToInvite);
        
        receivedInvitations = choreGroupUserController.findAllPendingReceivedInvitations();
        assertEquals("There should be one pending received invitation after being resent.", 1, receivedInvitations.size());
    }
    
    @Test
    public void invitingSelfToGroupShouldFail() throws IllegalAccessException {        
        ChoreUser userWhoInvited = createTestUserAndLogin();
        ChoreUser userToInvite = userWhoInvited;        
        ChoreGroup choreGroup = createTestChoreGroup();
        
        ChoreGroupUser invitation = new ChoreGroupUser();
        invitation.setChoreGroup(choreGroup);
        invitation.setChoreUser(userToInvite);
        try {
            choreGroupUserController.inviteChoreUserToChoreGroup(invitation);
            fail("A user should not be able to invite themselves to a chore group.");
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), messageSource.getMessage("non.unique.choreGroupUser", null, Locale.getDefault()));
        }
    }
    
    @Test
    public void reinviteToChoreGroupShouldFailAfterAccept() throws IllegalAccessException  {        
        ChoreUser userToInvite = createUserWithEmail("User@ToInvite.com");        
        ChoreUser userWhoInvited = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
               
        ChoreGroupUser invitation = new ChoreGroupUser();
        invitation.setChoreGroup(choreGroup);
        invitation.setChoreUser(userToInvite);
        
        choreGroupUserController.inviteChoreUserToChoreGroup(invitation);
        userController.logout();
        
        userController.login(userToInvite);
        
        List<ChoreGroupUser> receivedInvitations = choreGroupUserController.findAllPendingReceivedInvitations();
        invitation = receivedInvitations.get(0);        
        choreGroupUserController.acceptChoreGroupInvitation(invitation);       
        
        userController.logout();
        userController.login(userWhoInvited);
        
        invitation = new ChoreGroupUser();
        invitation.setChoreGroup(choreGroup);
        invitation.setChoreUser(userToInvite);

        try {
            choreGroupUserController.inviteChoreUserToChoreGroup(invitation);
            fail("A user should not be able to be invited to a group to which they belong.");
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), messageSource.getMessage("non.unique.choreGroupUser", null, Locale.getDefault()));
        }
    }
    
    @Test
    public void inviteNonExistentUser() throws IllegalAccessException {
        ChoreUser userWhoInvited = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
        
        ChoreUser userToInvite = new ChoreUser();
        userToInvite.setEmail("Totally@Fake.com");
        
        ChoreGroupUser invitation = new ChoreGroupUser();
        invitation.setChoreUser(userToInvite);
        invitation.setChoreGroup(choreGroup);
        
        try {
            choreGroupUserController.inviteChoreUserToChoreGroup(invitation);
            fail("A user should not be able to be invite a non-existent user.");
        } catch (IllegalArgumentException ex) {
            assertEquals(ex.getMessage(), messageSource.getMessage("non.known.email", null, Locale.getDefault()));
        }
    }
    
    @Test
    public void removeUserFromGroup() throws IllegalAccessException, InvalidActivityException {
        ChoreUser userToInvite = createUserWithEmail("User@ToInvite.com");        
        ChoreUser userWhoInvited = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
        
        inviteUserToChoreGroup(userToInvite, choreGroup);
        
        userController.logout();
        userController.login(userToInvite);
        acceptAllInvitations();
        userController.logout();
        userController.login(userWhoInvited);
        
        List<ChoreGroupUser> activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroup);
        List<ChoreGroupUser> allMembers = choreGroupController.membersOfChoreGroup(choreGroup);
        
        assertEquals("There should be 2 active members before removal.", 2, activeMembers.size());
        assertEquals("There should be 2 members before removal.", 2, allMembers.size());

        ChoreGroupUser invited = null;
        for(ChoreGroupUser activeMember : activeMembers) {
            if(activeMember.getChoreUser().equals(userToInvite)) {
                invited = activeMember;
            }
        }
        assertNotNull(invited);
        
        choreGroupUserController.removeChoreGroupUser(invited);
        
        activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroup);
        allMembers = choreGroupController.membersOfChoreGroup(choreGroup);
        
        assertEquals("There should be 1 active member after removal.", 1, activeMembers.size());
        assertEquals("There should be 2 total members after removal.", 2, allMembers.size());
    }
    
    @Test
    public void updateChoreGroupUserRole() throws IllegalAccessException {
        ChoreUser userToInvite = createUserWithEmail("User@ToInvite.com");        
        ChoreUser userWhoInvited = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
        
        inviteUserToChoreGroup(userToInvite, choreGroup);
        
        userController.logout();
        userController.login(userToInvite);
        acceptAllInvitations();
        userController.logout();
        userController.login(userWhoInvited);
        
        List<ChoreGroupUser> activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroup);
        
        assertEquals("There should be 2 active members before removal.", 2, activeMembers.size());

        ChoreGroupUser invited = null;
        for(ChoreGroupUser activeMember : activeMembers) {
            if(activeMember.getChoreUser().equals(userToInvite)) {
                invited = activeMember;
                assertEquals("Users who are invited should start as members.", ChoreGroupUserRole.MEMBER, activeMember.getChoreGroupUserRole());
            } else if(activeMember.getChoreUser().equals(userWhoInvited)) {
                assertEquals("Users who invite should start as owners.", ChoreGroupUserRole.OWNER, activeMember.getChoreGroupUserRole());
            }
        }
        assertNotNull(invited);
        invited.setChoreGroupUserRole(ChoreGroupUserRole.OWNER);
        
        activeMembers = choreGroupController.activeMembersOfChoreGroup(choreGroup);
        
        for(ChoreGroupUser activeMember : activeMembers) {
            assertEquals("After updating role, all users should be owners.", ChoreGroupUserRole.OWNER, activeMember.getChoreGroupUserRole());
        }
    }
    
    @Test
    public void removingOwnerFromGroupShouldFail() throws IllegalAccessException {
        ChoreUser ownerOfGroup = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
        
        ChoreGroupUser owner = new ChoreGroupUser();
        owner.setChoreGroup(choreGroup);
        owner.setChoreUser(ownerOfGroup);
        
        try {
            choreGroupUserController.removeChoreGroupUser(owner);
            fail("The owner of the group should not be able to be removed.");
        } catch (InvalidActivityException ex) {
            assertEquals("Removing the owner of a group should be an invalid activity", ex.getMessage(), messageSource.getMessage("non.valid.action", null, Locale.getDefault()));
        }
    }
    
    @Test
    public void demotingOwnerInGroupShouldFail() throws IllegalAccessException {
        ChoreUser ownerOfGroup = createTestUserAndLogin();
        ChoreGroup choreGroup = createTestChoreGroup();
        
        ChoreGroupUser owner = new ChoreGroupUser();
        owner.setChoreGroup(choreGroup);
        owner.setChoreUser(ownerOfGroup);
        owner.setChoreGroupUserRole(ChoreGroupUserRole.ADMIN);
        
        try {
            choreGroupUserController.updateChoreGroupUserRole(owner);
            fail("The owner of the group should not be able to be removed.");
        } catch (InvalidActivityException ex) {
            assertEquals("Removing the owner of a group should be an invalid activity", ex.getMessage(), messageSource.getMessage("non.valid.action", null, Locale.getDefault()));
        }
    }
}