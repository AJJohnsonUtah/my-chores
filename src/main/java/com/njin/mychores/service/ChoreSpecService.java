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
    public void autoUpdateNextInstance(ChoreSpec choreSpec);
    public ChoreSpec createChoreSpec(ChoreSpec choreSpec) throws IllegalAccessException;
    public ChoreSpec updateChoreSpec(ChoreSpec choreSpec) throws IllegalAccessException;
    public ChoreSpec findChoreSpec(Long choreSpecId) throws IllegalAccessException;
    public List<ChoreSpec> findChoreSpecsWithPreferredDoer(Long choreGroupUserId) throws IllegalAccessException;
    public List<ChoreSpec> findChoreSpecsWithPastNextInstanceDates();
    public void autoUpdateChoreSpec(ChoreSpec choreSpec);
}
