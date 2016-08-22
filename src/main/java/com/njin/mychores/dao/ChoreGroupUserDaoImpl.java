/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.ChoreGroupUser;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChoreGroupUserDaoImpl implements ChoreGroupUserDao {

    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public void createChoreGroupUser(ChoreGroupUser choreGroupUser) {
        entityManager.persist(choreGroupUser);
        entityManager.refresh(choreGroupUser.getChoreGroup());
    }

    @Override
    public void updateChoreGroupUser(ChoreGroupUser choreGroupUser) {
        entityManager.merge(choreGroupUser);
        entityManager.refresh(choreGroupUser.getChoreGroup());
    }

}
