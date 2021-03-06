/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreSpec;
import java.util.List;

/**
 *
 * @author AJ
 */
public interface ChoreGroupService {
    public ChoreGroup findChoreGroup(Long choreGroupId);
    public ChoreGroupUser createChoreGroup(ChoreGroup choreGroup);
    public ChoreGroup updateChoreGroup(ChoreGroup choreGroup);
    public void deleteChoreGroup(ChoreGroup choreGroup);
    public List<ChoreGroup> findAllActiveForCurrentUser();
    public List<ChoreGroupUser> findAllActiveMembersForChoreGroup(ChoreGroup choreGroup) throws IllegalAccessException ;
    public List<ChoreGroupUser> findAllMembersForChoreGroup(ChoreGroup choreGroup) throws IllegalAccessException ;
    public List<ChoreSpec> findAllChoreSpecsForChoreGroup(Long choreGroupId) throws IllegalAccessException;
}
