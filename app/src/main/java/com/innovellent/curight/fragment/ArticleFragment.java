package com.innovellent.curight.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.pixplicity.easyprefs.library.Prefs;


public class ArticleFragment extends Fragment implements View.OnClickListener {
    ImageView iv_home_icon,iv_remainder_icon,iv_article_icon,iv_track_icon,iv_profile_icon;
    TextView tv_home_txt,tv_remainder_txt,tv_article_txt,tv_track_txt,tv_profile_txt;
    RelativeLayout rlHealth;
    CardView card_view1,card_view2,card_view6,card_view4,card_view5,card_view3,card_view7;
    Context context;
    TextView tv_locationtxt,tv_locationsymbl,tvTitle;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.article_fragment, container, false);
        Prefs.putString("Articleflag","All");
        init(rootView);
        iniClick();

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

    public void init(View rootview) {

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

        card_view1 = (CardView) rootview.findViewById(R.id.card_view1);
        card_view2 = (CardView) rootview.findViewById(R.id.card_view2);
        card_view6 = (CardView) rootview.findViewById(R.id.card_view6);
        card_view4 = (CardView) rootview.findViewById(R.id.card_view4);
        card_view5 = (CardView) rootview.findViewById(R.id.card_view5);
        card_view3 = (CardView) rootview.findViewById(R.id.card_view3);
        card_view7 = (CardView) rootview.findViewById(R.id.card_view7);
        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        tvTitle.setText("Article");
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
    }

    public void iniClick() {

        card_view1.setOnClickListener(this);
        card_view2.setOnClickListener(this);
        card_view6.setOnClickListener(this);
        card_view4.setOnClickListener(this);
        card_view5.setOnClickListener(this);
        card_view3.setOnClickListener(this);
        card_view7.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

}
