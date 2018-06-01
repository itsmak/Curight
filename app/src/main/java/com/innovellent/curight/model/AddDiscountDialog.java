package com.innovellent.curight.model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.innovellent.curight.R;
import com.innovellent.curight.activities.PaymentDetailsActivity;
import com.innovellent.curight.adapter.CustomCouponrAdapter;
import com.innovellent.curight.adapter.CustomSpinnerAdapter2;
import com.innovellent.curight.adapter.PaymentDetailsAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.utility.Config;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 10/12/2017.
 */

public class AddDiscountDialog extends Dialog {

    private static final String TAG = ".Curight";
    public AddDiscountDialog.AddDiscountDialogClickListener listener;
    Context context;
    Spinner spCode;
    EditText etCode;
    String strcode;
    NumberPicker npDays;
    String[]spinner1;
    ArrayList<CouponRes> resultarray;
    CustomCouponrAdapter customSpinnerAdapter3;
    private ProgressDialog progressDialog;

    public AddDiscountDialog(Context context, AddDiscountDialog.AddDiscountDialogClickListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_discount_code);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        spCode=(Spinner)findViewById(R.id.spCode);
        etCode=(EditText)findViewById(R.id.etCode);
        get_coupon_code();

        findViewById(R.id.ivCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onCancel();

            }
        });

        findViewById(R.id.btnADD).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(etCode.getText().toString().trim().length() > 0){
                   listener.onSubmit(etCode.getText().toString());
                }else{
                    Log.d(TAG,"selected item : "+strcode);
                    listener.onSubmit(strcode);
                }

            }
        });

    }

    private void get_coupon_code() {
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        Call<ServerResponseCoupon> call = apiInterface.getAllcouponcode();


        call.enqueue(new Callback<ServerResponseCoupon>() {
            @Override
            public void onResponse(Call<ServerResponseCoupon> call, Response<ServerResponseCoupon> response) {
                progressDialog.dismiss();
                if(response.body()!=null) {
                    resultarray = response.body().getResults();
                    spinner1 = new String[resultarray.size()];
                    for(int i=0;i<resultarray.size();i++)
                    {
                        spinner1[i]=resultarray.get(i).getCopouncode();
                    }
                    customSpinnerAdapter3 = new CustomCouponrAdapter(context, resultarray);
                    spCode.setAdapter(customSpinnerAdapter3);
                    spCode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            strcode = resultarray.get(i).getCopouncode().toString();
                            Log.d(TAG,"On selected item :"+strcode);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {
//                            strcode = resultarray.get(0).getCopouncode().toString();
//                            Log.d(TAG,"On selected non item :"+strcode);
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ServerResponseCoupon> call, Throwable t) {
                progressDialog.dismiss();
                t.getMessage();
                String message = t.getMessage();
                Log.e("TAG","error :: "+message);

            }
        });
    }

    public interface AddDiscountDialogClickListener {

        void onSubmit(String code);
        void onCancel();

    }
}

