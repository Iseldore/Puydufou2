package com.exia.puydufou.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exia.puydufou.R;
import com.exia.puydufou.common.Storage;
import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.entity.Spectacle;
import com.exia.puydufou.entity.TaskObject;

import java.util.List;

/**
 * Created by Iseldore on 17/06/2015.
 */
public class CustomAdapterTasks extends BaseAdapter{
    List<TaskObject> listeTasks;
    Context context;

    public CustomAdapterTasks(List<TaskObject> listeTasks, Context context){
        this.listeTasks = listeTasks;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listeTasks.size();
    }

    @Override
    public TaskObject getItem(int arg0) {
        // TODO Auto-generated method stub
        return listeTasks.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {

        if(arg1==null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arg1 = inflater.inflate(R.layout.listitemtask, arg2,false);
        }

        TextView nomTv = (TextView)arg1.findViewById(R.id.textView1);
        TextView dureeTv = (TextView)arg1.findViewById(R.id.textView2);
        TextView heureTv = (TextView)arg1.findViewById(R.id.textView3);
        ImageView img = (ImageView)arg1.findViewById(R.id.imageView1);


        TaskObject to = listeTasks.get(arg0);
        Spectacle spectacle = to.getSpectacle();
        if(spectacle != null) {
            nomTv.setText(spectacle.getNom_spectacle());

            Storage storage = new Storage(context);
            img.setImageBitmap(storage.getThumbnail(spectacle.getUrl()));
            android.text.format.DateFormat df = new android.text.format.DateFormat();
            String duree = df.format("HH:mm", spectacle.getDuree_spectacle()).toString();
            dureeTv.setText("Duree : " + duree);

            String heure = df.format("HH:mm", spectacle.getHoraires()).toString();
            heureTv.setText(heure);
        }
        else {
            nomTv.setText(to.getNom());

            android.text.format.DateFormat df = new android.text.format.DateFormat();
            img.setImageBitmap(null);
            String duree = df.format("HH:mm", to.getDuree()).toString();
            dureeTv.setText("Duree : " + duree);

            String heure = df.format("HH:mm", to.getHoraire()).toString();
            heureTv.setText(heure);
        }

        return arg1;
    }

    public TaskObject getTask(int position)
    {
        return listeTasks.get(position);
    }
}
