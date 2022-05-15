package com.miola.eschool.Repository;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Group {
    private String nom;
    private String description;
    private double id;
    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }






    public Group() {

    }
    public Group(String nom, String description, long id) {
        this.nom = nom;
        this.description = description;
        this.id=id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
