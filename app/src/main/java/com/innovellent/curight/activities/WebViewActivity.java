package com.innovellent.curight.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.innovellent.curight.R;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by Mak on 4/18/2018.
 */

public class WebViewActivity extends AppCompatActivity{
    private static final String TAG = "Curight";
    WebView webview;
    ImageView imageView_res;
    ProgressBar progressbar;
    String resurcetype,resurceurl;
    PhotoViewAttacher photoview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        webview = (WebView) findViewById(R.id.webview);
        progressbar = (ProgressBar) findViewById(R.id.progressbar);
        imageView_res = (ImageView) findViewById(R.id.imageView_res);

        resurcetype = Prefs.getString("resurcetype","");
        resurceurl = Prefs.getString("resurceurl","");
        imageView_res.setOnTouchListener(
                new ImageView.OnTouchListener() {
                    public boolean onTouch(View v,
                                           MotionEvent m) {
                        photoview = new PhotoViewAttacher(imageView_res);
                        return true;
                    }
                }
        );
        if(resurcetype.equals("pdf"))
        {
            webview.setVisibility(View.VISIBLE);
            progressbar.setVisibility(View.VISIBLE);
            imageView_res.setVisibility(View.GONE);

            webview.getSettings().setJavaScriptEnabled(true);
            webview.loadUrl("http://docs.google.com/gview?embedded=true&url=" +resurceurl);
            webview.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    // do your stuff here
                    progressbar.setVisibility(View.GONE);
                }
            });
        }else if(resurcetype.equals("image"))
        {
            webview.setVisibility(View.GONE);
            progressbar.setVisibility(View.GONE);
            imageView_res.setVisibility(View.VISIBLE);
            Log.d(TAG,"resource url :"+resurceurl);
            Picasso.with(WebViewActivity.this).load(resurceurl).into(imageView_res);
        }

       // String filename ="http://103.87.129.162:8080/diagnosticAPI/images/patientreports/1.pdf";




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
