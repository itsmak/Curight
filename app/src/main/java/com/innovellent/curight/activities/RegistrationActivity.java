package com.innovellent.curight.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.innovellent.curight.MainActivity;
import com.innovellent.curight.R;

/**
 * Created by sagar on 9/15/2017.
 */

public class RegistrationActivity extends Activity {
    private Button btContinue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registraiton);
        btContinue=(Button)findViewById(R.id.etContinue);

        btContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i1);
            }
        });
    }
}

