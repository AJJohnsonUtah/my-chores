/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.model.ChoreSpec;
import java.util.List;

/**
 *
 * @author AJ
 */
public interface ChoreSpecService {    
    public ChoreSpec createChoreSpec(ChoreSpec choreSpec);
    public ChoreSpec updateChoreSpec(ChoreSpec choreSpec);
    public ChoreSpec findChoreSpec(Long choreSpecId);
    public List<ChoreSpec> findChoreSpecsWithPreferredDoer(Long choreGroupUserId);            
}
