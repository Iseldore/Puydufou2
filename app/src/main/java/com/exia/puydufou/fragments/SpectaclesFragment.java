package com.exia.puydufou.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.exia.puydufou.business.InfosPDF;
import com.exia.puydufou.R;
import com.exia.puydufou.activities.SpectacleActivity;
import com.exia.puydufou.adapter.CustomAdapterSpectacle;
import com.exia.puydufou.common.AsyncResponse;
import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.entity.Spectacle;
import com.exia.puydufou.entity.Spectacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpectaclesFragment extends Fragment implements AsyncResponse{
    private LoadSpectacleFragment asyncTask = new LoadSpectacleFragment();
    private View thisView;
    private List<Spectacle> listObjects = new ArrayList<>();
    private CustomAdapterSpectacle customAdapterSpectacle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_spectacles, container, false);
        this.thisView = rootView;

        this.asyncTask.delegate = this;
        this.asyncTask.execute();

        return rootView;
    }

    List<Map<String, String>> spectacleList = new ArrayList<Map<String, String>>();

    private void initList(List<Spectacle> list) {
        if(list != null) {
            for (int i = 0; i < list.size(); i++) {
                spectacleList.add(createSpectacle("spectacle", list.get(i).getNom_spectacle()));
                listObjects.add(list.get(i));
            }
        }
    }

    private HashMap<String, String> createSpectacle(String name, String number) {
        HashMap<String, String> spectacleNameNo = new HashMap<String, String>();
        spectacleNameNo.put(name, number);
        return spectacleNameNo;
    }

    @Override
    public void onLoadList(Object liste) {
        List<Spectacle> list = (List<Spectacle>) liste;
        initList(list);
        customAdapterSpectacle = new CustomAdapterSpectacle(listObjects, thisView.getContext());

        ListView lv = (ListView) thisView.findViewById(R.id.listView1);
        lv.setAdapter(customAdapterSpectacle);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Spectacle spectacle = customAdapterSpectacle.getSpectacle(arg2);

                Intent t = new Intent(getActivity(), SpectacleActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putInt("key", 1);
                mBundle.putSerializable("spectacle", spectacle);
                t.putExtras(mBundle);
                getActivity().startActivity(t);

            }
        });
    }

    private class LoadSpectacleFragment extends AsyncTask<Void, Void, List<Spectacle>> {
        private ProgressDialog dialog;
        public AsyncResponse delegate=null;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(thisView.getContext());
            dialog.setMessage("Chargement");
            dialog.show();
        }

        @Override
        protected List<Spectacle> doInBackground(Void... params) {
            InfosPDF infos = new InfosPDF(thisView.getContext());
            List<Spectacle> list = infos.reallyGetAllSpectacles();
            return list;
        }

        @Override
        protected void onPostExecute(List<Spectacle> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            delegate.onLoadList(result);
        }
    }
}