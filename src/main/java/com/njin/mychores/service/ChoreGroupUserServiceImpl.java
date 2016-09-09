/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.ChoreGroupUserDao;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreGroupUserRole;
import com.njin.mychores.model.ChoreGroupUserStatus;
import com.njin.mychores.model.ChoreUser;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import javax.activity.InvalidActivityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
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
    
    @Autowired
    MessageSource messageSource;
    
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
    public ChoreGroupUser findChoreGroupUser(ChoreGroupUser choreGroupUser) {
        if(choreGroupUser.getId() != null) {
            return choreGroupUserDao.find(choreGroupUser.getId());
        } else if (choreGroupUser.getChoreGroup() != null && choreGroupUser.getChoreUser() != null) {
            return choreGroupUserDao.findChoreGroupUser(choreGroupUser.getChoreGroup(), choreGroupUser.getChoreUser());
        } else {
            throw new IllegalArgumentException(messageSource.getMessage("not.enough.data", null, Locale.getDefault()));
        }
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
        updateChoreGroupUser(invitationToUpdate);    
    }

    @Override
    public void inviteUserToChoreGroup(ChoreGroupUser choreGroupUser) {
        ChoreUser invitedBy = sessionService.getCurrentUser();
        ChoreUser userToInvite = userService.findUser(choreGroupUser.getChoreUser().getEmail());
        ChoreGroup choreGroup = choreGroupService.findChoreGroup(choreGroupUser.getChoreGroup().getId());
        if(userToInvite == null) {
            throw new IllegalArgumentException(messageSource.getMessage("non.known.email", null, Locale.getDefault()));
        }
        if(choreGroup == null) {
            throw new IllegalArgumentException(messageSource.getMessage("non.known.choreGroup", null, Locale.getDefault()));
        }
        ChoreGroupUser invitation = choreGroupUserDao.findChoreGroupUser(choreGroup, userToInvite);
        // If this user has never been invited to this group, invite them
        if(invitation == null) {
            invitation = new ChoreGroupUser(choreGroup, userToInvite, invitedBy, ChoreGroupUserRole.MEMBER, ChoreGroupUserStatus.PENDING);
            choreGroupUserDao.createChoreGroupUser(invitation);    
        } else if(EnumSet.of(ChoreGroupUserStatus.ACCEPTED).contains(invitation.getStatus())) {        
            throw new IllegalArgumentException(messageSource.getMessage("non.unique.choreGroupUser", null, Locale.getDefault()));
        } else {
            invitation.setStatus(ChoreGroupUserStatus.PENDING);
            choreGroupUserDao.updateChoreGroupUser(invitation);
        }
        
    }
    
    @Override
    public List<ChoreGroupUser> findAllForUserWithStatus(ChoreUser user, ChoreGroupUserStatus status) {
        return choreGroupUserDao.findAllForUserWithStatus(user, status);
    }
    
    @Override
    public List<ChoreGroupUser> findAllForInviterWithStatus(ChoreUser invitedBy, ChoreGroupUserStatus status) {
        return choreGroupUserDao.findAllForInviterWithStatus(invitedBy, status);
    }

    @Override
    public boolean isActiveMemberOfChoreGroup(ChoreUser choreUser, ChoreGroup choreGroup) {
        ChoreGroupUser member = choreGroupUserDao.findChoreGroupUser(choreGroup, choreUser);
        if(member == null) {
            return false;
        } else if(member.getStatus() == ChoreGroupUserStatus.ACCEPTED) {
            return true;
        }
        return false;
    }
    
    @Override
    public boolean isOwnerOfChoreGroup(ChoreUser choreUser, ChoreGroup choreGroup) {
        ChoreGroupUser member = choreGroupUserDao.findChoreGroupUser(choreGroup, choreUser);
        if(member == null) {
            return false;
        } else if(member.getChoreGroupUserRole()== ChoreGroupUserRole.OWNER) {
            return true;
        }
        return false;
    }
            
    @Override
    public boolean isAdminOfChoreGroup(ChoreUser choreUser, ChoreGroup choreGroup) {
        ChoreGroupUser member = choreGroupUserDao.findChoreGroupUser(choreGroup, choreUser);
        if(member == null) {
            return false;
        } else if(member.getChoreGroupUserRole()== ChoreGroupUserRole.ADMIN) {
            return true;
        }
        return false;
    }
    
    @Override
    public void removeChoreGroupUser(ChoreGroupUser choreGroupUser) throws IllegalAccessException, InvalidActivityException {
        ChoreUser currentUser = sessionService.getCurrentUser();
        ChoreGroupUser userToRemove = findChoreGroupUser(choreGroupUser);
        if(userToRemove.getChoreGroupUserRole() == ChoreGroupUserRole.OWNER) {
            throw new InvalidActivityException(messageSource.getMessage("non.valid.action", null, Locale.getDefault()));
        }
        
        if(isOwnerOfChoreGroup(currentUser, userToRemove.getChoreGroup())) {
                userToRemove.setStatus(ChoreGroupUserStatus.REMOVED);
                choreGroupUserDao.updateChoreGroupUser(userToRemove);           
        } else if(isAdminOfChoreGroup(currentUser, userToRemove.getChoreGroup())) {
            userToRemove.setStatus(ChoreGroupUserStatus.REMOVED);
            choreGroupUserDao.updateChoreGroupUser(userToRemove);                    
        } else {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
    }
    
    @Override
    public void updateChoreGroupUserRole(ChoreGroupUser choreGroupUser) throws IllegalAccessException, InvalidActivityException {
        ChoreUser currentUser = sessionService.getCurrentUser();
        ChoreGroupUser choreGroupUserToUpdate = findChoreGroupUser(choreGroupUser);
        if(choreGroupUserToUpdate.getChoreGroupUserRole() == ChoreGroupUserRole.OWNER || currentUser.equals(choreGroupUserToUpdate.getChoreUser()) || choreGroupUser.getChoreGroupUserRole() == ChoreGroupUserRole.OWNER) {
            throw new InvalidActivityException(messageSource.getMessage("non.valid.action", null, Locale.getDefault()));
        }
        if(isOwnerOfChoreGroup(currentUser, choreGroupUserToUpdate.getChoreGroup())) {
            choreGroupUserToUpdate.setChoreGroupUserRole(choreGroupUser.getChoreGroupUserRole());
            choreGroupUserDao.updateChoreGroupUser(choreGroupUserToUpdate);
        } else {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
    }
}
