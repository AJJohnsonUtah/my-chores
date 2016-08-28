/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.ChoreGroupDao;
import com.njin.mychores.dao.ChoreGroupUserDao;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreGroupUserRole;
import com.njin.mychores.model.ChoreGroupUserStatus;
import com.njin.mychores.model.ChoreUser;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AJ
 */
@Service
@Transactional
public class ChoreGroupUserServiceImpl implements ChoreGroupUserService {

    @Autowired
    ChoreGroupUserDao choreGroupUserDao;
    
    @Autowired
    UserService userService;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    ChoreGroupService choreGroupService;
    
    @Override
    public void createChoreGroupUser(ChoreGroupUser choreGroupUser) {
        choreGroupUserDao.createChoreGroupUser(choreGroupUser);
    }

    @Override
    public void updateChoreGroupUser(ChoreGroupUser choreGroupUser) {
        choreGroupUserDao.updateChoreGroupUser(choreGroupUser);
    }

    @Override
    public List<ChoreGroupUser> findAllForUser(ChoreUser user) {
        return choreGroupUserDao.findAllForUser(user);
    }

    @Override
    public List<ChoreGroupUser> findAllForChoreGroup(ChoreGroup choreGroup) {
        return choreGroupUserDao.findAllForChoreGroup(choreGroup);
    }

    @Override
    public void acceptChoreGroupInvitation(ChoreGroupUser invitation) {                
        ChoreUser userToInvite = sessionService.getCurrentUser();
        ChoreGroup choreGroup = choreGroupService.findChoreGroup(invitation.getChoreGroup().getId());
        if(userToInvite == null || choreGroup == null) {
            throw new IllegalArgumentException("Invalid data for invitation.");
        }
        ChoreGroupUser invitationToUpdate = choreGroupUserDao.findChoreGroupUser(choreGroup, userToInvite);
        invitationToUpdate.setStatus(ChoreGroupUserStatus.ACCEPTED);
        updateChoreGroupUser(invitationToUpdate);
    }

    @Override
    public void declineChoreGroupInvitation(ChoreGroupUser invitation) {
        ChoreUser userToInvite = sessionService.getCurrentUser();
        ChoreGroup choreGroup = choreGroupService.findChoreGroup(invitation.getChoreGroup().getId());
        if(userToInvite == null || choreGroup == null) {
            throw new IllegalArgumentException("Invalid data for invitation.");
        }
        ChoreGroupUser invitationToUpdate = choreGroupUserDao.findChoreGroupUser(choreGroup, userToInvite);
        invitationToUpdate.setStatus(ChoreGroupUserStatus.DECLINED);
        updateChoreGroupUser(invitationToUpdate);    }

    @Override
    public void inviteUserToChoreGroup(ChoreGroupUser choreGroupUser) {
        ChoreUser invitedBy = sessionService.getCurrentUser();
        ChoreUser userToInvite = userService.findUser(choreGroupUser.getChoreUser().getEmail());
        ChoreGroup choreGroup = choreGroupService.findChoreGroup(choreGroupUser.getChoreGroup().getId());
        if(invitedBy == null || userToInvite == null || choreGroup == null) {
            throw new IllegalArgumentException("Invalid data for invitation.");
        }
        ChoreGroupUser invitation = new ChoreGroupUser(choreGroup, userToInvite, invitedBy, ChoreGroupUserRole.MEMBER, ChoreGroupUserStatus.PENDING);
        choreGroupUserDao.createChoreGroupUser(invitation);
    }
    
    @Override
    public List<ChoreGroupUser> findAllForUserWithStatus(ChoreUser user, ChoreGroupUserStatus status) {
        return choreGroupUserDao.findAllForUserWithStatus(user, status);
    }
    
    @Override
    public List<ChoreGroupUser> findAllForInviterWithStatus(ChoreUser invitedBy, ChoreGroupUserStatus status) {
        return choreGroupUserDao.findAllForInviterWithStatus(invitedBy, status);
    }
    
}
