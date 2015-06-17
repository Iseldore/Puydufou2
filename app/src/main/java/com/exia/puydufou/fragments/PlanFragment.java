package com.exia.puydufou.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.exia.puydufou.R;
import com.exia.puydufou.common.TouchImageView;

public class PlanFragment extends Fragment {

    public PlanFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_plan, container, false);
        TouchImageView img = (TouchImageView) rootView.findViewById(R.id.img);
        img.setImageResource(R.drawable.map);

        return rootView;
    }
}
