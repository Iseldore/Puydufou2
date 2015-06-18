package com.exia.puydufou.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.exia.puydufou.R;
import com.exia.puydufou.activities.SpectacleActivity;
import com.exia.puydufou.business.PlanningSQLite;

import java.util.List;

public class InformationsFragment extends Fragment {
    ListView listView;
    public InformationsFragment(){}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_informations, container, false);

        listView = (ListView) rootView.findViewById(R.id.listViewinfos);

        // Defined Array values to show in ListView
        /*String[] values = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View"
        };*/

        final PlanningSQLite planning = new PlanningSQLite(rootView.getContext());
        //planning.supprimerTable();
        List<String> list = planning.getPlanning();
        String[] values = list.toArray(new String[list.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(rootView.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, values);

        // Assign adapter to ListView
        listView.setAdapter(adapter);
        planning.supprimerTable();
        Button button = (Button) rootView.findViewById(R.id.buttoninfos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText mEdit = (EditText) rootView.findViewById(R.id.editText);
                planning.insert(mEdit.getText().toString(), 0);
            }
        });
        return rootView;
    }
}
