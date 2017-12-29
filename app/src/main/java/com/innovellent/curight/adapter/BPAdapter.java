package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.BloodPressureRecord;

import java.util.ArrayList;
import java.util.List;

public class BPAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int DATE = 0;
    private final int RECORD = 1;
    private final OnBloodPressureListener listener;

    private List<Object> objects = new ArrayList<>();
    private Context context;
    private int position;

    public BPAdapter(Context context, List<Object> arrayList,int position, OnBloodPressureListener listener) {
        this.context = context;
        this.objects = arrayList;
        this.listener = listener;
        this.position = position;
    }

    @Override
    public int getItemViewType(int position) {
        return objects.get(position) instanceof String ? DATE : RECORD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == 0) {
            return new StringViewHolder(inflater.inflate(R.layout.text_row, parent, false));
        }
        return new BloodPressureViewHolder(inflater.inflate(R.layout.blood_pressure_record_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Object item = objects.get(position);

        if (holder instanceof StringViewHolder)
            ((StringViewHolder) holder).text.setText(((String) item));
        else {
            final BloodPressureRecord record = (BloodPressureRecord) item;
            BloodPressureViewHolder bloodPressureViewHolder = ((BloodPressureViewHolder) holder);
            bloodPressureViewHolder.systolicDiastolic.setText(context.getString(R.string.systolic_diastolic_formatted, record.getSystolic(), record.getDiastolic()));
            bloodPressureViewHolder.pulse.setText(String.valueOf(record.getPulse()));
            bloodPressureViewHolder.time.setText(String.valueOf(record.getTime()));
            bloodPressureViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onDelete(record);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return objects != null ? objects.size() : 0;
    }

    public interface OnBloodPressureListener {
        void onDelete(BloodPressureRecord record);
    }

    public void ListClear(){

        objects.clear();
        notifyDataSetChanged();
    }
    private class BloodPressureViewHolder extends RecyclerView.ViewHolder {

        TextView systolicDiastolic, pulse, time;
        ImageView delete;

        BloodPressureViewHolder(View view) {
            super(view);
            systolicDiastolic = (TextView) view.findViewById(R.id.tvSysDia);
            pulse = (TextView) view.findViewById(R.id.tvPulse);
            time = (TextView) view.findViewById(R.id.tv_time);
            delete = (ImageView) view.findViewById(R.id.delete);
        }
    }

    private class StringViewHolder extends RecyclerView.ViewHolder {
        TextView text;

        StringViewHolder(View view) {
            super(view);
            text = (TextView) view;
        }
    }


}
