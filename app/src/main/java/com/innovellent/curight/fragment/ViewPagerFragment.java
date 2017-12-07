package com.innovellent.curight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;


public class ViewPagerFragment extends Fragment {
    public static final String LUCKY_NUMBER = "";
    ImageView ivItem;
    int[] imageId = {R.drawable.ic_inst, R.drawable.into_1, R.drawable.into_2,R.drawable.into_3, R.drawable.intro_4,R.drawable.intro_5,R.drawable.into_3};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_viewpager_item, container, false);

           ivItem=(ImageView)v.findViewById(R.id.imageView);
        int luckyNum = getArguments().getInt(LUCKY_NUMBER, 0);
     //   tvLuckNum.setText(Integer.toString(luckyNum, 10));

        try {

            ivItem.setImageResource(imageId[luckyNum]);
        }catch (Exception e){

        }

        return v;
    }
}
