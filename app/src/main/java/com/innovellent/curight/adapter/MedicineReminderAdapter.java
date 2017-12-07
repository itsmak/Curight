package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.system.Os;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.innovellent.curight.R;
import com.innovellent.curight.model.ChangeReminderPreferenceDialog;
import com.innovellent.curight.model.Medicine;

import java.util.ArrayList;


public class MedicineReminderAdapter extends RecyclerView.Adapter<MedicineReminderAdapter.MyViewHolder> {

    private ArrayList<Medicine> arrayList = new ArrayList<>();
    private Context mContext;
    private String state;
    private TextView tvTime;
    private ImageView ivmorningtimeChange;
    private ChangeReminderPreferenceDialog changeReminderPreferenceDialog;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvMedicineName1,tvMedicineMeasure;
        ImageView ivmorningtimeChange,tvEveningChange,deleteBtn;

        MyViewHolder(View view) {
            super(view);
            tvMedicineName1 = (TextView) view.findViewById(R.id.tvMedicineName1);
            tvMedicineMeasure = (TextView) view.findViewById(R.id.tvMedicineMeasure);
            ivmorningtimeChange=(ImageView)view.findViewById(R.id.ivmorningtimeChange);
            tvEveningChange=(ImageView)view.findViewById(R.id.tvEveningChange);
            deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);
        }
    }

    public MedicineReminderAdapter(Context context,ArrayList<Medicine> arrayList) {
        mContext = context;
        this.arrayList = arrayList;

    }

    @Override
    public MedicineReminderAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_medicine_reminder, parent, false);
        return new MedicineReminderAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MedicineReminderAdapter.MyViewHolder holder, final int position) {

        holder.tvMedicineName1.setText(arrayList.get(position).getMedicinename());
        holder.tvMedicineMeasure.setText(arrayList.get(position).getMedicinemeasure());

        holder.ivmorningtimeChange.setImageResource(R.drawable.ic_skip);
        holder.tvEveningChange.setImageResource(R.drawable.ic_skip);

        holder.ivmorningtimeChange.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 changePreference(holder,"morning");
             }
         });
        holder.tvEveningChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePreference(holder,"evening");
            }
        });

        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position);
            }
        });

    }
    private void changePreference(final MedicineReminderAdapter.MyViewHolder holder,final String type) {
        changeReminderPreferenceDialog = new ChangeReminderPreferenceDialog(mContext, new ChangeReminderPreferenceDialog.ChangeReminderPreferenceDialogClickListener(){


            @Override
            public void onSubmit(String change) {
                changeReminderPreferenceDialog.dismiss();

                if(type.equals("morning")) {
                    if (change.equals("take")) {
                        holder.ivmorningtimeChange.setImageResource(R.drawable.ic_right);


                    } else if (change.equals("close")) {
                        holder.ivmorningtimeChange.setVisibility(View.GONE);

                    } else if (change.equals("skip")) {

                        holder.ivmorningtimeChange.setImageResource(R.drawable.ic_skip);


                    }


                }else if(type.equals("evening")){
                    if (change.equals("take")) {
                        holder.tvEveningChange.setImageResource(R.drawable.ic_right);


                    } else if (change.equals("close")) {
                        holder.tvEveningChange.setVisibility(View.GONE);

                    } else if (change.equals("skip")) {

                        holder.tvEveningChange.setImageResource(R.drawable.ic_skip);


                    }

                }

            }
        });

        changeReminderPreferenceDialog.show();


    }

    private void removeAt(int position) {
        arrayList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, arrayList.size());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
}
