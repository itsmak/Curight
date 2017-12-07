package com.innovellent.curight.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.TrackAdapter;

import java.util.ArrayList;

/**
 * Created by mancha on 8/16/2017.
 */

public class TrackFragment extends Fragment {
    RecyclerView recycler_view;
    ArrayList<String> arrayList=new ArrayList<String>();
    TrackAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.track_fragment,container,false);
         init(rootView);
        iniClick();
        getData();
        return rootView;
    }
    public void init(View rootview){
        recycler_view=(RecyclerView)rootview.findViewById(R.id.recycler_view);
    }
    public void iniClick(){
        arrayList.add("BMI");
        arrayList.add("WHR");
        arrayList.add("BF");
        arrayList.add("LIPID");
        arrayList.add("BP");
        arrayList.add("BLOOD SUGAR");
        arrayList.add("HOSPITAL");
        arrayList.add("FEMALE CYCLE TRACK");
        arrayList.add("THYROID");
        arrayList.add("BLOOD CELL");
        arrayList.add("BLLOD COUNT");
        arrayList.add("VITAMIN PANEL");
        arrayList.add("WHR");
    }
    public void getData(){

        mAdapter=new TrackAdapter(getActivity(),arrayList);
        recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);

    }


}
