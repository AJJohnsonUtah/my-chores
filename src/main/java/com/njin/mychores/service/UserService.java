/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.model.User;

/**
 *
 * @author aj
 */
public interface UserService {
    public User findUser(Long id);
    public boolean authenticateUser(String email, String password);
    public void createUser(User user);
}
