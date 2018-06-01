package com.innovellent.curight.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.PayUmoney.PayuWebviewActivity;
import com.innovellent.curight.R;
import com.innovellent.curight.activities.Exercise.Add_CalBurned_Exersize;
import com.innovellent.curight.adapter.PaymentDetailsAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.AddDiscountDialog;
import com.innovellent.curight.model.Apply_Coupon_Pojo;
import com.innovellent.curight.model.BookedTest;
import com.innovellent.curight.model.CouponRes;
import com.innovellent.curight.model.Login;
import com.innovellent.curight.model.PaymentDetails;
import com.innovellent.curight.model.ServerResponseBookedTest;
import com.innovellent.curight.model.ServerResponseCoupon;
import com.innovellent.curight.model.TestBookingCreate;
import com.innovellent.curight.model.TestBookingDetail;
import com.innovellent.curight.utility.Config;
import com.innovellent.curight.utility.Util;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pramod on 08-11-2017.
 */

public class PaymentDetailsActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = ".Curight";
    RecyclerView recycler_view,recycler_view1;
    PaymentDetailsAdapter mAdapter;
    ImageView ivBack,ivOnline,ivCash;
    //Spinner spCode;
    TextView tvOnline,tvCash,tvCode,tvAddress;
    Button btnSubmit;
    //RelativeLayout llEntercode;
    AddDiscountDialog addDiscountDialog;
    ArrayList<PaymentDetails> arraypaymentList=new ArrayList<PaymentDetails>();
    RelativeLayout rlDiscountCode;
    Toolbar toolbar;
    String[]spinner1={"code"};
    TextView tvParentNamelabel,tvPhoneNo,tvEmailID,tvPayment,tvPayableAmount;
    ArrayList<CouponRes> resultarray;
    private ProgressDialog progressDialog;
    private Long test_booking_id;
    private String user_id,email,name,mobile,loc;
    private Long uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);
        Bundle bundle = getIntent().getExtras();
        if (bundle!=null) {
            test_booking_id = bundle.getLong("test_booking_id");
            user_id = bundle.getString("user_id");
            uid = bundle.getLong("uid");
            email = bundle.getString("email");
            name = bundle.getString("name");
            //if (Util.isEmpty(uid+"") || Util.isEmpty(email) || Util.isEmpty(name)) {
//                SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
//                uid = sharedPreferences.getLong("user_id",0L);
//                email = sharedPreferences.getString("email","");
//                mobile = sharedPreferences.getString("mobile","");
//                loc = sharedPreferences.getString("location","");
//                name = sharedPreferences.getString("user_name","");
//                Log.d(TAG,"Payment details email::"+email);
//                Log.d(TAG,"Payment details mobile::"+mobile);
//                Log.d(TAG,"Payment details loc::"+loc);
            //}
        }
        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        uid = sharedPreferences.getLong("user_id",0L);
        email = sharedPreferences.getString("email","");
        mobile = sharedPreferences.getString("mobile","");
        loc = sharedPreferences.getString("location","");
        name = sharedPreferences.getString("user_name","");
        Log.d(TAG,"Payment details email::"+email);
        Log.d(TAG,"Payment details mobile::"+mobile);
        Log.d(TAG,"Payment details loc::"+loc);
        init();
        iniClick();
        getData();

    }

    public void init(){
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
       // ivBack=(ImageView) findViewById(R.id.ivBack);
        recycler_view=(RecyclerView)findViewById(R.id.recycler_view);
        btnSubmit=(Button)findViewById(R.id.btnSubmit);
        ivOnline=(ImageView)findViewById(R.id.ivOnline);
        ivCash=(ImageView)findViewById(R.id.ivCash);
        tvCash=(TextView)findViewById(R.id.tvCash);
        tvCode=(TextView)findViewById(R.id.tvCode);
        tvOnline=(TextView)findViewById(R.id.tvOnline);
        rlDiscountCode=(RelativeLayout)findViewById(R.id.rlDiscountCode);

        tvParentNamelabel = (TextView) findViewById(R.id.tvParentNamelabel);
        tvPhoneNo = (TextView) findViewById(R.id.tvPhoneNo);
        tvEmailID = (TextView) findViewById(R.id.tvEmailID);
        tvAddress=(TextView)findViewById(R.id.tvAddress);
        tvPayment=(TextView)findViewById(R.id.tvPayment);
        tvPayableAmount=(TextView)findViewById(R.id.tvPaymentAmount);

        SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
        long total_amount = sharedPreferences.getLong("total_amount",0L);

        tvPayableAmount.setText(""+total_amount);
        tvPayment.setText(""+total_amount);
        tvParentNamelabel.setText(name);
        tvPhoneNo.setText(mobile);
        tvEmailID.setText(email);
        tvAddress.setText(loc);
    }
    public void iniClick(){
    //    ivBack.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        ivCash.setOnClickListener(this);
        ivOnline.setOnClickListener(this);
        rlDiscountCode.setOnClickListener(this);
        /*arraypaymentList.add(new PaymentDetails("X-RAY","2000"));
        arraypaymentList.add(new PaymentDetails("CT Scan","5000"));
        arraypaymentList.add(new PaymentDetails("Egg Scan","1200"));
        arraypaymentList.add(new PaymentDetails("Eye Scan","4200"));*/

    }

    public void getData(){

       mAdapter=new PaymentDetailsAdapter(PaymentDetailsActivity.this,arraypaymentList);
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        recycler_view.setAdapter(mAdapter);

    }

     public void setColor(){
         ivCash.setImageResource(R.mipmap.ic_cashgray);
         tvCash.setTextColor(Color.parseColor("#9DA1A0"));
         ivOnline.setImageResource(R.mipmap.ic_onlinegray);
         tvOnline.setTextColor(Color.parseColor("#9DA1A0"));

     }
    private void AddDiscount() {
        addDiscountDialog = new AddDiscountDialog(PaymentDetailsActivity.this, new AddDiscountDialog.AddDiscountDialogClickListener(){


            @Override
            public void onSubmit(String code) {
                apply_couponCode(code);
               // tvCode.setText(code);
               // addDiscountDialog.dismiss();
            }

            @Override
            public void onCancel() {
                addDiscountDialog.dismiss();
            }
        });

        addDiscountDialog.show();


    }

    private void apply_couponCode(String code) {
        progressDialog = ProgressDialog.show(PaymentDetailsActivity.this, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);
        int uid = (int) Prefs.getLong("user_id",0);
        Log.d(TAG,"Shared_profile_uid"+uid);
        Log.d(TAG,"payment coupon : input1"+code);
        Log.d(TAG,"payment coupon : input2"+uid);
        Apply_Coupon_Pojo coupon_pojo = new Apply_Coupon_Pojo(code,uid);
        Call<ServerResponseCoupon> call = apiInterface.applycouponcode(coupon_pojo);
        call.enqueue(new Callback<ServerResponseCoupon>() {
            @Override
            public void onResponse(Call<ServerResponseCoupon> call, Response<ServerResponseCoupon> response) {
                Log.e(TAG, "payment coupon: response: " + response.body());
                progressDialog.dismiss();
                if (response.body() != null) {
                    Log.e(TAG, "payment coupon: code: " + response.body().getCode());
                    if(response.body().getCode()==200){
                        resultarray = response.body().getResults();
                        Log.e(TAG, "payment coupon: discount: " + resultarray.get(0).getStatus());
                        if(resultarray.get(0).getStatus().equalsIgnoreCase("Expired")){
                            Toast.makeText(PaymentDetailsActivity.this, "Sorry This Coupon is Expired", Toast.LENGTH_SHORT).show();
                            addDiscountDialog.dismiss();
                        }else {
                            double discount_val = Double.parseDouble((resultarray.get(0).getDiscount()).substring(0,2));
                            double amount_before = Double.parseDouble(tvPayableAmount.getText().toString());
                            double discount = (amount_before * (discount_val/100));
                            double discounted_amount = amount_before - discount;
                            Log.d(TAG,"payment coupon discount val 3:"+discount);
                            Log.d(TAG,"payment coupon discount val 4:"+discounted_amount);
                            tvPayableAmount.setText(String.valueOf(discounted_amount));
                            Toast.makeText(PaymentDetailsActivity.this, "Coupon Successfully Applied", Toast.LENGTH_SHORT).show();
                            addDiscountDialog.dismiss();
                        }
                    }else {
                        Toast.makeText(PaymentDetailsActivity.this, "Sorry This Coupon is doesn't exists", Toast.LENGTH_SHORT).show();
                        addDiscountDialog.dismiss();
                    }
                }else {
                    Toast.makeText(PaymentDetailsActivity.this, "Sorry This Coupon is doesn't exists", Toast.LENGTH_SHORT).show();
                    addDiscountDialog.dismiss();
                }
            }
            @Override
            public void onFailure(Call<ServerResponseCoupon> call, Throwable t) {
                progressDialog.dismiss();
                t.getMessage();
                String message = t.getMessage();
                Log.e("TAG","error :: "+message);
                Toast.makeText(PaymentDetailsActivity.this, "Internal Error. ", Toast.LENGTH_SHORT).show();
                addDiscountDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {

            case R.id.btnSubmit:
                progressDialog = ProgressDialog.show(PaymentDetailsActivity.this, "Loading", "please wait", true, false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(new Config().SERVER_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                ApiInterface apiInterface = retrofit.create(ApiInterface.class);

                SharedPreferences sharedPreferences = getSharedPreferences("mypref", Context.MODE_PRIVATE);
                String x_access_token = sharedPreferences.getString("access_token","");

                TestBookingDetail testBookingDetail = new TestBookingDetail(test_booking_id);

                Call<ServerResponseBookedTest> call = apiInterface.testBookDetail(x_access_token,testBookingDetail);

                Log.e("PAYMENT","Request URL :: "+call.request().url());

                call.enqueue(new Callback<ServerResponseBookedTest>() {
                    @Override
                    public void onResponse(Call<ServerResponseBookedTest> call, Response<ServerResponseBookedTest> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()) {
                            Log.e("Payment","Server Response ::  "+response.body());
                            ArrayList<BookedTest> bookedTests = response.body().getResults();
                            for (int i = 0;i < bookedTests.size(); i ++) {
                                long tms_id = bookedTests.get(i).getTestbookingmasterid();
                                Log.e("PAID","Master ID :: "+tms_id);
                            }
//                            Intent intent = new Intent(PaymentDetailsActivity.this, AmountPaidActivity.class);
//                            Bundle bundle = new Bundle();
//                            bundle.putString("user_id", user_id);
//                            bundle.putString("mobile", mobile);
//
//                           // bundle.putLong("uid", uid);
//                            bundle.putString("name", name);
//                            bundle.putString("email", email);
//                            intent.putExtras(bundle);
//                            startActivity(intent);
                            Intent pay_intent = new Intent(PaymentDetailsActivity.this, PayuWebviewActivity.class);
                            startActivity(pay_intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<ServerResponseBookedTest> call, Throwable t) {
                        progressDialog.dismiss();
                    }
                });


                /*Intent i1=new Intent(PaymentDetailsActivity.this,AmountPaidActivity.class);
                Bundle bundle = getIntent().getExtras();
                String user_id = "",token = "";
                if (bundle!=null) {
                    user_id = bundle.getString("user_id");
                    token = bundle.getString("token");
                }
                Log.e("PAY","User Id :: "+user_id+ " $$ Token $$ "+token);
                i1.putExtras(bundle);
                startActivity(i1);*/
                break;
            case R.id.ivCash:
                setColor();
                ivCash.setImageResource(R.mipmap.ic_cashblue);
                tvCash.setTextColor(Color.parseColor("#0075b2"));
                break;
            case R.id.ivOnline:
                setColor();
                ivOnline.setImageResource(R.mipmap.ic_onlineblue);
                tvOnline.setTextColor(Color.parseColor("#0075b2"));

                break;
            case R.id.rlDiscountCode:
                AddDiscount();
                break;



        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
