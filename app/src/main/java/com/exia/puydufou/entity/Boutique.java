package com.exia.puydufou.entity;

import java.io.Serializable;

/**
 * Created by Iseldore on 16/06/2015.
 */
public class Boutique implements Serializable {
    private String nomBoutique;
    private String descriptionBoutique;
    private double latitude;
    private double longitude;
    private int nbNotes;
    private double noteMoy;
    private String url;
    private String idBoutique;

    public String getIdBoutique() {
        return idBoutique;
    }

    public void setIdBoutique(String idBoutique) {
        this.idBoutique = idBoutique;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNomBoutique() {
        return nomBoutique;
    }

    public void setNomBoutique(String nomBoutique) {
        this.nomBoutique = nomBoutique;
    }

    public String getDescriptionBoutique() {
        return descriptionBoutique;
    }

    public void setDescriptionBoutique(String descriptionBoutique) {
        this.descriptionBoutique = descriptionBoutique;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getNbNotes() {
        return nbNotes;
    }

    public void setNbNotes(int nbNotes) {
        this.nbNotes = nbNotes;
    }

    public double getNoteMoy() {
        return noteMoy;
    }

    public void setNoteMoy(double noteMoy) {
        this.noteMoy = noteMoy;
    }

}
