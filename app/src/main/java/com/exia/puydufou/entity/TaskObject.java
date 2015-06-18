package com.exia.puydufou.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Iseldore on 17/06/2015.
 */
public class TaskObject implements Serializable{
    private String nom;
    private Spectacle spectacle = null;
    private Date duree;
    private Date horaire;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Spectacle getSpectacle() {
        return spectacle;
    }

    public void setSpectacle(Spectacle spectacle) {
        this.spectacle = spectacle;
    }

    public Date getDuree() {
        return duree;
    }

    public void setDuree(Date duree) {
        this.duree = duree;
    }

    public Date getHoraire() {
        return horaire;
    }

    public void setHoraire(Date horaire) {
        this.horaire = horaire;
    }
}
