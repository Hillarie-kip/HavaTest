package com.hillarie.havatest.common;

import android.content.Context;
import android.widget.ImageView;

import com.hillarie.havatest.R;
import com.squareup.picasso.Picasso;


/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public class PicassoImage {

    public static void downloadImage(Context c, String imageUrl, ImageView img) {
        if (imageUrl != null && imageUrl.length() > 0) {
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_launcher_round).into(img);

        } else {
           // Picasso.with(c).load(R.mipmap.ic_profile).into(img);
            Picasso.get().load(imageUrl).placeholder(R.mipmap.ic_launcher_round).into(img);

        }
    }
}
