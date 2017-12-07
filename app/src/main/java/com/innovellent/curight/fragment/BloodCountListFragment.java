package com.innovellent.curight.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.activities.TrackActivity;
import com.innovellent.curight.adapter.BloodCountListAdapter;
import com.innovellent.curight.model.AddBloodCountDialog;
import com.innovellent.curight.model.BloodCount;


import java.util.ArrayList;


public class BloodCountListFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    BloodCountListAdapter mAdapter;
    TextView tvList;
    AddBloodCountDialog addBloodCountDialog;
    ImageView ivAdd;
    RelativeLayout rlAddBloodCount;
    ScrollView svAddBloodCount;
    ArrayList<BloodCount> arrayList=new ArrayList<BloodCount >();
    public BloodCountListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blood_count_list, container, false);
        initReferences(rootView);
        inClick();
        getData();
        return rootView;

    }

    public void getData(){
        arrayList.add(new BloodCount("4","4","3","2","6","3","8","0","6","9","9"));
        arrayList.add(new BloodCount("8","8","3","2","7","3","8","2","6","8","3"));
        arrayList.add(new BloodCount("9","4","8","2","6","3","3","0","0","9","8"));
        arrayList.add(new BloodCount("1","0","3","2","6","8","8","0","6","9","3"));
        arrayList.add(new BloodCount("2","0","2","2","6","8","8","4","6","5","6"));
        mAdapter=new BloodCountListAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        rlAddBloodCount.setVisibility(View.GONE);


    }

    public void initReferences(View rootView) {
        tvList=(TextView) rootView.findViewById(R.id.tvList);
        ivAdd=(ImageView)rootView.findViewById(R.id.ivAdd);
        rlAddBloodCount=(RelativeLayout)rootView.findViewById(R.id.rlAddBloodCount);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);

    }
    public void inClick(){
        tvList.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        tvList.setTextColor(Color.parseColor("#FFFFFF"));

    }

    private void AddBloodCountRecords() {
        addBloodCountDialog = new AddBloodCountDialog(getActivity(), new AddBloodCountDialog.AddBloodCountDialogClickListener(){


            @Override
            public void onSubmit() {
                addBloodCountDialog.dismiss();
            }

            @Override
            public void onCancel() {
                addBloodCountDialog.dismiss();
            }
        });

        addBloodCountDialog.show();


    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivAdd:
                tvList.setTextColor(Color.parseColor("#9DA1A0"));
                rlAddBloodCount.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                break;
            case R.id.tvList:
                tvList.setTextColor(Color.parseColor("#FFFFFF"));
                rlAddBloodCount.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            default:
                break;

        }
    }
}




