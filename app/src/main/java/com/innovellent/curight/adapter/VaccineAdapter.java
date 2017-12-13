package com.innovellent.curight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.innovellent.curight.R;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.fragment.AddRemainder_FRAGMENT_DAILOG;
import com.innovellent.curight.fragment.VaccineFragment;
import com.innovellent.curight.model.JSON_FEED;
import com.innovellent.curight.model.MyServer_Response;
import com.innovellent.curight.model.PostBodyClass;
import com.innovellent.curight.model.Vaccine;
import com.innovellent.curight.model.VaccineList;
import com.innovellent.curight.model.VaccineReminderYearDialog;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by sagar on 9/13/2017.
 */

public class VaccineAdapter  extends RecyclerView.Adapter<VaccineAdapter.MyViewHolder> {

    private ArrayList<Vaccine> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    private final OnItemClickListener listener;
    private final int position;

    RecyclerView remainder_rclrvw;
    VaccineReminderYearDialog vaccineReminderYearDialog;
    private static final String BASE_URL ="http://13.59.209.135:8090/diagnosticAPI/webapi/";
    AddRemainder_FRAGMENT_DAILOG vaccineadddailog;
    public interface OnItemClickListener {

        void onItemClick(Vaccine item,int position);
        void onItemClick_modify(Vaccine myitem, int position);
    }

    // VaccineAdapter mAdapter;
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvVaccine;
        TextView tvDate,duration_txtvw;
        ImageView ivTaken,add_medcn_imgvw;
        LinearLayout   llVaccince,duration_layout;
        MyViewHolder(View view) {
            super(view);
        //    remainder_rclrvw = (RecyclerView) view.findViewById(R.id.remainder_rclrvw);
            duration_layout = (LinearLayout) view.findViewById(R.id.duration_layout);
            tvVaccine = (TextView) view.findViewById(R.id.tvVaccine);
            tvDate = (TextView) view.findViewById(R.id.tv_date);
            duration_txtvw = (TextView) view.findViewById(R.id.duration_txtvw);
            llVaccince=(LinearLayout)view.findViewById(R.id.llVaccince);
            ivTaken=(ImageView)view.findViewById(R.id.ivTaken);
            add_medcn_imgvw =(ImageView)view.findViewById(R.id.add_medcn_imgvw);

        }
    }

    public VaccineAdapter(Context context,ArrayList<Vaccine> arrayList,int position,OnItemClickListener listener) {
        mContext = context;
        this.arrayList = arrayList;
        this.listener = listener;
        this.position = position;
    }

    @Override
    public VaccineAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_vaccine, parent, false);
        return new VaccineAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final VaccineAdapter.MyViewHolder holder, final int position) {

        if(arrayList.get(position).isVisiblity())
        {
            holder.duration_layout.setVisibility(View.VISIBLE);
            holder.llVaccince.setVisibility(View.GONE);
            Log.d("adapter age", String.valueOf(arrayList.get(position).getDuration_age()));
            holder.duration_txtvw.setText(arrayList.get(position).getDuration_age());
        }else{
            holder.duration_layout.setVisibility(View.GONE);
            holder.llVaccince.setVisibility(View.VISIBLE);
            if(position%2==0){
                holder.llVaccince.setBackgroundColor(Color.parseColor("#D9DADD"));

            }else{

            }
            holder.tvVaccine.setText(arrayList.get(position).getVaccinename());
            holder.tvDate.setText(arrayList.get(position).getDuedate());
            Log.d("duedate", String.valueOf(arrayList.get(position).getDate()));
          if(arrayList.get(position).getDate().equals("")){
            holder.ivTaken.setImageResource(R.drawable.multiply);
            }else {
                holder.ivTaken.setImageResource(R.mipmap.ic_right);
            }
        }

        Log.d("adapter visiblity", "adapter"+String.valueOf(arrayList.get(position).isVisiblity()));
        Log.d("adapter doctor", "adapter doctorname"+String.valueOf(arrayList.get(position).getDoctorname()));
        Log.d("adapter details", "adapter comments"+String.valueOf(arrayList.get(position).getComments()));
        Log.d("adapter userid", "adapter userid"+String.valueOf(arrayList.get(position).getUserid()));
        Log.d("adapter vaccineid", "adapter userid"+String.valueOf(arrayList.get(position).getVaccineactivityid()));
        Log.d("adapter vaccineid", "adapter vaccinename"+String.valueOf(arrayList.get(position).getVaccinename()));
      //  Log.d("adapter vaccineid", "adapter vaccinechartid"+String.valueOf(arrayList.get(position).));
        if(position%2==0){
          holder.llVaccince.setBackgroundColor(Color.parseColor("#D9DADD"));

        }else{
            holder.llVaccince.setBackgroundColor(Color.parseColor("#ffffff"));
        }
      holder.add_medcn_imgvw.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
//
              listener.onItemClick(arrayList.get(position),position);
          }
      });
      holder.llVaccince.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {

               listener.onItemClick_modify(arrayList.get(position),position);
           }
       });
    }


    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
