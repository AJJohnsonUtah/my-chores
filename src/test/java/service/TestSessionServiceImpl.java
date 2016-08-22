/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.service.SessionService;

/**
 *
 * @author AJ
 */
public class TestSessionServiceImpl implements SessionService {

    private static ChoreUser currentUser;
    
    @Override
    public void setCurrentUser(ChoreUser user) {
        currentUser = user;
    }

    @Override
    public ChoreUser getCurrentUser() {
        return currentUser;
    }
    
}
