package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.CustomSpinnerAdapter1;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.PROFILE_SPINNER_ADAPTER;
import com.innovellent.curight.adapter.VaccineAdapter;
import com.innovellent.curight.adapter.VaccineAdapter1;
import com.innovellent.curight.adapter.VaccineAdapter2;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.JSON_FEED;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.MyServer_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyClass;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.Vaccine;
import com.innovellent.curight.model.VaccineAddReminderDialog;
import com.innovellent.curight.model.VaccineList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 10/11/2017.
 */

public class VaccineFragment extends Fragment implements View.OnClickListener {
    RecyclerView remainder_rclrvw;
    VaccineAdapter mAdapter;

    Spinner spYear;
    EditText spAge;
    RelativeLayout rlDate;
//    ImageView ivAdd1,ivAdd2,ivAdd3,ivBack;
    private static final String TAG = ".Retro_MainActivity";
    private static final String BASE_URL ="http://13.59.209.135:8090/diagnosticAPI/webapi/";
    String USER_ID;
    PROFILE_SPINNER_ADAPTER customSpinnerAdapter3;
    VaccineAddReminderDialog vaccineAddReminderDialog;
    ArrayList<Vaccine> arrayList=new ArrayList<Vaccine>();
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();


 //   private int mYear, mMonth, mDay;
    TextView tvDate;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_vaccine,container,false);
        init(rootView);
//     iniClick();
//     GetData(USER_ID);
//     getData2();
        getSpinnerData();
        return rootView;
    }
    public void init(View rootview){
        remainder_rclrvw=(RecyclerView)rootview.findViewById(R.id.remainder_rclrvw);
        spYear=(Spinner)rootview.findViewById(R.id.spYear);
        spAge=(EditText)rootview.findViewById(R.id.spAge);
        rlDate=(RelativeLayout)rootview.findViewById(R.id.date_layout);
        tvDate=(TextView)rootview.findViewById(R.id.tv_date);
        spAge.setEnabled(false);
    }
    public void iniClick(){
        arrayList.add(new Vaccine("HPV1 ","12-09-2017"));
        arrayList.add(new Vaccine("HPV11 ","12-10-2017"));

        rlDate.setOnClickListener(this);
    }


    public void getData2() {


        customSpinnerAdapter3 = new PROFILE_SPINNER_ADAPTER(getActivity(), spinnerList);
        spYear.setAdapter(customSpinnerAdapter3);
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                spAge.setText(spinnerList.get(i).getUser_age());
                GetData(spinnerList.get(i).getUser_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    private void addVaccine() {
        vaccineAddReminderDialog = new VaccineAddReminderDialog(getActivity(), new VaccineAddReminderDialog.VaccineAddReminderDialogClickListener(){


            @Override
            public void onSubmit() {
                vaccineAddReminderDialog.dismiss();
            }
        });

        vaccineAddReminderDialog.show();


    }

    @Override
    public void onClick(View v) {

    }
    private void getSpinnerData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);

        PostBodyProfile postBodyprofile = new PostBodyProfile(1,"family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);

        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {


                if(response.body()!=null) {


                    Log.e(TAG, "profileResponse: code: " + response.body().getCode());
                    ArrayList<PROFILE_FEED> result = response.body().getResults();
                    Log.e(TAG, "profileResponse: listsize: " +result.size());
                    for(int i=0;i<result.size();i++)
                    {

                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getName(),result.get(i).getAge(),result.get(i).getRelationship()));
                    }
                    getData2();
                    GetData(result.get(1).getUserid());
                }else{

                    Toast.makeText(getActivity(), response.message(),Toast.LENGTH_SHORT).show();

                }

                remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e(TAG,"onFailure: Somethings went wrong"+t.getMessage());
                Toast.makeText(getActivity(),"Somethings went wrong",Toast.LENGTH_SHORT).show();

            }
        });
    }
    private void GetData(String user_id){
        clearData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
  //      Toast.makeText(getActivity(),"I am getting called",Toast.LENGTH_SHORT).show();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);

        PostBodyClass postBodyClass = new PostBodyClass(user_id);

        Call<MyServer_Response> call = reditapi.getData(postBodyClass);
        call.enqueue(new Callback<MyServer_Response>() {
            @Override
            public void onResponse(Call<MyServer_Response> call, Response<MyServer_Response> response) {


                if(response.body()!=null) {

                    Log.d(TAG, "onResponse: Server Response: " + response);

                    ArrayList<JSON_FEED> result = response.body().getResults();
                    Log.d(TAG, "onResponse: code: " +result.size());
                    for(int i=0;i<result.size();i++)
                    {
                        Log.d(TAG, "onResponse: age: " + result.get(i).getAgeinonth());

                       // arrayList.add(new Vaccine(result.get(i).getAge(),"","",true,""));
                        arrayList.add(new Vaccine(result.get(i).getAge(),"","","","","","","","",true));
                        ArrayList<VaccineList> vlist = result.get(i).getVaccines();
                        Log.d(TAG, "onResponse: each vaccinelist size " +vlist.size());
                        for(int j=0;j<vlist.size();j++)
                        {

                            Log.d(TAG, "onResponse: each vaccinelist " +vlist.get(j).getVaccinechartid());
                            Log.d(TAG, "onResponse: each doctor " +vlist.get(j).getDoctorname());
                            Log.d(TAG, "onResponse: each comments " +vlist.get(j).getComments());
                            Log.d(TAG, "onResponse: each vacineid" +vlist.get(j).getVaccineactivityid());
                            Log.d(TAG, "onResponse: each vacineid" +vlist.get(j).getUserid());
                            arrayList.add(new Vaccine
                                    ("",vlist.get(j).getVaccinename(),vlist.get(j).getVaccinedate(),vlist.get(j).getVaccineduedate(),vlist.get(j).getDoctorname(),
                                            vlist.get(j).getComments(),result.get(i).getAge(),"","",false));

                            Log.d(TAG, "onResponse: each vaccinelist" +vlist.get(j).getVaccineactivityid());

                        }
                    }
                }else{

                    Toast.makeText(getActivity(), response.message(),Toast.LENGTH_SHORT).show();

                }
                mAdapter=new VaccineAdapter(getActivity(),arrayList);
                remainder_rclrvw.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyServer_Response> call, Throwable t) {

                Log.e(TAG,"onFailure: Somethings went wrong"+t.getMessage());
                Toast.makeText(getActivity(),"Somethings went wrong",Toast.LENGTH_SHORT).show();

            }
        });


    }
    public void clearData() {
        mAdapter = new VaccineAdapter(getActivity(),arrayList);
        arrayList.clear(); //clear list
        mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.

    }
}
