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
import com.njin.mychores.service.SessionService;
import java.util.List;
import static org.junit.Assert.fail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    
    @Autowired
    ChoreGroupController choreGroupController;
    
    @Autowired
    ChoreGroupUserController choreGroupUserController;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    MessageSource messageSource;
    
    public ChoreUser createTestUserAndLogin() {
        ChoreUser user = new ChoreUser();
        user.setEmail("test@test.com");
        user.setPassword("fakearoni??22");
        
        userController.createUser(user);               
        userController.login(user);
        return userController.getCurrentUser();
    }
    
    public ChoreUser createUserWithEmail(String email) {
        ChoreUser user = new ChoreUser();
        user.setEmail(email);
        user.setPassword("fakearoni??22");
        
        return userController.createUser(user);        
    }
           
    public ChoreGroupUser createTestChoreGroup() {
        ChoreGroup choreGroup = new ChoreGroup();
        choreGroup.setChoreGroupName("Test Chore Group");
        try {
            choreGroupController.createChoreGroup(choreGroup);
            List<ChoreGroupUser> currentChoreGroups = choreGroupUserController.findChoreGroupUsersForCurrentUser();
            return currentChoreGroups.get(0);
        } catch (IllegalAccessException ex) {
            fail("Chore group creation should not fail if logged in.");
            return null;
        }
    }
    
    public void inviteUserToChoreGroup(ChoreUser choreUser, ChoreGroup choreGroup) {
        ChoreGroupUser choreGroupUser = new ChoreGroupUser();
        choreGroupUser.setChoreGroup(choreGroup);
        choreGroupUser.setChoreUser(choreUser);
        try {
            choreGroupUserController.inviteChoreUserToChoreGroup(choreGroupUser);        
        } catch (IllegalAccessException ex) {
            fail("Chore group invitation should not fail if logged in.");
        }
    }
    
    public void acceptAllInvitations() {    
       try {
            for(ChoreGroupUser invitation : choreGroupUserController.findAllPendingReceivedInvitations()) {
                choreGroupUserController.acceptChoreGroupInvitation(invitation);
            }            
        } catch (IllegalAccessException ex) {
            fail("Chore group creation should not fail if logged in.");            
        }
    }
        
    public void resetCurrentUser() {
        sessionService.setCurrentUser(null);
    }
}
