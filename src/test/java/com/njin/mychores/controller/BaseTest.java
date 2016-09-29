/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.service.SessionService;
import java.time.LocalDateTime;
import java.util.List;
import javax.activity.InvalidActivityException;
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
@ContextConfiguration(classes = {JpaConfiguration.class}, loader = AnnotationConfigContextLoader.class)
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
    ChoreSpecController choreSpecController;

    @Autowired
    ChoreController choreController;

    @Autowired
    SessionService sessionService;

    @Autowired
    MessageSource messageSource;

    public ChoreUser createTestUserAndLogin() {
        ChoreUser user = new ChoreUser();
        user.setEmail(TestConstants.testEmail1);
        user.setPassword(TestConstants.testPassword1);

        userController.createUser(user);
        userController.login(user);
        return userController.getCurrentUser();
    }

    public ChoreUser createUserWithEmail(String email) {
        ChoreUser user = new ChoreUser();
        user.setEmail(email);
        user.setPassword(TestConstants.testPassword1);

        return userController.createUser(user);
    }

    public ChoreGroupUser createTestChoreGroup() {
        ChoreGroup choreGroup = new ChoreGroup();
        choreGroup.setChoreGroupName(TestConstants.testChoreGroupName);
        try {
            choreGroupController.createChoreGroup(choreGroup);
            List<ChoreGroupUser> currentChoreGroups = choreGroupUserController.findChoreGroupUsersForCurrentUser();
            return currentChoreGroups.get(0);
        } catch (IllegalAccessException ex) {
            fail("Chore group creation should not fail if logged in.");
            return null;
        }
    }

    public ChoreSpec createTestChoreSpecWithPreferredUser(ChoreGroupUser choreGroupUser) throws InvalidActivityException, IllegalAccessException {
        ChoreSpec choreSpecToCreate = new ChoreSpec();
        choreSpecToCreate.setChoreGroup(choreGroupUser.getChoreGroup());
        choreSpecToCreate.setName(TestConstants.testChoreSpecName);
        choreSpecToCreate.setFrequency(TestConstants.frequencyMondays);
        choreSpecToCreate.setNextInstanceDate(TestConstants.currentTime);
        choreSpecToCreate.setPreferredDoer(choreGroupUser);
        return choreSpecController.createChoreSpec(choreSpecToCreate);
    }

    public ChoreSpec createTestChoreSpecWithPreferredUserAndDate(ChoreGroupUser choreGroupUser, LocalDateTime nextInstance) throws InvalidActivityException, IllegalAccessException {
        ChoreSpec choreSpecToCreate = new ChoreSpec();
        choreSpecToCreate.setChoreGroup(choreGroupUser.getChoreGroup());
        choreSpecToCreate.setName(TestConstants.testChoreSpecName);
        choreSpecToCreate.setFrequency(TestConstants.frequencyMondays);
        choreSpecToCreate.setNextInstanceDate(nextInstance);
        choreSpecToCreate.setPreferredDoer(choreGroupUser);
        return choreSpecController.createChoreSpec(choreSpecToCreate);
    }

    public ChoreSpec createTestChoreSpec(ChoreGroup choreGroup) throws InvalidActivityException, IllegalAccessException {
        ChoreSpec choreSpecToCreate = new ChoreSpec();
        choreSpecToCreate.setChoreGroup(choreGroup);
        choreSpecToCreate.setName(TestConstants.testChoreSpecName);
        choreSpecToCreate.setFrequency(TestConstants.frequencyMondays);
        choreSpecToCreate.setNextInstanceDate(TestConstants.currentTime);
        return choreSpecController.createChoreSpec(choreSpecToCreate);
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
            for (ChoreGroupUser invitation : choreGroupUserController.findAllPendingReceivedInvitations()) {
                choreGroupUserController.acceptChoreGroupInvitation(invitation.getId());
            }
        } catch (IllegalAccessException ex) {
            fail("Chore group creation should not fail if logged in.");
        }
    }

    public void resetCurrentUser() {
        sessionService.setCurrentUser(null);
    }
}
