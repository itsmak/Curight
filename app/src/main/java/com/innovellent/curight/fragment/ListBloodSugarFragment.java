package com.innovellent.curight.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BloodPressureAdapter;
import com.innovellent.curight.model.BloodPressure;

import java.util.ArrayList;
public class ListBloodSugarFragment extends Fragment implements View.OnClickListener{


    ArrayList<BloodPressure> arrayList=new ArrayList<BloodPressure>();
    RecyclerView recyclerView,recyclerViewKids;
    BloodPressureAdapter mAdapter;
    private SharedPreferences sharedPreferences;

    public ListBloodSugarFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list_blood_sugar, container, false);
        initReferences(rootView);
        initOnClick();
        arrayList.add(new BloodPressure("197/23","92"));
        arrayList.add(new BloodPressure("127/53","42"));
        arrayList.add(new BloodPressure("177/63","81"));
        arrayList.add(new BloodPressure("183/12","55"));
        arrayList.add(new BloodPressure("162/33","64"));
        arrayList.add(new BloodPressure("132/33","53"));
        arrayList.add(new BloodPressure("163/33","63"));
        arrayList.add(new BloodPressure("152/63","91"));
        arrayList.add(new BloodPressure("122/23","32"));
        arrayList.add(new BloodPressure("162/73","61"));

        return rootView;

    }

    public void initReferences(View rootView) {
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        mAdapter=new BloodPressureAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);


    }


    public void initOnClick() {
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            default:
                break;

        }
    }



}




