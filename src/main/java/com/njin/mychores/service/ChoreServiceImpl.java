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
import static com.njin.mychores.model.Chore_.choreSpec;
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
    public Chore updateChore(Chore chore) throws IllegalAccessException {
        ChoreSpec choreSpec = chore.getChoreSpec();
        if (!choreGroupUserService.isOwnerOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())
                && !choreGroupUserService.isAdminOfChoreGroup(sessionService.getCurrentUser(), choreSpec.getChoreGroup())) {
            throw new IllegalAccessException(messageSource.getMessage("not.own.data", null, Locale.getDefault()));
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
    public List<Chore> findChoresWithChoreSpecId(Long choreSpecId) {
        return choreDao.findChoresWithChoreSpecId(choreSpecId);
    }

    @Override
    public List<Chore> findChoresWithChoreGroupUserId(Long choreGroupUserId) {
        return choreDao.findChoresWithChoreGroupUserId(choreGroupUserId);
    }

    @Override
    public List<Chore> findChoresWithChoreGroupUserIdAndStatus(Long choreGroupUserId, ChoreStatus status) {
        return choreDao.findChoresWithChoreGroupUserIdAndStatus(choreGroupUserId, status);
    }

    @Override
    public List<Chore> findChoresWithChoreUser(Long choreUserId) {
        return choreDao.findChoresWithChoreUser(choreUserId);
    }

    @Override
    public List<Chore> findChoresWithChoreUserAndStatus(Long choreUserId, ChoreStatus status) {
        return choreDao.findChoresWithChoreGroupUserIdAndStatus(choreUserId, status);
    }

    @Override
    public List<Chore> findChoresWithChoreGroupId(Long choreGroupId) {
        return choreDao.findChoresWithChoreGroupId(choreGroupId);
    }

    @Override
    public List<Chore> findChoresWithChoreGroupIdAndStatus(Long choreGroupId, ChoreStatus status) {
        return choreDao.findChoresWithChoreGroupIdAndStatus(choreGroupId, status);
    }

}
