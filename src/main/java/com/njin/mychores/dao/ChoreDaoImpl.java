/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.Chore;
import com.njin.mychores.model.ChoreGroup;
import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreSpec;
import com.njin.mychores.model.ChoreStatus;
import com.njin.mychores.model.ChoreUser;
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
    public List<Chore> findChoresWithChoreSpec(ChoreSpec choreSpec) {
        try {
            return em.createNamedQuery("Chore.findByChoreSpec", Chore.class).setParameter("choreSpec", choreSpec).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreGroupUser(ChoreGroupUser choreGroupUser) {
        try {
            return em.createNamedQuery("Chore.findByDoer", Chore.class).setParameter("doer", choreGroupUser).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreGroupUserAndStatus(ChoreGroupUser choreGroupUser, ChoreStatus status) {
        try {
            return em.createNamedQuery("Chore.findByDoerAndStatus", Chore.class).setParameter("doer", choreGroupUser).setParameter("status", status).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreUser(ChoreUser choreUser) {
        try {
            return em.createNamedQuery("Chore.findByChoreUser", Chore.class).setParameter("choreUser", choreUser).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreUserAndStatus(ChoreUser choreUser, ChoreStatus status) {
        try {
            return em.createNamedQuery("Chore.findByChoreUserAndStatus", Chore.class).setParameter("choreUser", choreUser).setParameter("status", status).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreGroup(ChoreGroup choreGroup) {
        try {
            return em.createNamedQuery("Chore.findByChoreGroup", Chore.class).setParameter("choreGroup", choreGroup).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<Chore> findChoresWithChoreGroupAndStatus(ChoreGroup choreGroup, ChoreStatus status) {
        try {
            return em.createNamedQuery("Chore.findByChoreGroupAndStatus", Chore.class).setParameter("choreGroup", choreGroup).setParameter("status", status).getResultList();
        } catch (NoResultException ex) {
            return null;
        }
    }
}
