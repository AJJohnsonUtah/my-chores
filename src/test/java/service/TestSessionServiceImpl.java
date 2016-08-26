/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.njin.mychores.model.ChoreUser;
import com.njin.mychores.service.SessionService;
import com.njin.mychores.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author AJ
 */
public class TestSessionServiceImpl implements SessionService {

    private static Long currentUserId;
    
    @Autowired
    UserService userService;
    
    @Override
    public void setCurrentUser(ChoreUser user) {
        currentUserId = user == null ? null : user.getId();
    }

    @Override
    public ChoreUser getCurrentUser() {
        if(currentUserId == null) {
            return null;
        }
        return userService.findUser(currentUserId);
    }                
}
