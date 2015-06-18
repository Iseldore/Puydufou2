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

import com.exia.puydufou.R;
import com.exia.puydufou.activities.BoutiqueActivity;
import com.exia.puydufou.adapter.CustomAdapterBoutique;
import com.exia.puydufou.business.InfosPDF;
import com.exia.puydufou.common.AsyncResponse;
import com.exia.puydufou.entity.Boutique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoutiquesFragment extends Fragment implements AsyncResponse {
    LoadBoutiqueFragment asyncTask = new LoadBoutiqueFragment();
    private List<Boutique> listObjects = new ArrayList<>();
    InfosPDF infos;
    private CustomAdapterBoutique customAdapterBoutique;
    View thisView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_boutiques, container, false);
        this.thisView = rootView;
        this.asyncTask.delegate = this;
        this.asyncTask.execute();

        return rootView;
    }

    List<Map<String, String>> BoutiqueList = new ArrayList<Map<String, String>>();

    private void initList(List<Boutique> list) {
        if(list != null) {
            for (int i = 0; i < list.size(); i++) {
                BoutiqueList.add(createBoutique("Boutique", list.get(i).getNomBoutique()));
                listObjects.add(list.get(i));
            }
        }
    }

    private HashMap<String, String> createBoutique(String name, String number) {
        HashMap<String, String> BoutiqueNameNo = new HashMap<String, String>();
        BoutiqueNameNo.put(name, number);
        return BoutiqueNameNo;
    }

    @Override
    public void onLoadList(Object liste) {
        List<Boutique> list = (List<Boutique>) liste;
        initList(list);
        customAdapterBoutique = new CustomAdapterBoutique(listObjects, thisView.getContext());

        ListView lv = (ListView) thisView.findViewById(R.id.listViewBoutiques);
        lv.setAdapter(customAdapterBoutique);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Boutique boutique = customAdapterBoutique.getBoutique(arg2);

                Intent t = new Intent(getActivity(), BoutiqueActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("boutique", boutique);
                t.putExtras(mBundle);
                getActivity().startActivity(t);

            }
        });
    }

    private class LoadBoutiqueFragment extends AsyncTask<Void, Void, List<Boutique>> {
        private ProgressDialog dialog;
        public AsyncResponse delegate=null;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(thisView.getContext());
            dialog.setMessage("Chargement");
            dialog.show();
        }

        @Override
        protected List<Boutique> doInBackground(Void... params) {
            InfosPDF infos = new InfosPDF(thisView.getContext());
            List<Boutique> list = infos.getAllBoutiques();
            return list;
        }

        @Override
        protected void onPostExecute(List<Boutique> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            delegate.onLoadList(result);
        }
    }

}