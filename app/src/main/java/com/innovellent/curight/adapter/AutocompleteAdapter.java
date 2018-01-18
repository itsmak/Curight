package com.innovellent.curight.adapter;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filterable;

import com.innovellent.curight.model.Category_List;
import com.innovellent.curight.model.Medicine;

import java.util.ArrayList;

/**
 * Created by Mak on 1/18/2018.
 */

public class AutocompleteAdapter extends ArrayAdapter implements Filterable {

    ArrayList<Category_List> categorylist = new ArrayList<>();
    private Context mContext;

    public AutocompleteAdapter(Context context, ArrayList<Category_List> arrayList) {
        super(context,);
        mContext = context;
        this.categorylist = arrayList;
    }

    @Override
    public int getCount() {
        return categorylist.size();
    }
}
