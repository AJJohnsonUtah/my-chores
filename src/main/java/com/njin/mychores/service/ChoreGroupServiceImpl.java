/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.ChoreGroupDao;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreGroupUserRole;
import com.njin.mychores.model.ChoreGroupUserStatus;
import com.njin.mychores.model.ChoreUser;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
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
public class ChoreGroupServiceImpl implements ChoreGroupService {
    
    @Autowired
    ChoreGroupDao choreGroupDao;
    
    @Autowired
    ChoreGroupUserService choreGroupUserService;    
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    MessageSource messageSource;
    
    @Override
    public ChoreGroup findChoreGroup(Long choreGroupId) {
        return choreGroupDao.findChoreGroup(choreGroupId);
    }
    
    @Override
    public void createChoreGroup(ChoreGroup choreGroup) {             
        choreGroupDao.createChoreGroup(choreGroup);
        ChoreGroupUser choreGroupUser = new ChoreGroupUser(choreGroup,
                sessionService.getCurrentUser(),
                null,
                ChoreGroupUserRole.OWNER,
                ChoreGroupUserStatus.ACCEPTED);        
        choreGroupUserService.createChoreGroupUser(choreGroupUser);        
    }
    
    @Override
    public ChoreGroup updateChoreGroup(ChoreGroup choreGroup) {
        choreGroupDao.updateChoreGroup(choreGroup);
        return findChoreGroup(choreGroup.getId());
    }
    
    @Override
    public List<ChoreGroup> findAllActiveForCurrentUser() {
        List<ChoreGroup> choreGroups = new ArrayList<>();
        ChoreUser currentUser = sessionService.getCurrentUser();
        List<ChoreGroupUser> users = choreGroupUserService.findAllForUser(currentUser);
        for(ChoreGroupUser choreGroupUser : users) {
            if(EnumSet.of(ChoreGroupUserStatus.ACCEPTED).contains(choreGroupUser.getStatus())) {
                choreGroups.add(choreGroupUser.getChoreGroup());
            }
        }
        return choreGroups;
    }               
    
    @Override
    public List<ChoreGroupUser> findAllActiveMembersForChoreGroup(ChoreGroup choreGroup) throws IllegalAccessException {
        List<ChoreGroupUser> choreGroupUsersOfGroup = choreGroup.getChoreGroupUsers();
        List<ChoreGroupUser> listOfUsers = new ArrayList<>();
        if(!choreGroupUserService.isActiveMemberOfChoreGroup(sessionService.getCurrentUser(), choreGroup)) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        for(ChoreGroupUser choreGroupUser : choreGroupUsersOfGroup) {
            if(choreGroupUser.getStatus() == ChoreGroupUserStatus.ACCEPTED) {
                listOfUsers.add(choreGroupUser);
            }
        }
        return listOfUsers;
    }
    
    @Override
    public List<ChoreGroupUser> findAllMembersForChoreGroup(ChoreGroup choreGroup) throws IllegalAccessException {
        List<ChoreGroupUser> choreGroupUsersOfGroup = findChoreGroup(choreGroup.getId()).getChoreGroupUsers();
        List<ChoreGroupUser> listOfUsers = new ArrayList<>();
        if(!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreGroup)) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        for(ChoreGroupUser choreGroupUser : choreGroupUsersOfGroup) {
            listOfUsers.add(choreGroupUser);
        }
        return listOfUsers;
    }
}
