package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.BloodCount;
import com.innovellent.curight.model.DeleteFctDataPojo;
import com.innovellent.curight.model.FCT;
import com.innovellent.curight.utility.Config;

import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 10/16/2017.
 */

public class FemaleCycleAdapter extends RecyclerView.Adapter<FemaleCycleAdapter.MyViewHolder> {

    int femalecycletrackid;
    FCT item;
    private ArrayList<FCT> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    public FemaleCycleAdapter(Context context,ArrayList<FCT> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public FemaleCycleAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_female_cycle, parent, false);
        return new FemaleCycleAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FemaleCycleAdapter.MyViewHolder holder, final int position) {
       // holder.tvAntiCPP.setText(arrayList.get(position).getAntiCPP());
        item = arrayList.get(position);

        if(arrayList.size()>0) {
            holder.tvDays.setText(item.getNormalperiodduration());
            holder.tvgap_days.setText(item.getGap());
            holder.tvCurrentPeriod.setText(item.getCurrentperioddate());
            holder.tvMissed.setText(item.getMiss());
            holder.tvNoteslabel.setText(item.getNotes());
            holder.txt_reminderdays.setText(item.getReminderdays());
            holder.fctid.setText(String.valueOf(item.getFemalecycletrackid()));

        }else{
            holder.tvDays.setText("");
            holder.tvgap_days.setText("");
            holder.tvCurrentPeriod.setText("");
            holder.tvMissed.setText("");
            holder.tvNoteslabel.setText("");
            holder.txt_reminderdays.setText("");
            holder.fctid.setText(String.valueOf(""));

        }


        femalecycletrackid = item.getFemalecycletrackid();
        Log.d("femalecycletrackid", ""+femalecycletrackid);


        holder.img_deletefctrecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                item = arrayList.get(position);
                deletefctrecord(item.getFemalecycletrackid());
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private void deletefctrecord(int femaletrackid){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try{

            DeleteFctDataPojo deleteFctDataPojo = new DeleteFctDataPojo(femaletrackid);
            final Call<ResponseBody> call = apiInterface.deletefctrecord("abc", deleteFctDataPojo);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
                        try{
                            String res_delete = response.body().string();
                            Log.d("deletefct_datares", res_delete);
                            JSONObject jsonObject = new JSONObject(res_delete);
                            Log.d("deletefct_datajsonres", ""+jsonObject);
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {


        TextView tvDays,tvgap_days,tvCurrentPeriod,tvMissed,tvNoteslabel,txt_reminderdays,fctid;
        ImageView img_deletefctrecord;

        MyViewHolder(View view) {
            super(view);


            tvDays = (TextView)view.findViewById(R.id.tvDays);
            tvgap_days = (TextView)view.findViewById(R.id.tvgap_days);
            tvCurrentPeriod = (TextView)view.findViewById(R.id.tvCurrentPeriod);
            tvMissed = (TextView)view.findViewById(R.id.tvMissed);
            tvNoteslabel = (TextView)view.findViewById(R.id.tvNoteslabel);
            txt_reminderdays = (TextView)view.findViewById(R.id.txt_reminderdays);
            img_deletefctrecord = (ImageView)view.findViewById(R.id.img_deleteitemforfct);
            fctid = (TextView)view.findViewById(R.id.fctid);
        }
    }
}
