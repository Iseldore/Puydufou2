package com.exia.puydufou.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iseldore on 16/06/2015.
 */
public class Restaurant implements Serializable {
    private String nomRestaurant;
    private String descriptionRestaurant;
    private double latitude;
    private double longitude;
    private int nbNotes;
    private double noteMoy;
    private String url;
    private String idRestaurant;
    private List<MenuRestau> menus = new ArrayList();

    public void setListMenu(List<MenuRestau> menu){
        this.menus = menu;
    }

    public List<MenuRestau> getListMenus(){
        return menus;
    }

    public String getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(String idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNomRestaurant() {
        return nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

    public String getDescriptionRestaurant() {
        return descriptionRestaurant;
    }

    public void setDescriptionRestaurant(String descriptionRestaurant) {
        this.descriptionRestaurant = descriptionRestaurant;
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
