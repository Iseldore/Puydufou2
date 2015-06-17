package com.exia.puydufou.fragments;

/**
 * Created by Iseldore on 17/06/2015.
 */
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.TextView;

import com.exia.puydufou.R;
import com.exia.puydufou.entity.Boutique;
import com.exia.puydufou.entity.Spectacle;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

public class MapsFragment extends FragmentActivity implements LocationListener {

    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Object object = (Object) getIntent().getSerializableExtra("object");
        double latitude = 0;
        double longitude = 0;
        String nom = null;

        if(object.getClass() == Spectacle.class){
            Spectacle spectacle = (Spectacle) object;
            latitude = spectacle.getLatitude();
            longitude = spectacle.getLongitude();
            nom = spectacle.getNom_spectacle();
        }
        else if(object.getClass() == Boutique.class){
            Boutique boutique = (Boutique) object;
            latitude = boutique.getLatitude();
            longitude = boutique.getLongitude();
            nom = boutique.getNomBoutique();
        }

        // Getting reference to the SupportMapFragment of activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.googlemap);

        // Getting GoogleMap object from the fragment
        googleMap = fm.getMap();

        Marker marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(nom));
        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);


        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
           // onLocationChanged(location);
        }

        final LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(marker.getPosition());
        LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
        builder.include(current);
        LatLngBounds bounds = builder.build();
        int padding = 5; // offset from edges of the map in pixels

        final CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

        //googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                googleMap.animateCamera(cu);
            }
        });

        locationManager.requestLocationUpdates(provider, 20000, 0, this);

    }


    @Override
    public void onLocationChanged(Location location) {
/*
        //TextView tvLocation = (TextView) findViewById(R.id.googlemap);

        // Getting latitude of the current location
        double latitude = location.getLatitude();

        // Getting longitude of the current location
        double longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        // Setting latitude and longitude in the TextView tv_location
        //tvLocation.setText("Latitude:" +  latitude  + ", Longitude:"+ longitude );
*/
    }

    @Override
    public void onProviderDisabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onProviderEnabled(String provider) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // TODO Auto-generated method stub
    }

}
