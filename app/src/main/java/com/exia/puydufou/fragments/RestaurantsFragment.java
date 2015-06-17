package com.exia.puydufou.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exia.puydufou.R;

public class RestaurantsFragment extends Fragment {

    public RestaurantsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_restaurants, container, false);

        return rootView;
    }
}
