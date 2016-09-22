/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.ChoreDao;
import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.model.ChoreStatus;
import java.time.LocalDateTime;
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
public class ChoreServiceImpl implements ChoreService {

    @Autowired
    ChoreDao choreDao;

    @Autowired
    ChoreSpecService choreSpecService;

    @Autowired
    ChoreGroupUserService choreGroupUserService;

    @Autowired
    SessionService sessionService;

    @Autowired
    MessageSource messageSource;
    
    @Autowired
    UserService userService;
    
    @Autowired
    ChoreGroupService choreGroupService;

    @Override
    public Chore createChore(Chore chore) throws IllegalAccessException {
        ChoreSpec choreSpec = chore.getChoreSpec();
        if (!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())
                && !choreGroupUserService.isAdminOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }

        choreDao.createChore(chore);
        return chore;
    }
    
    @Override
    public Chore createChoreAutomatically(Chore chore) {
        choreDao.createChore(chore);
        return chore;
    }

    @Override
    public Chore updateChore(Chore chore) throws IllegalAccessException {
        ChoreSpec choreSpec = chore.getChoreSpec();
        if (!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())
                && !choreGroupUserService.isAdminOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        
        if(chore.getStatus() == ChoreStatus.COMPLETED) {
            choreSpecService.autoUpdateNextInstance(chore.getChoreSpec());
        }
        
        choreDao.updateChore(chore);
        return chore;
    }

    @Override
    public Chore findChore(Long choreId) throws IllegalAccessException {
        Chore chore = choreDao.findChore(choreId);
        ChoreSpec choreSpec = chore.getChoreSpec();
        if (!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())
                && !choreGroupUserService.isAdminOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
        }
        
        return choreDao.findChore(choreId);
    }

    @Override
    public List<Chore> findChoresWithChoreSpecId(Long choreSpecId) throws IllegalAccessException {
        return choreDao.findChoresWithChoreSpec(choreSpecService.findChoreSpec(choreSpecId));
    }

    @Override
    public List<Chore> findChoresWithChoreGroupUserId(Long choreGroupUserId) throws IllegalAccessException {
        return choreDao.findChoresWithChoreGroupUser(choreGroupUserService.findChoreGroupUser(choreGroupUserId));
    }

    @Override
    public List<Chore> findChoresWithChoreGroupUserIdAndStatus(Long choreGroupUserId, ChoreStatus status) throws IllegalAccessException {
        return choreDao.findChoresWithChoreGroupUserAndStatus(choreGroupUserService.findChoreGroupUser(choreGroupUserId), status);
    }

    @Override
    public List<Chore> findChoresWithChoreUser(Long choreUserId) throws IllegalAccessException {
        return choreDao.findChoresWithChoreUser(userService.findUser(choreUserId));
    }

    @Override
    public List<Chore> findChoresWithChoreUserAndStatus(Long choreUserId, ChoreStatus status) throws IllegalAccessException {
        return choreDao.findChoresWithChoreUserAndStatus(userService.findUser(choreUserId), status);
    }

    @Override
    public List<Chore> findChoresWithChoreGroupId(Long choreGroupId) throws IllegalAccessException {
        return choreDao.findChoresWithChoreGroup(choreGroupService.findChoreGroup(choreGroupId));
    }

    @Override
    public List<Chore> findChoresWithChoreGroupIdAndStatus(Long choreGroupId, ChoreStatus status) throws IllegalAccessException {
        return choreDao.findChoresWithChoreGroupAndStatus(choreGroupService.findChoreGroup(choreGroupId), status);
    }

    @Override
    public Chore createChoreFromChoreSpec(ChoreSpec choreSpec) throws IllegalAccessException {
        choreSpec.setNextInstanceDate((LocalDateTime) null);
        choreSpecService.updateChoreSpec(choreSpec);
        Chore newChore = new Chore();
        newChore.setChoreSpec(choreSpec);
        newChore.setChoreDoer(choreSpec.getPreferredDoer());
        newChore.setStatus(ChoreStatus.TODO);
        return createChoreAutomatically(newChore);
    }

}
