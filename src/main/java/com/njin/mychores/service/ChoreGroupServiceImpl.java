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
import org.springframework.beans.factory.annotation.Autowired;
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
    ChoreGroupUserDao choreGroupUserService;
    
    @Autowired
    SessionService sessionService;
    
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
    public void updateChoreGroup(ChoreGroup choreGroup) {
        choreGroupDao.updateChoreGroup(choreGroup);
    }
}
