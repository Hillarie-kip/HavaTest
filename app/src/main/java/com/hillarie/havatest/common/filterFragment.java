package com.hillarie.havatest.common;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.hillarie.havatest.R;
/**
 * Created by Hillarie Kalya on 07/14/2021.
 * Copyright (c) 2021 All rights reserved.
 */
public class filterFragment extends BottomSheetDialogFragment
    implements View.OnClickListener {
  public static final String TAG = "ActionBottomDialog";
  private ItemClickListener mListener;
  public static filterFragment newInstance() {
    return new filterFragment();
  }
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.bottom_sheet, container, false);
  }
  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    view.findViewById(R.id.tv_reset).setOnClickListener(this);

    view.findViewById(R.id.tv_any).setOnClickListener(this);
    view.findViewById(R.id.tv_under3).setOnClickListener(this);
    view.findViewById(R.id.tv_3to8).setOnClickListener(this);
    view.findViewById(R.id.tv_8to15).setOnClickListener(this);
    view.findViewById(R.id.tv_plus15).setOnClickListener(this);

    view.findViewById(R.id.tv_anytime).setOnClickListener(this);
    view.findViewById(R.id.tv_under5min).setOnClickListener(this);
    view.findViewById(R.id.tv_5to10min).setOnClickListener(this);
    view.findViewById(R.id.tv_10to20).setOnClickListener(this);
    view.findViewById(R.id.tv_plus20).setOnClickListener(this);

    view.findViewById(R.id.btn_filter).setOnClickListener(this);
    view.findViewById(R.id.sw_cancel).setOnClickListener(this);

  }
  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof ItemClickListener) {
      mListener = (ItemClickListener) context;
    } else {
      throw new RuntimeException(context.toString() + " must implement ItemClickListener");
    }
  }
  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }
  @Override
  public void onClick(View view) {
    TextView tvSelected = (TextView) view;
    int pos=0;
    if(tvSelected.getText().toString().equals("Any Distance")){
      pos =1;
    }else if(tvSelected.getText().toString().equals("Under 3KM")){
      pos =2;
    }
    else if(tvSelected.getText().toString().equals("3KM TO 8 KM")){
      pos =3;
    }
    else if(tvSelected.getText().toString().equals("8KM to 15 KM")){
      pos =4;
    }
    else if(tvSelected.getText().toString().equals("More than 15 KM")){
      pos =5;
    }

    else if(tvSelected.getText().toString().equals("Any Time")){
      pos =6;
    }
    else if(tvSelected.getText().toString().equals("Under 5 MIN")){
      pos =7;
    }
    else if(tvSelected.getText().toString().equals("5 TO 10 MIN")){
      pos =8;
    }
    else if(tvSelected.getText().toString().equals("10 to 20 MIN")){
      pos =9;
    }
    else if(tvSelected.getText().toString().equals("More than 20 MIN")){
      pos =10;
    }

    else if(tvSelected.getText().toString().equals("FILTER")){
      pos =11;
    }

    mListener.onItemClick(pos);
    dismiss();
  }
  public interface ItemClickListener {
    void onItemClick(int item);
  }
}