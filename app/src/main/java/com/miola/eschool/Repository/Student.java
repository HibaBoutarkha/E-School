package com.miola.eschool.Repository;

public class Student {
    private String nom;
    private String email;
    private String photoURL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;



    public Student(String nom, String email, String photoURL) {
        this.nom = nom;
        this.email = email;
        this.photoURL = photoURL;
    }

    public Student() {

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }


}
