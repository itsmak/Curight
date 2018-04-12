package com.innovellent.curight.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.REMAINDER_SPINNER_ADAPTER;
import com.innovellent.curight.adapter.TRACK_SPINNER_ADAPTER;
import com.innovellent.curight.adapter.VaccineAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.JSON_FEED;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.MyServer_Response;
import com.innovellent.curight.model.POST_CREATE_CLASS;
import com.innovellent.curight.model.POST_UPDATE_CLASS;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyClass;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.VACCINE_UPDATE_RESPONSE;
import com.innovellent.curight.model.Vaccine;
import com.innovellent.curight.model.VaccineList;
import com.innovellent.curight.model.VaccineReminderYearDialog;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 10/11/2017.
 */
public class VaccineFragment extends Fragment implements View.OnClickListener {
    //ImageView ivAdd1,ivAdd2,ivAdd3,ivBack;
    private static final String TAG = "CuRight";
 //   private static final String BASE_URL = "http://13.59.209.135:8090/diagnosticAPI/webapi/";
    RecyclerView remainder_rclrvw;
    VaccineAdapter mAdapter;
    TextView tv_locationtxt,tv_locationsymbl,tvTitle;
    Spinner spYear;
    EditText spAge;
    RelativeLayout rlDate;
    LinearLayoutManager layoutManager;
    VaccineReminderYearDialog vaccineReminderYearDialog;
    AddRemainder_FRAGMENT_DAILOG vaccineadddailog;
    String USER_ID;
    Context context;
    REMAINDER_SPINNER_ADAPTER customSpinnerAdapter3;
   //VaccineAddReminderDialog vaccineAddReminderDialog;
    ArrayList<Vaccine> arrayList = new ArrayList<Vaccine>();
    int position;
    ArrayList<PROFILE> spinnerList = new ArrayList<PROFILE>();
    //   private int mYear, mMonth, mDay;
    TextView tvDate;
    private ProgressDialog progressDialog;

    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
        Log.d(TAG,"calling spinner on attach ::");
        getSpinnerData();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_vaccine, container, false);
        init(rootView);
     //   Log.d(TAG,"calling spinner oncreate ::");

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }



    public void init(View rootview) {
        remainder_rclrvw = (RecyclerView) rootview.findViewById(R.id.remainder_rclrvw);
        spYear = (Spinner) rootview.findViewById(R.id.spYear);
        spAge = (EditText) rootview.findViewById(R.id.spAge);
        rlDate = (RelativeLayout) rootview.findViewById(R.id.date_layout);
        tvDate = (TextView) rootview.findViewById(R.id.tv_date);
        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        spAge.setEnabled(false);
        tvTitle.setText("Remainder");
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
        //   remainder_rclrvw.addOnItemTouchListener();

    }

    public void iniClick() {
        arrayList.add(new Vaccine("HPV1 ", "12-09-2017"));
        arrayList.add(new Vaccine("HPV11 ", "12-10-2017"));

        rlDate.setOnClickListener(this);
    }

    public void getData2() {

        customSpinnerAdapter3 = new REMAINDER_SPINNER_ADAPTER(getActivity(), spinnerList);
        spYear.setAdapter(customSpinnerAdapter3);
        spYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                spAge.setText(spinnerList.get(i).getUser_age());
                USER_ID = spinnerList.get(i).getUser_id();
                Log.d(TAG, "Myuserid on select" + USER_ID);
                GetData(USER_ID);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    @Override
    public void onClick(View v) {

    }
    private void getSpinnerData() {
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        clearSpinnerData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
        Log.d(TAG,"Shared_profile_uid"+uid);
        PostBodyProfile postBodyprofile = new PostBodyProfile(uid, "family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);
        progressDialog.dismiss();
        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {
                progressDialog.dismiss();

                if (response.body() != null) {

                    Log.e(TAG, "profileResponse: code: " + response.body().getCode());
                    ArrayList<PROFILE_FEED> result = response.body().getResults();
                    Log.e(TAG, "profileResponse: listsize: " + result.size());
                    for (int i = 0; i < result.size(); i++)
                    {
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
                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), firstName, result.get(i).getAge(), result.get(i).getRelationship(),result.get(i).getGender()));
                    }
                    getData2();
                    USER_ID = result.get(0).getUserid();
                    Log.d(TAG, "Myuserid default" + USER_ID);
                    GetData(USER_ID);
                } else {

                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();
                }
                remainder_rclrvw.setAdapter(mAdapter);
//                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void GetData(String user_id) {
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        clearData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //      Toast.makeText(getActivity(),"I am getting called",Toast.LENGTH_SHORT).show();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Log.d(TAG, "onResponse: userid" + user_id);
        PostBodyClass postBodyClass = new PostBodyClass(user_id);

        Call<MyServer_Response> call = reditapi.getData(postBodyClass);
        progressDialog.dismiss();
        call.enqueue(new Callback<MyServer_Response>() {
            @Override
            public void onResponse(Call<MyServer_Response> call, Response<MyServer_Response> response) {
                progressDialog.dismiss();
                if (response.body() != null) {

                    Log.d(TAG, "onResponse: Server Response: " + response);

                    ArrayList<JSON_FEED> result = response.body().getResults();
                    Log.d(TAG, "onResponse: code: " + result.size());
                    for (int i = 0; i < result.size(); i++) {
                        Log.d(TAG, "onResponse: age: " + result.get(i).getAgeinonth());
                        Log.d(TAG, "onResponse: Duration: " + result.get(i).getAge());
                        // arrayList.add(new Vaccine(result.get(i).getAge(),"","",true,""));
                        arrayList.add(new Vaccine(result.get(i).getAge(), "", "", "", "", "", result.get(i).getAgeinonth(), USER_ID, 0, 0, true));
                        ArrayList<VaccineList> vlist = result.get(i).getVaccines();
                        Log.d(TAG, "onResponse: each vaccinelist size " + vlist.size());
                        for (int j = 0; j < vlist.size(); j++) {

                            Log.d(TAG, "onResponse: each vaccinelist " + vlist.get(j).getVaccinechartid());
                            Log.d(TAG, "onResponse: each doctor " + vlist.get(j).getDoctorname());
                            Log.d(TAG, "onResponse: each comments " + vlist.get(j).getComments());
                            Log.d(TAG, "onResponse: each vaccine " + vlist.get(j).getVaccinename());
                            Log.d(TAG, "onResponse: each vacineactivityid" + vlist.get(j).getVaccineactivityid());
                            Log.d(TAG, "onResponse: each vacinechartid" + vlist.get(j).getVaccinechartid());
                            Log.d(TAG, "onResponse: each vacine userid" + vlist.get(j).getUserid());
                            Log.d(TAG, "onResponse: each  vaccine duedate" + vlist.get(j).getVaccineduedate());
                            Log.d(TAG, "onResponse: each  vaccine date" + vlist.get(j).getVaccinedate());

                            arrayList.add(new Vaccine
                                    ("", vlist.get(j).getVaccinename(), vlist.get(j).getVaccinedate(), vlist.get(j).getVaccineduedate(), vlist.get(j).getDoctorname(),
                                            vlist.get(j).getComments(), result.get(i).getAge(), String.valueOf(vlist.get(j).getUserid()), vlist.get(j).getVaccineactivityid(), vlist.get(j).getVaccinechartid(), false));

                            Log.d(TAG, "onResponse: each vaccinelist" + vlist.get(j).getVaccineactivityid());

                        }
                    }
                } else {

                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }
                mAdapter = new VaccineAdapter(getActivity(), arrayList, position, new VaccineAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(Vaccine item, int position) {
                        vaccineadddailog = new AddRemainder_FRAGMENT_DAILOG(getContext(), item, new AddRemainder_FRAGMENT_DAILOG.AddRemainder_FRAGMENT_DAILOGClickListener() {
                            @Override
                            public void onSubmit(Vaccine updatedItem, String remainder1, String remainder2) {
                                vaccineadddailog.dismiss();
                                addapical(updatedItem, remainder1, remainder2);
                            }
                        });
                        vaccineadddailog.show();
                    }

                    @Override
                    public void onItemClick_modify(Vaccine myitem, int position) {
                        vaccineReminderYearDialog = new VaccineReminderYearDialog(getContext(),myitem, new VaccineReminderYearDialog.VaccineReminderYearDialogClickListener() {
                            @Override
                            public void onSubmit(Vaccine myupdatedItem, String remainder1, String remainder2) {
                                vaccineReminderYearDialog.dismiss();
                                apical_modify(myupdatedItem,remainder1,remainder2);
                            }
                        });
                        vaccineReminderYearDialog.show();
                    }
                });

                remainder_rclrvw.setAdapter(mAdapter);
                mAdapter.notifyDataSetChanged();
                layoutManager = new LinearLayoutManager(getActivity());
                remainder_rclrvw.setLayoutManager(layoutManager);

            }

            @Override
            public void onFailure(Call<MyServer_Response> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }
    private void clearSpinnerData() {
        spinnerList.clear();
        customSpinnerAdapter3 = new REMAINDER_SPINNER_ADAPTER(getActivity(), spinnerList);

        customSpinnerAdapter3.notifyDataSetChanged();
    }
    public void clearData() {
        mAdapter = new VaccineAdapter(getActivity(), arrayList, position, new VaccineAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Vaccine item, int position) {

            }

            @Override
            public void onItemClick_modify(Vaccine myitem, int position) {

            }
        });
        arrayList.clear(); //clear list
        mAdapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.

    }

    private void addapical(Vaccine item, String REMAINDER1, String REMAINDER2) {
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        POST_CREATE_CLASS post_create_class = new POST_CREATE_CLASS(Integer.parseInt(item.getUserid()), item.getAgeinonth(), "0", item.getVaccinename(), "", "",
                item.getDate(), item.getComments(), item.getDoctorname(), REMAINDER1, REMAINDER2);
        Call<VACCINE_UPDATE_RESPONSE> call = reditapi.get_vaccine_create(post_create_class);
        progressDialog.dismiss();
        call.enqueue(new Callback<VACCINE_UPDATE_RESPONSE>() {

            @Override
            public void onResponse(Call<VACCINE_UPDATE_RESPONSE> call, Response<VACCINE_UPDATE_RESPONSE> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Successfully Created!", Toast.LENGTH_SHORT).show();

                    Log.d("Dialog", "remainderResponse: code: " + response.body());
                    Log.d("Dialog", "remainderReseponse: code: " + response.body().getCode());
                    Log.d("Dialog", "remainderResponse: result: " + response.body().getResults());
                    Log.d("Dialog", "remainderResponse: userid: " + USER_ID);
                    //   Intent fkint = new Intent(context, HomeActivity.class);
                    GetData(USER_ID);
                }
            }

            @Override
            public void onFailure(Call<VACCINE_UPDATE_RESPONSE> call, Throwable t) {
                Toast.makeText(getContext(), "Somethings went wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });

    }

    private void apical_modify(Vaccine item,String REMAINDER1,String REMAINDER2) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);

        POST_UPDATE_CLASS post_update_class = new POST_UPDATE_CLASS(Integer.parseInt(item.getUserid()), item.getVaccineactivityid(),item.getVaccinechartid(),item.getDate(),item.getComments(),item.getDoctorname(),REMAINDER1,
                REMAINDER2);
        Call<VACCINE_UPDATE_RESPONSE> call = reditapi.get_vaccine_update(post_update_class);
        call.enqueue(new Callback<VACCINE_UPDATE_RESPONSE>() {

            @Override
            public void onResponse(Call<VACCINE_UPDATE_RESPONSE> call, Response<VACCINE_UPDATE_RESPONSE> response) {

                if (response.isSuccessful()){
                    Toast.makeText(getActivity(),"Successfully Updated!",Toast.LENGTH_SHORT).show();

                    Log.d("Dialog", "remainderResponse: code: " +response.body());
                    Log.d("Dialog", "remainderReseponse: code: " +response.body().getCode());
                    Log.d("Dialog", "remainderResponse: result: " +response.body().getResults());
                   GetData(USER_ID);
                }
            }

            @Override
            public void onFailure(Call<VACCINE_UPDATE_RESPONSE> call, Throwable t) {
                Toast.makeText(getActivity(),"Somethings went wrong",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
