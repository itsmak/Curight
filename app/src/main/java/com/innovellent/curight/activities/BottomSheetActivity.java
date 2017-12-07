package com.innovellent.curight.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.innovellent.curight.R;
import com.innovellent.curight.adapter.ItemAdapter;
import com.innovellent.curight.model.Item;

import java.util.ArrayList;
import java.util.List;


public class BottomSheetActivity extends AppCompatActivity implements ItemAdapter.ItemListener{

    private static final int SPLASH_TIME_OUT = 5000;
    private SharedPreferences sharedPreferences;
    private Button btnView, btnDialog;
    BottomSheetBehavior behavior;
    private BottomSheetDialog mBottomSheetDialog;
    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet);

        btnView = (Button) findViewById(R.id.btnView);
        btnDialog = (Button) findViewById(R.id.btnDialog);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        btnDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });

        View bottomSheet = findViewById(R.id.bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });


    }


    private void showBottomSheetDialog() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }

        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.sheet, null);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.setListener(null);
    }

    public List<Item> createItems() {

        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.mipmap.ic_launcher, "Item 1"));
        items.add(new Item(R.mipmap.ic_launcher, "Item 2"));
        items.add(new Item(R.mipmap.ic_launcher, "Item 3"));
        items.add(new Item(R.mipmap.ic_launcher, "Item 4"));

        return items;
    }

    @Override
    public void onItemClick(Item item) {
        behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
    }

}
