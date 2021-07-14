package com.hillarie.havatest.common;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.hillarie.havatest.R;


/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public class CustomerWindowInfo implements GoogleMap.InfoWindowAdapter {

    View myView;
    Context c;

    public CustomerWindowInfo(Context context) {
        myView = LayoutInflater.from(context)
                .inflate(R.layout.model_user_map,null);
    }

    @Override
    public View getInfoWindow(Marker marker) {

        TextView txtPickUPTitle = ((TextView)myView.findViewById(R.id.tv_NameInfo));
        txtPickUPTitle.setText(marker.getTitle());

        TextView txtPickUPSnippet = ((TextView)myView.findViewById(R.id.tv_rating));
        txtPickUPSnippet.setText(marker.getSnippet());

/*        ImageView IVUserImage = myView.findViewById(R.id.iv_doctorimage);
        PicassoImage.downloadImage(c,GET_USERIMAGE+IVUserImage,IVUserImage);*/

        return myView;
    }

    @Override
    public View getInfoContents(Marker marker) {
        return null;
    }
}
