package com.example.cs2340_project;

import com.example.cs2340_project.model.User;
import com.example.cs2340_project.viewmodels.LoginViewModel;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LoginViewModelTest {

    private LoginViewModel loginViewModel;

    private User observedUser;

    @Before
    public void setup() {
        loginViewModel = new LoginViewModel();
        observedUser = null;

        // Observer to capture changes in UserLiveData
        loginViewModel.getUserLiveData().observeForever(user -> observedUser = user);
    }

    @Test
    public void testSuccessfulLogin() {
        // Trigger a successful login
        loginViewModel.loginUser("testUser", "password");

        // Verify that the LiveData has been updated with the authenticated user
        assertEquals("testUser", observedUser.getUsername());
        assertEquals("password", observedUser.getPassword());
    }

    @Test
    public void testFailedLogin() {
        // Trigger a failed login
        loginViewModel.loginUser("invalidUser", "wrongPassword");

        // Verify that the LiveData has not been updated (authentication failure)
        assertEquals(null, observedUser);
    }
}
