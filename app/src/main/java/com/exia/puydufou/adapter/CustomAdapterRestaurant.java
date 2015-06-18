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
import com.exia.puydufou.entity.Restaurant;

import java.util.List;

/**
 * Created by Iseldore on 16/06/2015.
 */
public class CustomAdapterRestaurant extends BaseAdapter {
    List<Restaurant> listeRestaurant;
    Context context;
    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;

    public CustomAdapterRestaurant(List<Restaurant> listeRestaurant, Context context){
        this.listeRestaurant = listeRestaurant;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listeRestaurant.size();
    }

    @Override
    public Restaurant getItem(int arg0) {
        // TODO Auto-generated method stub
        return listeRestaurant.get(arg0);
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
            arg1 = inflater.inflate(R.layout.listitemrestaurant, arg2,false);
        }

        TextView nomRestaurant = (TextView)arg1.findViewById(R.id.itemNomRestaurant);
        img = (ImageView)arg1.findViewById(R.id.imageViewItemRestaurant);


        Restaurant restaurant = listeRestaurant.get(arg0);

        nomRestaurant.setText(restaurant.getNomRestaurant());

        Storage storage = new Storage(context);
        img.setImageBitmap(storage.getThumbnail(restaurant.getUrl()));

        return arg1;
    }

    public Restaurant getRestaurant(int position)
    {
        return listeRestaurant.get(position);
    }
}
