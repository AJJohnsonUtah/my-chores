/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.model.ChoreUser;

/**
 *
 * @author aj
 */
public interface UserService {
    public ChoreUser findUser(Long id);
    public ChoreUser findUser(String email);
    public boolean authenticateUser(ChoreUser user);
    public void createUser(ChoreUser user);
}
