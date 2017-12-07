package com.innovellent.curight.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.CholesterolRecord;

import java.util.ArrayList;
import java.util.List;

public class CholesterolAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int DATE = 0;
    private final int RECORD = 1;
    private final OnCholesterolListener listener;
    private List<Object> objects = new ArrayList<>();
    private Context context;

    public CholesterolAdapter(Context context, List<Object> objects, OnCholesterolListener listener) {
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
    }

    @Override
    public int getItemCount() {
        return objects != null ? objects.size() : 0;
    }

    public interface OnCholesterolListener {
        void onDelete(CholesterolRecord record);
    }

    private class CholesterolViewHolder extends RecyclerView.ViewHolder {

        TextView ldlHdl;
        TextView triglycerides,time;
        ImageView delete;

        CholesterolViewHolder(View view) {
            super(view);
            ldlHdl = (TextView) view.findViewById(R.id.ldl_hdl);
            triglycerides = (TextView) view.findViewById(R.id.triglycerides);
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
