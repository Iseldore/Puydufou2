package com.exia.puydufou.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.exia.puydufou.R;
import com.exia.puydufou.business.InfosPDF;
import com.exia.puydufou.entity.MenuRestau;
import com.exia.puydufou.entity.Restaurant;
import com.exia.puydufou.fragments.MapsFragment;

import java.util.List;

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
        List<MenuRestau> listMenus = restaurant.getListMenus();

        String[] values = new String[listMenus.size()];
        for(int i = 0; i<listMenus.size(); i++){
            values[i] = listMenus.get(i).getDescription()+" ("+listMenus.get(i).getTarif()+" euros)";
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        final ListView listView = (ListView) findViewById(R.id.listView5);
        // Assign adapter to ListView
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) listView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(),
                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();

            }

        });
    }
}
