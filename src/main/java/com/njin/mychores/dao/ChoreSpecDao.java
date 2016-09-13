/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreSpec;
import java.util.List;

/**
 *
 * @author AJ
 */
public interface ChoreSpecDao {
    public void createChoreSpec(ChoreSpec choreSpec);
    public void updateChoreSpec(ChoreSpec choreSpec);
    public ChoreSpec findChoreSpec(Long choreSpecId);
    public List<Chore> findChoresWithChoreSpec(Long choreSpecId);
    public List<ChoreSpec> findChoresWithPreferredDoer(Long choreGroupUserId);    
}
