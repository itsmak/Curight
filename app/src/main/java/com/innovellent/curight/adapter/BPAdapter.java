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
import com.innovellent.curight.model.BloodPressureRecord;
import com.innovellent.curight.model.DeleteBPRecordParameter;
import com.innovellent.curight.model.ServerResponse;
import com.innovellent.curight.model.WHR;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

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

import static com.innovellent.curight.utility.Constants.CURIGHT_TAG;

public class BPAdapter extends RecyclerView.Adapter<BPAdapter.BloodPressureViewHolder> {

    private final int DATE = 0;
    private final int RECORD = 1;
    ArrayList<BP> bpArrayList = new ArrayList<BP>();
    BP bp;
    private List<Object> objects = new ArrayList<>();
     Context context;
    private int position;
    ProgressDialog progressDialog;
    public BPAdapter(Context context, ArrayList<BP> bpArrayList) {
        this.context = context;
        this.bpArrayList = bpArrayList;

    }


    @Override
    public BPAdapter.BloodPressureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.blood_pressure_record_row, parent, false);
        return new BPAdapter.BloodPressureViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BPAdapter.BloodPressureViewHolder holder, final int position) {

        holder.tvdate.setText(bpArrayList.get(position).getDate());
        holder.systolicDiastolic.setText(String.valueOf(bpArrayList.get(position).getSystolic())+"/"+String.valueOf(bpArrayList.get(position).getDiastolic()));
        holder.pulse.setText(String.valueOf(bpArrayList.get(position).getPulse()));
        holder.time.setText(bpArrayList.get(position).getTime());


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bp = bpArrayList.get(position);
                showProgressDialog("Deleting item");
                deleteBloodPressureRecord(bp.getBpid());
                removeAt(position);

            }
        });

    }


    private void removeAt(int position) {
        bpArrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bpArrayList.size());
    }

    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(context, title, "please wait", true, false);
        progressDialog.show();
    }

    @Override
    public int getItemCount() {
        return bpArrayList.size();
    }



     class BloodPressureViewHolder extends RecyclerView.ViewHolder {

        TextView systolicDiastolic, pulse, time,tvdate;
        ImageView delete;

        BloodPressureViewHolder(View view) {
            super(view);
            systolicDiastolic = (TextView) view.findViewById(R.id.tvSysDia);
            pulse = (TextView) view.findViewById(R.id.tvPulse);
            time = (TextView) view.findViewById(R.id.tv_time);
            delete = (ImageView) view.findViewById(R.id.delete);
            tvdate =(TextView)view.findViewById(R.id.tv_date);
        }
    }


    private void deleteBloodPressureRecord(int bpid) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try {
            DeleteBPRecordParameter deleteBPRecordParameter = new DeleteBPRecordParameter(bpid);
            final Call<ResponseBody> call = apiInterface.deleteBloodPressureRecord("abc", deleteBPRecordParameter);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if(response.isSuccessful()){
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

                }
            });
            /*JSONObject paramObject = new JSONObject();
            paramObject.put("bpid", record.getBpId());

            Call<ServerResponse<String>> call = apiInterface.deleteBloodPressureRecord("abc", paramObject.toString());
            call.enqueue(new Callback<ServerResponse<String>>() {
                @Override
                public void onResponse(Call<ServerResponse<String>> call, Response<ServerResponse<String>> response) {
                    if (getActivity() != null) {

                        progressDialog.dismiss();
                        if (response.isSuccessful()) {

                            ServerResponse<String> serverResponse = response.body();
                            if (serverResponse.getResults().equals("Success")) {
                                Toast.makeText(getActivity(), "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                showProgressDialog("Loading");
                                int uid = (int) Prefs.getLong("user_id",0);
                                getBloodPressureRecords(uid);
                            } else {
                                Toast.makeText(getActivity(), "please try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<ServerResponse<String>> call, Throwable t) {
                    if (getActivity() != null) progressDialog.dismiss();
                    Log.d(CURIGHT_TAG, t.getMessage());
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
