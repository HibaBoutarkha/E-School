package com.miola.eschool.Repository;

public class Professeur {
    private String photoURL;
    private String departement;
    private String matiere;
    private String nom;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Professeur(String departement, String matiere, String nom, String photoURL, String email) {
        this.departement = departement;
        this.matiere = matiere;
        this.nom = nom;
        this.photoURL= photoURL;
        this.email=email;
    }
    public Professeur() {

    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    private String[] semesters;

    public String getDepartement() {
        return departement;
    }

    public void setDepartement(String departement) {
        this.departement = departement;
    }

    public String getMatiere() {
        return matiere;
    }

    public void setMatiere(String matiere) {
        this.matiere = matiere;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getSemesters() {
        return semesters;
    }

    public void setSemesters(String[] semesters) {
        this.semesters = semesters;
    }
}
