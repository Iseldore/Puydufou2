package com.exia.puydufou.entity;

import java.io.Serializable;

/**
 * Created by Iseldore on 18/06/2015.
 */
public class MenuRestau implements Serializable{
    public String getDescription(){
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    private String description;

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    private String tarif;

}
