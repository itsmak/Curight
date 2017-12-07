package com.innovellent.curight.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.EmergencyContactAdapter;
import com.innovellent.curight.model.BloodPressure;

import java.util.ArrayList;

public class EmergencyContactFragment extends Fragment implements View.OnClickListener{

    LinearLayout linearLayoutLottery;
    ArrayList<BloodPressure> arrayList=new ArrayList<BloodPressure>();
    RecyclerView recyclerView;
    EditText etContactNo,etName;
    Button btnAdd;
    EmergencyContactAdapter mAdapter;
    private SharedPreferences sharedPreferences;

    public EmergencyContactFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_emergency_contact, container, false);
        initReferences(rootView);
        initOnClick(rootView);

        return rootView;

    }

    public void initReferences(View rootView) {
        btnAdd=(Button)rootView.findViewById(R.id.btnAdd);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        mAdapter=new EmergencyContactAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);


    }


    public void initOnClick(View v) {
        etContactNo=(EditText)v.findViewById(R.id.etContactNo);
        etName=(EditText)v.findViewById(R.id.etName);
        btnAdd.setOnClickListener((View.OnClickListener) getActivity());
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.btnAdd:
               arrayList.add(new BloodPressure(etName.getText().toString(),etContactNo.getText().toString()));
                break;

        }
    }



}





