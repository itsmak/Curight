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
import com.innovellent.curight.model.BMIRecord;
import com.innovellent.curight.model.BloodPressure;
import com.innovellent.curight.model.DeleteParameterPojo;
import com.innovellent.curight.model.DoctorList;
import com.innovellent.curight.model.WHR;
import com.innovellent.curight.model.WHR_LIST;
import com.innovellent.curight.model.WHR_LIST_DATE;
import com.innovellent.curight.model.WhrList;
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
 * Created by SUNIL on 12/11/2017.
 */

public class WHRAdapter extends RecyclerView.Adapter<WHRAdapter.MyViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<WHR_LIST_DATE> arraylist_whr_list_dates = new ArrayList<WHR_LIST_DATE>();
    ArrayList<WHR_LIST> arraylist_whr_list = new ArrayList<WHR_LIST>();
    ArrayList<WHR> whrs = new ArrayList<WHR>();
    WHR whr_pojo;
    private WHRAdapter.OnWHRListener listener;
    WHR_LIST_DATE whr_list_date;
    WHR_LIST whr_list;
    ProgressDialog progressDialog;
    Context mContext;




    public WHRAdapter(Context context, ArrayList<WHR> whrs){
        mContext=context;
        this.whrs =whrs;
    }
    /*public WHRAdapter(Context context,ArrayList<WHR_LIST_DATE> arraylist_whr_list_dates,ArrayList<WHR_LIST> arraylist_whr_list) {
        mContext = context;
        this.arraylist_whr_list_dates = arraylist_whr_list_dates;
        this.arraylist_whr_list = arraylist_whr_list;
        //this.listener = listener;
    }*/
    @Override
    public WHRAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_blood_pressure, parent, false);
        return new WHRAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(WHRAdapter.MyViewHolder holder, final int position) {

        /*whrflag = whrlistListArrayList.get(position).getWhrFlag();
        date = whr_list_dateArrayList.get(position).getDate();
        waistcircumference = whrlistListArrayList.get(position).getWaistcircumference();
        hipcircumference = whrlistListArrayList.get(position).getHipcircumference();
        whr = whrlistListArrayList.get(position).getWhr();*/
        //Log.d("whrfinaldata===", whr);

       /* whr_list_date = arraylist_whr_list_dates.get(position);
        whr_list = arraylist_whr_list.get(position);

        Log.d("arraylist_whr_lisdates", ""+arraylist_whr_list_dates.size());
        Log.d("arraylist_whr_list", ""+arraylist_whr_list.size());
        if(arraylist_whr_list_dates.size()>0&&arraylist_whr_list.size()>0){

            holder.tvSysDia.setText("W/H:"+" "+arraylist_whr_list.get(position).getWaistcircumference()+"/"+arraylist_whr_list.get(position).getHipcircumference());
            holder.tvDate.setText(arraylist_whr_list_dates.get(position).getDate());
            holder.tvPulse.setText(String.valueOf(arraylist_whr_list.get(position).getWhr()));
            holder.txt_whrid.setText(String.valueOf(arraylist_whr_list.get(position).getWhrid()));
        }else{
            holder.tvSysDia.setText("");
            holder.tvDate.setText("");
            holder.tvPulse.setText("");
            holder.txt_whrid.setText("");
        }
*/

        holder.tvDate.setText(whrs.get(position).getDate());
        holder.tvSysDia.setText("W/H:"+" "+whrs.get(position).getWaistcircumference()+"/"+whrs.get(position).getHipcircumference());
        holder.tvPulse.setText("WHR:"+" "+whrs.get(position).getWhr_subdata());
        holder.txt_whrid.setText(String.valueOf(whrs.get(position).getWhrid()));




       holder.delete_whr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                whr_pojo = whrs.get(position);
                //Log.d("item_id"+"WHRID", ""+whr_list.getWhrid());
                showProgressDialog("Deleting item");
                DeleteWhrData(whr_pojo.getWhrid());
                removeAt(position);


               // listener.onDelete(arraylist_whr_list.get(position).getWhrid());


            }
        });
    }


    private void removeAt(int position) {
        whrs.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, whrs.size());
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(mContext, title, "please wait", true, false);
        progressDialog.show();
    }



    @Override
    public int getItemCount() {

        return whrs.size();
    }

    public interface OnWHRListener {
        void onDelete(int whrid);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvSysDia;
        TextView tvPulse;
        TextView tvDate,txt_whrid;
        ImageView delete_whr;
        MyViewHolder(View view) {
            super(view);
            tvSysDia = (TextView) view.findViewById(R.id.tvSysDia);
            tvPulse = (TextView) view.findViewById(R.id.tvPulse);
            tvDate=(TextView)view.findViewById(R.id.tv_date);
            delete_whr = (ImageView)view.findViewById(R.id.delete_whr);
            txt_whrid = (TextView)view.findViewById(R.id.txt_whrid);

        }
    }


    private void DeleteWhrData(int whr_list){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try{
            DeleteParameterPojo deleteParameterPojo = new DeleteParameterPojo(whr_list);
            final Call<ResponseBody> call = apiInterface.deleteWhrdata("abc", deleteParameterPojo);

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

                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
