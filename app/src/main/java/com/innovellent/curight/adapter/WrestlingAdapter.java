package com.innovellent.curight.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.DeleteExercizeRecord_Pojo;
import com.innovellent.curight.model.Softball;
import com.innovellent.curight.model.Weightlifting;
import com.innovellent.curight.model.Wrestling;
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

/**
 * Created by Mak on 3/25/2018.
 */

public class WrestlingAdapter extends RecyclerView.Adapter<WrestlingAdapter.MyViewHolder> {

    Wrestling runing;
    ProgressDialog progressDialog;
    private ArrayList<Wrestling> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    private int rowlayout;
    private LinearLayout llAmount;

    public WrestlingAdapter(Context context,int  rowlayout,ArrayList<Wrestling> arrayList) {
        mContext = context;
        this.arrayList = arrayList;
        this.rowlayout=rowlayout;

    }

    @Override
    public WrestlingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowlayout, parent, false);
        return new WrestlingAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final WrestlingAdapter.MyViewHolder holder, final int position) {

        holder.tvTestName.setText(arrayList.get(position).getExercisetype()+"");
        holder.tvHour.setText(arrayList.get(position).getExercisetime()+"");
        holder.tvDistance.setText(arrayList.get(position).getDistancecovered()+"");
        if((position%2)==0){

        }else{
            holder.llLayout.setBackgroundColor(ContextCompat.getColor(mContext, R.color.gray));
            //  holder.tvUnit.setText("23.2");
        }
        holder.iv_exercise_delt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runing = arrayList.get(position);
                //Log.d("item_id"+"WHRID", ""+whr_list.getWhrid());
                showProgressDialog("Deleting item");
                deleteExercizeRecord(runing.getExerciseid());
                removeAt(position);
            }
        });

    }

    private void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(mContext, title, "please wait", true, false);
        progressDialog.show();
    }

    private void deleteExercizeRecord(long exercizeid) {


        int ex_id = (int) exercizeid;
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try{

            DeleteExercizeRecord_Pojo deleteBMIRecord = new DeleteExercizeRecord_Pojo(ex_id);

            final Call<ResponseBody> call = apiInterface.deleteExerciseRecord("abc",deleteBMIRecord);

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
                                Toast.makeText(mContext, "Successfully Deleted", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvTestName,tvHour,tvDistance,tvUnit;
        LinearLayout llLayout;
        ImageView iv_exercise_delt;

        MyViewHolder(View view) {
            super(view);
            tvTestName = (TextView) view.findViewById(R.id.tvTestName);
            tvDistance=(TextView) view.findViewById(R.id.tvDistance);
            tvUnit=(TextView) view.findViewById(R.id.tvUnit);
            tvHour=(TextView)view.findViewById(R.id.tvHour);
            iv_exercise_delt = (ImageView) view.findViewById(R.id.iv_exercise_delt);
            llLayout=(LinearLayout)view.findViewById(R.id.llLayout);
        }
    }
}

