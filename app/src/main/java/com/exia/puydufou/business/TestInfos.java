package com.exia.puydufou.business;

import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.entity.Spectacle;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Iseldore on 16/06/2015.
 */
public class TestInfos {
    public List<Spectacle> getAllSpectacles() {
        List<Spectacle> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Spectacle spectacle = new Spectacle();
            spectacle.setId_spectacle(String.valueOf(i));
            spectacle.setNom_spectacle("Spectacle " + i);
            Date horaire = null;
            Date duree = null;
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            try {
                horaire = format.parse(i+":20");
                duree = format.parse("00:15");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            spectacle.setHoraires(horaire);
            spectacle.setDuree_spectacle(duree);
            spectacle.setInfo_spectacle("Informations du spectacle " + i);

           // Storage storage = new Storage(context);
            //Bitmap bitmap = storage.getBitmap("http://towtows.free.fr/eagle4/images/logo.gif");
            String url = "spectacle_"+spectacle.getId_spectacle()+".jpg";
            //storage.saveImageToInternalStorage(bitmap, url);
            spectacle.setUrl(url);

            list.add(spectacle);
        }
        return list;
    }

    public List<Boutique> getAllBoutiques() {
        List<Boutique> list = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Boutique boutique = new Boutique();
            boutique.setIdBoutique(String.valueOf(i));
            boutique.setNomBoutique("Boutique" + i);
            boutique.setDescriptionBoutique("Description boutique " + i);

            // Storage storage = new Storage(context);
            //Bitmap bitmap = storage.getBitmap("http://towtows.free.fr/eagle4/images/logo.gif");
            String url = "boutique_"+boutique.getIdBoutique()+".jpg";
            //storage.saveImageToInternalStorage(bitmap, url);
            boutique.setUrl(url);

            list.add(boutique);
        }
        return list;
    }
}
