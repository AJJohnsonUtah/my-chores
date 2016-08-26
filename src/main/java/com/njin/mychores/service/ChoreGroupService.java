/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.model.ChoreGroup;
import java.util.List;

/**
 *
 * @author AJ
 */
public interface ChoreGroupService {
    public ChoreGroup findChoreGroup(Long choreGroupId);
    public void createChoreGroup(ChoreGroup choreGroup);
    public void updateChoreGroup(ChoreGroup choreGroup);
    public List<ChoreGroup> findAllForCurrentUser();
}
