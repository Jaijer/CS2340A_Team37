package com.example.cs2340_project.viewmodels;

import androidx.lifecycle.ViewModel;
import com.example.cs2340_project.model.User;

public class LoginViewModel extends ViewModel {
    private final User user;

    public LoginViewModel() {
        user = new User();
    }

    public void setLogin(String username, String password) {
        user.setUsername(username);
        user.setPassword(password);
    }

    public User getUser() {
        return user;
    }
}
