package com.example.cs2340_project.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.cs2340_project.model.User;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.lifecycle.MutableLiveData;


public class LoginViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    // Getter for observing userLiveData in the View
    public LiveData<User> getUserLiveData() {
        return userLiveData;
    }

    // Method for login
    public void loginUser(String username, String password) {
        firebaseAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            User user = new User();
                            user.setUsername(firebaseUser.getDisplayName());
                            user.setPassword(firebaseUser.getEmail());
                            userLiveData.setValue(user);
                        }
                    } else {
                        // Handle login failure
                        userLiveData.setValue(null);
                    }
                });
    }

    // Method for account creation
    public void createAccount(String username, String password) {
        firebaseAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Account creation successful, you can also update userLiveData here
                        loginUser(username, password);
                    } else {
                        // Handle account creation failure
                        userLiveData.setValue(null);
                    }
                });
    }
}
