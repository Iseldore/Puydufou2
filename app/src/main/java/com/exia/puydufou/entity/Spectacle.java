package com.exia.puydufou.entity;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Iseldore on 15/06/2015.
 */
public class Spectacle implements Serializable {
    private String nom_spectacle;
    private String info_spectacle;
    private Date duree_spectacle;
    private Date datecreation_spectacle;
    private Integer nbacteur_spectacle;
    private String evhistorique_spectacle;
    private double note_moy;
    private Integer nb_notes;
    private String id_spectacle;
    private Date horaires;
    private String url;

    public Date getHoraires() {
        return horaires;
    }

    public void setHoraires(Date horaires) {
        this.horaires = horaires;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String getId_spectacle() {
        return id_spectacle;
    }

    public void setId_spectacle(String id_spectacle) {
        this.id_spectacle = id_spectacle;
    }

    public Integer getNb_notes() {
        return nb_notes;
    }

    public void setNb_notes(Integer nb_notes) {
        this.nb_notes = nb_notes;
    }

    public String getNom_spectacle() {
        return nom_spectacle;
    }

    public void setNom_spectacle(String nom_spectacle) {
        this.nom_spectacle = nom_spectacle;
    }

    public String getInfo_spectacle() {
        return info_spectacle;
    }

    public void setInfo_spectacle(String info_spectacle) {
        this.info_spectacle = info_spectacle;
    }

    public Date getDuree_spectacle() {
        return duree_spectacle;
    }

    public void setDuree_spectacle(Date duree_spectacle) {
        this.duree_spectacle = duree_spectacle;
    }

    public Date getDatecreation_spectacle() {
        return datecreation_spectacle;
    }

    public void setDatecreation_spectacle(Date datecreation_spectacle) {
        this.datecreation_spectacle = datecreation_spectacle;
    }

    public Integer getNbacteur_spectacle() {
        return nbacteur_spectacle;
    }

    public void setNbacteur_spectacle(Integer nbacteur_spectacle) {
        this.nbacteur_spectacle = nbacteur_spectacle;
    }

    public String getEvhistorique_spectacle() {
        return evhistorique_spectacle;
    }

    public void setEvhistorique_spectacle(String evhistorique_spectacle) {
        this.evhistorique_spectacle = evhistorique_spectacle;
    }

    public double getNote_moy() {
        return note_moy;
    }

    public void setNote_moy(double note_moy) {
        this.note_moy = note_moy;
    }

}
