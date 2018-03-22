package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.adapter.BloodCountListAdapter;
import com.innovellent.curight.adapter.TRACK_SPINNER_ADAPTER;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBloodCountDialog;
import com.innovellent.curight.model.BloodCount;
import com.innovellent.curight.model.BloodcountPojo;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.PROFILE;
import com.innovellent.curight.model.PROFILE_FEED;
import com.innovellent.curight.model.PostBodyProfile;
import com.innovellent.curight.model.ServerResponseBloodCount;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static android.content.ContentValues.TAG;
import static com.innovellent.curight.utility.Constants.CURIGHT_TAG;


public class BloodCountListFragment extends Fragment implements View.OnClickListener {

    RecyclerView recyclerView;
    BloodCountListAdapter mAdapter;
    Button btnAdd;
    EditText tvProfile,etCRP,tvntContactUs,tvFeedback,etHbA1c,etINR,etPlatelet,etProlactin,etRBC,etRF,etWBC;
    TextView tvList,txt_selectdateforbloodcount;
    AddBloodCountDialog addBloodCountDialog;
    ImageView ivAdd;
    RelativeLayout rlAddBloodCount;
    BloodCount bloodCount;
    ProgressDialog progressDialog;
    LinearLayout linear_datedialog;
    String bcid,anticep,crp,esr,haemoglobin,hbalc,inr,platelets,prolactin,rbc,rf,wbc,date;
    ScrollView svAddBloodCount;
    String USER_ID;
    ArrayList<PROFILE> spinnerList=new ArrayList<PROFILE>();
    TRACK_SPINNER_ADAPTER customSpinnerAdapter3;
    Spinner spUser;
    int uid;
    ArrayList<BloodCount> arrayList=new ArrayList<BloodCount >();
    private DatePickerDialog datePickerDialog;
    public BloodCountListFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_blood_count_list, container, false);


       // getbloodcountdata();
        initReferences(rootView);
        inClick();
        //getData();

        spinnerList.clear();
        getSpinnerData();

       /* final Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                txt_selectdateforbloodcount.setText(getContext().getString(R.string.date_formatted, year, month + 1, dayOfMonth));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));


        linear_datedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });*/

        return rootView;

    }

    /*public void getData(){
        *//*arrayList.add(new BloodCount("4","4","3","2","6","3","8","0","6","9","9"));
        arrayList.add(new BloodCount("8","8","3","2","7","3","8","2","6","8","3"));
        arrayList.add(new BloodCount("9","4","8","2","6","3","3","0","0","9","8"));
        arrayList.add(new BloodCount("1","0","3","2","6","8","8","0","6","9","3"));
        arrayList.add(new BloodCount("2","0","2","2","6","8","8","4","6","5","6"));*//*
        mAdapter=new BloodCountListAdapter(getActivity(),arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(mAdapter);
        rlAddBloodCount.setVisibility(View.GONE);


    }*/

    private void showProgressDialog(String title) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("please wait");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(true);
        progressDialog.show();
    }

    public void initReferences(View rootView) {
        tvList=(TextView) rootView.findViewById(R.id.tvList);
        ivAdd=(ImageView)rootView.findViewById(R.id.ivAdd);
        btnAdd = (Button)rootView.findViewById(R.id.btnAdd);
        rlAddBloodCount=(RelativeLayout)rootView.findViewById(R.id.rlAddBloodCount);
        txt_selectdateforbloodcount = (TextView)rootView.findViewById(R.id.txt_selectdateforbloodcount);
       // linear_datedialog = (LinearLayout)rootView.findViewById(R.id.linear_datedialog);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recycler_view);
        tvProfile = (EditText)rootView.findViewById(R.id.tvProfile);
        etCRP = (EditText)rootView.findViewById(R.id.etCRP);
        tvntContactUs =(EditText)rootView.findViewById(R.id.tvntContactUs);
        tvFeedback = (EditText)rootView.findViewById(R.id.tvFeedback);
        etHbA1c = (EditText)rootView.findViewById(R.id.etHbA1c);
        etINR = (EditText)rootView.findViewById(R.id.etINR);
        etPlatelet = (EditText)rootView.findViewById(R.id.etPlatelet);
        etProlactin = (EditText)rootView.findViewById(R.id.etProlactin);
        etRBC = (EditText)rootView.findViewById(R.id.etRBC);
        etRF = (EditText)rootView.findViewById(R.id.etRF);
        etWBC = (EditText)rootView.findViewById(R.id.etWBC);
        spUser = (Spinner) rootView.findViewById(R.id.spUser);



    }
    public void inClick(){
        tvList.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        tvList.setTextColor(Color.parseColor("#FFFFFF"));

    }

    private void AddBloodCountRecords() {
       addBloodCountDialog = new AddBloodCountDialog(getActivity(), new AddBloodCountDialog.AddBloodCountDialogClickListener() {
           @Override
           public void onSubmit(String Date,String Anticep, String Crp, String Esr, String Haemoglobin, String Hbalc, String Inr, String Platelest, String Prolactin, String Rbc, String Rf, String Wbc) {
               addBloodCountDialog.dismiss();
               showProgressDialog("Adding");
               addbloodcountdata(Date,Anticep,Crp,Esr,Haemoglobin,Hbalc,Inr,Platelest,Prolactin,Rbc,Rf,Wbc);
           }

           @Override
           public void onCancel() {
               addBloodCountDialog.dismiss();
           }
       });
        addBloodCountDialog.show();

    }


    private void getSpinnerData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
        PostBodyProfile postBodyprofile = new PostBodyProfile(uid, "family");
        Call<MyProfile_Response> call = reditapi.getProfile(postBodyprofile);

        call.enqueue(new Callback<MyProfile_Response>() {
            @Override
            public void onResponse(Call<MyProfile_Response> call, Response<MyProfile_Response> response) {


                if (response.body() != null) {


                    Log.e("", "profileResponse: code: " + response.body().getCode());

                    ArrayList<PROFILE_FEED> result = response.body().getResults();

                    Log.e("", "profileResponse: listsize: " + result.size());
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
                        USER_ID = result.get(i).getUserid();
                        //spinnerList.add(new PROFILE("","","",""));
                        spinnerList.add(new PROFILE(result.get(i).getUserid(),result.get(i).getId(), firstName, result.get(i).getAge(), result.get(i).getRelationship()));
                    }
                    getData2();
                    USER_ID = result.get(0).getUserid();
                    // GetData(result.get(1).getUserid());
                } else {

                    Toast.makeText(getActivity(), response.message(), Toast.LENGTH_SHORT).show();

                }

                //remainder_rclrvw.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MyProfile_Response> call, Throwable t) {

                Log.e("", "onFailure: Somethings went wrong" + t.getMessage());
                Toast.makeText(getActivity(), "Somethings went wrong", Toast.LENGTH_SHORT).show();

            }
        });
    }


    public void getData2() {

        customSpinnerAdapter3 = new TRACK_SPINNER_ADAPTER(getActivity(), spinnerList);
        spUser.setAdapter(customSpinnerAdapter3);
        spUser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //   Toast.makeText(PasswordRecoveryQuestionsActivity.this, spinner3[i], Toast.LENGTH_SHORT).show();
                //spAge.setText(spinnerList.get(i).getUser_age());
                USER_ID = spinnerList.get(i).getUser_id();
                Prefs.putLong("spinner_id", Long.parseLong(spinnerList.get(i).getUser_id()));
                uid = (int) Prefs.getLong("spinner_id",0);
                Log.d(TAG, "Myuserid on select" + uid);

                getbloodcountdata(uid);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                int uid = (int) Prefs.getLong("user_id",0);
                Log.d("user_forwhr", ""+uid);
                Log.d(TAG, "MyUSER_ID on spinner" + USER_ID);
                getbloodcountdata(uid);
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ivAdd:
                AddBloodCountRecords();
                /*tvList.setTextColor(Color.parseColor("#9DA1A0"));
                rlAddBloodCount.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);*/
                break;
            case R.id.tvList:
                tvList.setTextColor(Color.parseColor("#FFFFFF"));
                rlAddBloodCount.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                break;
            case R.id.btnAdd:
               // showProgressDialog("Adding");
                //addbloodcountdata(tvProfile.getText().toString().trim(),etCRP.getText().toString().trim(),tvntContactUs.getText().toString().trim(),tvFeedback.getText().toString().trim(),etHbA1c.getText().toString().trim(),etINR.getText().toString().trim(),etPlatelet.getText().toString().trim(),etProlactin.getText().toString().trim(),etRBC.getText().toString().trim(),etRF.getText().toString().trim(),etWBC.getText().toString().trim(),txt_selectdateforbloodcount.getText().toString());
                tvList.setTextColor(Color.parseColor("#FFFFFF"));
                rlAddBloodCount.setVisibility(View.GONE);
                mAdapter.notifyDataSetChanged();
                recyclerView.setVisibility(View.VISIBLE);

            default:
                break;

        }
    }


    private void addbloodcountdata(String Anticep, String Crp, String Esr,String Haemoglobin,String Hbalc,String Inr,String Platelest,String Prolactin,String Rbc,String Rf, String Wbc,String Date){



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try{

            JSONObject paramObject = new JSONObject();
            int uid = (int) Prefs.getLong("spinner_id",0);
                paramObject.put("userid", uid);
                paramObject.put("anticep", Anticep);
                paramObject.put("crp", Crp);
                paramObject.put("esr", Esr);
                paramObject.put("haemoglobin", Haemoglobin);
                paramObject.put("hba1c", Hbalc);
                paramObject.put("inr", Inr);
                paramObject.put("platelets", Platelest);
                paramObject.put("prolactin", Prolactin);
                paramObject.put("rbc", Rbc);
                paramObject.put("rf", Rf);
                paramObject.put("wbc", Wbc);
                paramObject.put("date", Date);

            Call<ServerResponseBloodCount<String>> call = apiInterface.addbloodcountrecord("abc", paramObject.toString());

            call.enqueue(new Callback<ServerResponseBloodCount<String>>() {
                @Override
                public void onResponse(Call<ServerResponseBloodCount<String>> call, Response<ServerResponseBloodCount<String>> response) {
                    if (getActivity() != null) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()){
                            ServerResponseBloodCount<String>  serverResponseBloodCount = response.body();
                            Log.d("addwhrdialog_response", serverResponseBloodCount.getResults());

                            if(serverResponseBloodCount.getResults().equals("Success")){
                                Toast.makeText(getActivity(), "Successfully Added", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                int uid = (int) Prefs.getLong("spinner_id",0);
                                getbloodcountdata(uid);
                                progressDialog.dismiss();
                            }else {
                                Toast.makeText(getActivity(), "please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponseBloodCount<String>> call, Throwable t) {
                    if (getActivity() != null) progressDialog.dismiss();
                    Log.d(CURIGHT_TAG, t.getMessage());
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private void getbloodcountdata(int user_id){
        cleardata();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        BloodcountPojo bloodcountPojo = new BloodcountPojo(user_id);

        final Call<ResponseBody> call = apiInterface.getbloodcountdata("abc", bloodcountPojo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                    try
                    {
                        String res_data = response.body().string();
                        Log.e("res_dataforbloodcount", res_data);

                        JSONObject jsonObject = new JSONObject(res_data);
                        String code = jsonObject.getString("Code");

                        JSONArray jsonArray = jsonObject.getJSONArray("Results");
                        for(int i=0; i<jsonArray.length(); i++) {

                            bloodCount = new BloodCount();
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            bcid = jsonObject1.getString("BCid");
                            anticep = jsonObject1.getString("anticep");
                            crp = jsonObject1.getString("crp");
                            esr = jsonObject1.getString("esr");
                            haemoglobin = jsonObject1.getString("haemoglobin");
                            hbalc = jsonObject1.getString("hba1c");
                            inr = jsonObject1.getString("inr");
                            platelets = jsonObject1.getString("platelets");
                            prolactin = jsonObject1.getString("prolactin");
                            rbc = jsonObject1.getString("rbc");
                            rf = jsonObject1.getString("rf");
                            wbc = jsonObject1.getString("wbc");
                            date =jsonObject1.getString("date");
                            Log.d(TAG,"bloodcountid ::"+bcid);
                            bloodCount.setBcid(bcid);
                            bloodCount.setAntiCPP(anticep);
                            bloodCount.setCRP(crp);
                            bloodCount.setESR(esr);
                            bloodCount.setHaemoglobin(haemoglobin);
                            bloodCount.setHbA1c(hbalc);
                            bloodCount.setINR(inr);
                            bloodCount.setPlatelet(platelets);
                            bloodCount.setProlactin(prolactin);
                            bloodCount.setRBC(rbc);
                            bloodCount.setRF(rf);
                            bloodCount.setWBC(wbc);
                            bloodCount.setDate(date);


                            arrayList.add(bloodCount);


                        }

                        if(getActivity()!=null) {
                            mAdapter = new BloodCountListAdapter(getActivity(), arrayList);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                            recyclerView.setAdapter(mAdapter);
                            rlAddBloodCount.setVisibility(View.GONE);
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }else{
                    recyclerView.removeAllViews();
                    Toast.makeText(getActivity(), "No Record Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (getActivity() != null) progressDialog.dismiss();
                Log.d(CURIGHT_TAG, t.getMessage());
            }
        });

    }

    private void cleardata(){

        mAdapter = new BloodCountListAdapter(getActivity(),arrayList);

        arrayList.clear();

        Log.d("bloodcountclear", ""+arrayList.size());
        mAdapter.notifyDataSetChanged();
    }
}




