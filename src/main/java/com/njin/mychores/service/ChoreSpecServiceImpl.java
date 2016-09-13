/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.dao.ChoreSpecDao;
import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.model.ChoreStatus;
import java.util.Date;
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
public class ChoreSpecServiceImpl implements ChoreSpecService {

    @Autowired
    ChoreSpecDao choreSpecDao;
    
    @Autowired
    ChoreService choreService;
    
    @Override
    public ChoreSpec createChoreSpec(ChoreSpec choreSpec) {
        choreSpecDao.createChoreSpec(choreSpec);
        
        if(choreSpec.getNextInstanceDate().before(new Date())) {
            Chore newChore = new Chore();
            newChore.setChoreSpec(choreSpec);
            newChore.setDoer(choreSpec.getPreferredDoer());
            newChore.setStatus(ChoreStatus.TODO);
            choreService.createChore(newChore);
        }
        
        return choreSpec;
    }

    @Override
    public ChoreSpec updateChoreSpec(ChoreSpec choreSpec) {
        choreSpecDao.updateChoreSpec(choreSpec);
        return choreSpec;
    }

    @Override
    public ChoreSpec findChoreSpec(Long choreSpecId) {
        return choreSpecDao.findChoreSpec(choreSpecId);
    }

    @Override
    public List<ChoreSpec> findChoreSpecsWithPreferredDoer(Long choreGroupUserId) {
        return choreSpecDao.findChoresWithPreferredDoer(choreGroupUserId);
    }    
    
}
