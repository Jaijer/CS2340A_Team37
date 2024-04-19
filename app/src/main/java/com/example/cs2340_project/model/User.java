package com.example.cs2340_project.model;

public class User {
    private String username;
    private String password;
    private String height;
    private String weight;
    private String gender;


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

    public String getWeight() { return weight; }
    public String getHeight() { return height; }
    public String getGender() { return gender; }


    public void setPassword(String password) {
        this.password = password;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void setWeight(String weight) {this.weight = weight; }
    public void setGender(String gender) {this.gender = gender; }


}
