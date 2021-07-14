package com.hillarie.havatest.rides;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;


import com.hillarie.havatest.R;
import com.hillarie.havatest.rides.detail.RideDetailActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */

public class rideAdapter extends RecyclerView.Adapter<rideAdapter.MyViewHolder> implements Filterable {
    private static final int MSG_TYPE_RIGHT = 0;
    private static final int MSG_TYPE_LEFT = 1;
    private final Context context;
    private final List<pojo> pojoList;
    private List<pojo> pojoListFiltered;
    private final ContactsAdapterListener listener;
    ProgressDialog progressDialog;



    public class MyViewHolder extends RecyclerView.ViewHolder {


        public View _vw_blayer;

        TextView tvDriverName, tvPickup, tvDropOff,tvPickUpDate,tvCost,tvDuration,tvStatus,
        tvDistance;

        public MyViewHolder(View view) {
            super(view);

            tvDriverName= view.findViewById(R.id.tv_drivername);
            tvPickup= view.findViewById(R.id.tv_pickup);
            tvDropOff= view.findViewById(R.id.tv_dropOff);
            tvPickUpDate= view.findViewById(R.id.tv_timestamp);
            tvCost= view.findViewById(R.id.tv_cost);
            tvDuration= view.findViewById(R.id.tv_duration);
            tvStatus= view.findViewById(R.id.tv_status);

            tvDistance= view.findViewById(R.id.tv_distance);



        }
    }


    public rideAdapter(Context context, List<pojo> pojoList, ContactsAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.pojoList = pojoList;
        this.pojoListFiltered = pojoList;
        progressDialog = new ProgressDialog(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
 View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model_rides, parent, false);
 return new MyViewHolder(itemView);


    }

    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final pojo pojo = pojoListFiltered.get(position);

        holder.tvDriverName.setText(pojo.getDriver_name());
        holder.tvPickup.setText(pojo.getPickup_location());
        holder.tvDropOff.setText(pojo.getDropoff_location());
        holder.tvDuration.setText(pojo.getDuration() +" "+pojo.getDuration_unit());
        holder.tvPickUpDate.setText(pojo.getPickup_date());
        holder.tvCost.setText("Total: "+pojo.getCost()+" "+pojo.getCost_unit());
        holder.tvStatus.setText(pojo.getStatus());
        holder.tvDistance.setText(pojo.getDistance()+" "+pojo.getDistance_unit());

        if(pojo.getStatus().equals("COMPLETED")){
         holder.tvStatus.setBackgroundResource(R.drawable.button_closed);
         }else if(pojo.getStatus().equals("CANCELLED")){
         holder.tvStatus.setBackgroundResource(R.drawable.button_cancelled);

        }
        holder.tvCost.setText(pojo.getCost()+" "+pojo.getCost_unit());



        //  PicassoImage.downloadImage(context, GET_USERIMAGE + pojo.getUserImage(), holder.IVUserImage);

holder.itemView.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        Intent i = new Intent(context, RideDetailActivity.class);
        i.putExtra("request_date",pojo.getRequest_date());
        i.putExtra("pickup_location",pojo.getPickup_location());
        i.putExtra("dropoff_location",pojo.getDropoff_location());
        i.putExtra("pickup_date",pojo.getPickup_date());
        i.putExtra("dropoff_date",pojo.getDropoff_date());
        i.putExtra("type",pojo.getType());
        i.putExtra("driver_name",pojo.getDriver_name());
        i.putExtra("driver_pic",pojo.getDriver_pic());
        i.putExtra("car_make",pojo.getCar_make());
        i.putExtra("car_model",pojo.getCar_model());
        i.putExtra("car_number",pojo.getCar_number());
        i.putExtra("car_pic",pojo.getCar_pic());
        i.putExtra("duration_unit",pojo.getDuration_unit());
        i.putExtra("distance_unit",pojo.getDistance_unit());
        i.putExtra("cost_unit",pojo.getCost_unit());

        i.putExtra("pickup_lat",pojo.getPickup_lat());
        i.putExtra("pickup_lng",pojo.getPickup_lng());
        i.putExtra("dropoff_lat",pojo.getDropoff_lat());
        i.putExtra("dropoff_lng",pojo.getDropoff_lng());
        i.putExtra("distance",pojo.getDistance());

        i.putExtra("duration",pojo.getDuration());
        i.putExtra("cost",pojo.getCost());
        context.startActivity(i);
    }
});
    }

    @Override
    public int getItemCount() {
        return pojoListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    pojoListFiltered = pojoList;
                } else {
                    List<pojo> filteredList = new ArrayList<>();
                    for (pojo row : pojoList) {

                        if (row.getDriver_name().toLowerCase().contains(charString.toLowerCase()) ||
                                row.getDropoff_location().contains(charSequence)||row.getPickup_location().contains(charSequence)
                        ) {
                            filteredList.add(row);
                        }
                    }

                    pojoListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = pojoListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                pojoListFiltered = (ArrayList<pojo>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface ContactsAdapterListener {
        void onContactSelected(pojo pojo);
    }


}
