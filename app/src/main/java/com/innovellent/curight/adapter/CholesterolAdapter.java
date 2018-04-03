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
import com.innovellent.curight.model.Cholesterol;
import com.innovellent.curight.model.CholesterolRecord;
import com.innovellent.curight.model.DeleteCholesterolRecordParameter;
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

public class CholesterolAdapter extends RecyclerView.Adapter<CholesterolAdapter.CholesterolViewHolder> {

    private final int DATE = 0;
    private final int RECORD = 1;
    ArrayList<Cholesterol> cholesterols = new ArrayList<Cholesterol>();
    Cholesterol cholesterol;
     Context context;
    ProgressDialog progressDialog;
    private List<Object> objects = new ArrayList<>();

    public CholesterolAdapter(Context context, ArrayList<Cholesterol> cholesterolArrayList) {
        this.context = context;
        this.cholesterols = cholesterolArrayList;
    }


/*
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == 0) {
            return new StringViewHolder(inflater.inflate(R.layout.text_row, parent, false));
        }
        return new CholesterolViewHolder(inflater.inflate(R.layout.cholesterol_record_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Object item = objects.get(position);

        if (holder instanceof StringViewHolder)
            ((StringViewHolder) holder).text.setText(((String) item));
        else {
            final CholesterolRecord record = (CholesterolRecord) item;
            CholesterolViewHolder cholesterolViewHolder = ((CholesterolViewHolder) holder);
            cholesterolViewHolder.ldlHdl.setText(context.getString(R.string.systolic_diastolic_formatted, record.getLdl(), record.getHdl()));
            cholesterolViewHolder.triglycerides.setText(String.valueOf(record.getTriglycerides()));
            cholesterolViewHolder.time.setText(String.valueOf(record.getTime()));
            cholesterolViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(record);
                }
            });
        }
    }*/


    @Override
    public CholesterolAdapter.CholesterolViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cholesterol_record_row, parent, false);
        return new CholesterolAdapter.CholesterolViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CholesterolAdapter.CholesterolViewHolder holder, final int position) {

        holder.tv_date.setText(cholesterols.get(position).getDate());
        holder.ldlHdl.setText(String.valueOf("HDL/LDL:"+cholesterols.get(position).getHdl())+"/"+String.valueOf(cholesterols.get(position).getLdl()));
        holder.time.setText(cholesterols.get(position).getTime());
        holder.triglycerides.setText(String.valueOf(cholesterols.get(position).getTriglycerides()));


        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cholesterol = cholesterols.get(position);
                showProgressDialog("Deleting item");
                deleteCholesterolRecord(cholesterol.getCholestrolid());
                removeAt(position);
            }
        });

    }

    private void removeAt(int position) {
        cholesterols.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, cholesterols.size());
    }


    private void showProgressDialog(String title) {
        progressDialog = ProgressDialog.show(context, title, "please wait", true, false);
        progressDialog.show();
    }

    @Override
    public int getItemCount() {
        return cholesterols.size();
    }

    private void deleteCholesterolRecord(int cholestrolid) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface = retrofit.create(ApiInterface.class);

        try{

            DeleteCholesterolRecordParameter deleteCholesterolRecordParameter = new DeleteCholesterolRecordParameter(cholestrolid);
            final Call<ResponseBody> call = apiInterface.deleteCholesterolRecord("abc", deleteCholesterolRecordParameter);

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
        }catch (Exception e){
            e.printStackTrace();
        }




    }

    public class CholesterolViewHolder extends RecyclerView.ViewHolder {

        TextView ldlHdl;
        TextView triglycerides,time,tv_date;
        ImageView delete;

        CholesterolViewHolder(View view) {
            super(view);
            ldlHdl = (TextView) view.findViewById(R.id.ldl_hdl);
            triglycerides = (TextView) view.findViewById(R.id.triglycerides);
            delete = (ImageView) view.findViewById(R.id.delete);
            time = (TextView) view.findViewById(R.id.tv_time);
            tv_date = (TextView)view.findViewById(R.id.tv_date);
        }
    }


}
