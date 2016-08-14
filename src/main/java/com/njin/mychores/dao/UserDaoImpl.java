/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

/**
 *
 * @author aj
 */
@Repository
public class UserDaoImpl implements UserDao {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Override
    public User findUser(Long id) {
        return entityManager.find(User.class, id);
    }
    
    @Override
    public void createUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findUserForAuthentication(String email) {
        return entityManager.createNamedQuery("findUserForAuthentication", User.class).getSingleResult();        
    }
}
