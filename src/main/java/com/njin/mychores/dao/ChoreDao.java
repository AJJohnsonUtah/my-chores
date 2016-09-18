/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.model.ChoreStatus;
import com.njin.mychores.model.ChoreUser;
import java.util.List;

/**
 *
 * @author AJ
 */
public interface ChoreDao {
    public void createChore(Chore chore);
    public void updateChore(Chore chore);
    public Chore findChore(Long choreId);
    public List<Chore> findChoresWithChoreSpec(ChoreSpec choreSpec);
    public List<Chore> findChoresWithChoreGroupUser(ChoreGroupUser choreGroupUser);    
    public List<Chore> findChoresWithChoreGroupUserAndStatus(ChoreGroupUser choreGroupUser, ChoreStatus status);
    public List<Chore> findChoresWithChoreGroup(ChoreGroup choreGroup);
    public List<Chore> findChoresWithChoreGroupAndStatus(ChoreGroup choreGroup, ChoreStatus status);
    public List<Chore> findChoresWithChoreUser(ChoreUser choreUser);
    public List<Chore> findChoresWithChoreUserAndStatus(ChoreUser choreUser, ChoreStatus status);
}
