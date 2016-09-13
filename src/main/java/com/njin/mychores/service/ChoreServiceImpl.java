/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.ChoreDao;
import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreStatus;
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
public class ChoreServiceImpl implements ChoreService {

    @Autowired
    ChoreDao choreDao;
    
    @Override
    public Chore createChore(Chore chore) {
        choreDao.createChore(chore);
        return chore;
    }

    @Override
    public Chore updateChore(Chore chore) {
        choreDao.updateChore(chore);
        return chore;
    }

    @Override
    public Chore findChore(Long choreId) {
        return choreDao.findChore(choreId);        
    }

    @Override
    public List<Chore> findChoresWithChoreSpecId(Long choreGroupUserId) {
        return choreDao.findChoresWithChoreSpecId(choreGroupUserId);
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
