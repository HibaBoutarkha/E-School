package com.miola.eschool.Repository;

import com.google.firebase.auth.FirebaseUser;

public class User {

    private boolean isAuthenticated;
    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password=password;
    }

    //Methods

    public boolean emptyFields(){
        if(this.email.equals("")||this.password.equals("")) return true;
        else return false;
    }

    //Getters and Setters

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAuthenticated(boolean authenticated){
        this.isAuthenticated=authenticated;
    }

    public boolean getAuthenticated(){ return this.isAuthenticated;}
}
