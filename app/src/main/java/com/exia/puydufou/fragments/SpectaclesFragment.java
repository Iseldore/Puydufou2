package com.exia.puydufou.fragments;

import android.app.Fragment;
import android.content.Intent;
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
import com.exia.puydufou.entity.Spectacle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpectaclesFragment extends Fragment {

    private List<Spectacle> listObjects = new ArrayList<>();
    InfosPDF infos;
    private CustomAdapterSpectacle customAdapterSpectacle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_spectacles, container, false);
        infos = new InfosPDF(rootView.getContext());

        List<Spectacle> list = (List<Spectacle>) this.getArguments().getSerializable("list");

        initList(list);
        customAdapterSpectacle = new CustomAdapterSpectacle(listObjects, rootView.getContext());

        ListView codeLearnLessons = (ListView) rootView.findViewById(R.id.listView1);
        codeLearnLessons.setAdapter(customAdapterSpectacle);

        codeLearnLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Spectacle spectacle = customAdapterSpectacle.getSpectacle(arg2);

                Intent t = new Intent(getActivity(), SpectacleActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("spectacle", spectacle);
                t.putExtras(mBundle);
                getActivity().startActivity(t);

            }
        });

        return rootView;
    }

    List<Map<String, String>> spectacleList = new ArrayList<Map<String, String>>();

    private void initList(List<Spectacle> list) {
        //list = infos.getAllSpectacles();
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
}