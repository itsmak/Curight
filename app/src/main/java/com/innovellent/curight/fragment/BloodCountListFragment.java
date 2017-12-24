package com.innovellent.curight.fragment;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.activities.TrackActivity;
import com.innovellent.curight.adapter.BloodCountListAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddBloodCountDialog;
import com.innovellent.curight.model.BloodCount;
import com.innovellent.curight.model.BloodcountPojo;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.ServerResponseBloodCount;
import com.innovellent.curight.utility.Config;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
    private DatePickerDialog datePickerDialog;

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


        getbloodcountdata();
        initReferences(rootView);
        inClick();
        //getData();

        final Calendar calendar = Calendar.getInstance();

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
        });

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
        linear_datedialog = (LinearLayout)rootView.findViewById(R.id.linear_datedialog);
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



    }
    public void inClick(){
        tvList.setOnClickListener(this);
        ivAdd.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
        tvList.setTextColor(Color.parseColor("#FFFFFF"));

    }

   /* private void AddBloodCountRecords() {
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


    }*/
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
            case R.id.btnAdd:
                showProgressDialog("Adding");
                addbloodcountdata(tvProfile.getText().toString().trim(),etCRP.getText().toString().trim(),tvntContactUs.getText().toString().trim(),tvFeedback.getText().toString().trim(),etHbA1c.getText().toString().trim(),etINR.getText().toString().trim(),etPlatelet.getText().toString().trim(),etProlactin.getText().toString().trim(),etRBC.getText().toString().trim(),etRF.getText().toString().trim(),etWBC.getText().toString().trim(),txt_selectdateforbloodcount.getText().toString());
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
                paramObject.put("userid", 1);
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
                                getbloodcountdata();
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


    private void getbloodcountdata(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        BloodcountPojo bloodcountPojo = new BloodcountPojo(1);

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

                           // bloodCount.setBcid(bcid);
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

                        mAdapter=new BloodCountListAdapter(getActivity(),arrayList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                        recyclerView.setAdapter(mAdapter);
                        rlAddBloodCount.setVisibility(View.GONE);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }
}




