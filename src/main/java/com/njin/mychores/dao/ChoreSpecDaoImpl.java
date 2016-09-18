/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.ChoreGroupUser;
import com.njin.mychores.model.ChoreSpec;
import java.util.Collections;
import java.util.Date;
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
public class ChoreSpecDaoImpl implements ChoreSpecDao {

    @PersistenceContext
    EntityManager em;
    
    @Override
    public void createChoreSpec(ChoreSpec choreSpec) {
        em.persist(choreSpec);
    }

    @Override
    public void updateChoreSpec(ChoreSpec choreSpec) {
        em.merge(choreSpec);
    }

    @Override
    public ChoreSpec findChoreSpec(Long choreSpecId) {
        try {
            return em.find(ChoreSpec.class, choreSpecId);
        } catch (NoResultException ex) {
            return null;
        }
    }

    @Override
    public List<ChoreSpec> findChoresWithPreferredDoer(ChoreGroupUser preferredDoer) {
        try {
            return em.createNamedQuery("ChoreSpec.findByPreferredDoer", ChoreSpec.class).setParameter("preferredDoer", preferredDoer).getResultList();
        } catch (NoResultException ex) {
            return Collections.emptyList();
        }
    }
    
    @Override
    public List<ChoreSpec> findChoreSpecsWithPastNextInstanceDates() {
        try {
            return em.createNamedQuery("ChoreSpec.findByPastDate", ChoreSpec.class).setParameter("date", new Date()).getResultList();
        } catch (NoResultException ex) {
            return Collections.emptyList();
        }
    }
}
