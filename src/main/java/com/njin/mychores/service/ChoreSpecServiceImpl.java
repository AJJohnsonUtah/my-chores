/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.ChoreSpecDao;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreSpec;
import java.time.LocalDateTime;
import java.util.Date;
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
public class ChoreSpecServiceImpl implements ChoreSpecService {

    @Autowired
    ChoreSpecDao choreSpecDao;
    
    @Autowired
    ChoreService choreService;
    
    @Autowired
    SessionService sessionService;
    
    @Autowired
    ChoreGroupUserService choreGroupUserService;
    
    @Autowired
    MessageSource messageSource;
    
    @Override
    public ChoreSpec createChoreSpec(ChoreSpec choreSpec) throws IllegalAccessException {
        if(!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup()) &&
           !choreGroupUserService.isAdminOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        
        choreSpecDao.createChoreSpec(choreSpec);        
        
        if(choreSpec.getNextInstanceDate().isBefore(LocalDateTime.now())) {
            choreService.createChoreFromChoreSpec(choreSpec);
        }
        
        return choreSpec;
    }  

    @Override
    public ChoreSpec updateChoreSpec(ChoreSpec choreSpec) throws IllegalAccessException {
        if(!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup()) &&
           !choreGroupUserService.isAdminOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        choreSpecDao.updateChoreSpec(choreSpec);
        return choreSpec;
    }

    @Override
    public ChoreSpec findChoreSpec(Long choreSpecId) throws IllegalAccessException {
        ChoreSpec choreSpec = choreSpecDao.findChoreSpec(choreSpecId);
        if(!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup()) &&
           !choreGroupUserService.isAdminOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        return choreSpec;
    }

    @Override
    public List<ChoreSpec> findChoreSpecsWithPreferredDoer(Long choreGroupUserId) throws IllegalAccessException {
        ChoreGroupUser userToFind = new ChoreGroupUser();
        userToFind.setId(choreGroupUserId);
        ChoreGroupUser choreGroupUser = choreGroupUserService.findChoreGroupUser(userToFind);
        if(!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreGroupUser.getChoreGroup()) ||
           !choreGroupUserService.isAdminOfChoreGroup(sessionService.getCurrentUser(), choreGroupUser.getChoreGroup())) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        return choreSpecDao.findChoresWithPreferredDoer(choreGroupUser);
    }    

    @Override
    public List<ChoreSpec> findChoreSpecsWithPastNextInstanceDates() {
        return choreSpecDao.findChoreSpecsWithPastNextInstanceDates();
    }

    @Override
    public void autoUpdateNextInstance(ChoreSpec choreSpec) {
        if(choreSpec.getNextInstanceDate() == null) {
            choreSpec.setNextInstanceDate(choreSpec.getFrequency().getTimeOfNextInstance(LocalDateTime.now()));
        }
    }
        
}
