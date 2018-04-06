package com.innovellent.curight.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innovellent.curight.R;
import com.innovellent.curight.adapter.MyOfferingAdapter;
import com.innovellent.curight.adapter.VaccineAdapter;
import com.innovellent.curight.api.ApiInterface;
import com.innovellent.curight.model.Article_FEED;
import com.innovellent.curight.model.MyProfile_Response;
import com.innovellent.curight.model.Offer;
import com.innovellent.curight.model.Post_Body_Article;
import com.innovellent.curight.model.Post_Body_Toggle;
import com.innovellent.curight.model.Registration_Response;
import com.innovellent.curight.model.ServerResponseOffer;
import com.innovellent.curight.model.Vaccine;
import com.innovellent.curight.utility.Config;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ForyouFragment  extends Fragment {

    private static final String TAG = ".Curight";
    ArrayList<Article_FEED> arrayList=new ArrayList<Article_FEED>();
    MyOfferingAdapter myOfferAdapter;
    TextView tvOffer,tvFromDate,tvOfferDescription,tvToDate,tvOfferTitle;
    RecyclerView recyclerView;
    int position;
    ForyouFragment myinstance;
    RelativeLayout container;
    TextView tv_locationtxt,tv_locationsymbl,tvTitle;
    ImageView expandedImageView;
    Context context;

    private ProgressDialog progressDialog;
    private Animator mCurrentAnimator;
    // The system "short" animation time duration, in milliseconds. This
    // duration is ideal for subtle animations or animations that occur
    // very frequently.
    private int mShortAnimationDuration;
    public ForyouFragment() {
    }
    @Override
    public void onAttach(Activity activity){
        super.onAttach(activity);
        context = getActivity();
        Log.d(TAG,"On 1Attach");
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"On 1Create");
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_foryou, container, false);
        Log.d(TAG,"On 1CreateView");
        tv_locationtxt = (TextView) getActivity().findViewById(R.id.tv_locationtxt);
        tv_locationsymbl = (TextView) getActivity().findViewById(R.id.tv_locationsymbl);
        tvTitle = (TextView) getActivity().findViewById(R.id.tvTitle);
        tvTitle.setText("Article");
        tv_locationtxt.setVisibility(View.GONE);
        tv_locationsymbl.setVisibility(View.GONE);
        initClickListner(rootView);
        String articleCategory = Prefs.getString("ArticleCategory","");
        getOffer(articleCategory);
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        return rootView;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG,"On 1Activity Created");
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"On 1Start");
//        String articleCategory = Prefs.getString("ArticleCategory","");
//        getOffer(articleCategory);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"On 1Resume");
    }

    public void initClickListner(View view){

        recyclerView=(RecyclerView)view.findViewById(R.id.recycler_view);
        container=(RelativeLayout)view.findViewById(R.id.container);
        expandedImageView = (ImageView)view.findViewById(R.id.expanded_image);

    }

    public void getOffer(String articleCategory) {
        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        clearData();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        int uid = (int) Prefs.getLong("user_id",0);
        Log.d(TAG,"Shared_profile_uid"+uid);
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Post_Body_Article post_body_article = new Post_Body_Article(1,articleCategory);
        Call<ServerResponseOffer> call = reditapi.getArticle(post_body_article);
        call.enqueue(new Callback<ServerResponseOffer>() {
            @Override
            public void onResponse(Call<ServerResponseOffer> call, Response<ServerResponseOffer> response) {
                progressDialog.dismiss();
                Log.d(TAG,"Article response :"+response);
                Log.d(TAG,"Article response body :"+response.body());
                ServerResponseOffer  serverResponseOffer = response.body();
                int code = serverResponseOffer.getCode();
                if (code==200) {
                    Log.d(TAG,"Article response cd:"+response.body().getCode());
                    arrayList = serverResponseOffer.getResults();
                    Log.d(TAG,"Article size :"+arrayList.size());
                    myOfferAdapter = new MyOfferingAdapter(getActivity(), arrayList, "Y", ForyouFragment.this, position, new MyOfferingAdapter.OnToggleclicklistner() {
                        @Override
                        public void onlikeClick(Article_FEED item_m, int position) {

                        }

                        @Override
                        public void ontoggleClick(Article_FEED item_m, int position) {

                            Long uid = Prefs.getLong("user_id",0);
                            if(uid==0)
                            {
                                Toast.makeText(context, "Please login to toggle it", Toast.LENGTH_SHORT).show();
                            }else {
                                send_togglelist(item_m.getArticleid(),uid);
                            }


                        }

                        @Override
                        public void onshareClick(Article_FEED item_m, int position) {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "CuRight");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "You can check our Article by visiting :- http://www.innovellent.com/ ");
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        }

                    });
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                    recyclerView.setAdapter(myOfferAdapter);
                }
            }

            @Override
            public void onFailure(Call<ServerResponseOffer> call, Throwable t) {
                t.getMessage();
                String s = t.getMessage();
                progressDialog.dismiss();
            }
        });

    }
    public void zoomOut(final View thumbView,final String imgurl){
        recyclerView.setVisibility(View.GONE);
        zoomImageFromThumb(thumbView, imgurl);

    }


    private void zoomImageFromThumb(final View thumbView,String url) {
        // If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Load the high-resolution "zoomed-in" image.

        Glide.with(getActivity()).load(new Config().IMAGE_URL+url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(expandedImageView);

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        thumbView.getGlobalVisibleRect(startBounds);
        container.getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        thumbView.setAlpha(0f);
        expandedImageView.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
        expandedImageView.setPivotX(0f);
        expandedImageView.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat(expandedImageView, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat(expandedImageView, View.SCALE_X,
                        startScale, 1f)).with(ObjectAnimator.ofFloat(expandedImageView,
                View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;


        final float startScaleFinal = startScale;
        expandedImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat(expandedImageView, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.Y,startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat(expandedImageView,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        thumbView.setAlpha(1f);
                        recyclerView.setVisibility(View.VISIBLE);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                        thumbView.setAlpha(1f);
                        expandedImageView.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    public void clearData() {
        myOfferAdapter = new MyOfferingAdapter(context, arrayList, "Y", ForyouFragment.this, position, new MyOfferingAdapter.OnToggleclicklistner() {

            @Override
            public void onlikeClick(Article_FEED item_m, int position) {

            }

            @Override
            public void ontoggleClick(Article_FEED item_m, int position) {

            }

            @Override
            public void onshareClick(Article_FEED item_m, int position) {

            }
        });
        arrayList.clear(); //clear list
        myOfferAdapter.notifyDataSetChanged();//let your adapter know about the changes and reload view.

    }
    private void send_togglelist(int item_m, Long uid) {

        progressDialog = ProgressDialog.show(context, "Loading", "please wait", true, false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(new Config().SERVER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiInterface reditapi = retrofit.create(ApiInterface.class);
        Log.d(TAG,"toggle aricleid :"+item_m);
        Log.d(TAG,"toggle userid :"+uid);
        Post_Body_Toggle post_body_toggle = new Post_Body_Toggle(uid,item_m);
        Call<Registration_Response> call = reditapi.sendToggle(post_body_toggle);
        call.enqueue(new Callback<Registration_Response>() {
            @Override
            public void onResponse(Call<Registration_Response> call, Response<Registration_Response> response1) {
                progressDialog.dismiss();
                Log.d(TAG,"toggle response :"+response1);
                Log.d(TAG,"toggle response body :"+response1.body());
                int code = response1.body().getCode();
                Log.d(TAG,"toggle response body :"+response1.body());
                if (code==200) {
                    Toast.makeText(context, "Successfully Updated", Toast.LENGTH_SHORT).show();
                    String articleCategory = Prefs.getString("ArticleCategory","");
                    getOffer(articleCategory);
                }
            }
            @Override
            public void onFailure(Call<Registration_Response> call, Throwable t) {
                t.getMessage();
                String s = t.getMessage();
                progressDialog.dismiss();
            }
        });


    }

}
