package com.innovellent.curight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.innovellent.curight.R;



public class ArticleFragment extends Fragment implements View.OnClickListener {
    RelativeLayout rlHealth;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.article_fragment, container, false);
        init(rootView);
        iniClick();
        return rootView;
    }

    public void init(View rootview) {


    }

    public void iniClick() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

}
