/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.njin.mychores.controller;

import com.njin.mychores.config.JpaConfiguration;
import com.njin.mychores.model.ChoreUser;
import java.util.Locale;
import static org.hamcrest.CoreMatchers.is;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author AJ
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JpaConfiguration.class}, loader = AnnotationConfigContextLoader.class)
@ActiveProfiles("test")
@Transactional
public class UserControllerTest extends BaseTest {

    public UserControllerTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @Rollback
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateUserAndAuthenticate() {
        ChoreUser user = new ChoreUser();
        String email = "test@test.com";
        String password = "fakearoni??22";
        user.setEmail(email);
        user.setPassword(password);

        ChoreUser createdUser = userController.createUser(user);

        assertEquals(createdUser.getEmail(), email);
        assertNotNull(createdUser.getId());

        user = new ChoreUser();
        user.setEmail(email);
        user.setPassword(password);
        ChoreUser loggedInUser = userController.login(user);
        assertEquals(createdUser.getEmail(), email);
        assertNotNull(createdUser.getId());

        assertEquals(loggedInUser, userController.getCurrentUser());
    }

    @Test
    public void createDuplicateUserAttempt() {
        ChoreUser user1 = new ChoreUser();
        String email1 = "test@test.com";
        String password1 = "fakearoni??11";

        user1.setEmail(email1);
        user1.setPassword(password1);

        userController.createUser(user1);

        ChoreUser user2 = new ChoreUser();
        String email2 = email1;
        String password2 = "fakearoni??22";

        user2.setEmail(email2);
        user2.setPassword(password2);

        try {
            userController.createUser(user2);
            fail("Should not be able to create a user with the same email.");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), is(messageSource.getMessage("non.unique.email", new String[] {email1}, Locale.getDefault())));
        }
    }

    @Test
    public void loginWithIncorrectPassword() {
        ChoreUser user = new ChoreUser();
        String email = "test@test.com";
        String password = "fakearoni??11";

        user.setEmail(email);
        user.setPassword(password);

        userController.createUser(user);

        user = new ChoreUser();
        user.setEmail(email);
        user.setPassword(password + "wrongStuff");
        try {
            userController.login(user);        
            fail("Should not be able to login with the incorrect password.");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), is(messageSource.getMessage("non.correct.password", null, Locale.getDefault())));
        }
    }
    
    @Test
    public void loginWithInvalidEmail() {
                ChoreUser user = new ChoreUser();
        String email = "test@test.com";
        String password = "fakearoni??11";

        user.setEmail(email);
        user.setPassword(password);
        try {
            userController.login(user);  
            fail("Should not be able to login with an invalid email.");
        } catch (IllegalArgumentException ex) {
            assertThat(ex.getMessage(), is(messageSource.getMessage("non.known.email", null, Locale.getDefault())));
        }
    }
    
    @Test
    public void logoutTest() {
        ChoreUser user = new ChoreUser();
        String email = "test@test.com";
        String password = "fakearoni??11";

        user.setEmail(email);
        user.setPassword(password);

        userController.createUser(user);
        userController.login(user);
        
        assertNotNull("When logged in, current user should not be null", userController.getCurrentUser());
        
        userController.logout();
        assertNull("When logged out, current user shoulde be null.", userController.getCurrentUser());
    }
}
