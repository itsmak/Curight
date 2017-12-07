package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.BMIRecord;

import java.util.List;

public class BMIAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int DATE = 0;
    private final int RECORD = 1;
    private OnBMIListener listener;
    private List<Object> objects;
    private Context context;

    public BMIAdapter(Context context, List<Object> objects, OnBMIListener listener) {
        this.context = context;
        this.objects = objects;
        this.listener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return objects.get(position) instanceof String ? DATE : RECORD;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);

        if (viewType == DATE) {
            return new StringViewHolder(inflater.inflate(R.layout.text_row, parent, false));
        }
        return new BMIViewHolder(inflater.inflate(R.layout.bmi_record_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Object item = objects.get(position);

        if (holder instanceof StringViewHolder)
            ((StringViewHolder) holder).text.setText(((String) item));
        else {
            final BMIRecord record = (BMIRecord) item;
            BMIViewHolder bmiViewHolder = ((BMIViewHolder) holder);
            bmiViewHolder.bmi.setText(String.valueOf(record.getBmi()));
            bmiViewHolder.time.setText(String.valueOf(record.getTime()));
            bmiViewHolder.delete.setOnClickListener(new View.OnClickListener() {
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

    public interface OnBMIListener {
        void onDelete(BMIRecord record);
    }

    private class BMIViewHolder extends RecyclerView.ViewHolder {

        TextView bmi;
        TextView time;
        ImageView delete;

        BMIViewHolder(View view) {
            super(view);
            bmi = (TextView) view.findViewById(R.id.bmi);
            delete = (ImageView) view.findViewById(R.id.delete);
            time = (TextView) view.findViewById(R.id.tv_time);
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
