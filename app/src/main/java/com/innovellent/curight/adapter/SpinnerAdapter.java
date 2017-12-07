package com.innovellent.curight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovellent.curight.R;

import java.util.ArrayList;

/**
 * Created by sagar on 10/9/2017.
 */

public class SpinnerAdapter extends BaseAdapter {
    ArrayList<String> payments;
    Context mContext;
    String type;
    LayoutInflater layoutInflater;

    public SpinnerAdapter(ArrayList<String> state,Context context) {
        payments = state;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        //return states.length;
        return payments.size();//>0 ? payments.size()-1 : payments.size() ;
    }

    @Override
    public Object getItem(int position) {
        return payments.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {

                convertView = layoutInflater.inflate(R.layout.list_row_dropdown, parent, false);

        }
        try {
            TextView textView = (TextView) convertView.findViewById(R.id.txtComplaints);
            textView.setText(payments.get(position).toString());

        }catch (Exception e){

        }
        return convertView;
    }

}


