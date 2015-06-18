package com.exia.puydufou.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.exia.puydufou.R;
import com.exia.puydufou.activities.PlanningManuelActivity;
import com.exia.puydufou.activities.SpectacleActivity;
import com.exia.puydufou.business.PlanningSQLite;

import java.util.List;

/**
 * Created by Iseldore on 17/06/2015.
 */
public class PlanningManuelFragment extends Fragment {
    private ArrayAdapter<String> adapter;
    private PlanningSQLite planning;
    private ListView listView;
    private View thisView;

    public PlanningManuelFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_planning_manuel, container, false);
        this.thisView = rootView;
        
        planning = new PlanningSQLite(rootView.getContext());
        List<String> list = planning.getPlanning();
        String[] values = list.toArray(new String[list.size()]);

        // The checkbox for the each item is specified by the layout android.R.layout.simple_list_item_multiple_choice
        adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, values);

        listView = (ListView) rootView.findViewById(R.id.listViewPM);
        listView.setAdapter(adapter);

        Button button = (Button) rootView.findViewById(R.id.buttonPM);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(rootView.getContext(), PlanningManuelActivity.class);
                rootView.getContext().startActivity(t);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Make the adapter again
        List<String> list = planning.getPlanning();
        String[] values = list.toArray(new String[list.size()]);
        adapter = new ArrayAdapter<String>(thisView.getContext(), android.R.layout.simple_list_item_1, values);
        // Set it to the list again
        listView.setAdapter(adapter);
    }
}
