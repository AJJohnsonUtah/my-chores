/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.service;

import com.njin.mychores.model.ChoreUser;

/**
 *
 * @author AJ
 */
public interface SessionService {
    public void setCurrentUser(ChoreUser user);
    public ChoreUser getCurrentUser();
}
