package com.exia.puydufou.fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.exia.puydufou.R;
import com.exia.puydufou.activities.RestaurantActivity;
import com.exia.puydufou.adapter.CustomAdapterRestaurant;
import com.exia.puydufou.business.InfosPDF;
import com.exia.puydufou.common.AsyncResponse;
import com.exia.puydufou.entity.Restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RestaurantsFragment extends Fragment implements AsyncResponse {
    LoadRestaurantFragment asyncTask = new LoadRestaurantFragment();
    private List<Restaurant> listObjects = new ArrayList<>();
    private CustomAdapterRestaurant customAdapterRestaurant;
    View thisView;

    public RestaurantsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_restaurants, container, false);
        this.thisView = rootView;
        this.asyncTask.delegate = this;
        this.asyncTask.execute();
        return rootView;
    }

    List<Map<String, String>> RestaurantList = new ArrayList<Map<String, String>>();

    private void initList(List<Restaurant> list) {
        if(list != null) {
            for (int i = 0; i < list.size(); i++) {
                RestaurantList.add(createRestaurant("Restaurant", list.get(i).getNomRestaurant()));
                listObjects.add(list.get(i));
            }
        }
    }

    private HashMap<String, String> createRestaurant(String name, String number) {
        HashMap<String, String> RestaurantNameNo = new HashMap<String, String>();
        RestaurantNameNo.put(name, number);
        return RestaurantNameNo;
    }
    
    @Override
    public void onLoadList(Object liste) {
        List<Restaurant> list = (List<Restaurant>) liste;
        initList(list);
        customAdapterRestaurant = new CustomAdapterRestaurant(listObjects, thisView.getContext());

        ListView lv = (ListView) thisView.findViewById(R.id.listViewRestaurants);
        lv.setAdapter(customAdapterRestaurant);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                Restaurant restaurant = customAdapterRestaurant.getRestaurant(arg2);

                Intent t = new Intent(getActivity(), RestaurantActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("restaurant", restaurant);
                t.putExtras(mBundle);
                getActivity().startActivity(t);

            }
        });
    }

    private class LoadRestaurantFragment extends AsyncTask<Void, Void, List<Restaurant>> {
        private ProgressDialog dialog;
        public AsyncResponse delegate=null;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(thisView.getContext());
            dialog.setMessage("Chargement");
            dialog.show();
        }

        @Override
        protected List<Restaurant> doInBackground(Void... params) {
            InfosPDF infos = new InfosPDF(thisView.getContext());
            List<Restaurant> list = infos.getAllRestaurants();
            return list;
        }

        @Override
        protected void onPostExecute(List<Restaurant> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            delegate.onLoadList(result);
        }
    }
}
