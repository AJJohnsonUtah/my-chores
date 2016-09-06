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
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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

    @Override
    public List<ChoreGroupUser> findAllForUser(ChoreUser user) {
        try {
            return entityManager.createNamedQuery("findAllForUser", ChoreGroupUser.class).setParameter("choreUser", user).getResultList();
        } catch (NoResultException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<ChoreGroupUser> findAllForChoreGroup(ChoreGroup choreGroup) {
        try {
            return entityManager.createNamedQuery("findAllForChoreGroup", ChoreGroupUser.class).setParameter("choreGroup", choreGroup).getResultList();
        } catch (NoResultException ex) {
            return Collections.emptyList();
        }
    }

    @Override
    public ChoreGroupUser findChoreGroupUser(ChoreGroup choreGroup, ChoreUser choreUser) {       
        try {
            return entityManager.createNamedQuery("findChoreGroupUser", ChoreGroupUser.class).setParameter("choreGroup", choreGroup).setParameter("choreUser", choreUser).getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
    }
    
    @Override
    public List<ChoreGroupUser> findAllForUserWithStatus(ChoreUser user, ChoreGroupUserStatus status) {
        try {
            return entityManager.createNamedQuery("findAllForUserWithStatus", ChoreGroupUser.class).setParameter("choreUser", user).setParameter("status", status).getResultList();
        } catch (NoResultException ex) {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<ChoreGroupUser> findAllForInviterWithStatus(ChoreUser invitedBy, ChoreGroupUserStatus status) {
                try {
            return entityManager.createNamedQuery("findAllForInviterWithStatus", ChoreGroupUser.class).setParameter("invitedBy", invitedBy).setParameter("status", status).getResultList();
        } catch (NoResultException ex) {
            return Collections.emptyList();
        }
    }
    
    @Override
    public ChoreGroupUser find(Long id) {
        try {
            return entityManager.find(ChoreGroupUser.class, id);
        } catch (NoResultException ex) {
            return null;
        }
    }
}
