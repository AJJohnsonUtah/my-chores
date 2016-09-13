/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreStatus;
import java.util.List;

/**
 *
 * @author AJ
 */
public interface ChoreDao {
    public void createChore(Chore chore);
    public void updateChore(Chore chore);
    public Chore findChore(Long choreId);
    public List<Chore> findChoresWithChoreSpecId(Long choreGroupUserId);
    public List<Chore> findChoresWithChoreGroupUserId(Long choreGroupUserId);    
    public List<Chore> findChoresWithChoreGroupUserIdAndStatus(Long choreGroupUserId, ChoreStatus status);
    public List<Chore> findChoresWithChoreGroupId(Long choreGroupId);
    public List<Chore> findChoresWithChoreGroupIdAndStatus(Long choreGroupId, ChoreStatus status);
    public List<Chore> findChoresWithChoreUser(Long choreUserId);
    public List<Chore> findChoresWithChoreUserAndStatus(Long choreUserId, ChoreStatus status);
}
