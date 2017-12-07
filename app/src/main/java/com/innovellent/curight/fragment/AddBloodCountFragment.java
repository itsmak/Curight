package com.innovellent.curight.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.TrackActivity;


public class AddBloodCountFragment extends Fragment implements
        View.OnClickListener{
     TextView tvList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_add_blood_count,container,false);
        init(rootView);
        initRegister();

        return rootView;
    }
    public void init(View rootView){
        tvList=(TextView)rootView.findViewById(R.id.tvList);

    }
    public void initRegister(){
        tvList.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvList:
                ((TrackActivity)getActivity()).bloodcount();
                      break;


        }

    }
}
