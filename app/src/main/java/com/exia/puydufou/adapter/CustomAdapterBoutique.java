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

import java.util.List;

/**
 * Created by Iseldore on 16/06/2015.
 */
    public class CustomAdapterBoutique extends BaseAdapter {
        List<Boutique> listeBoutique;
        Context context;
        ImageView img;
        Bitmap bitmap;
        ProgressDialog pDialog;

        public CustomAdapterBoutique(List<Boutique> listeBoutique, Context context){
            this.listeBoutique = listeBoutique;
            this.context = context;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return listeBoutique.size();
        }

        @Override
        public Boutique getItem(int arg0) {
            // TODO Auto-generated method stub
            return listeBoutique.get(arg0);
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
                arg1 = inflater.inflate(R.layout.listitemboutique, arg2,false);
            }

            TextView nomBoutique = (TextView)arg1.findViewById(R.id.itemNomBoutique);
            img = (ImageView)arg1.findViewById(R.id.imageViewItemBoutique);


            Boutique boutique = listeBoutique.get(arg0);

            nomBoutique.setText(boutique.getNomBoutique());

            Storage storage = new Storage(context);
            img.setImageBitmap(storage.getThumbnail(boutique.getUrl()));

            return arg1;
        }

        public Boutique getBoutique(int position)
        {
            return listeBoutique.get(position);
        }
}
