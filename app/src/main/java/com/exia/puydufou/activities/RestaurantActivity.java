package com.exia.puydufou.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.exia.puydufou.R;
import com.exia.puydufou.entity.Restaurant;
import com.exia.puydufou.fragments.MapsFragment;

/**
 * Created by Iseldore on 18/06/2015.
 */
public class RestaurantActivity extends Activity{
    Restaurant restaurant;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restaurant);
        restaurant = (Restaurant) getIntent().getSerializableExtra("restaurant");

        TextView nom = (TextView) findViewById(R.id.nomRestaurant);
        TextView infos = (TextView) findViewById(R.id.infoRestaurant);
        nom.setText(restaurant.getNomRestaurant());
        infos.setText(restaurant.getDescriptionRestaurant());

        Button button = (Button) findViewById(R.id.buttonrestaurant);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent t = new Intent(RestaurantActivity.this, MapsFragment.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("object", restaurant);
                t.putExtras(mBundle);
                RestaurantActivity.this.startActivity(t);
            }
        });
    }
}
