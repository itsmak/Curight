package com.innovellent.curight.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.BMI;
import com.innovellent.curight.model.BMIRecord;
import com.innovellent.curight.model.DeleteBMIRecord;
import com.innovellent.curight.utility.Config;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class BMIAdapter extends RecyclerView.Adapter<BMIAdapter.BMIViewHolder> {

    private final int DATE = 0;
    private final int RECORD = 1;
    private List<Object> objects;
     Context context;
    ArrayList<BMI> bmiArrayList = new ArrayList<BMI>();
    BMI bmi;
    ProgressDialog progressDialog;

    public BMIAdapter(Context context,ArrayList<BMI> bmis) {
        this.context = context;
        this.bmiArrayList = bmis;

    }



    /*@Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == DATE) {
            return new StringViewHolder(inflater.inflate(R.layout.text_row, parent, false));
        }
        return new BMIViewHolder(inflater.inflate(R.layout.bmi_record_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Object item = objects.get(position);

        if (holder instanceof StringViewHolder)
            ((StringViewHolder) holder).text.setText(((String) item));
        else {
            final BMIRecord record = (BMIRecord) item;
            BMIViewHolder bmiViewHolder = ((BMIViewHolder) holder);
            bmiViewHolder.bmi.setText(String.valueOf(record.getBmi()));
            bmiViewHolder.time.setText(String.valueOf(record.getTime()));
            bmiViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(record);
                }
            });
        }

    }*/

    @Override
    public BMIAdapter.BMIViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bmi_record_row, parent, false);
        return new BMIAdapter.BMIViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BMIViewHolder holder, final int position) {

        holder.tvDate.setText(bmiArrayList.get(position).getDate());
        holder.bmi.setText("H/W:"+" "+bmiArrayList.get(position).getHeight()+"/"+bmiArrayList.get(position).getWeight());
        holder.tvBmivalue.setText("BMI:"+" "+bmiArrayList.get(position).getBmi());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bmi = bmiArrayList.get(position);
                //Log.d("item_id"+"WHRID", ""+whr_list.getWhrid());
                showProgressDialog("Deleting item");
                deleteBMIRecord(bmi.getBmiid());
                removeAt(position);
            }
        });

    }


    private void removeAt(int position) {
        bmiArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bmiArrayList.size());
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(context, title, "please wait", true, false);
        progressDialog.show();
    }

    @Override
    public int getItemCount() {
        return bmiArrayList.size();
    }



    public class BMIViewHolder extends RecyclerView.ViewHolder {

        TextView bmi,tvDate,tvBmivalue;
        ImageView delete;

        BMIViewHolder(View view) {
            super(view);
            bmi = (TextView) view.findViewById(R.id.bmi);
            delete = (ImageView) view.findViewById(R.id.delete);
            tvDate = (TextView)view.findViewById(R.id.tv_date);
            tvBmivalue = (TextView)view.findViewById(R.id.tvBmivalue);
        }
    }


    private void deleteBMIRecord(int bmiid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try{

            DeleteBMIRecord deleteBMIRecord = new DeleteBMIRecord(bmiid);

            final Call<ResponseBody> call = apiInterface.deleteBMIRecord("abc",deleteBMIRecord);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        progressDialog.dismiss();
                        try {
                            String res_delete = response.body().string();

                            JSONObject jsonObject = new JSONObject(res_delete);

                            String results = jsonObject.getString("Results");

                            if(results.equals("Success")){
                                Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                progressDialog.dismiss();
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }



    }


}
