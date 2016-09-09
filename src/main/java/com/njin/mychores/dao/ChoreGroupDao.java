/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.ChoreGroup;

/**
 *
 * @author AJ
 */
public interface ChoreGroupDao {
    public ChoreGroup findChoreGroup(Long choreGroupId);
    public void createChoreGroup(ChoreGroup choreGroup);
    public void updateChoreGroup(ChoreGroup choreGroup);
    public void deleteChoreGroup(ChoreGroup choreGroup);
}
