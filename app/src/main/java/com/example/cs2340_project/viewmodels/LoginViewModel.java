package com.example.cs2340_project.viewmodels;

import androidx.lifecycle.ViewModel;
import com.example.cs2340_project.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import androidx.lifecycle.MutableLiveData;


public class LoginViewModel extends ViewModel {
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> authErrorLiveData = new MutableLiveData<>();
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    // Getter for userLiveData
    public MutableLiveData<User> getUserLiveData() {
        return userLiveData;
    }

    // Getter for authErrorLiveData
    public MutableLiveData<String> getAuthErrorLiveData() {
        return authErrorLiveData;
    }

    // Login method
    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            User user = new User();
                            user.setUsername(firebaseUser.getEmail()); // Use email as username
                            userLiveData.setValue(user);
                        }
                    } else {
                        authErrorLiveData.setValue("Login failed. "
                                + "Please check your credentials and try again.");
                    }
                });
    }

    // Account creation method
    public void createAccount(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        loginUser(email, password); // Log in after successful account creation
                    } else {
                        authErrorLiveData.setValue("Account creation failed."
                                + " Please try again later.");
                    }
                });
    }
}