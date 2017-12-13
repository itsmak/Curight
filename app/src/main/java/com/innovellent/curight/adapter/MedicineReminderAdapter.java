package com.innovellent.curight.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
    private static final String TAG = ".Retro_MainActivity";
    private ChangeReminderPreferenceDialog changeReminderPreferenceDialog;

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvMedicineName1,tvMedicineMeasure,tv_time,tvMorninglabel,tvTimeNoon,tvNoonlabel,tvTimeEvening,tvEveninglabel,tvTimetNight,tvNightlabel;
        ImageView ivmorningtimeChange,ivnoonChange,tvEveningChange,tvNightLabel,deleteBtn,ivMorning,tvNoon,tvEvening,tvNight;

        MyViewHolder(View view) {
            super(view);
            tvMedicineName1 = (TextView) view.findViewById(R.id.tvMedicineName1);
            tvMedicineMeasure = (TextView) view.findViewById(R.id.tvMedicineMeasure);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            tvMorninglabel = (TextView) view.findViewById(R.id.tvMorninglabel);
            tvTimeNoon = (TextView) view.findViewById(R.id.tvTimeNoon);
            tvNoonlabel = (TextView) view.findViewById(R.id.tvNoonlabel);
            tvTimeEvening = (TextView) view.findViewById(R.id.tvTimeEvening);
            tvEveninglabel = (TextView) view.findViewById(R.id.tvEveninglabel);
            tvTimetNight = (TextView) view.findViewById(R.id.tvTimetNight);
            tvNightlabel = (TextView) view.findViewById(R.id.tvNightlabel);
            ivmorningtimeChange=(ImageView)view.findViewById(R.id.ivmorningtimeChange);
            ivnoonChange = (ImageView)view.findViewById(R.id.ivnoonChange);
            tvEveningChange=(ImageView)view.findViewById(R.id.tvEveningChange);
            tvNightLabel =(ImageView)view.findViewById(R.id.tvNightLabel);
            deleteBtn = (ImageView) view.findViewById(R.id.delete_btn);
            ivMorning = (ImageView) view.findViewById(R.id.ivMorning);
            tvNoon = (ImageView) view.findViewById(R.id.tvNoon);
            tvEvening = (ImageView) view.findViewById(R.id.tvEvening);
            tvNight = (ImageView) view.findViewById(R.id.tvNight);
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

        Log.d(TAG,"medicineadapter:"+arrayList);
        holder.tvMedicineName1.setText(arrayList.get(position).getMedicinename());
        holder.tvMedicineMeasure.setText(arrayList.get(position).getMedicinemeasure());
        Log.d(TAG,"medicineadapter:morning"+arrayList.get(position).getMorningtime());
        Log.d(TAG,"medicineadapter:noon"+arrayList.get(position).getNighttime());
        if(arrayList.get(position).getMorningtime().equals(""))
        {
            holder.ivMorning.setImageResource(R.drawable.ic_morning_black);
            holder.tv_time.setTextColor(Color.parseColor("#bbbdbf"));
            holder.tvMorninglabel.setTextColor(Color.parseColor("#bbbdbf"));
            holder.ivmorningtimeChange.setVisibility(View.GONE);
        }else {
            holder.tv_time.setText(arrayList.get(position).getMorningtime());
            holder.ivMorning.setImageResource(R.drawable.ic_morning_blue);
            holder.tv_time.setTextColor(Color.parseColor("#0075b2"));
            holder.tvMorninglabel.setTextColor(Color.parseColor("#0075b2"));
            holder.ivmorningtimeChange.setVisibility(View.VISIBLE);
            if(arrayList.get(position).getMorningmedstatus().equals(""))
            {
                //holder.ivmorningtimeChange.setImageResource(R.drawable.);
            }
        }
        if(arrayList.get(position).getNoontime().equals(""))
        {
            holder.tvNoon.setImageResource(R.drawable.ic_noon_black);
            holder.tvTimeNoon.setTextColor(Color.parseColor("#bbbdbf"));
            holder.tvNoonlabel.setTextColor(Color.parseColor("#bbbdbf"));
            holder.ivnoonChange.setVisibility(View.GONE);
        }else {
            holder.tvTimeNoon.setText(arrayList.get(position).getNoontime());
            holder.tvNoon.setImageResource(R.drawable.ic_noon_blue);
            holder.tvTimeNoon.setTextColor(Color.parseColor("#0075b2"));
            holder.tvNoonlabel.setTextColor(Color.parseColor("#0075b2"));
            holder.ivnoonChange.setVisibility(View.VISIBLE);
        }
        if(arrayList.get(position).getEveningtime().equals(""))
        {
            holder.tvEvening.setImageResource(R.drawable.ic_evening_black);
            holder.tvTimeEvening.setTextColor(Color.parseColor("#bbbdbf"));
            holder.tvEveninglabel.setTextColor(Color.parseColor("#bbbdbf"));
            holder.tvEveningChange.setVisibility(View.GONE);
        }else {
            holder.tvTimeEvening.setText(arrayList.get(position).getEveningtime());
            holder.tvEvening.setImageResource(R.drawable.ic_evening_blue);
            holder.tvTimeEvening.setTextColor(Color.parseColor("#0075b2"));
            holder.tvEveninglabel.setTextColor(Color.parseColor("#0075b2"));
            holder.tvEveningChange.setVisibility(View.VISIBLE);
            if(arrayList.get(position).getMorningmedstatus().equals(""))
            {

            }
        }
        if(arrayList.get(position).getNighttime().equals(""))
        {
            holder.tvNight.setImageResource(R.drawable.ic_evening_black);
            holder.tvTimetNight.setTextColor(Color.parseColor("#bbbdbf"));
            holder.tvNightlabel.setTextColor(Color.parseColor("#bbbdbf"));
            holder.tvNightLabel.setVisibility(View.GONE);
        }else {
            holder.tvTimetNight.setText(arrayList.get(position).getNighttime());
            holder.tvNight.setImageResource(R.drawable.ic_evening_blue);
            holder.tvTimetNight.setTextColor(Color.parseColor("#0075b2"));
            holder.tvNightlabel.setTextColor(Color.parseColor("#0075b2"));
            holder.tvNightLabel.setVisibility(View.VISIBLE);
        }

//        holder.ivmorningtimeChange.setImageResource(R.drawable.ic_skip);
//        holder.tvEveningChange.setImageResource(R.drawable.ic_skip);
//
//        holder.ivmorningtimeChange.setOnClickListener(new View.OnClickListener() {
//             @Override
//             public void onClick(View view) {
//                 changePreference(holder,"morning");
//             }
//         });
//        holder.tvEveningChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                changePreference(holder,"evening");
//            }
//        });

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
