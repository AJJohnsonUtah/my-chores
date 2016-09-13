/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreStatus;
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
public class ChoreDaoImpl implements ChoreDao {

    @PersistenceContext
    EntityManager em;

    @Override
    public void createChore(Chore chore) {
        em.persist(chore);
    }

    @Override
    public void updateChore(Chore chore) {
        em.merge(chore);
    }

    @Override
    public Chore findChore(Long choreId) {
        try {
            return em.find(Chore.class, choreId);
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreSpecId(Long choreSpecId) {
        try {
            return em.createNamedQuery("Chore.findByChoreSpecId", Chore.class).setParameter("choreSpecId", choreSpecId).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreGroupUserId(Long choreGroupUserId) {
        try {
            return em.createNamedQuery("Chore.findByDoer", Chore.class).setParameter("doer", choreGroupUserId).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreGroupUserIdAndStatus(Long choreGroupUserId, ChoreStatus status) {
        try {
            return em.createNamedQuery("Chore.findByDoerAndStatus", Chore.class).setParameter("doer", choreGroupUserId).setParameter("status", status).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreUser(Long choreUserId) {
        try {
            return em.createNamedQuery("Chore.findByChoreUser", Chore.class).setParameter("choreUser", choreUserId).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreUserAndStatus(Long choreUserId, ChoreStatus status) {
        try {
            return em.createNamedQuery("Chore.findByChoreUserAndStatus", Chore.class).setParameter("choreUser", choreUserId).setParameter("status", status).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreGroupId(Long choreGroupId) {
        try {
            return em.createNamedQuery("Chore.findByChoreGroup", Chore.class).setParameter("choreGroup", choreGroupId).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreGroupIdAndStatus(Long choreGroupId, ChoreStatus status) {
        try {
            return em.createNamedQuery("Chore.findByChoreGroup", Chore.class).setParameter("choreGroup", choreGroupId).setParameter("status", status).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
