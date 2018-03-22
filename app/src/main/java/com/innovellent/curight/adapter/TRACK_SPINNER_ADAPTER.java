package com.innovellent.curight.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.innovellent.curight.R;
import com.innovellent.curight.model.PROFILE;

import java.util.ArrayList;

/**
 * Created by Mak on 12/5/2017.
 */

public class TRACK_SPINNER_ADAPTER extends BaseAdapter {

    Context context;
    LayoutInflater inflter;
    private ArrayList<PROFILE> spinner2;

    public TRACK_SPINNER_ADAPTER(Context context, ArrayList<PROFILE> spinner2) {
        this.context = context;
        this.spinner2 = spinner2;
        inflter = (LayoutInflater.from(context));

    }

    @Override
    public int getCount() {
        return spinner2.size();
    }

    @Override
    public Object getItem(int i) {
        return spinner2.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.track_custom_spinner, null);
        TextView names = (TextView) view.findViewById(R.id.name_textview);
        TextView relation = (TextView) view.findViewById(R.id.relation_textview);
        Log.e("main", "adapterprofileResponse: code: " + spinner2.get(i).getUser_relation());
        names.setText(spinner2.get(i).getUser_name());
        relation.setText(spinner2.get(i).getUser_relation());
        return view;
    }
}
