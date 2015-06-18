package com.exia.puydufou.activities;

import android.app.Activity;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.Toast;

import com.exia.puydufou.R;
import com.exia.puydufou.business.InfosPDF;
import com.exia.puydufou.business.PlanningSQLite;
import com.exia.puydufou.common.AsyncResponse;
import com.exia.puydufou.entity.Spectacle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Iseldore on 18/06/2015.
 */
public class PlanningManuelActivity extends ListActivity implements AsyncResponse{
    private LoadSpectacleFragment asyncTask = new LoadSpectacleFragment();
    private Context context;
    private String[] listSpec;
    private List<String> listUtilisateur = new ArrayList<>();
    private List<Integer> listPosition = new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planningmanuel);
        this.context = PlanningManuelActivity.this;
        this.asyncTask.delegate = this;
        this.asyncTask.execute();

        Button button = (Button) findViewById(R.id.buttonSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlanningSQLite planning = new PlanningSQLite(PlanningManuelActivity.this);
                planning.viderTable();
                for (int i = 0; i < listUtilisateur.size(); i++) {
                    planning.insert(listUtilisateur.get(i), listPosition.get(i));
                }
                finish();
                setResult(RESULT_OK);
            }
        });
    }

    public void onListItemClick(ListView parent, View v,int position,long id){
        CheckedTextView item = (CheckedTextView) v;
        /*Toast.makeText(this, listSpec[position] + " checked : " +
                item.isChecked(), Toast.LENGTH_SHORT).show();*/
        if(item.isChecked()){
            listUtilisateur.add(listSpec[position]);
            listPosition.add(position);
        }
        else {
            listUtilisateur.remove(listSpec[position]);
            listPosition.remove((Object) position);
        }
    }

    @Override
    public void onLoadList(Object liste) {
        List<Spectacle> list = (List<Spectacle>) liste;
        String[] values = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            Spectacle spectacle = list.get(i);
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            String heure = df.format("HH:mm", spectacle.getHoraires()).toString();
            values[i] = spectacle.getNom_spectacle()+" ("+heure+")";
        }
        ListView listview = getListView();
        //	listview.setChoiceMode(listview.CHOICE_MODE_NONE);
        //	listview.setChoiceMode(listview.CHOICE_MODE_SINGLE);
        listview.setChoiceMode(listview.CHOICE_MODE_MULTIPLE);

        //--	text filtering
        listview.setTextFilterEnabled(true);

        setListAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, values));

        this.listSpec = values;
    }

    private class LoadSpectacleFragment extends AsyncTask<Void, Void, List<Spectacle>> {
        private ProgressDialog dialog;
        public AsyncResponse delegate=null;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(context);
            dialog.setMessage("Chargement");
            dialog.show();
        }

        @Override
        protected List<Spectacle> doInBackground(Void... params) {
            InfosPDF infos = new InfosPDF(context);
            List<Spectacle> list = infos.getAllSpectacles();
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
