package com.exia.puydufou.business;

import android.content.Context;
import android.graphics.Bitmap;

import com.exia.puydufou.data.SoapCommunicator;
import com.exia.puydufou.common.Storage;
import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.entity.Restaurant;
import com.exia.puydufou.entity.Spectacle;
import com.exia.puydufou.entity.TaskObject;

import org.ksoap2.serialization.SoapObject;

import java.net.SocketException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Iseldore on 15/06/2015.
 */

public class InfosPDF {
    private static final String METHOD_NAME_GET_BOUTIQUES = "getAllBoutiques";
    private static final String METHOD_NAME_GET_RESTAURANTS = "getAllRestaurants";
    private SoapCommunicator sc;
    private static final String NAMESPACE = "http://puydufou.exia.com/";
    private static final String METHOD_NAME_TEST_HELLO = "testHello";
    private static final String METHOD_NAME_TEST_HELLO_PARAM = "testHelloParam";
    private static final String METHOD_NAME_GET_PLANNING = "getPlanning";
    private static final String METHOD_NAME_GET_BEST_PLANNING = "getBestPlanning";
    private static final String METHOD_NAME_GET_PLANNING_WITH_HORAIRE = "getAllSpectaclesWithHoraires";
    private static final String METHOD_NAME_SET_NOTE = "noteSpectacle";


    private Context context;

    public InfosPDF(Context context){
        this.context = context;
        sc = new SoapCommunicator(context);
    }

    public List<Spectacle> getAllSpectacles() {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_PLANNING);
        SoapObject result = sc.sendRequest(request);
        if(result == null)
            return null;

        List<Spectacle> liste = new ArrayList<>();

        for (int i = 0; i < result.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject)result.getProperty(i);
            Spectacle spectacle = getSpectacle(soapObject);
            liste.add(spectacle);
        }
        return liste;
    }

    public List<Spectacle> reallyGetAllSpectacles() {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_PLANNING_WITH_HORAIRE);
        SoapObject result = sc.sendRequest(request);
        if(result == null)
            return null;

        List<Spectacle> liste = new ArrayList<>();

        for (int i = 0; i < result.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject)result.getProperty(i);
            Spectacle spectacle = getSpectacle(soapObject);
            liste.add(spectacle);
        }
        return liste;
    }

    public List<Spectacle> getAllSpectaclesWithHoraire() {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_PLANNING_WITH_HORAIRE);
        SoapObject result = sc.sendRequest(request);
        if(result == null)
            return null;

        List<Spectacle> liste = new ArrayList<>();

        for (int i = 0; i < result.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject)result.getProperty(i);
            Spectacle spectacle = getSpectacle(soapObject);
            liste.add(spectacle);
        }
        return liste;
    }

    public Spectacle getSpectacle(SoapObject soapObject){
        SoapObject soapObjectSp = (SoapObject) soapObject.getProperty("IDspectacle");
        // Pour chaque spectacle
        Spectacle spectacle = new Spectacle();
        spectacle.setNom_spectacle(soapObjectSp.getPropertyAsString("nomSpectacle"));
        spectacle.setInfo_spectacle(soapObjectSp.getPropertyAsString("infoSpectacle"));

        SimpleDateFormat formatDuree = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String time = soapObjectSp.getPropertyAsString("dureeSpectacle");
            time = time.replace("T", " ");
            //time = time.replace("+01:00", "");
            spectacle.setDuree_spectacle(formatDuree.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        SimpleDateFormat formatCreation = new SimpleDateFormat("yyyy-MM-dd");
        try {
            spectacle.setDatecreation_spectacle(formatCreation.parse(soapObjectSp.getPropertyAsString("datecreationSpectacle")));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        spectacle.setNbacteur_spectacle(Integer.parseInt(soapObjectSp.getPropertyAsString("nbacteurSpectacle")));
        spectacle.setEvhistorique_spectacle(soapObjectSp.getPropertyAsString("evhistoriqueSpectacle"));
//            spectacle.setNote_moy(Integer.parseInt(soapObjectSp.getPropertyAsString("noteMoy")));
        //   spectacle.setNb_notes(Integer.parseInt(soapObjectSp.getPropertyAsString("nbNotes")));
        spectacle.setId_spectacle(soapObjectSp.getPropertyAsString("IDspectacle"));

        SimpleDateFormat formatHoraire = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            String time = soapObject.getPropertyAsString("horaireSpectacle");
            time = time.replace("T", " ");
            //time = time.replace("+01:00", "");
            spectacle.setHoraires(formatHoraire.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
            System.err.println(e.getMessage());
        }

        SoapObject note = (SoapObject) soapObjectSp.getProperty("IDnote");
        spectacle.setNb_notes(Integer.parseInt(note.getPropertyAsString("nbNotes")));
        spectacle.setNote_moy(Double.parseDouble(note.getPropertyAsString("noteMoy")));

        SoapObject gps = (SoapObject) soapObjectSp.getProperty("IDlocalisation");
        spectacle.setLatitude(Double.parseDouble(gps.getPropertyAsString("latitude")));
        spectacle.setLongitude(Double.parseDouble(gps.getPropertyAsString("longitude")));

        Storage storage = new Storage(context);
        System.err.println("http://10.176.130.60/PuyDuFou/img_spectacles/"+soapObjectSp.getPropertyAsString("urlSpectacle"));
        Bitmap bitmap = storage.getBitmap("http://10.176.130.60/PuyDuFou/img_spectacles/"+soapObjectSp.getPropertyAsString("urlSpectacle"));
        String url = "spectacle_"+spectacle.getId_spectacle()+".jpg";
        storage.saveImageToInternalStorage(bitmap, url);
        spectacle.setUrl(url);

        return spectacle;
    }

    public Boutique getBoutique(SoapObject soapObject){
        Boutique boutique = new Boutique();

        boutique.setNomBoutique(soapObject.getPropertyAsString("nomBoutique"));
        boutique.setDescriptionBoutique(soapObject.getPropertyAsString("descriptionBoutique"));
        boutique.setIdBoutique(soapObject.getPropertyAsString("IDboutique"));

        SoapObject notes = (SoapObject) soapObject.getProperty("IDnote");
        boutique.setNbNotes(Integer.parseInt(notes.getPropertyAsString("nbNotes")));
        boutique.setNoteMoy(Double.parseDouble(notes.getPropertyAsString("noteMoy")));

        SoapObject gps = (SoapObject) soapObject.getProperty("IDlocalisation");
        boutique.setLatitude(Double.parseDouble(gps.getPropertyAsString("latitude")));
        boutique.setLongitude(Double.parseDouble(gps.getPropertyAsString("longitude")));

        Storage storage = new Storage(context);
        Bitmap bitmap = storage.getBitmap("http://10.176.130.60/PuyDuFou/img_boutiques/"+soapObject.getPropertyAsString("urlBoutique"));
        String url = "boutique_"+boutique.getIdBoutique()+".jpg";
        System.err.println("ID Boutique = " + boutique.getIdBoutique());
        storage.saveImageToInternalStorage(bitmap, url);
        boutique.setUrl(url);

        return boutique;
    }

    public Restaurant getRestaurant(SoapObject soapObject){
        Restaurant restaurant = new Restaurant();

        restaurant.setNomRestaurant(soapObject.getPropertyAsString("nomRestaurant"));
        restaurant.setDescriptionRestaurant(soapObject.getPropertyAsString("descriptionRestaurant"));
        restaurant.setIdRestaurant(soapObject.getPropertyAsString("IDrestaurant"));

        SoapObject notes = (SoapObject) soapObject.getProperty("IDnote");
        restaurant.setNbNotes(Integer.parseInt(notes.getPropertyAsString("nbNotes")));
        restaurant.setNoteMoy(Double.parseDouble(notes.getPropertyAsString("noteMoy")));

        SoapObject gps = (SoapObject) soapObject.getProperty("IDlocalisation");
        restaurant.setLatitude(Double.parseDouble(gps.getPropertyAsString("latitude")));
        restaurant.setLongitude(Double.parseDouble(gps.getPropertyAsString("longitude")));

        Storage storage = new Storage(context);
        Bitmap bitmap = storage.getBitmap("http://10.176.130.60/PuyDuFou/img_restaurants/"+soapObject.getPropertyAsString("urlRestaurant"));
        String url = "restaurant_"+restaurant.getIdRestaurant()+".jpg";
        System.err.println("ID Restaurant = " + restaurant.getIdRestaurant());
        storage.saveImageToInternalStorage(bitmap, url);
        restaurant.setUrl(url);

        return restaurant;
    }
    
    public List<Boutique> getAllBoutiques() {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_BOUTIQUES);
        SoapObject result = sc.sendRequest(request);
        if(result == null)
            return null;

        List<Boutique> liste = new ArrayList<>();

        for (int i = 0; i < result.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) result.getProperty(i);
            Boutique boutique = getBoutique(soapObject);
            liste.add(boutique);
        }
        return liste;
    }

    public List<TaskObject> getBestPlanning(){
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_BEST_PLANNING);
        SoapObject result = sc.sendRequest(request);

        List<TaskObject> liste = new ArrayList<>();

        for(int i = 0; i < result.getPropertyCount(); i++){
            SoapObject soapObject = (SoapObject) result.getProperty(i);

            TaskObject to = new TaskObject();
            if(Integer.parseInt(soapObject.getPropertyAsString("IDhoraire")) != 0){
                Spectacle spectacle = getSpectacle(soapObject);
                to.setSpectacle(spectacle);
                liste.add(to);
            }
            else{
                SoapObject sp = (SoapObject) soapObject.getProperty("IDspectacle");
                to.setNom(sp.getPropertyAsString("nomSpectacle"));

                SimpleDateFormat formatDuree = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    String time = sp.getPropertyAsString("dureeSpectacle");
                    time = time.replace("T", " ");
                    //time = time.replace("+01:00", "");
                    to.setDuree(formatDuree.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }

                SimpleDateFormat formatHoraire = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    String time = soapObject.getPropertyAsString("horaireSpectacle");
                    time = time.replace("T", " ");
                    //time = time.replace("+01:00", "");
                    to.setHoraire(formatHoraire.parse(time));
                } catch (ParseException e) {
                    e.printStackTrace();
                    System.err.println(e.getMessage());
                }

                liste.add(to);
            }

        }

        return liste;
    }

    public double setNote(String id_spectacle, String note){
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_SET_NOTE);
        request.addProperty("id_spectacle", id_spectacle);
        request.addProperty("note", note);
        SoapObject result = sc.sendRequest(request);
        NumberFormat nf = NumberFormat.getInstance(); // get instance for your locale
        nf.setMaximumFractionDigits(2); // set decimal places
        String s = nf.format(Double.parseDouble(result.getPropertyAsString(0))); // the parameter must be a long or double

        return Double.parseDouble(s);
    }

    public List<Restaurant> getAllRestaurants() {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_RESTAURANTS);
        SoapObject result = sc.sendRequest(request);
        if(result == null)
            return null;

        List<Restaurant> liste = new ArrayList<>();

        for (int i = 0; i < result.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) result.getProperty(i);
            Restaurant restaurant = getRestaurant(soapObject);
            liste.add(restaurant);
        }
        return liste;
    }
}

/*
public class InfosPDF {
    private Context context;

    public InfosPDF(Context context){
        this.context = context;
    }

    public List<Spectacle> getAllSpectacles() {
        List<Spectacle> list = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            Spectacle spectacle = new Spectacle();
            spectacle.setId_spectacle(String.valueOf(i));
            spectacle.setNom_spectacle("Spectacle " + i);
            Date horaire = null;
            Date duree = null;
            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
            try {
                horaire = format.parse("17:0"+i);
                duree = format.parse("00:15");
            } catch (ParseException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            spectacle.setHoraires(horaire);
            spectacle.setDuree_spectacle(duree);
            spectacle.setInfo_spectacle("Informations du spectacle " + i);
            spectacle.setNb_notes(5);
            spectacle.setNote_moy(3.75);

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

    public List<TaskObject> getBestPlanning() {
        return null;
    }

    public List<Spectacle> getAllSpectaclesWithHoraire() {
        return null;
    }

    public double setNote(String id_spectacle, String note) {
        return 3.5;
    }
}
*/