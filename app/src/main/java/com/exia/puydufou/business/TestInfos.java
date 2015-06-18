package com.exia.puydufou.business;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.widget.ListView;

import com.exia.puydufou.R;
import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.entity.Spectacle;
import com.exia.puydufou.entity.TaskObject;
import com.exia.puydufou.fragments.BoutiquesFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Iseldore on 16/06/2015.
 */
public class TestInfos {
    private Context context;

    public TestInfos(Context context){
        this.context = context;
    }

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

    public List<TaskObject> getBestPlanning() {
        return null;
    }

    private class LoadBoutiqueFragment extends AsyncTask<Object, Void, Void> {
        private ProgressDialog dialog;

        public LoadBoutiqueFragment(Context context) {
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Chargement");
            dialog.show();
        }

        @Override
        protected Void doInBackground(Object... params) {
            ListView mDrawerList = (ListView) params[0];
            String[] navMenuTitles = (String[]) params[1];
            int position = (int) params[2];
            DrawerLayout mDrawerLayout = (DrawerLayout) params[3];
            FragmentManager fragmentManager = (FragmentManager) params[4];

            Fragment fragment = new BoutiquesFragment();
            List<Boutique> listBoutiques = getAllBoutiques();
            Bundle args = new Bundle();
            args.putSerializable("list", (ArrayList<Boutique>) listBoutiques);
            fragment.setArguments(args);

            if (fragment != null) {
                //FragmentManager fragmentManager = getFragmentManager();

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_container, fragment).commit();

                // update selected item and title, then close the drawer
                mDrawerList.setItemChecked(position, true);
                mDrawerList.setSelection(position);
                //setTitle(navMenuTitles[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
            } else {
                // error in creating fragment
                Log.e("MainActivity", "Error in creating fragment");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }


    }

}
