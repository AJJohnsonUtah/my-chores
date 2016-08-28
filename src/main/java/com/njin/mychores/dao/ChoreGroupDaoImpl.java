/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.ChoreGroup;
import java.util.Collections;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author AJ
 */
@Repository
public class ChoreGroupDaoImpl implements ChoreGroupDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public ChoreGroup findChoreGroup(Long choreGroupId) {
        try {
            return entityManager.find(ChoreGroup.class, choreGroupId);
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public void createChoreGroup(ChoreGroup choreGroup) {
        entityManager.persist(choreGroup);
    }

    @Override
    public void updateChoreGroup(ChoreGroup choreGroup) {
        entityManager.merge(choreGroup);
    }

}
