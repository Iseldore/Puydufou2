package com.exia.puydufou.business;

import android.content.Context;
import android.graphics.Bitmap;

import com.exia.puydufou.data.SoapCommunicator;
import com.exia.puydufou.Storage;
import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.entity.Spectacle;

import org.ksoap2.serialization.SoapObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iseldore on 15/06/2015.
 */
public class InfosPDF {
    private static final String METHOD_NAME_GET_BOUTIQUES = "getAllBoutiques";
    private SoapCommunicator sc;
    private static final String NAMESPACE = "http://puydufou.exia.com/";
    private static final String METHOD_NAME_TEST_HELLO = "testHello";
    private static final String METHOD_NAME_TEST_HELLO_PARAM = "testHelloParam";
    private static final String METHOD_NAME_GET_PLANNING = "getPlanning";
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
            spectacle.setNote_moy(Double.parseDouble(note.getPropertyAsString("nbNotes")));

            Storage storage = new Storage(context);
            System.err.println("http://10.176.130.60/PuyDuFou/img_spectacles/"+soapObjectSp.getPropertyAsString("urlSpectacle"));
            Bitmap bitmap = storage.getBitmap("http://10.176.130.60/PuyDuFou/img_spectacles/"+soapObjectSp.getPropertyAsString("urlSpectacle"));
            String url = "spectacle_"+spectacle.getId_spectacle()+".jpg";
            storage.saveImageToInternalStorage(bitmap, url);
            spectacle.setUrl(url);

            liste.add(spectacle);
        }
        return liste;
    }


    public List<Boutique> getAllBoutiques() {
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME_GET_BOUTIQUES);
        SoapObject result = sc.sendRequest(request);
        if(result == null)
            return null;

        List<Boutique> liste = new ArrayList<>();

        for (int i = 0; i < result.getPropertyCount(); i++) {
            SoapObject soapObject = (SoapObject) result.getProperty(i);
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
            System.err.println("ID Boutique = "+boutique.getIdBoutique());
            storage.saveImageToInternalStorage(bitmap, url);
            boutique.setUrl(url);

            liste.add(boutique);
        }
        return liste;
    }
}
