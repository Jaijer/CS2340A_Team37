package com.example.cs2340_project.model;

public class User {
    private String username;
    private String password;

    // Constructor
    public User() {
        // Default constructor required for Firebase
    }

    // Getter and Setter methods for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and Setter methods for email
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
