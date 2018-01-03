package com.innovellent.curight.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.BP;
import com.innovellent.curight.model.BloodSugar;
import com.innovellent.curight.model.BloodSugarDeletePojo;
import com.innovellent.curight.utility.Config;

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

/**
 * Created by SUNIL on 1/3/2018.
 */

public class BloodSugarAdapter extends RecyclerView.Adapter<BloodSugarAdapter.BloodSugarViewHolder> {

    Context context;
    ArrayList<BloodSugar> bloodSugarArrayList = new ArrayList<BloodSugar>();
    BloodSugar bloodSugar;
    ProgressDialog progressDialog;

    public BloodSugarAdapter(Context context, ArrayList<BloodSugar> bloodSugarArrayList) {
        this.context = context;
        this.bloodSugarArrayList = bloodSugarArrayList;

    }

    @Override
    public BloodSugarAdapter.BloodSugarViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_bloodsugar, parent, false);
        return new BloodSugarAdapter.BloodSugarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BloodSugarAdapter.BloodSugarViewHolder holder, final int position) {

        holder.tv_beforemeal.setText("Before Meal:"+bloodSugarArrayList.get(position).getBeforemeal());
        holder.tv_aftermeal.setText("After Meal:"+bloodSugarArrayList.get(position).getAftermeal());
        holder.tv_date.setText(bloodSugarArrayList.get(position).getDate());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bloodSugar=  bloodSugarArrayList.get(position);
                showProgressDialog("Deleting item");
                deletebloodsugarrecord(bloodSugar.getBsid());
                removeAt(position);
            }
        });

    }


    private void removeAt(int position) {
        bloodSugarArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bloodSugarArrayList.size());
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(context, title, "please wait", true, false);
        progressDialog.show();
    }
    @Override
    public int getItemCount() {
        return bloodSugarArrayList.size();
    }

    class BloodSugarViewHolder extends RecyclerView.ViewHolder {

        TextView tv_beforemeal,tv_aftermeal,tv_date;
        ImageView delete;
        public BloodSugarViewHolder(View itemView) {
            super(itemView);

            tv_beforemeal = (TextView)itemView.findViewById(R.id.tv_beforemeal);
            tv_aftermeal = (TextView)itemView.findViewById(R.id.tv_aftermeal);
            delete = (ImageView)itemView.findViewById(R.id.delete_bloodsugar);
            tv_date = (TextView)itemView.findViewById(R.id.tv_date);
        }
    }


    private void deletebloodsugarrecord(int bsid)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        BloodSugarDeletePojo bloodSugarDeletePojo = new BloodSugarDeletePojo(bsid);

        final Call<ResponseBody> call = apiInterface.deletebloodsugardata("abc", bloodSugarDeletePojo);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()) {
                    progressDialog.dismiss();

                    try{

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


    }
}
