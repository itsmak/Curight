package com.innovellent.curight.fragment;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.pixplicity.easyprefs.library.Prefs;


public class ArticleFragment extends Fragment {
    private static final String TAG = "CuRight";
    ImageView iv_home_icon,iv_remainder_icon,iv_article_icon,iv_track_icon,iv_profile_icon,ivHealthToday;
    TextView tv_home_txt,tv_remainder_txt,tv_article_txt,tv_track_txt,tv_profile_txt;
    CardView cv_cartview1,cv_cartview2,cv_cartview3,cv_cartview4,cv_cartview5,cv_cartview6,cv_cartview7;
    ViewPager viewpager;
    TabLayout tab_layout;

    Context context;

    TextView tv_locationtxt,tv_locationsymbl,tvTitle;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.article_fragment_nw, container, false);
        Prefs.putString("ArticleCategory","all");
        init(rootView);
        initclick();

        iv_home_icon.setImageResource(R.drawable.home_grey);
        iv_remainder_icon.setImageResource(R.drawable.reminder_grey);
        iv_article_icon.setImageResource(R.drawable.artical_blue);
        iv_track_icon.setImageResource(R.drawable.track_grey);
        iv_profile_icon.setImageResource(R.drawable.profile_grey);

        tv_home_txt.setTextColor(Color.parseColor("#54666E"));
        tv_remainder_txt.setTextColor(Color.parseColor("#54666E"));
        tv_article_txt.setTextColor(Color.parseColor("#0B63F8"));
        tv_track_txt.setTextColor(Color.parseColor("#54666E"));
        tv_profile_txt.setTextColor(Color.parseColor("#54666E"));

        return rootView;
    }

    private void initclick() {

        cv_cartview1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Prefs.putString("ArticleCategory","health");
                viewpager.setCurrentItem(1,true);
            }
        });
        cv_cartview2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Prefs.putString("ArticleCategory","health");
                viewpager.setCurrentItem(1,true);
            }
        });
        cv_cartview3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Prefs.putString("ArticleCategory","health");
                viewpager.setCurrentItem(1,true);
            }
        });
        cv_cartview4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Prefs.putString("ArticleCategory","health");
                viewpager.setCurrentItem(1,true);
            }
        });
        cv_cartview5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Prefs.putString("ArticleCategory","health");
                viewpager.setCurrentItem(1,true);
            }
        });
        cv_cartview6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  Prefs.putString("ArticleCategory","health");
                viewpager.setCurrentItem(1,true);
            }
        });
        cv_cartview7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    Prefs.putString("ArticleCategory","health");
                viewpager.setCurrentItem(1,true);
            }
        });
    }


    public void init(View rootview) {

        viewpager = (ViewPager) getActivity().findViewById(R.id.viewpager);
        iv_home_icon = (ImageView) getActivity().findViewById(R.id.iv_home_icon);
        iv_remainder_icon = (ImageView) getActivity().findViewById(R.id.iv_remainder_icon);
        iv_article_icon = (ImageView) getActivity().findViewById(R.id.iv_article_icon);
        iv_track_icon = (ImageView) getActivity().findViewById(R.id.iv_track_icon);
        iv_profile_icon = (ImageView) getActivity().findViewById(R.id.iv_profile_icon);
        tv_home_txt = (TextView) getActivity().findViewById(R.id.tv_home_txt);
        tv_remainder_txt = (TextView) getActivity().findViewById(R.id.tv_remainder_txt);
        tv_article_txt = (TextView) getActivity().findViewById(R.id.tv_article_txt);
        tv_track_txt = (TextView) getActivity().findViewById(R.id.tv_track_txt);
        tv_profile_txt = (TextView) getActivity().findViewById(R.id.tv_profile_txt);

        cv_cartview1 = (CardView) rootview.findViewById(R.id.cv_cartview1);
        cv_cartview2 = (CardView) rootview.findViewById(R.id.cv_cartview2);
        cv_cartview3 = (CardView) rootview.findViewById(R.id.cv_cartview3);
        cv_cartview4 = (CardView) rootview.findViewById(R.id.cv_cartview4);
        cv_cartview5 = (CardView) rootview.findViewById(R.id.cv_cartview5);
        cv_cartview6 = (CardView) rootview.findViewById(R.id.cv_cartview6);
        cv_cartview7 = (CardView) rootview.findViewById(R.id.cv_cartview7);

        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        tvTitle.setText("Article");
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
     }

//    @Override
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.card_view1:
//                Log.d(TAG,"I am clicked");
//                Toast.makeText(getContext(),"1 is clicked",Toast.LENGTH_SHORT);
//                viewpager.setCurrentItem(2,true);
//                break;
//
//        }
//    }

}
