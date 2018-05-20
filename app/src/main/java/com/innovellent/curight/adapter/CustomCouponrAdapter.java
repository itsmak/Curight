package com.innovellent.curight.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.CouponRes;

import java.util.ArrayList;

/**
 * Created by Mak on 5/20/2018.
 */

public class CustomCouponrAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    ArrayList<CouponRes> resultarray;

    public CustomCouponrAdapter(Context context, ArrayList<CouponRes> resultarray) {
        this.context = context;
        this.resultarray = resultarray;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return resultarray.size();
    }

    @Override
    public Object getItem(int i) {
        return resultarray.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.custom_spinner_item1, null);
        TextView names = (TextView) view.findViewById(R.id.textview);
        names.setText(resultarray.get(i).getCopouncode());
        return view;
    }
}
