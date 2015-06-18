package com.exia.puydufou.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.exia.puydufou.R;
import com.exia.puydufou.activities.BoutiqueActivity;
import com.exia.puydufou.activities.SpectacleActivity;
import com.exia.puydufou.adapter.CustomAdapterBoutique;
import com.exia.puydufou.adapter.CustomAdapterTasks;
import com.exia.puydufou.business.InfosPDF;
import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.entity.Spectacle;
import com.exia.puydufou.entity.TaskObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlanningFragment extends Fragment {

    private List<TaskObject> listObjects = new ArrayList<>();
    InfosPDF infos;
    private CustomAdapterTasks customAdapterTasks;

    public PlanningFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_planning, container, false);

        infos = new InfosPDF(rootView.getContext());

        List<TaskObject> list = (List<TaskObject>) this.getArguments().getSerializable("list");
        initList(list);
        customAdapterTasks = new CustomAdapterTasks(listObjects, rootView.getContext());

        ListView codeLearnLessons = (ListView) rootView.findViewById(R.id.listViewTasks);
        codeLearnLessons.setAdapter(customAdapterTasks);

        codeLearnLessons.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                TaskObject to = customAdapterTasks.getTask(arg2);
                Spectacle spectacle = to.getSpectacle();
                if (spectacle != null){
                    Intent t = new Intent(getActivity(), SpectacleActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("spectacle", spectacle);
                    t.putExtras(mBundle);
                    getActivity().startActivity(t);
                }
            }
        });

        return rootView;
    }

    List<Map<String, String>> TaskList = new ArrayList<Map<String, String>>();

    private void initList(List<TaskObject> list) {
        if(list != null) {
            for (int i = 0; i < list.size(); i++) {
                TaskList.add(createTask("Spectacle", list.get(i).getNom()));
                listObjects.add(list.get(i));
            }
        }
    }

    private HashMap<String, String> createTask(String name, String number) {
        HashMap<String, String> BoutiqueNameNo = new HashMap<String, String>();
        BoutiqueNameNo.put(name, number);
        return BoutiqueNameNo;
    }
}
