/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.model.ChoreStatus;
import java.util.List;

/**
 *
 * @author AJ
 */
public interface ChoreService {    
    public Chore createChore(Chore chore) throws IllegalAccessException;    
    public Chore createChoreFromChoreSpec(ChoreSpec choreSpec) throws IllegalAccessException;
    public Chore updateChore(Chore chore) throws IllegalAccessException;
    public Chore findChore(Long choreId) throws IllegalAccessException;    
    public void createAllScheduledChores() throws IllegalAccessException;
    public List<Chore> findChoresWithChoreSpecId(Long choreGroupUserId) throws IllegalAccessException;
    public List<Chore> findChoresWithChoreGroupUserId(Long choreGroupUserId) throws IllegalAccessException;
    public List<Chore> findChoresWithChoreGroupUserIdAndStatus(Long choreGroupUserId, ChoreStatus status) throws IllegalAccessException;
    public List<Chore> findChoresWithChoreGroupId(Long choreGroupId) throws IllegalAccessException;
    public List<Chore> findChoresWithChoreGroupIdAndStatus(Long choreGroupId, ChoreStatus status) throws IllegalAccessException;
    public List<Chore> findChoresWithChoreUser(Long choreUserId) throws IllegalAccessException;
    public List<Chore> findChoresWithChoreUserAndStatus(Long choreUserId, ChoreStatus status) throws IllegalAccessException;
}
