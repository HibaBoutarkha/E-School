package com.miola.eschool.Repository;

public class CR {
    private String description;
    private String matière;
    private String salle;

    //Constructor

    public CR(){}
    public CR(String description, String matière, String salle) {
        this.description = description;
        this.matière = matière;
        this.salle = salle;
    }

    //getters and setters
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMatière() {
        return matière;
    }

    public void setMatière(String matière) {
        this.matière = matière;
    }

    public String getSalle() {
        return salle;
    }

    public void setSalle(String salle) {
        this.salle = salle;
    }

}
