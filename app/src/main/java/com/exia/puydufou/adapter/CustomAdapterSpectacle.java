package com.exia.puydufou.adapter;

import java.util.List;

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
import com.exia.puydufou.Storage;
import com.exia.puydufou.entity.Spectacle;

/**
 * Created by Iseldore on 15/06/2015.
 */
public class CustomAdapterSpectacle extends BaseAdapter {
    List<Spectacle> listeSpectacle;
    Context context;
    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;

    public CustomAdapterSpectacle(List<Spectacle> listeSpectacle, Context context){
        this.listeSpectacle = listeSpectacle;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listeSpectacle.size();
    }

    @Override
    public Spectacle getItem(int arg0) {
        // TODO Auto-generated method stub
        return listeSpectacle.get(arg0);
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
            arg1 = inflater.inflate(R.layout.listitemspectacle, arg2,false);
        }

        TextView nomSpectacle = (TextView)arg1.findViewById(R.id.textView1);
        TextView dureeSpectacle = (TextView)arg1.findViewById(R.id.textView2);
        TextView heureSpectacle = (TextView)arg1.findViewById(R.id.textView3);
        img = (ImageView)arg1.findViewById(R.id.imageView1);


        Spectacle spectacle = listeSpectacle.get(arg0);

        nomSpectacle.setText(spectacle.getNom_spectacle());
       /* try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL("http://10.176.130.60/PuyDuFou/img_spectacles/" + spectacle.getUrl()).getContent());
            img.setImageBitmap(bitmap);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //new LoadImage().execute("http://10.176.130.60/PuyDuFou/img_spectacles/" + spectacle.getUrl());
*/
        Storage storage = new Storage(context);
        img.setImageBitmap(storage.getThumbnail(spectacle.getUrl()));
        android.text.format.DateFormat df = new android.text.format.DateFormat();
        String duree = df.format("HH:mm", spectacle.getDuree_spectacle()).toString();
        dureeSpectacle.setText("Duree : " + duree);

        String heure = df.format("HH:mm", spectacle.getHoraires()).toString();
        heureSpectacle.setText(heure);

        return arg1;
    }

    public Spectacle getSpectacle(int position)
    {
        return listeSpectacle.get(position);
    }
}
