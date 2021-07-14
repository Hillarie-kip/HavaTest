package com.hillarie.havatest.rides;


import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hillarie.havatest.R;
import com.hillarie.havatest.common.filterFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import dmax.dialog.SpotsDialog;


/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public class RidesActivity extends AppCompatActivity implements rideAdapter.ContactsAdapterListener, filterFragment.ItemClickListener {
    private static final String TAG = RidesActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    private List<pojo> pojoList;
    private rideAdapter mAdapter;
    private SearchView searchView;


    SpotsDialog pd;




    Handler handler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rides);
        recyclerView = findViewById(R.id.rv);


        pojoList = new ArrayList<>();
        mAdapter = new rideAdapter(this, pojoList, this);
        setTitle("RIDES "+pojoList.size());


        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //recyclerView.addItemDecoration(new MyDividerItemDecoration(this, DividerItemDecoration.VERTICAL, 1));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, true));
        recyclerView.setAdapter(mAdapter);
        fetchRides("https://hr.hava.bz/trips/recent.json");

    }

    /**
     * fetches json by making http calls
     */
    public void showBottomSheet() {
        filterFragment addPhotoBottomDialogFragment = filterFragment.newInstance();
        addPhotoBottomDialogFragment.show(getSupportFragmentManager(), filterFragment.TAG);

    }

    @Override
    public void onItemClick(int item) {

        if (item==1){
            mAdapter.notifyDataSetChanged();
        }
        if (item==2){
            //under 3KM
            RangeDistance(0,3);


        }
        if (item==3){
            // 3KM-8KM
            RangeDistance(3,8);


        }
        if (item==4){
             // 8KM-15KM
            RangeDistance(8,15);

        }
        if (item==5){
            // more than 15KM
            DistanceGreaterThan(15);
        }
        if (item==6){
            mAdapter.notifyDataSetChanged();
            setTitle("RIDES "+pojoList.size());
        }
        if (item==7){
            //under 5 min
            RangeDistance(0,5);


        }
        if (item==8){
            // 5-10 min
            RangeDistance(5,10);


        }
        if (item==9){
            // 10-20min
            RangeTime(10,20);

        }
        if (item==10){
            // more than 20min
            TimeGreaterThan(20);
        }

        if (item==11){
            // more than 20min
            TimeGreaterThan(20);
        }
    }

    public  void RangeDistance(int lower,int Uper){
        List<pojo> filtered = pojoList.stream().filter(x -> {
            double distance = x.getDistance();
            return distance >= lower && distance <= Uper;
        }).collect(Collectors.toList());

      //  pojoList.clear();
        pojoList.addAll(filtered);
        mAdapter.notifyDataSetChanged();
        setTitle("RIDES "+pojoList.size());

    }
    public  void DistanceGreaterThan(int value){
        List<pojo> filtered = pojoList.stream().filter(x -> {
            double distance = x.getDistance();
            return distance >=value ;
        }).collect(Collectors.toList());

        pojoList.clear();
        pojoList.addAll(filtered);
        mAdapter.notifyDataSetChanged();
        setTitle("RIDES "+pojoList.size());
    }


    public  void RangeTime(int lower,int Uper){
        List<pojo> filtered = pojoList.stream().filter(x -> {
            int time = x.getDuration();
            return time >= lower && time <= Uper;
        }).collect(Collectors.toList());

        pojoList.clear();
        pojoList.addAll(filtered);
        mAdapter.notifyDataSetChanged();
        setTitle("RIDES "+pojoList.size());
    }
    public  void TimeGreaterThan(int value){
        List<pojo> filtered = pojoList.stream().filter(x -> {
            int time = x.getDuration();
            return time > value ;
        }).collect(Collectors.toList());

        pojoList.clear();
        pojoList.addAll(filtered);
        mAdapter.notifyDataSetChanged();
        setTitle("RIDES "+pojoList.size());
    }

    private void fetchRides(String url) {

        pd = new SpotsDialog(RidesActivity.this, R.style.ProgressKali);
        pd.setCancelable(true);
        pd.show();


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("fetchRides", String.valueOf(response));
                        pd.cancel();
                        try {

                            String trips = (response.getString("trips"));

                            List<pojo> items = new Gson().fromJson(trips, new TypeToken<List<pojo>>() {}.getType());


                            pojoList.clear();
                            pojoList.addAll(items);
                            setTitle("RIDES "+pojoList.size());
                            // refreshing recycler view
                            mAdapter.notifyDataSetChanged();





                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                    }
                }
        ){

            //This is for Headers If You Needed
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }
        if (id == R.id.action_refresh) {
            fetchRides("https://hr.hava.bz/trips/recent.json");
        }
        if (id == R.id.action_filter) {
            showBottomSheet();
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        // close search view on background button pressed
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
        handler.removeCallbacksAndMessages(null);
    }


    @Override
    public void onContactSelected(pojo pojo) {

    }


}
