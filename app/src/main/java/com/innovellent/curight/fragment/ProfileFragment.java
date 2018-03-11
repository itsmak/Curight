package com.innovellent.curight.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.SupportMapFragment;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.EditProfileActivity;
import com.innovellent.curight.activities.HomeActivity;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PROFILE_RESPONSE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.Profile_Response;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "CuRight";
//    private static final String BASE_URL = "http://13.59.209.135:8090/diagnosticAPI/webapi/";
    LinearLayout linearLayoutLottery;
    RecyclerView recyclerView, recyclerViewKids;
    ImageView ivEdit;
    TextView tv_locationtxt,tv_locationsymbl,tvTitle;
    TextView tvprofile_name,tv_patient_id,tvWeight,tvBloodPressure,tvBMI,tvCholestrol,tvHDL,tvLDL,tvMyHeight;
    Spinner spUser;
    ArrayList<PROFILE> spinnerList = new ArrayList<PROFILE>();
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter2;
    //String[] spinner1 = {"John", "Jobby", "Suresh", "Mahesh"};
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
        getSpinnerData();
        initOnClick();
        //getData();
        return rootView;

    }

    public void initReferences(View rootView) {
        ivEdit = (ImageView) rootView.findViewById(R.id.ivEdit);
        spUser = (Spinner) rootView.findViewById(R.id.spUser);
        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        tvTitle.setText("Profile");
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
        tvprofile_name = (TextView) rootView.findViewById(R.id.tvprofile_name);
        tv_patient_id = (TextView) rootView.findViewById(R.id.tv_patient_id);
        tvWeight = (TextView) rootView.findViewById(R.id.tvWeight);
        tvBloodPressure = (TextView) rootView.findViewById(R.id.tvBloodPressure);
        tvBMI = (TextView) rootView.findViewById(R.id.tvBMI);
        tvCholestrol = (TextView) rootView.findViewById(R.id.tvCholestrol);
        tvHDL = (TextView) rootView.findViewById(R.id.tvHDL);
        tvLDL = (TextView) rootView.findViewById(R.id.tvLDL);
        tvMyHeight = (TextView) rootView.findViewById(R.id.tvMyHeight);

    }


    public void initOnClick() {
        ivEdit.setOnClickListener(this);
    }
    public void getData()
    {
        customSpinnerAdapter2 = new PROFILE_SPINNER_ADAPTER(getActivity(), spinnerList);
        spUser.setAdapter(customSpinnerAdapter2);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                Prefs.putInt("family_id", spinnerList.get(i).getId());
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                Prefs.putString("relationship",spinnerList.get(i).getUser_relation());
                int uid = (int) Prefs.getLong("spinner_id",0);
                Log.d(TAG,"Shared_profile_spinnerid"+uid);
                getProfileData(uid);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
    private void getSpinnerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        int uid = (int) Prefs.getLong("user_id",0);
        Log.d(TAG,"Shared_profile_uid"+uid);
        ApiInterface reditapi = retrofit.create(ApiInterface.class);

        PostBodyProfile postBodyprofile = new PostBodyProfile(uid, "family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);

        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {

                if (response.body() != null) {

                   Log.e(TAG, "profilefragment_Response: code: " + response.body().getCode());
                    ArrayList<PROFILE_FEED> result = response.body().getResults();
                   Log.e(TAG, "profilefragment_Response: listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++) {
                        String name = result.get(i).getName();
                        String lastName = "";
                        String firstName= "";
                        if(name.split("\\w+").length>1){

                            //lastName = name.substring(name.lastIndexOf(" ")+1);
                            firstName = name.substring(0, name.lastIndexOf(' '));
                        }
                        else{
                            firstName = name;
                        }
                        Log.d(TAG,"firstname::"+firstName);
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), firstName, result.get(i).getAge(), result.get(i).getRelationship()));
                    }
                    getData();
                    int uid = (int) Prefs.getLong("spinner_id",0);
                    Log.d(TAG,"Shared_profile_uid"+uid);
                    getProfileData(uid);
                } else {

                    Toast.makeText(getActivity(), "No Data Found for the User", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

               Log.e(TAG, "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getProfileData(int userid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);

        PostBodyProfile postBodyprofile = new PostBodyProfile(userid, "individual");
        Call<Profile_Response> call = reditapi.getProfileIndividual(postBodyprofile);

        call.enqueue(new Callback<Profile_Response>(){

            @Override
            public void onResponse(Call<Profile_Response> call, Response<Profile_Response> response) {

                if (response.body() != null) {
                    Log.e(TAG, "profileResponse: code: " + response.body().getCode());
                    ArrayList<PROFILE_RESPONSE_FEED> result = response.body().getResults();
                    Log.e(TAG, "profileResponse: listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++) {

                       Log.e(TAG, "profileResponse: id: " + result.get(i).getId());

                        tvprofile_name.setText("Name: "+result.get(i).getName());
                        tv_patient_id.setText("Patient ID: "+result.get(i).getPatientid());
                        tvWeight.setText(result.get(i).getWeight());
                        tvBloodPressure.setText(result.get(i).getBloodpressure());
                        tvBMI.setText(String.valueOf(result.get(i).getBmi()));
                        tvCholestrol.setText(result.get(i).getWhr());
                        tvHDL.setText(result.get(i).getBloodsugar());
                        tvLDL.setText(String.valueOf(result.get(i).getHdl())+"-"+String.valueOf(result.get(i).getLdl()));
                        tvMyHeight.setText(result.get(i).getHeight());
                    }
                }else{
                    Toast.makeText(getActivity(), "No Data", Toast.LENGTH_SHORT).show();
                    tvprofile_name.setText("Name: ");
                    tv_patient_id.setText("Patient ID: ");
                    tvWeight.setText(" ");
                    tvBloodPressure.setText(" ");
                    tvBMI.setText(" ");
                    tvCholestrol.setText(" ");
                    tvHDL.setText(" ");
                    tvLDL.setText(" ");
                    tvMyHeight.setText(" ");
                }

            }
            @Override
            public void onFailure(Call<Profile_Response> call, Throwable t) {

                Log.e(TAG, "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

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


