/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.dao;

import com.njin.mychores.model.User;

/**
 *
 * @author aj
 */
public interface UserDao {
    public User findUser(Long id);
    public User findUserForAuthentication(String email);
    public void createUser(User user);
}
