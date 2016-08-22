/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.ChoreUser;

/**
 *
 * @author aj
 */
public interface UserDao {
    public ChoreUser findUser(Long id);
    public ChoreUser findUserForAuthentication(String email);
    public void createUser(ChoreUser user);
}
