package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.activities.ProfileActivity;

import java.util.ArrayList;

/**
 * Created by sagar on 9/8/2017.
 */

public class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.MyViewHolder> {

    private ArrayList<String> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvProfile;

        MyViewHolder(View view) {
            super(view);
            tvProfile = (TextView) view.findViewById(R.id.tvProfile);

        }
    }

    public TrackAdapter(Context context, ArrayList<String> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public TrackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row_track, parent, false);
        return new TrackAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final TrackAdapter.MyViewHolder holder, final int position) {

        holder.tvProfile.setText(arrayList.get(position).toString());
        holder.tvProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(arrayList.get(position).toString().equals("BMI")){
                    //(mContext(ProfileActivity.class).)
                    ((ProfileActivity)mContext).fragmentChange("BMI");
                }else if(arrayList.get(position).toString().equals("BP")){
                    ((ProfileActivity)mContext).fragmentChange("BP");

                }else if(arrayList.get(position).toString().equals("BP")){
                    ((ProfileActivity)mContext).fragmentChange("BP");

                }else if(arrayList.get(position).toString().equals("BloodSugar")){
                    ((ProfileActivity)mContext).fragmentChange("BloodSugar");
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}


