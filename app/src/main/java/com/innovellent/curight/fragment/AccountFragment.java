package com.innovellent.curight.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.ProfileActivity;


public class AccountFragment extends Fragment implements
        View.OnClickListener{

    Double longi,lati;
    LinearLayout llprofile,llLayout2,llLayout3,llLayout4,llLayout41,llLayout5;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.account_fragment,container,false);
        init(rootView);
        initRegister();

        return rootView;
    }
    public void init(View rootView){
        llprofile=(LinearLayout)rootView.findViewById(R.id.llprofile);
        llLayout2=(LinearLayout)rootView.findViewById(R.id.llLayout2);
        llLayout3=(LinearLayout)rootView.findViewById(R.id.llLayout3);
        llLayout4=(LinearLayout)rootView.findViewById(R.id.llLayout4);
        llLayout41=(LinearLayout)rootView.findViewById(R.id.llLayout41);
        llLayout5=(LinearLayout)rootView.findViewById(R.id.llLayout5);

    }
    public void initRegister(){
        llprofile.setOnClickListener(this);
        llLayout2.setOnClickListener(this);
        llLayout3.setOnClickListener(this);
        llLayout4.setOnClickListener(this);
        llLayout41.setOnClickListener(this);
        llLayout5.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llprofile:
                Intent i=new Intent(getActivity(), ProfileActivity.class);
                startActivity(i);

              /*  Fragment fragment2 = new ProfileFragment();
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.content_id, fragment2);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();
                */
                break;


        }

    }
}
