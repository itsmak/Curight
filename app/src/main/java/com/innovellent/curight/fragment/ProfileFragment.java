package com.innovellent.curight.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.google.android.gms.maps.SupportMapFragment;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.EditProfileActivity;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    LinearLayout linearLayoutLottery;
    RecyclerView recyclerView, recyclerViewKids;
    ImageView ivEdit;
    Spinner spUser;
    String[] spinner1 = {"John", "Jobby", "Suresh", "Mahesh"};
    Button btnLottery, btnDistibutor, btnEmployee, btnSweets, btnOffice, farm_btn, btnBank, home_btn, other_btn, self_btn;
    private SharedPreferences sharedPreferences;

    public ProfileFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        initReferences(rootView);
        initOnClick();
        getData();
        return rootView;

    }

    public void initReferences(View rootView) {
        ivEdit = (ImageView) rootView.findViewById(R.id.ivEdit);
        spUser = (Spinner) rootView.findViewById(R.id.spUser);
    }


    public void initOnClick() {
        ivEdit.setOnClickListener(this);
    }

    public void getData() {

        CustomSpinnerAdapter2 customSpinnerAdapter3 = new CustomSpinnerAdapter2(getActivity(), spinner1);
        spUser.setAdapter(customSpinnerAdapter3);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivEdit:
                Intent intent1 = new Intent(getActivity(), EditProfileActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                break;
           default:
                break;

        }
    }


}


