/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.ChoreGroupUserDao;
import com.njin.mychores.model.ChoreGroupUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author AJ
 */
@Service
public class ChoreGroupUserServiceImpl implements ChoreGroupUserService {

    @Autowired
    ChoreGroupUserDao choreGroupUserDao;
    
    @Override
    public void createChoreGroupUser(ChoreGroupUser choreGroupUser) {
        choreGroupUserDao.createChoreGroupUser(choreGroupUser);
    }

    @Override
    public void updateChoreGroupUser(ChoreGroupUser choreGroupUser) {
        choreGroupUserDao.updateChoreGroupUser(choreGroupUser);
    }
    
}
