/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreGroupUserStatus;
import com.njin.mychores.model.ChoreUser;
import java.util.List;

/**
 *
 * @author AJ
 */
public interface ChoreGroupUserDao {
    public void createChoreGroupUser(ChoreGroupUser choreGroupUser);
    public void updateChoreGroupUser(ChoreGroupUser choreGroupUser);
    public ChoreGroupUser find(Long id);
    public List<ChoreGroupUser> findAllForUser(ChoreUser user);
    public List<ChoreGroupUser> findAllForUserWithStatus(ChoreUser user, ChoreGroupUserStatus status);
    public List<ChoreGroupUser> findAllForChoreGroup(ChoreGroup choreGroup);
    public ChoreGroupUser findChoreGroupUser(ChoreGroup choreGroup, ChoreUser choreUser);
    public List<ChoreGroupUser> findAllForInviterWithStatus(ChoreUser invitedBy, ChoreGroupUserStatus status);
}
